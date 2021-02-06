package com.basis.sge.service.servico;

import com.basis.sge.service.dominio.Pergunta;
import com.basis.sge.service.repositorio.PerguntaRepositorio;
import com.basis.sge.service.servico.dto.PerguntaDTO;
import com.basis.sge.service.servico.exception.RegraNegocioException;
import com.basis.sge.service.servico.mapper.PerguntaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PerguntaServico {

    private final PerguntaRepositorio perguntaRepositorio;
    private final PerguntaMapper perguntaMapper;

    public List<PerguntaDTO> listar(){
        List<Pergunta> perguntas = perguntaRepositorio.findAll();
        return perguntaMapper.toDto(perguntas);
    }

    public PerguntaDTO obterPorId(Integer id){
        Pergunta pergunta = perguntaRepositorio.findById(id)
                .orElseThrow(()-> new RegraNegocioException("Pergunta não encontrado"));

        return perguntaMapper.toDto(pergunta);
    }

    public PerguntaDTO salvar(PerguntaDTO perguntaDto){

        if( !perguntaRepositorio.findByTitulo(perguntaDto.getTitulo()).isEmpty() ){
            throw new RegraNegocioException("A pergunta já existe");
        }

        Pergunta pergunta = perguntaRepositorio.save(perguntaMapper.toEntity(perguntaDto));
        return perguntaMapper.toDto(pergunta);
    }

    public PerguntaDTO editar( PerguntaDTO perguntaDto){
        Pergunta pergunta = perguntaRepositorio.findById(perguntaDto.getId())
                .orElseThrow(()-> new RegraNegocioException("Pergunta não encontrada"));

        List<Pergunta> list = perguntaRepositorio.findByTitulo(perguntaDto.getTitulo());
        list.remove(pergunta);

        if(!list.isEmpty()){
            throw new RegraNegocioException("Pergunta já existe");
        }

        perguntaRepositorio.save(perguntaMapper.toEntity(perguntaDto));
        return perguntaMapper.toDto(pergunta);
    }

    public void remover(Integer id){

        if(!perguntaRepositorio.existsById(id)){
            throw new RegraNegocioException("Pergunta não existe");
        }

        perguntaRepositorio.deleteById(id);
    }
}
