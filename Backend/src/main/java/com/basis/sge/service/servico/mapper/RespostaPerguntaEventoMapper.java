package com.basis.sge.service.servico.mapper;

import com.basis.sge.service.dominio.RespostaPerguntaEvento;
import com.basis.sge.service.servico.DTO.RespostaPerguntaEventoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})

public interface RespostaPerguntaEventoMapper extends EntityMapper<RespostaPerguntaEventoDTO, RespostaPerguntaEvento>{
}
