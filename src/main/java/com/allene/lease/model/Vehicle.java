package com.allene.lease.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;
    @OneToOne
    @JoinColumn(name = "model_id", nullable = false)
    private Model model;
    @Column(nullable = false)
    private Integer modelYear;
    @Column(name = "vin", unique = true)
    private String vin;
    @Column(nullable = false)
    private BigDecimal price;
}
