package com.basis.sge.service.servico;

import com.basis.sge.service.dominio.PreInscricao;
import com.basis.sge.service.repositorio.PreInsRepositorio;
import com.basis.sge.service.servico.DTO.PreInsDTO;
import com.basis.sge.service.servico.mapper.PreInsMapper;
import com.basis.sge.service.servico.exception.RegraNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
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
        if (preInsDTO.getIdUsuario() == null){
            throw new RegraNegocioException("A pré inscrição não tem usuário");
        }
        if (preInsDTO.getIdSituacaoPreInscricao() == null){
            throw new RegraNegocioException("A pré inscrição não tem situação");
        }

        PreInscricao preInscricao = preInsRepositorio.save(preInsMapper.toEntity(preInsDTO));
        return preInsMapper.toDto(preInscricao);

    }

    public List<PreInsDTO> buscarPreinscricaoPorIdEvento(Integer id){
        List<PreInsDTO> preInscricoesPorIdEvento = new ArrayList<PreInsDTO>();
        List<PreInsDTO> preInscricoes = preInsMapper.toDto(preInsRepositorio.findAll());
        for (PreInsDTO preInscricao: preInscricoes) {
            if(preInscricao.getIdEvento() == id){
                preInscricoesPorIdEvento.add(preInscricao);
            }
        }
        return preInscricoesPorIdEvento;
    }

    public void delete(Integer id){
        if (!preInsRepositorio.existsById(id)){
            throw new RegraNegocioException("Pré Inscrição não existe");
        }
        preInsRepositorio.deleteById(id);
    }
}
