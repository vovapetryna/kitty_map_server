package com.example.numo.entities.postgres;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Table(name = "users")
@Data
public class User {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int age;
    @Column(unique = true, nullable = false)
    private String email;
    private Integer status;

//    @OneToMany
//    List<Possession> possessionList;


//    public List<Possession> getPossessionList() {
//        return possessionList;
//    }
//
//    public void setPossessionList(List<Possession> possessionList) {
//        this.possessionList = possessionList;
//    }

}
