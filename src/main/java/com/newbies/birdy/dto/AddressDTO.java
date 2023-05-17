package com.newbies.birdy.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDTO {

    private Integer id;

    private String address;

    private String ward;

    private String city;

    private String province;

    private Boolean isDefault;

    private Boolean status;

    private Integer userId;

    private String fullName;


}
