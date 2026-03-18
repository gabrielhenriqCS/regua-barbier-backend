package com.gabriel_henrique.regua_barbier.domain.barbeiro;

import com.gabriel_henrique.regua_barbier.domain.UsuarioRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "barbeiros")
@Data
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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "usuario_role", joinColumns = @JoinColumn(name = "barbeiro_id"))
    @Enumerated(EnumType.STRING)
    @Column
    private UsuarioRole role;

    @Column(nullable = false)
    private String especialidade;

    @Column(nullable = false)
    private Boolean ativo = true;
}
