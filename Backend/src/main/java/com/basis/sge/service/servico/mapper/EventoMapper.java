package com.basis.sge.service.servico.mapper;

import com.basis.sge.service.dominio.Evento;
import com.basis.sge.service.servico.DTO.EventoDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {EventoPerguntaMapper.class})
public interface EventoMapper extends EntityMapper<EventoDTO, Evento> {

    @Override
    @Mapping(source = "idTipoEvento", target = "tipoEvento.id")
    Evento toEntity(EventoDTO eventoDTO);

    @Override
    @Mapping(source = "tipoEvento.id", target = "idTipoEvento")
    EventoDTO toDto(Evento evento);

/*    @AfterMapping
    default void atualizarRelacionamentos(@MappingTarget Evento evento) {
        evento.getPerguntas().forEach(eventoPergunta -> eventoPergunta.setEvento(evento));
    }*/

}
