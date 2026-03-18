package com.gabriel_henrique.regua_barbier.domain;

import lombok.Getter;

@Getter
public enum UsuarioRole {
    ADMIN("admin"), CLIENTE("cliente"), BARBEIRO("barbeiro");

    private final String role;

    UsuarioRole(String role) {
        this.role = role;
    }

}
