package com.newbies.birdy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Integer id;

    private String password;

    private String fullName;

    private String phoneNumber;

    private String email;

    private Date dob;

    private Integer gender;

    private String avatarUrl;

    private Boolean status;

    private Integer roleId;

    private String roleName;


}
