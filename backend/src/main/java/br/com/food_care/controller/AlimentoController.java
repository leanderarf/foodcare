package br.com.food_care.controller;

import br.com.food_care.dto.AlimentoRequestDTO;
import br.com.food_care.dto.AlimentoResponseDTO;
import br.com.food_care.entity.Alimento;
import br.com.food_care.mapper.AlimentoMapper;
import br.com.food_care.service.AlimentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "https://localhost:3000")
@RequestMapping("/alimento")
@RestController
@Tag(name = "Alimentos", description = "Endpoints para gerenciamento do estoque de alimentos doados")
public class AlimentoController {

    @Autowired
    private AlimentoService service;

    @Operation(summary = "Registrar um novo alimento", description = "Vincula e salva um novo alimento no estoque associando-o ao ID do usuário doador.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Alimento registrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados da requisição inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuário doador não encontrado")
    })
    @PostMapping("/registrar/{id}")
    public ResponseEntity<AlimentoResponseDTO> cadastraAlimento (@PathVariable Long id,
                                                                 @RequestBody AlimentoRequestDTO alimentoDto) {
        Alimento novoAlimento = service.salvarAlimento(id, alimentoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(AlimentoMapper.paraResponse(novoAlimento));
    }

    @Operation(summary = "Atualizar dados de um alimento", description = "Atualiza as informações de um alimento existente buscando-o pelo ID e validação do nome.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alimento atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Alimento não encontrado")
    })
    @PutMapping("/editar/{id}")
    public  ResponseEntity<AlimentoResponseDTO> atualizaAlimento (@PathVariable Long id,
                                                   @RequestParam String nome,
                                                   @RequestBody AlimentoRequestDTO alimentoDto) {
        Alimento alimentoAtualizado = service.atualizarAlimento(id, nome, alimentoDto);
        return ResponseEntity.ok().body(AlimentoMapper.paraResponse(alimentoAtualizado));
    }

    @Operation(summary = "Listar alimentos disponíveis por validade", description = "Retorna uma lista de alimentos que possuem estoque disponível, ordenados pela proximidade da data de vencimento.")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @GetMapping("/disponiveis")
    public ResponseEntity<List<AlimentoResponseDTO>> listarDisponiveis() {
        List<AlimentoResponseDTO> disponiveis = service.listarDisponiveisPorValidade();
        return ResponseEntity.ok(disponiveis);
    }

    @Operation(summary = "Excluir um alimento", description = "Remove logicamente ou fisicamente um alimento do sistema através do ID do doador, exigindo senha e nome do item por segurança.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "244", description = "Alimento excluído com sucesso (No Content)"),
            @ApiResponse(responseCode = "401", description = "Senha inválida ou não autorizada")
    })
    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<Void> excluiAlimento (@PathVariable Long id,
                                                @RequestParam String senha,
                                                @RequestParam String nome) {
        service.excluirAlimento(id, senha, nome);
        return ResponseEntity.noContent().build();
    }
}
