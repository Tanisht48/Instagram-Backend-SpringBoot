package com.geekster.Instagram.backend.Model.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateLogInDetails {
    private String userEmail;
    private String userPassword;
}
