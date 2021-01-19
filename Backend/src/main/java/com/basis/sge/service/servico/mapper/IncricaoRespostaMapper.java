package com.basis.sge.service.servico.mapper;

import com.basis.sge.service.dominio.InscricaoResposta;
import com.basis.sge.service.servico.DTO.InscricaoRespostaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})

public interface IncricaoRespostaMapper extends EntityMapper<InscricaoRespostaDTO, InscricaoResposta>{
    @Override
    @Mapping(source = "idEvento", target = "evento.id")
    @Mapping(source = "idPergunta", target = "pergunta.id")
    @Mapping(source = "idPreInscricao", target = "preInscricao.id")
    InscricaoResposta toEntity(InscricaoRespostaDTO inscricaoRespostaDTO);


    @Override
    @Mapping(source = "evento.id", target = "idEvento")
    @Mapping(source = "pergunta.id", target = "idPergunta")
    @Mapping(source = "preInscricao.id", target = "idPreInscricao")
    InscricaoRespostaDTO toDto(InscricaoResposta inscricaoResposta);
}
