package com.basis.sge.service.servico.mapper;

import com.basis.sge.service.dominio.Pergunta;
import com.basis.sge.service.servico.DTO.PerguntaDTO;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring", uses = {})
public interface PerguntaMapper extends EntityMapper<PerguntaDTO, Pergunta>{


}
