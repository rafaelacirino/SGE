package com.basis.sge.service.servico;

import com.basis.sge.service.dominio.Pergunta;
import com.basis.sge.service.repositorio.PerguntaRepositorio;
import com.basis.sge.service.servico.DTO.PerguntaDTO;
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


    // GET (LISTA)
    public List<PerguntaDTO> listar(){
        List<Pergunta> perguntas = perguntaRepositorio.findAll();
        return perguntaMapper.toDto(perguntas);
    }

    // GET (ID)
    public PerguntaDTO obterPorId(Integer id){
        Pergunta pergunta = perguntaRepositorio.findById(id)
                .orElseThrow(()-> new RegraNegocioException("Pergunta não encontrado"));
        // Conversão para DTO
        return perguntaMapper.toDto(pergunta);
    }

    // POST
    public PerguntaDTO salvar(PerguntaDTO perguntaDto){
        // EXCEPTION PERGUNTA NULA
        if (perguntaDto == null){
            throw new RegraNegocioException("A pergunta é nula");
        }
        //EXCEPTION TITULO
        if(perguntaDto.getTitulo() == null){
            throw new RegraNegocioException("A pergunta não possui titulo");
        }

        if (perguntaDto.getObrigatorio() == null){
            throw new RegraNegocioException("A obrigatoriedade não existe");
        }

        Pergunta pergunta = perguntaRepositorio.save(perguntaMapper.toEntity(perguntaDto));

        return perguntaMapper.toDto(pergunta);

    }

    //PUT
    public PerguntaDTO editar (Integer id, PerguntaDTO perguntaDto){
        Pergunta pergunta = perguntaRepositorio.findById(id)
                .orElseThrow(()-> new RegraNegocioException("Pergunta não encontrada"));
        // EXCEPTION PERGUNTA NULA
        if(perguntaDto == null){
            throw new RegraNegocioException("A pergunta é nula");
        }

        //EXCEPTION TITULO
        List<Pergunta> list = perguntaRepositorio.findByTitulo(perguntaDto.getTitulo());
        list.remove(pergunta);

        if (perguntaDto.getTitulo() == null){
            throw new RegraNegocioException("A pergunta não possui titulo");
        }

        else if(list.isEmpty() != true){
            throw new RegraNegocioException("Pergunta já existe");
        }
        else{
             pergunta.setTitulo(perguntaDto.getTitulo());
        }
        //EXCEPTION OBRIGATORIO
        if (perguntaDto.getObrigatorio() == null){
            throw new RegraNegocioException("A obrigatoriedade é nula");
        }

        pergunta.setObrigatorio(perguntaDto.getObrigatorio());

        return perguntaMapper.toDto(pergunta);
    }

    // DELETE
    public void remover(Integer id){

        if(!perguntaRepositorio.existsById(id)){
            throw new RegraNegocioException("Pergunta não existe");
        }
        perguntaRepositorio.deleteById(id);
    }
}
