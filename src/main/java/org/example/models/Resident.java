package org.example.models;

import jakarta.persistence.*;

@Entity
public class Resident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(length = 100)
    private String fullName;
    private boolean enterCar;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "apartment_id")
    private Apartment apartment;
    @OneToOne(mappedBy = "resident", cascade = CascadeType.ALL)
    private Member member;


    public Resident() {
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isEnterCar() {
        return enterCar;
    }

    public void setEnterCar(boolean enterCar) {
        this.enterCar = enterCar;
    }

    @Override
    public String toString() {
        return "Resident{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", enterCar=" + enterCar +
 //               ", apartment=" + apartment +
                '}';
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }
}
