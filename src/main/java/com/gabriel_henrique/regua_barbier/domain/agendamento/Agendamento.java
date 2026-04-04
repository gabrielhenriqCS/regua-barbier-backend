package com.gabriel_henrique.regua_barbier.domain.agendamento;

import com.gabriel_henrique.regua_barbier.domain.barbeiro.Barbeiro;
import com.gabriel_henrique.regua_barbier.domain.cliente.Cliente;
import com.gabriel_henrique.regua_barbier.domain.pagamento.PagamentoStatus;
import com.gabriel_henrique.regua_barbier.domain.servico.Servico;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "agendamento")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Agendamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "agendamento_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "barbeiro_id")
    private Barbeiro barbeiro;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "servico_id")
    private Servico servico;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataInicio;

    @PrePersist
    protected void onCreate() {
        this.dataInicio = LocalDateTime.now();
        if (this.statusAgendamento == null) {
            this.statusAgendamento = StatusAgendamento.PENDENTE_PAGAMENTO;
        }
    }

    @Column(name = "data_termino", nullable = false)
    private LocalDateTime dataTermino;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_agendamento", nullable = false)
    private StatusAgendamento statusAgendamento;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_pagamento", nullable = false)
    private PagamentoStatus statusPagamento = PagamentoStatus.PENDENTE;
}
