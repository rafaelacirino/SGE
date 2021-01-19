package com.basis.sge.service.servico;

import com.basis.sge.service.dominio.SituacaoPreInscricao;
import com.basis.sge.service.repositorio.SituacaoPreInscricaoRepositorio;
import com.basis.sge.service.servico.DTO.SituacaoPreInscricaoDTO;
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

    public SituacaoPreInscricaoDTO buscarPorId(Integer id){
        SituacaoPreInscricao situacaoPreInscricao = situacaoPreInscricaoRepositorio.findById(id)
                .orElseThrow(()-> new RegraNegocioException("Situação não encontrado"));
        return situacaoPreInscricaoMapper.toDto(situacaoPreInscricao);
    }

    public SituacaoPreInscricaoDTO salvar (SituacaoPreInscricaoDTO situacaoPreInscricaoDTO){
        if (situacaoPreInscricaoDTO == null){
            throw new RegraNegocioException("A situação é nula");
        }
        if (situacaoPreInscricaoDTO.getIdSituacao() == null){
            throw new RegraNegocioException("A situação não existe");
        }
        if (situacaoPreInscricaoDTO.getDescricao() == null){
            throw new RegraNegocioException("A descrição não existe");
        }
        SituacaoPreInscricao situacaoPreInscricao = situacaoPreInscricaoRepositorio.save(situacaoPreInscricaoMapper.toEntity(situacaoPreInscricaoDTO));
        return situacaoPreInscricaoMapper.toDto(situacaoPreInscricao);
    }

    public SituacaoPreInscricaoDTO atualizar (Integer id, SituacaoPreInscricaoDTO situacaoPreInscricaoDTO){
        SituacaoPreInscricao situacaoPreInscricao = situacaoPreInscricaoRepositorio.findById(id)
                .orElseThrow(()-> new RegraNegocioException("Situação não encontrada"));

        if (situacaoPreInscricaoDTO == null){
            throw new RegraNegocioException("A situação é nula");
        }

        situacaoPreInscricao.setDescricao(situacaoPreInscricaoDTO.getDescricao());

        return situacaoPreInscricaoMapper.toDto(situacaoPreInscricao);
    }

    public void delete(Integer id){

        if(!situacaoPreInscricaoRepositorio.existsById(id)){
            throw new RegraNegocioException("Situação não existe");
        }
        situacaoPreInscricaoRepositorio.deleteById(id);
    }
}
