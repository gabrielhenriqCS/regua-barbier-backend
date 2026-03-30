package com.gabriel_henrique.regua_barbier.domain.cliente;

import com.gabriel_henrique.regua_barbier.domain.UsuarioRole;
import com.gabriel_henrique.regua_barbier.domain.pagamento.Pagamento;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "cliente_id")
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, length = 20)
    private String telefone;

    @Email
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String senha;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "usuario_role", joinColumns = @JoinColumn(name = "cliente_id"))
    @Enumerated(EnumType.STRING)
    @Column
    private UsuarioRole role;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private List<Pagamento> pagamentos;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role == UsuarioRole.CLIENTE) {
            return List.of(new SimpleGrantedAuthority("cliente"));
        }
        return List.of(new SimpleGrantedAuthority("cliente"));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
