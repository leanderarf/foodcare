package br.com.food_care.repository;

import br.com.food_care.entity.Solicitacao;
import br.com.food_care.entity.StatusSolicitacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Long> {

    List<Solicitacao> findByAlimentoDoadorIdAndStatus(Long doadorId, StatusSolicitacao status);

    List<Solicitacao> findByAlimentoDoadorIdAndStatusNot(Long doadorId, StatusSolicitacao status);

    List<Solicitacao> findByReceptorId(Long receptorId);
}
