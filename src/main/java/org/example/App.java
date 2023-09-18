package org.example;

import jakarta.persistence.criteria.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import org.apache.log4j.Logger;
import org.example.models.*;

import java.util.List;

/**
 * Hello world!
 */
public class App {
    private static final Logger LOGGER = Logger.getLogger(App.class);
    public static void main(String[] args) {
        LOGGER.info("start application");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("osbbPersistenceUnit");
        EntityManager em = emf.createEntityManager();

        selectMembersNoAutoAllowedByCriteriaApi(em);

        em.close();
        emf.close();
        LOGGER.info("stop application");
    }

    private static void selectMembersNoAutoAllowedByCriteriaApi(EntityManager em) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);

        /*
        select m.fullname, m.email, b.address, a.square, a.number
        from member as m
        join apartment as a on m.id = a.member_id
        left join resident as r on m.resident_id = r.id
        join building as b on a.building_id = b.id
        where r.entercar = false and r.id is not null
        group by m.fullname, m.email, b.address, a.square, a.number
        having count(a.id) < 2;
        */

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
