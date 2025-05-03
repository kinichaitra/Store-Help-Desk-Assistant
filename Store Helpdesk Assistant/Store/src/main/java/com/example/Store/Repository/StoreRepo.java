package com.example.Store.Repository;

import com.example.Store.Model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepo extends JpaRepository<Store,Integer> {
    Optional<Store> findById(int number);
    public List<Store> findByNumber(int number);
    public List<Store> findByCity(String city);
    public List<Store> findByZipcode(int zipcode);
    public List<Store> findByNumberAndCityAndZipcode(int number, String city, int zipcode);
    public List<Store> findByNumberAndZipcode(int number, int zipcode);
    public List<Store> findByNumberAndCity(int number, String city);
    @Transactional
    @Modifying
    public List<Store> findByZipcodeAndCity(int zipcode, String city);

}
