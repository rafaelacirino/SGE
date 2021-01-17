package com.basis.sge.service.servico;

import com.basis.sge.service.dominio.IdPerguntaEventoInscricao;
import com.basis.sge.service.dominio.PreInscricao;
import com.basis.sge.service.dominio.RespostaPerguntaEvento;
import com.basis.sge.service.repositorio.RespostaPerguntaEventoRepositorio;
import com.basis.sge.service.servico.DTO.PreInsDTO;
import com.basis.sge.service.servico.DTO.RespostaPerguntaEventoDTO;
import com.basis.sge.service.servico.exception.RegraNegocioException;
import com.basis.sge.service.servico.mapper.RespostaPerguntaEventoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RespostaPerguntaEventoServico {

    private final RespostaPerguntaEventoRepositorio respostaPerguntaEventoRepositorio;
    private final RespostaPerguntaEventoMapper respostaPerguntaEventoMapper;

    public List<RespostaPerguntaEventoDTO> listar(){
        List<RespostaPerguntaEvento> respostaPerguntaEventos = respostaPerguntaEventoRepositorio.findAll();
        return respostaPerguntaEventoMapper.toDto(respostaPerguntaEventos);
    }

    public RespostaPerguntaEventoDTO buscarPorId(Integer id){
        RespostaPerguntaEvento respostaPerguntaEvento = respostaPerguntaEventoRepositorio.findById(id)
                .orElseThrow(()-> new com.basis.sge.service.servico.exception.RegraNegocioException("Não encontrado"));
        return respostaPerguntaEventoMapper.toDto(respostaPerguntaEvento);
    }

    public RespostaPerguntaEventoDTO salvar (RespostaPerguntaEventoDTO respostaPerguntaEventoDTO){
        if (respostaPerguntaEventoDTO == null){
            throw new RegraNegocioException("Não pode ser salvo");
        }
        if (respostaPerguntaEventoDTO.getId() == null){
            throw new RegraNegocioException("Não tem id");
        }
        if (respostaPerguntaEventoDTO.getPergunta() == null){
            throw new RegraNegocioException("Não tem pergunta");
        }
        if (respostaPerguntaEventoDTO.getEvento() == null){
            throw new RegraNegocioException("Não tem evento");
        }
        if (respostaPerguntaEventoDTO.getPreInscricao() == null){
            throw new RegraNegocioException("Não tem Pré Inscrição");
        }
        if (respostaPerguntaEventoDTO.getResposta() == null){
            throw new RegraNegocioException("Não tem respostas");
        }

        RespostaPerguntaEvento respostaPerguntaEvento = respostaPerguntaEventoRepositorio.save(respostaPerguntaEventoMapper.toEntity(respostaPerguntaEventoDTO));
        return respostaPerguntaEventoMapper.toDto(respostaPerguntaEvento);

    }

    public void delete (Integer id){
        if (!respostaPerguntaEventoRepositorio.existsById(id)){
            throw new RegraNegocioException("Não existe");
        }
        respostaPerguntaEventoRepositorio.deleteById(id);
    }
}