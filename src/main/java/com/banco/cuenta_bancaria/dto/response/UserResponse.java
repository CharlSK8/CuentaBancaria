package com.banco.cuenta_bancaria.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserResponse(
    @JsonProperty("id_user") Long idUser
) {
}
