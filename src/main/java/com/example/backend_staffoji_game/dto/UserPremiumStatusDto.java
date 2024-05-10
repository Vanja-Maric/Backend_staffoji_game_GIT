package com.example.backend_staffoji_game.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Details for updating user premium status.")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPremiumStatusDto {


    @Schema(description = "User Name")
    private String username;

    @Schema(description = "User Premium Status")
    private boolean isPremium;
}
