package br.com.food_care.controller;

import br.com.food_care.dto.UsuarioRequestDTO;
import br.com.food_care.dto.UsuarioResponseDTO;
import br.com.food_care.entity.Usuario;
import br.com.food_care.mapper.UsuarioMapper;
import br.com.food_care.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "https://localhost:3000")
@RequestMapping("/usuario")
@RestController
@Tag(name = "Usuários", description = "Endpoints para gerenciamento do perfil de Doadores e Receptores (Cadastro e alterações)")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @Operation(summary = "Cadastrar novo usuário", description = "Registra uma nova conta no FoodCare, definindo se o perfil será um DOADOR (mercados, doadores físicos) ou RECEPTOR (instituições, comunidades).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "E-mail já cadastrado ou dados inválidos")
    })
    @PostMapping("/cadastrar")
    public ResponseEntity<UsuarioResponseDTO> cadastraUsuario(@RequestBody @Valid UsuarioRequestDTO usuarioDto) {
        Usuario novoUsuario = service.salvarUsuario(usuarioDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.paraResponse(novoUsuario));
    }

    @Operation(summary = "Atualizar informações do usuário", description = "Atualiza os dados cadastrais (nome, telefone, etc.) de um perfil baseado em seu ID.")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizaUsuario(@PathVariable Long id,
                                                   @RequestBody @Valid UsuarioRequestDTO usuarioDto) {
        Usuario usuarioAtualizado = service.atualizarUsuario(id, usuarioDto);
        return ResponseEntity.ok().body(UsuarioMapper.paraResponse(usuarioAtualizado));
    }

    @Operation(summary = "Deletar conta de usuário", description = "Remove a conta do usuário do sistema mediante confirmação por senha.")
    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Void> excluiUsuario (@PathVariable Long id,
                                                @RequestParam String senha) {
        service.excluirUsuario(id, senha);
        return ResponseEntity.noContent().build();
    }
}
