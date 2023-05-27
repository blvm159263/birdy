package com.newbies.birdy.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_order_status")
public class OrderStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @Column(name = "order_status_name", nullable = false)
    private String orderStatusName;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @OneToMany(mappedBy = "orderStatus", fetch = FetchType.LAZY)
    @JsonBackReference
    private List<OrderStatusDetail> orderStatusDetailList;
}
