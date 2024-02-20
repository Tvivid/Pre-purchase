package com.example.preorder.Dto;

import lombok.Data;

@Data
public class ChangePassword {
    private String currentPassword;
    private String newPassword;
}
