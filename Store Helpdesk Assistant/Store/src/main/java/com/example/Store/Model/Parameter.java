package com.example.Store.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="parameter")
public class Parameter {

    @Column(name="parameteroid")
    private int parameteroid;

    @Id
    @Column(name="parameter")
    private String parameter;

    @Column(name="description")
    private String description;


//    ignore one side of relationship
    @JsonIgnore
    @OneToMany(mappedBy = "parameterT",fetch=FetchType.EAGER)
    private List<StoreParameter> storeParameterList;

//    public Parameter(int i, String enableZo, Object o) {
//    }



}
