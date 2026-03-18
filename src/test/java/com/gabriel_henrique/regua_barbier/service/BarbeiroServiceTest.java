package com.gabriel_henrique.regua_barbier.service;

import com.gabriel_henrique.regua_barbier.domain.barbeiro.Barbeiro;
import com.gabriel_henrique.regua_barbier.domain.barbeiro.BarbeiroDTO;
import com.gabriel_henrique.regua_barbier.infra.DadosPreenchidosMultiplasVezesException;
import com.gabriel_henrique.regua_barbier.repository.BarbeiroRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BarbeiroServiceTest {

    @InjectMocks
    private BarbeiroService barbeiroService;

    @Mock
    private BarbeiroRepository barbeiroRepository;

    @Test
    @DisplayName("Deve cadastrar um barbeiro")
    void cadastrarBarbeiroComSucesso() {
        var dto = new BarbeiroDTO("Júlio César", "juliocesar@email.com", "julio_2004JL", "85912345665");

        var barbeiro = Barbeiro.builder()
                .id(UUID.randomUUID())
                .nome(dto.nome())
                .email(dto.email())
                .telefone(dto.telefone())
                .especialidade("Corte e barba")
                .ativo(true)
                .build();

        when(barbeiroRepository.save(any(Barbeiro.class))).thenReturn(barbeiro);

        var resultado = barbeiroService.novoBarbeiro(dto);

        assertEquals(dto.nome(), resultado.getNome());
        assertEquals(dto.email(), resultado.getEmail());
    }

    @Test
    @DisplayName("Deve ocorrer um erro ao cadastrar barbeiro com dados duplicados")
    void cadastrarBarbeiroComErroDeDados() {
        var dto = new BarbeiroDTO("Júlio César", "juliocesar@email.com", "julio2004JL", "85977632144");
        when(barbeiroRepository.existsByEmail(dto.email())).thenReturn(true);

        assertThrows(DadosPreenchidosMultiplasVezesException.class, () -> {
            barbeiroService.novoBarbeiro(dto);
        });
        verify(barbeiroRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve ocorrer erro de duplicata ao atualizar cadastro")
    void erroAoAtualizarDados() {

    }
}