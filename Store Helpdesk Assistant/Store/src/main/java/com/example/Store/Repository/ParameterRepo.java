package com.example.Store.Repository;

import com.example.Store.Model.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParameterRepo extends JpaRepository<Parameter, String> {

     public Parameter findByParameter(String parameter);


}