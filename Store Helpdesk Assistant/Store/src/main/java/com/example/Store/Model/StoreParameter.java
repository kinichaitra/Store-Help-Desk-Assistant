package com.example.Store.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="store_parameter")

public class StoreParameter {
    @Id
    @JsonIgnore
    @Column(name="storeparameteroid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int storeParameteroid;


    @Column(name="parameter",insertable=false,updatable = false)
    private String parameter;

    @JsonIgnore
    @Column(name="number",insertable=false,updatable = false)
    private int number;


    @Column(name="parametervalue")
    private int parameterValue;


    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name="number")
    private Store store;


    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name="parameter")
    private Parameter parameterT;
}
