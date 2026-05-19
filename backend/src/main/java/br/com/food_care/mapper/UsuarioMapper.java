package br.com.food_care.mapper;

import br.com.food_care.dto.UsuarioRequestDTO;
import br.com.food_care.dto.UsuarioResponseDTO;
import br.com.food_care.entity.Endereco;
import br.com.food_care.entity.Usuario;

public class UsuarioMapper {
    public static Usuario criar(UsuarioRequestDTO usuarioDto) {
        Usuario usuario = new Usuario();

        usuario.setCpf(usuarioDto.cpf());
        usuario.setCnpj(usuarioDto.cnpj());
        usuario.setNome(usuarioDto.nome());
        usuario.setDataNascimento(usuarioDto.dataNascimentoFormatada());
        usuario.setEmail(usuarioDto.email());
        usuario.setSenha(usuarioDto.senha());
        usuario.setTelefone(usuarioDto.telefone());
        usuario.setEndereco(new Endereco(usuarioDto.rua(),
                usuarioDto.numero(),
                usuarioDto.bairro(),
                usuarioDto.cidade(),
                usuarioDto.estado()));
        usuario.setSexo(usuarioDto.sexoFormatado());
        usuario.setTipo(usuarioDto.tipoUsuarioFormatado());
        usuario.setAtivo(true);

        return usuario;
    }

    public static Usuario atualizar(UsuarioRequestDTO usuarioDto, Usuario usuario) {
        if (usuarioDto.nome() != null) usuario.setNome(usuarioDto.nome());
        if (usuarioDto.email() != null) usuario.setEmail(usuarioDto.email());
        if (usuarioDto.senha() != null) usuario.setSenha(usuarioDto.senha());
        if (usuarioDto.telefone() != null) usuario.setTelefone(usuarioDto.telefone());

        if (isQualquerCampoPreenchido(usuarioDto)) {
            Endereco atual = usuario.getEndereco();

            usuario.setEndereco(new Endereco(
                    usuarioDto.rua() != null ? usuarioDto.rua() : (atual.rua() != null ? atual.rua() : null),
                    usuarioDto.numero() != null ? usuarioDto.numero() : (atual.numero() != null ? atual.numero() : null),
                    usuarioDto.bairro() != null ? usuarioDto.bairro() : (atual.bairro() != null ? atual.bairro() : null),
                    usuarioDto.cidade() != null ? usuarioDto.cidade() : (atual.cidade() != null ? atual.cidade() : null),
                    usuarioDto.estado() != null ? usuarioDto.estado() : (atual.estado() != null ? atual.estado() : null)

            ));
        }

        return usuario;
    }

    public static void desativar(Usuario usuario) {
        usuario.setAtivo(false);
        usuario.setLogado(false);
        usuario.setEmail("DESATIVADO_" + usuario.getId() + "_" + usuario.getEmail());
    }

    private static boolean isQualquerCampoPreenchido(UsuarioRequestDTO usuarioDto) {
        return usuarioDto.rua() != null || usuarioDto.numero() != null || usuarioDto.bairro() != null ||
                usuarioDto.cidade() != null || usuarioDto.estado() != null;
    }

    public static UsuarioResponseDTO paraResponse (Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getSenha(),
                usuario.isAtivo()
        );
    }
}
