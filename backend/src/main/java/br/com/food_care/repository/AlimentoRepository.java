package br.com.food_care.repository;

import br.com.food_care.entity.Alimento;
import br.com.food_care.entity.StatusAlimento;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlimentoRepository extends JpaRepository<Alimento, Long> {
    Optional<Alimento> findByNomeIgnoreCaseAndDoadorId(String nome, Long doadorId);

    List<Alimento> findAllByStatusOrderByDataValidadeAsc(StatusAlimento status);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM Alimento a WHERE a.id=:id")
    Optional<Alimento> findByIdWithLock(Long id);
}
