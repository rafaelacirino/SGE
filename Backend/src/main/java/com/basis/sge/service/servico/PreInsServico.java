package com.basis.sge.service.servico;

import com.basis.sge.service.dominio.PreInscricao;
import com.basis.sge.service.repositorio.PreInsRepositorio;
import com.basis.sge.service.servico.DTO.PreInsDTO;
import com.basis.sge.service.servico.mapper.PreInsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PreInsServico {

    private final PreInsRepositorio preInsRepositorio;
    private final PreInsMapper preInsMapper;

    public List<PreInsDTO> listar(){
        List<PreInscricao> preInscricaos = preInsRepositorio.findAll();
        return preInsMapper.toDto(preInscricaos);
    }

    public PreInsDTO buscarPorId(Integer id){
        PreInscricao preInscricao = preInsRepositorio.findById(id).get();
        return preInsMapper.toDto(preInscricao);
    }

    public PreInsDTO salvar (PreInsDTO preInsDTO){
        return preInsMapper.toDto(preInsRepositorio.save(preInsMapper.toEntity(preInsDTO)));
    }

    public PreInsDTO atualizar (PreInsDTO preInsDTO){
        return preInsMapper.toDto(preInsRepositorio.save(preInsMapper.toEntity(preInsDTO)));
    }

    public void delete(Integer id){
        preInsRepositorio.deleteById(id);
    }
}
