package com.basis.sge.service.servico;

import com.basis.sge.service.dominio.SituacaoPreInscricao;
import com.basis.sge.service.repositorio.SituacaoPreInscricaoRepositorio;
import com.basis.sge.service.servico.dto.SituacaoPreInscricaoDTO;
import com.basis.sge.service.servico.mapper.SituacaoPreInscricaoMapper;
import com.basis.sge.service.servico.exception.RegraNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SituacaoPreInscricaoServico {
    private final SituacaoPreInscricaoRepositorio situacaoPreInscricaoRepositorio;
    private final SituacaoPreInscricaoMapper situacaoPreInscricaoMapper;

    public List<SituacaoPreInscricaoDTO> listar(){
        List<SituacaoPreInscricao> situacaoPreInscricaos = situacaoPreInscricaoRepositorio.findAll();
        return situacaoPreInscricaoMapper.toDto(situacaoPreInscricaos);
    }

    public SituacaoPreInscricaoDTO salvar(SituacaoPreInscricaoDTO situacaoPreInscricaoDTO){
        SituacaoPreInscricao situacaoPreInscricao = situacaoPreInscricaoRepositorio.save(situacaoPreInscricaoMapper.toEntity(situacaoPreInscricaoDTO));
        return situacaoPreInscricaoMapper.toDto(situacaoPreInscricao);
    }


    public SituacaoPreInscricaoDTO buscarPorId(Integer id){
        SituacaoPreInscricao situacaoPreInscricao = situacaoPreInscricaoRepositorio.findById(id)
                .orElseThrow(()-> new RegraNegocioException("Situação não encontrado"));
        return situacaoPreInscricaoMapper.toDto(situacaoPreInscricao);
    }
}
