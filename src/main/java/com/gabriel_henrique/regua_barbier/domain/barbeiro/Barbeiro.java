package com.gabriel_henrique.regua_barbier.domain.barbeiro;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "barbeiros")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Barbeiro {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "barbeiro_id")
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false)
    private String especialidade;

    @Column(nullable = false)
    private Boolean ativo = true;
}
