package com.example.backend_staffoji_game.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "All details about the user entity. ")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @Schema(description = "User Name")
    private String username;

    @Schema(description = "User Password")
    private String password;

    @Schema(description = "User Email")
    private String email;

    @Schema(description = "User Premium Status")
    private boolean isPremium = false;

    public UserDto(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

}
