package br.com.food_care.controller;

import br.com.food_care.dto.SolicitacaoRequestDTO;
import br.com.food_care.dto.SolicitacaoResponseDTO;
import br.com.food_care.entity.Solicitacao;
import br.com.food_care.mapper.SolicitacaoMapper;
import br.com.food_care.service.SolicitacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "https://localhost:3000")
@RequestMapping("/solicitacao")
@RestController
@Tag(name = "Solicitações", description = "Endpoints para controle do fluxo de pedidos de alimentos (criação, aprovação, recusa, histórico)")
public class SolicitacaoController {

    @Autowired
    private SolicitacaoService service;

    @Operation(summary = "Criar uma nova solicitação", description = "Permite que um usuário do tipo Receptor requisite uma quantidade específica de um alimento disponível.")
    @PostMapping("/criar")
    public ResponseEntity<SolicitacaoResponseDTO> criaSolicitacao(@RequestBody @Valid SolicitacaoRequestDTO solicitacaoDto) {
        Solicitacao novaSolicitacao = service.criarSolicitacao(solicitacaoDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(SolicitacaoMapper.paraResponse(novaSolicitacao));
    }

    @Operation(summary = "Alterar dados de uma solicitação", description = "Permite editar justificativas ou quantidades de um pedido pendente.")
    @PutMapping("/alterar/{id}")
    public ResponseEntity<SolicitacaoResponseDTO> atualizaSolicitacao(@PathVariable Long id,
                                                           @RequestBody @Valid SolicitacaoRequestDTO solicitacaoDto) {
        Solicitacao solicitacaoAtualizada = service.alterarSolicitacao(id, solicitacaoDto);
        return ResponseEntity.ok().body(SolicitacaoMapper.paraResponse(solicitacaoAtualizada));
    }

    @Operation(summary = "Aprovar uma solicitação", description = "Ação executada pelo Doador para aceitar o pedido do Receptor e reservar o estoque correspondente.")
    @PostMapping("/aprovar/{idUsuario}")
    public ResponseEntity<Void> aprovaSolicitacao(@PathVariable Long idUsuario,
                                                  @RequestParam Long idSolicitacao) {
        service.aprovarSolicitacao(idSolicitacao, idUsuario);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Recusar uma solicitação", description = "Ação executada pelo Doador para rejeitar o pedido do Receptor.")
    @PostMapping("/recusar/{idUsuario}")
    public ResponseEntity<Void> recusaSolicitacao(@PathVariable Long idUsuario,
                                                  @RequestParam Long idSolicitacao) {
        service.recusarSolicitacao(idSolicitacao, idUsuario);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Cancelar uma solicitação", description = "Permite ao Receptor desistir de um pedido efetuado.")
    @PostMapping("/cancelar/{idUsuario}")
    public ResponseEntity<Void> cancelaSolicitacao(@PathVariable Long idUsuario,
                                                  @RequestParam Long idSolicitacao) {
        service.cancelarSolicitacao(idUsuario, idSolicitacao);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Concluir uma solicitação", description = "Finaliza o processo confirmando que o alimento foi retirado/entregue com sucesso.")
    @PostMapping("/concluir/{idUsuario}")
    public ResponseEntity<Void> concluiSolicitacao(@PathVariable Long idUsuario,
                                                  @RequestParam Long idSolicitacao) {
        service.concluirSolicitacao(idUsuario, idSolicitacao);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Listar solicitações pendentes recebidas por um doador", description = "Exibe para o Doador logado quais pedidos aguardam sua aprovação ou recusa na central de solicitações.")
    @GetMapping("/pendentes/{idDoador}")
    public ResponseEntity<List<SolicitacaoResponseDTO>> listarPendentes(@PathVariable Long idDoador) {
        List<SolicitacaoResponseDTO> pendentes = service.listarPendentesPorDoador(idDoador);
        return ResponseEntity.ok(pendentes);
    }

    @Operation(summary = "Listar histórico de solicitações feitas por um receptor", description = "Retorna todas as requisições realizadas pelo Receptor logado para popular sua tela de histórico.")
    @GetMapping("/minhas-solicitacoes/{idReceptor}")
    public ResponseEntity<List<SolicitacaoResponseDTO>> listarMinhasSolicitacoes(@PathVariable Long idReceptor) {
        List<SolicitacaoResponseDTO> lista = service.listarPorReceptor(idReceptor);
        return ResponseEntity.ok(lista);
    }
}