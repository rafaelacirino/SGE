package com.basis.sge.service.servico;

import com.basis.sge.service.dominio.SituacaoPreInscricao;
import com.basis.sge.service.repositorio.STPRepositorio;
import com.basis.sge.service.servico.DTO.STPDTO;
import com.basis.sge.service.servico.mapper.STPMapper;
import com.basis.sge.service.servico.exception.RegraNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class STPServico {
    private final STPRepositorio stpRepositorio;
    private final STPMapper stpMapper;

    public List<STPDTO> listar(){
        List<SituacaoPreInscricao> situacaoPreInscricaos = stpRepositorio.findAll();
        return stpMapper.toDto(situacaoPreInscricaos);
    }

    public STPDTO buscarPorId(Integer id){
        SituacaoPreInscricao situacaoPreInscricao = stpRepositorio.findById(id)
                .orElseThrow(()-> new RegraNegocioException("Situação não encontrado"));
        return stpMapper.toDto(situacaoPreInscricao);
    }

    public STPDTO salvar (STPDTO stpdto){
        if (stpdto == null){
            throw new RegraNegocioException("A situação é nula");
        }
        if (stpdto.getIdSituacao() == null){
            throw new RegraNegocioException("A situação não existe");
        }
        if (stpdto.getDescricao() == null){
            throw new RegraNegocioException("A descrição não existe");
        }
        SituacaoPreInscricao situacaoPreInscricao = stpRepositorio.save(stpMapper.toEntity(stpdto));
        return stpMapper.toDto(situacaoPreInscricao);
    }

    public STPDTO atualizar (Integer id, STPDTO stpdto){
        SituacaoPreInscricao situacaoPreInscricao = stpRepositorio.findById(id)
                .orElseThrow(()-> new RegraNegocioException("Situação não encontrada"));

        if (stpdto == null){
            throw new RegraNegocioException("A situação é nula");
        }

        situacaoPreInscricao.setDescricao(stpdto.getDescricao());

        return stpMapper.toDto(situacaoPreInscricao);
    }

    public void delete(Integer id){

        if(!stpRepositorio.existsById(id)){
            throw new RegraNegocioException("Situação não existe");
        }
        stpRepositorio.deleteById(id);
    }
}
