package com.example.Store.Model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
//@Getter
//@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="store")
public class Store {

    @Id
    @Column(name="number")
    private int number;

    public void setCity(String city) {
        this.city = city;
    }


    @Column(name = "city")
    private String city;

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }


    @Column(name="zipcode")
    private int zipcode;

//    @JsonIgnore
    @OneToMany(mappedBy = "store",fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    private List<StoreParameter> storeParameterList;

////    constructor
//
//    public Store(int i, int i1, String kolkata, int i2, Object o) {
//    }

//    public List<Store> StoreList;

    @Override
    public String toString() {
        return "Store{" +
                ", number=" + number +
                ", city='" + city  +
                ", zipcode=" + zipcode +

                '}';
    }


}
