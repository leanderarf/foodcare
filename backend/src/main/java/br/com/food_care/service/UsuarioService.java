package br.com.food_care.service;

import br.com.food_care.dto.UsuarioRequestDTO;
import br.com.food_care.entity.Usuario;
import br.com.food_care.mapper.UsuarioMapper;
import br.com.food_care.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Transactional
    public Usuario salvarUsuario(UsuarioRequestDTO dto) {
        Usuario usuario = UsuarioMapper.criar(dto);
        return repository.save(usuario);
    }

    @Transactional
    public Usuario atualizarUsuario(Long id, UsuarioRequestDTO dto) {
        Usuario usuario = verificarSeUsuarioExiste(id);
        UsuarioMapper.atualizar(dto, usuario);
        return repository.save(usuario);
    }

    @Transactional
    public void excluirUsuario(Long id, String senhaConfirmacao) {
        Usuario usuario = verificarSeUsuarioExiste(id);

        if (!usuario.getSenha().equals(senhaConfirmacao))
            throw new RuntimeException("Senha de confirmação incorreta. Operação cancelada.");

        UsuarioMapper.desativar(usuario);
        repository.save(usuario);
    }

    public Usuario verificarSeUsuarioExiste(Long id) {
        return repository.findById(id).
                orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
    }
}
