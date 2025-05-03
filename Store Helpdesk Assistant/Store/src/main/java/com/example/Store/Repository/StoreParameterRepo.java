package com.example.Store.Repository;

import com.example.Store.Model.StoreParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface StoreParameterRepo extends JpaRepository<StoreParameter,Integer> {
    public StoreParameter findByNumberAndParameter(int number, String parameter);

}