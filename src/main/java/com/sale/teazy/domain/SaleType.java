package com.sale.teazy.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;

@Entity
@Getter
@Table(name = "saleTypes")
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaleType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String saleType;

    private Double commission;

}
