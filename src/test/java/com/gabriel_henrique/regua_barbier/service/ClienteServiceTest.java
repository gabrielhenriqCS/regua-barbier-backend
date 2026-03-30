package com.gabriel_henrique.regua_barbier.service;

import com.gabriel_henrique.regua_barbier.domain.cliente.Cliente;
import com.gabriel_henrique.regua_barbier.domain.cliente.ClienteDTO;
import com.gabriel_henrique.regua_barbier.domain.cliente.exceptions.ClienteNaoEncontrado;
import com.gabriel_henrique.regua_barbier.infra.DadosInvalidosException;
import com.gabriel_henrique.regua_barbier.infra.DadosPreenchidosMultiplasVezesException;
import com.gabriel_henrique.regua_barbier.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ClienteService clienteService;

    private UUID id;
    private Cliente clienteExistente;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();

        clienteExistente = Cliente.builder()
                .nome("Gabriel")
                .email("gabriel@email.com")
                .telefone("85999999999")
                .senha("senhaEncoded")
                .build();
    }

    // =========================================================
    // mostrarClientes()
    // =========================================================

    @Nested
    @DisplayName("mostrarClientes()")
    class MostrarClientes {

        @Test
        @DisplayName("Deve retornar lista de clientes")
        void deveRetornarListaDeClientes() {
            when(clienteRepository.findAll()).thenReturn(List.of(clienteExistente));

            List<Cliente> resultado = clienteService.mostrarClientes();

            assertThat(resultado).hasSize(1);
            assertThat(resultado.getFirst().getNome()).isEqualTo("Gabriel");
            verify(clienteRepository, times(1)).findAll();
        }

        @Test
        @DisplayName("Deve retornar lista vazia quando não houver clientes")
        void deveRetornarListaVazia() {
            when(clienteRepository.findAll()).thenReturn(List.of());

            List<Cliente> resultado = clienteService.mostrarClientes();

            assertThat(resultado).isEmpty();
        }
    }

    // =========================================================
    // mostrarCliente(UUID)
    // =========================================================

    @Nested
    @DisplayName("mostrarCliente(UUID)")
    class MostrarCliente {

        @Test
        @DisplayName("Deve retornar cliente quando o ID existir")
        void deveRetornarClienteQuandoIdExistir() {
            when(clienteRepository.findById(id)).thenReturn(Optional.of(clienteExistente));

            Cliente resultado = clienteService.mostrarCliente(id);

            assertThat(resultado).isNotNull();
            assertThat(resultado.getNome()).isEqualTo("Gabriel");
        }

        @Test
        @DisplayName("Deve lançar ClienteNaoEncontrado quando o ID não existir")
        void deveLancarExcecaoQuandoIdNaoExistir() {
            when(clienteRepository.findById(id)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> clienteService.mostrarCliente(id))
                    .isInstanceOf(ClienteNaoEncontrado.class);
        }
    }

    // =========================================================
    // novoCliente(ClienteDTO)
    // =========================================================

    @Nested
    @DisplayName("novoCliente(ClienteDTO)")
    class NovoCliente {

        @Test
        @DisplayName("Deve criar cliente com dados válidos")
        void deveCriarClienteComDadosValidos() {
            ClienteDTO dto = new ClienteDTO("Gabriel", "gabriel@email.com", "senha123", "85999999999");

            when(passwordEncoder.encode("senha123")).thenReturn("senhaEncoded");
            when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteExistente);

            Cliente resultado = clienteService.novoCliente(dto);

            assertThat(resultado).isNotNull();
            verify(clienteRepository, times(1)).save(any(Cliente.class));
            verify(passwordEncoder, times(1)).encode("senha123");
        }

        @Test
        @DisplayName("Deve lançar DadosInvalidosException quando nome for nulo")
        void deveLancarExcecaoQuandoNomeForNulo() {
            ClienteDTO dto = new ClienteDTO(null, "gabriel@email.com", "senha123", "85999999999");

            assertThatThrownBy(() -> clienteService.novoCliente(dto))
                    .isInstanceOf(DadosInvalidosException.class)
                    .hasMessageContaining("Nome");
        }

        @Test
        @DisplayName("Deve lançar DadosInvalidosException quando email for nulo")
        void deveLancarExcecaoQuandoEmailForNulo() {
            ClienteDTO dto = new ClienteDTO("Gabriel", null, "senha123", "85999999999");

            assertThatThrownBy(() -> clienteService.novoCliente(dto))
                    .isInstanceOf(DadosInvalidosException.class)
                    .hasMessageContaining("Email");
        }

        @Test
        @DisplayName("Deve lançar DadosInvalidosException quando senha for nula")
        void deveLancarExcecaoQuandoSenhaForNula() {
            ClienteDTO dto = new ClienteDTO("Gabriel", "gabriel@email.com", null, "85999999999");

            assertThatThrownBy(() -> clienteService.novoCliente(dto))
                    .isInstanceOf(DadosInvalidosException.class)
                    .hasMessageContaining("Senha");
        }

        @Test
        @DisplayName("Deve lançar DadosInvalidosException quando telefone for nulo")
        void deveLancarExcecaoQuandoTelefoneForNulo() {
            ClienteDTO dto = new ClienteDTO("Gabriel", "gabriel@email.com", "senha123", null);

            assertThatThrownBy(() -> clienteService.novoCliente(dto))
                    .isInstanceOf(DadosInvalidosException.class)
                    .hasMessageContaining("Telefone");
        }

        @Test
        @DisplayName("Deve encodar a senha antes de salvar")
        void deveEncodarSenhaAntesDeSalvar() {
            ClienteDTO dto = new ClienteDTO("Gabriel", "gabriel@email.com", "senha123", "85999999999");

            when(passwordEncoder.encode("senha123")).thenReturn("$2a$hashedValue");
            when(clienteRepository.save(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));

            Cliente resultado = clienteService.novoCliente(dto);

            assertThat(resultado.getSenha()).isEqualTo("$2a$hashedValue");
        }
    }

    // =========================================================
    // removerCliente(UUID)
    // =========================================================

    @Nested
    @DisplayName("removerCliente(UUID)")
    class RemoverCliente {

        @Test
        @DisplayName("Deve remover cliente quando ID existir")
        void deveRemoverClienteQuandoIdExistir() {
            when(clienteRepository.existsById(id)).thenReturn(true);

            clienteService.removerCliente(id);

            verify(clienteRepository, times(1)).deleteById(id);
        }

        @Test
        @DisplayName("Deve lançar ClienteNaoEncontrado quando ID não existir")
        void deveLancarExcecaoQuandoIdNaoExistir() {
            when(clienteRepository.existsById(id)).thenReturn(false);

            assertThatThrownBy(() -> clienteService.removerCliente(id))
                    .isInstanceOf(ClienteNaoEncontrado.class);

            verify(clienteRepository, never()).deleteById(any());
        }
    }

    // =========================================================
    // atualizarCliente(UUID, ClienteDTO)
    // =========================================================

    @Nested
    @DisplayName("atualizarCliente(UUID, ClienteDTO)")
    class AtualizarCliente {

        @Test
        @DisplayName("Deve atualizar cliente com dados válidos e diferentes")
        void deveAtualizarClienteComDadosValidos() {
            ClienteDTO dto = new ClienteDTO("Gabriel Novo", "novo@email.com", null, "85988888888");

            when(clienteRepository.findById(id)).thenReturn(Optional.of(clienteExistente));

            clienteService.atualizarCliente(id, dto);

            assertThat(clienteExistente.getNome()).isEqualTo("Gabriel Novo");
            assertThat(clienteExistente.getEmail()).isEqualTo("novo@email.com");
            assertThat(clienteExistente.getTelefone()).isEqualTo("85988888888");
        }

        @Test
        @DisplayName("Deve lançar ClienteNaoEncontrado quando ID não existir")
        void deveLancarExcecaoQuandoIdNaoExistir() {
            ClienteDTO dto = new ClienteDTO("Gabriel Novo", "novo@email.com", null, "85988888888");

            when(clienteRepository.findById(id)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> clienteService.atualizarCliente(id, dto))
                    .isInstanceOf(ClienteNaoEncontrado.class);
        }

        @Test
        @DisplayName("Deve lançar DadosPreenchidosMultiplasVezesException quando dados forem iguais")
        void deveLancarExcecaoQuandoDadosForemIguais() {
            ClienteDTO dto = new ClienteDTO("Gabriel", "gabriel@email.com", null, "85999999999");

            when(clienteRepository.findById(id)).thenReturn(Optional.of(clienteExistente));

            assertThatThrownBy(() -> clienteService.atualizarCliente(id, dto))
                    .isInstanceOf(DadosPreenchidosMultiplasVezesException.class)
                    .hasMessageContaining("iguais");
        }

        @Test
        @DisplayName("Deve lançar DadosInvalidosException quando nome estiver em branco")
        void deveLancarExcecaoQuandoNomeEmBranco() {
            ClienteDTO dto = new ClienteDTO("", "novo@email.com", null, "85988888888");

            when(clienteRepository.findById(id)).thenReturn(Optional.of(clienteExistente));

            assertThatThrownBy(() -> clienteService.atualizarCliente(id, dto))
                    .isInstanceOf(DadosInvalidosException.class)
                    .hasMessageContaining("vazios");
        }

        @Test
        @DisplayName("Deve lançar DadosInvalidosException quando email estiver em branco")
        void deveLancarExcecaoQuandoEmailEmBranco() {
            ClienteDTO dto = new ClienteDTO("Gabriel Novo", "", null, "85988888888");

            when(clienteRepository.findById(id)).thenReturn(Optional.of(clienteExistente));

            assertThatThrownBy(() -> clienteService.atualizarCliente(id, dto))
                    .isInstanceOf(DadosInvalidosException.class)
                    .hasMessageContaining("vazios");
        }

        @Test
        @DisplayName("Deve lançar DadosInvalidosException quando telefone estiver em branco")
        void deveLancarExcecaoQuandoTelefoneEstiverEmBranco() {
            ClienteDTO dto = new ClienteDTO("Gabriel Novo", "", null, "");

            when(clienteRepository.findById(id)).thenReturn(Optional.of(clienteExistente));

            assertThatThrownBy(() -> clienteService.atualizarCliente(id, dto))
                    .isInstanceOf(DadosInvalidosException.class)
                    .hasMessageContaining("vazios");
        }
    }
}