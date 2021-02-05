package com.basis.sge.service.servico.mapper;

import com.basis.sge.service.dominio.InscricaoResposta;
import com.basis.sge.service.servico.dto.InscricaoRespostaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IncricaoRespostaMapper extends EntityMapper<InscricaoRespostaDTO, InscricaoResposta>{

    @Override
    @Mapping(source = "idEvento", target = "evento.id")
    @Mapping(source = "idPergunta", target = "pergunta.id")
    @Mapping(source = "idInscricao", target = "preInscricao.id")
    @Mapping(source = "idPergunta", target = "id.idPergunta")
    @Mapping(source = "idEvento", target = "id.idEvento")
    @Mapping(source = "idInscricao", target = "id.idInscricao")
    InscricaoResposta toEntity(InscricaoRespostaDTO inscricaoRespostaDTO);

    @Override
    @Mapping(source = "evento.id", target = "idEvento")
    @Mapping(source = "pergunta.id", target = "idPergunta")
    @Mapping(source = "preInscricao.id", target = "idInscricao")
    InscricaoRespostaDTO toDto(InscricaoResposta inscricaoResposta);
}
