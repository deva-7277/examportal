package com.exam.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userRoleId;

//    mapping user to userroles
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

//    mapping role to userroles
    @ManyToOne
    private Role role;

}
