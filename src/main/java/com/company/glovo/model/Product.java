package com.company.glovo.model;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private Double cost;
}
