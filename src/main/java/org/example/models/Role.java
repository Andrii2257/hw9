package org.example.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 45, name = "role_name")
    private String roleName;
    @ManyToMany(mappedBy = "roles", cascade = CascadeType.ALL)
    private List<Member> members = new ArrayList<>();

    public Role() {
    }

    public int getId() {
        return id;
    }

    public String getRoleName() {
        return roleName;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
//                ", members=" + members +
                '}';
    }
}
