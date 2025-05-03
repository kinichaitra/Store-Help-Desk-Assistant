package com.pji.user.repository;

import com.pji.user.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//JPA repository to interact with database
@Repository
public interface UserRepository extends JpaRepository<MyUser,Integer> {
    public MyUser findByUserName(String userName);

    Optional<MyUser> findById(int id);


}
