package com.basis.sge.service.servico;

import com.basis.sge.service.dominio.PreInscricao;
import com.basis.sge.service.repositorio.PreInsRepositorio;
import com.basis.sge.service.servico.DTO.PreInsDTO;
import com.basis.sge.service.servico.mapper.PreInsMapper;
import com.basis.sge.service.servico.exception.RegraNegocioException;
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
        PreInscricao preInscricao = preInsRepositorio.findById(id)
                .orElseThrow(()-> new RegraNegocioException("Pré Inscrição não encontrado"));
        return preInsMapper.toDto(preInscricao);
    }

    public PreInsDTO salvar (PreInsDTO preInsDTO){
        if (preInsDTO == null){
            throw new RegraNegocioException("A pré inscrição não pode ser criada");
        }
        if (preInsDTO.getId() == null){
            throw new RegraNegocioException("A pré inscrição não tem id");
        }
        if (preInsDTO.getUsuario() == null){
            throw new RegraNegocioException("A pré inscrição não tem usuário");
        }
        if (preInsDTO.getEvento() == null){
            throw new RegraNegocioException("A pré inscrição não tem Evento");
        }
        if (preInsDTO.getSituacaoPreInscricao() == null){
            throw new RegraNegocioException("A pré inscrição não tem situação");
        }

        PreInscricao preInscricao = preInsRepositorio.save(preInsMapper.toEntity(preInsDTO));
        return preInsMapper.toDto(preInscricao);

    }

    public void delete(Integer id){
        if (!preInsRepositorio.existsById(id)){
            throw new RegraNegocioException("Pré Inscrição não existe");
        }
        preInsRepositorio.deleteById(id);
    }
}
