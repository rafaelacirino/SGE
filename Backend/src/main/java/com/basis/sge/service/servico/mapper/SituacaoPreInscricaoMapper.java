package com.basis.sge.service.servico.mapper;

import com.basis.sge.service.dominio.SituacaoPreInscricao;
import com.basis.sge.service.servico.DTO.SituacaoPreInscricaoDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface SituacaoPreInscricaoMapper extends EntityMapper<SituacaoPreInscricaoDTO, SituacaoPreInscricao> {

}