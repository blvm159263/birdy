package com.newbies.birdy.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_addess")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "ward", length = 50, nullable = false)
    private String ward;

    @Column(name = "city", length = 50, nullable = false)
    private String city;

    @Column(name = "province", length = 50, nullable = false)
    private String province;

    @Column(name = "isDefault", nullable = false)
    private Boolean isDefault;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "user_id", nullable = false)
    private User userAddress;

}
