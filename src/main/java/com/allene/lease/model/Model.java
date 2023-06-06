package com.allene.lease.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "model", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "brand_id"}))
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private String name;
    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;

}
