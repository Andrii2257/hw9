package org.example.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.example.models.Apartment;
import org.example.models.Building;
import org.example.models.Member;
import org.example.models.Resident;

import java.util.List;

public class JpaService {
    public static void selectMembersNoAutoAllowedByCriteriaApi(EntityManager em) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);

        Root<Member> memberRoot = criteriaQuery.from(Member.class);
        Join<Member, Apartment> apartmentJoin = memberRoot.join("apartments");
        Join<Member, Resident> residentJoin = memberRoot.join("resident", JoinType.LEFT);
        Join<Apartment, Building> buildingJoin = apartmentJoin.join("building");

        Predicate condition = criteriaBuilder.and(
                criteriaBuilder.equal(residentJoin.get("enterCar"), false),
                criteriaBuilder.isNotNull(residentJoin.get("id"))
        );

        criteriaQuery.multiselect(
                memberRoot.get("fullName"),
                memberRoot.get("email"),
                buildingJoin.get("address"),
                apartmentJoin.get("square"),
                apartmentJoin.get("number")
        );

        criteriaQuery.where(condition);

        criteriaQuery.groupBy(
                memberRoot.get("fullName"),
                memberRoot.get("email"),
                buildingJoin.get("address"),
                apartmentJoin.get("square"),
                apartmentJoin.get("number")
        );

        ParameterExpression<Integer> countApartments = criteriaBuilder.parameter(Integer.class);

        criteriaQuery.having(criteriaBuilder.lt(
                criteriaBuilder.count(apartmentJoin.get("id")),
                countApartments)
        );

        TypedQuery<Object[]> query = em.createQuery(criteriaQuery);
        query.setParameter(countApartments, 2);

        List<Object[]> resultList = query.getResultList();

        for (Object[] fields
                : resultList) {
            System.out.println(fields[0] + ", " +
                    fields[1] + ", " +
                    fields[2] + ", " +
                    fields[3] + ", " +
                    fields[4]
            );
        }
    }
}
