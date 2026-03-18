package com.gabriel_henrique.regua_barbier.controller;

import com.gabriel_henrique.regua_barbier.config.TokenConfig;
import com.gabriel_henrique.regua_barbier.domain.UsuarioRole;
import com.gabriel_henrique.regua_barbier.domain.auth.Cadastrar;
import com.gabriel_henrique.regua_barbier.domain.auth.LoginRequest;
import com.gabriel_henrique.regua_barbier.domain.auth.LoginResponse;
import com.gabriel_henrique.regua_barbier.domain.cliente.Cliente;
import com.gabriel_henrique.regua_barbier.repository.ClienteRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenConfig tokenConfig;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) {
        UsernamePasswordAuthenticationToken usuarioESenha = new UsernamePasswordAuthenticationToken(request.email(), request.senha());
        Authentication authentication = authenticationManager.authenticate(usuarioESenha);

        if (authentication.getPrincipal() instanceof Cliente cliente) {
            String token = tokenConfig.gerarToken(cliente);
            return ResponseEntity.ok(new LoginResponse(token));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Cadastrar> cadastrar(@RequestBody @Valid Cadastrar request) {
        Cliente cliente = new Cliente();
        cliente.setNome(request.nome());
        cliente.setEmail(request.email());
        cliente.setTelefone(request.telefone());
        cliente.setSenha(passwordEncoder.encode(request.senha()));

        cliente.setRole(UsuarioRole.CLIENTE);

        clienteRepository.save(cliente);

        return ResponseEntity.status(HttpStatus.CREATED).body(new Cadastrar(cliente.getNome(), cliente.getEmail(), cliente.getSenha(), cliente.getTelefone()));
    }

}
