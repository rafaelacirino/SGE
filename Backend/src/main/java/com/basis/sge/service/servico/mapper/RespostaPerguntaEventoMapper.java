package com.basis.sge.service.servico.mapper;

import com.basis.sge.service.dominio.PreInscricao;
import com.basis.sge.service.dominio.RespostaPerguntaEvento;
import com.basis.sge.service.servico.DTO.PreInsDTO;
import com.basis.sge.service.servico.DTO.RespostaPerguntaEventoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})

public interface RespostaPerguntaEventoMapper extends EntityMapper<RespostaPerguntaEventoDTO, RespostaPerguntaEvento>{
    @Override
    @Mapping(source = "idEvento", target = "evento.id")
    @Mapping(source = "idPergunta", target = "pergunta.id")
    @Mapping(source = "idPreInscricao", target = "preInscricao.id")
    RespostaPerguntaEvento toEntity(RespostaPerguntaEventoDTO respostaPerguntaEventoDTO);


    @Override
    @Mapping(source = "evento.id", target = "idEvento")
    @Mapping(source = "pergunta.id", target = "idPergunta")
    @Mapping(source = "preInscricao.id", target = "idPreInscricao")
    RespostaPerguntaEventoDTO toDto(RespostaPerguntaEvento respostaPerguntaEvento);
}
