package com.basis.sge.service.servico.mapper;

import com.basis.sge.service.dominio.PreInscricao;
import com.basis.sge.service.servico.DTO.PreInsDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {})

public interface PreInsMapper extends EntityMapper<PreInsDTO, PreInscricao>{
    @Override
    @Mapping(source = "idEvento", target = "evento.id")
    @Mapping(source = "idUsuario", target = "usuario.id")
    @Mapping(source = "idSituacaoPreInscricao", target = "situacaoPreInscricao.id")
    PreInscricao toEntity(PreInsDTO preInsDTO);

    @Override
    @Mapping(source = "evento.id", target = "idEvento")
    @Mapping(source = "usuario.id", target = "idUsuario")
    @Mapping(source = "situacaoPreInscricao.id", target = "idSituacaoPreInscricao")
    PreInsDTO toDto(PreInscricao preInscricao);




}
