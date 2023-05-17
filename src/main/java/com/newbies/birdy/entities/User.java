package com.newbies.birdy.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @Column(name = "user_name", length = 50, nullable = false)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "full_name", length = 50, nullable = false)
    private String fullName;

    @Column(name = "phone_number", length = 20, nullable = false)
    private String phoneNumber;

    @Column(name = "email", length = 50, nullable = false)
    private String email;

    @Column(name = "dob", nullable = false)
    private Date dob;

    @Column(name = "gender", nullable = false)
    private Integer gender;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @OneToOne(mappedBy = "userShop")
    @JsonBackReference
    private Shop shop;

    @OneToMany(mappedBy = "userAddress",fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Address> addressList;

    @OneToMany(mappedBy = "userOrder",fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Order> orderList;

}
