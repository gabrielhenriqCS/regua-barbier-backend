package com.gabriel_henrique.regua_barbier.domain.servico;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "servico")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Servico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "servico_id")
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Float preco;

    @Column(nullable = false)
    private Integer duracaoMinutos;
}
