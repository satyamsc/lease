package com.allene.lease.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Contract {
    @Id
    @GeneratedValue(generator = "contract_id generator")
    @GenericGenerator(name = "contract_id generator", strategy = "com.allene.lease.generator.LeaseContractIdGenerator")
    private Long contractNumber;
    private BigDecimal monthlyRate;
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    @OneToOne
    @JoinColumn(name = "vehicle_id", unique = true, nullable = false)
    private Vehicle vehicle;
}
