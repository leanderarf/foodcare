package br.com.food_care.service;

import br.com.food_care.dto.LoginResponseDTO;
import br.com.food_care.entity.Usuario;
import br.com.food_care.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository repository;

    @Transactional
    public LoginResponseDTO realizarLogin(String email, String senha) {
        Usuario usuario = repository.findByEmailAndSenhaAndAtivoTrue(email, senha)
                .orElseThrow(() -> new RuntimeException("E-mail ou senha incorretos."));

        usuario.setLogado(true);
        repository.save(usuario);

        return new LoginResponseDTO(usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getSenha(),
                usuario.getTipo().name(),
                usuario.isLogado());
    }
}
