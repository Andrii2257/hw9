package org.example.models;


import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100)
    private String address;

    private byte levels;

    private byte entrances;
    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Apartment> apartments = new ArrayList<>();

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public byte getLevels() {
        return levels;
    }

    public byte getEntrances() {
        return entrances;
    }

    public List<Apartment> getApartments() {
        return apartments;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLevels(byte levels) {
        this.levels = levels;
    }

    public void setEntrances(byte entrances) {
        this.entrances = entrances;
    }

    @Override
    public String toString() {
        return "Building{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", levels=" + levels +
                ", entrances=" + entrances +
                ", apartments=" + apartments +
                '}';
    }
}
