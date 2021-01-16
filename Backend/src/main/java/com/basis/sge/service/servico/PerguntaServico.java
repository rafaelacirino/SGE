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
        PerguntaDTO perguntaDTO = perguntaMapper.toDto(pergunta);
        return perguntaDTO;
    }

    // POST
    public PerguntaDTO salvar(PerguntaDTO perguntaDto){
        Integer perguntaId = perguntaDto.getId();

        if(perguntaRepositorio.findById(perguntaId) != null){
            throw new RegraNegocioException("Pergunta já cadastrada");
        }
        if (perguntaDto == null){
            throw new RegraNegocioException("A pergunta é nula");
        }
        if(perguntaDto.getTitulo() == null){
            throw new RegraNegocioException("A pergunta não possui titulo");
        }
        if (perguntaDto.getObrigatorio() == null){
            throw new RegraNegocioException("A obrigatoriedade não existe");
        }
        Pergunta pergunta = perguntaMapper.toEntity(perguntaDto);

        return perguntaDto;
    }

    //PUT
    public PerguntaDTO editar (Integer id, PerguntaDTO perguntaDto){

        Pergunta pergunta = perguntaRepositorio.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Pergunta não existe"));
        if(perguntaDto == null){
            throw new RegraNegocioException("A pergunta é nula");
        }

        //DELETA
        perguntaRepositorio.delete(pergunta);
        //SUBSTITUIÇÃO
        pergunta = perguntaMapper.toEntity(perguntaDto);
        //SALVO
        perguntaRepositorio.save(pergunta);

        return null;
    }

    // DELETE
    public void remover(Integer id){

        if(perguntaRepositorio.findById(id) == null){
            throw new RegraNegocioException("Pergunta não existe");
        }
        perguntaRepositorio.deleteById(id);
    }
}
