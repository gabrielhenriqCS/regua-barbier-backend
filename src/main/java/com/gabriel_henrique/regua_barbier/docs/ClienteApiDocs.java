package com.gabriel_henrique.regua_barbier.docs;

import com.gabriel_henrique.regua_barbier.domain.cliente.Cliente;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@Tag(name = "Clientes", description = "API de gerenciamento de clientes")
public interface ClienteApiDocs {

    @Operation(summary = "Listar todos os clientes", description = "Retorna os dados de cadastro de todos os clientes")
    @ApiResponse(responseCode = "200", description = "Clientes listados com sucesso!")
    @ApiResponse(responseCode = "404", description = "Nenhum cliente encontrado.")
    ResponseEntity<List<Cliente>> mostrarTodosClientes();

    @Operation(summary = "Encontrar um cliente com o id", description = "Retorna um determinado cliente usando o seu id único")
    @ApiResponse(responseCode = "200", description = "Cliente com id: encontrado")
    @ApiResponse(responseCode = "404", description = "Cliente nao encontrado")
    ResponseEntity<Cliente> encontrarCliente(@PathVariable("id") UUID id);
}