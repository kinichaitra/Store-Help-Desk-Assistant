package com.pji.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="system_user")
public class MyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="systemuserOid")
    private int systemUserOid;

    @Column(name="name")
    private String name;

    @Column(name="accesslevel")
    private String accessLevel;

    @Column(name="username")
    private String userName;

    @Column(name="password")
    private String password;

}
