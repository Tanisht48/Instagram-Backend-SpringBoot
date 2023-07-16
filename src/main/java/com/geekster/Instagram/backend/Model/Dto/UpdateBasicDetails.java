package com.geekster.Instagram.backend.Model.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateBasicDetails {
    private String userName;
    private String userHandle;
    private Integer userAge;
}
