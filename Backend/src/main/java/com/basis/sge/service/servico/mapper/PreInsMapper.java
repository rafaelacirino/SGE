package com.basis.sge.service.servico.mapper;

import com.basis.sge.service.dominio.PreInscricao;
import com.basis.sge.service.servico.DTO.PreInsDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})

public interface PreInsMapper extends EntityMapper<PreInsDTO, PreInscricao>{
}
