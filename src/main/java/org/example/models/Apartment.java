package org.example.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Apartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int number;
    private byte rooms;
    private float square;
    private byte floor;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "building_id")
    private Building building;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private Member member;
    @OneToMany(mappedBy = "apartment", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Resident> residents = new ArrayList<>();

    public int getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public byte getRooms() {
        return rooms;
    }

    public float getSquare() {
        return square;
    }

    public byte getFloor() {
        return floor;
    }

    public Building getBuilding() {
        return building;
    }

    public Member getMember() {
        return member;
    }

    public List<Resident> getResidents() {
        return residents;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setRooms(byte rooms) {
        this.rooms = rooms;
    }

    public void setSquare(float square) {
        this.square = square;
    }

    public void setFloor(byte floor) {
        this.floor = floor;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    @Override
    public String toString() {
        return "Apartment{" +
                "id=" + id +
                ", number=" + number +
                ", rooms=" + rooms +
                ", square=" + square +
                ", floor=" + floor +
//                ", building=" + building +
//                ", member=" + member +
//                ", residents=" + residents +
                '}';
    }
}
