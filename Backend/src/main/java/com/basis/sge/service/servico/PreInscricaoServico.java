package com.basis.sge.service.servico;

import com.basis.sge.service.dominio.PreInscricao;
import com.basis.sge.service.repositorio.PreInscricaoRepositorio;
import com.basis.sge.service.servico.DTO.PreInscricaoDTO;
import com.basis.sge.service.servico.mapper.PreInscricaoMapper;
import com.basis.sge.service.servico.exception.RegraNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PreInscricaoServico {

    private final PreInscricaoRepositorio preInscricaoRepositorio;
    private final PreInscricaoMapper preInscricaoMapper;

    public List<PreInscricaoDTO> listar(){
        List<PreInscricao> preInscricaos = preInscricaoRepositorio.findAll();
        return preInscricaoMapper.toDto(preInscricaos);
    }

    public PreInscricaoDTO buscarPorId(Integer id){
        PreInscricao preInscricao = preInscricaoRepositorio.findById(id)
                .orElseThrow(()-> new RegraNegocioException("Pré Inscrição não encontrado"));
        return preInscricaoMapper.toDto(preInscricao);
    }

    public PreInscricaoDTO salvar (PreInscricaoDTO preInscricaoDTO){
        if (preInscricaoDTO == null){
            throw new RegraNegocioException("A pré inscrição não pode ser criada");
        }
        if (preInscricaoDTO.getIdUsuario() == null){
            throw new RegraNegocioException("A pré inscrição não tem usuário");
        }
        if (preInscricaoDTO.getIdSituacaoPreInscricao() == null){
            throw new RegraNegocioException("A pré inscrição não tem situação");
        }

        PreInscricao preInscricao = preInscricaoRepositorio.save(preInscricaoMapper.toEntity(preInscricaoDTO));
        return preInscricaoMapper.toDto(preInscricao);

    }

    public List<PreInscricaoDTO> buscarPreinscricaoPorIdEvento(Integer id){
        List<PreInscricaoDTO> preInscricoesPorIdEvento = new ArrayList<PreInscricaoDTO>();
        List<PreInscricaoDTO> preInscricoes = preInscricaoMapper.toDto(preInscricaoRepositorio.findAll());
        for (PreInscricaoDTO preInscricao: preInscricoes) {
            if(preInscricao.getIdEvento() == id){
                preInscricoesPorIdEvento.add(preInscricao);
            }
        }
        return preInscricoesPorIdEvento;
    }

    public void delete(Integer id){
        if (!preInscricaoRepositorio.existsById(id)){
            throw new RegraNegocioException("Pré Inscrição não existe");
        }
        preInscricaoRepositorio.deleteById(id);
    }
}