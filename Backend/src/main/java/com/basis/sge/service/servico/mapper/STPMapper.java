package com.basis.sge.service.servico.mapper;

import com.basis.sge.service.dominio.SituacaoPreInscricao;
import com.basis.sge.service.servico.DTO.STPDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})

public interface STPMapper extends EntityMapper<STPDTO, SituacaoPreInscricao> {
}