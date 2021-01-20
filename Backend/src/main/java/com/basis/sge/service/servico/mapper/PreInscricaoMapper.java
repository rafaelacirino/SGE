package com.basis.sge.service.servico.mapper;

import com.basis.sge.service.dominio.Evento;
import com.basis.sge.service.dominio.PreInscricao;
import com.basis.sge.service.servico.DTO.PreInscricaoDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {IncricaoRespostaMapper.class})
public interface PreInscricaoMapper extends EntityMapper<PreInscricaoDTO, PreInscricao>{
    @Override
    @Mapping(source = "idEvento", target = "evento.id")
    @Mapping(source = "idUsuario", target = "usuario.id")
    @Mapping(source = "idSituacaoPreInscricao", target = "situacaoPreInscricao.id")
    PreInscricao toEntity(PreInscricaoDTO preInscricaoDTO);

    @Override
    @Mapping(source = "evento.id", target = "idEvento")
    @Mapping(source = "usuario.id", target = "idUsuario")
    @Mapping(source = "situacaoPreInscricao.id", target = "idSituacaoPreInscricao")
    PreInscricaoDTO toDto(PreInscricao preInscricao);

/*    @AfterMapping
    default void atualizarRelacionamentos(@MappingTarget PreInscricao preInscricao) {
        preInscricao.getInscricaoRespostas().forEach(inscricaoResposta -> {
            inscricaoResposta.setPreInscricao(preInscricao);
        });
    }*/


}
