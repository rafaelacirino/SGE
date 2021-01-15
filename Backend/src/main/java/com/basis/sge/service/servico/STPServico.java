package com.basis.sge.service.servico;

import com.basis.sge.service.dominio.SituacaoPreInscricao;
import com.basis.sge.service.repositorio.STPRepositorio;
import com.basis.sge.service.servico.DTO.STPDTO;
import com.basis.sge.service.servico.mapper.STPMapper;
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
        SituacaoPreInscricao situacaoPreInscricao = stpRepositorio.findById(id).get();
        return stpMapper.toDto(situacaoPreInscricao);
    }

    public STPDTO salvar (STPDTO stpdto){
        return stpMapper.toDto(stpRepositorio.save(stpMapper.toEntity(stpdto)));
    }

    public STPDTO atualizar (STPDTO stpdto){
        return stpMapper.toDto(stpRepositorio.save(stpMapper.toEntity(stpdto)));
    }

    public void delete(Integer id){
        stpRepositorio.deleteById(id);
    }
}
