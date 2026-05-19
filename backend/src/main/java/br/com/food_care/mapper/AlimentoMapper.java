package br.com.food_care.mapper;

import br.com.food_care.dto.AlimentoRequestDTO;
import br.com.food_care.dto.AlimentoResponseDTO;
import br.com.food_care.entity.Alimento;
import br.com.food_care.entity.StatusAlimento;
import br.com.food_care.entity.Usuario;

public class AlimentoMapper {
    public static Alimento criar(AlimentoRequestDTO alimentoDto, Usuario doador) {
        Alimento alimento = new Alimento();

        alimento.setNome(alimentoDto.nome());
        alimento.setDescricao(alimentoDto.descricao());
        alimento.setQuantidadeTotal(alimentoDto.quantidadeTotal());
        alimento.setDataValidade(alimentoDto.dataValidadeFormatada());
        alimento.setStatus(StatusAlimento.DISPONIVEL);
        alimento.setDoador(doador);

        return alimento;
    }

    public static Alimento atualizar(AlimentoRequestDTO alimentoDto, Alimento alimento) {
        if (alimentoDto.nome() != null) alimento.setNome(alimentoDto.nome());
        if (alimentoDto.descricao() != null) alimento.setDescricao(alimentoDto.descricao());
        if (alimentoDto.quantidadeTotal() != null) alimento.setQuantidadeTotal(alimentoDto.quantidadeTotal());
        if (alimentoDto.dataValidade() != null) alimento.setDataValidade(alimentoDto.dataValidadeFormatada());

        return alimento;
    }

    public static AlimentoResponseDTO paraResponse(Alimento alimento) {
        return new AlimentoResponseDTO(
                alimento.getId(),
                alimento.getNome(),
                alimento.getQuantidadeTotal(),
                alimento.getQuantidadeReservada(),
                alimento.getDataValidade().toString(),
                alimento.getStatus().name()
        );
    }
}
