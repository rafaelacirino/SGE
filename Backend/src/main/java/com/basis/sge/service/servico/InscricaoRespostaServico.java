package com.basis.sge.service.servico;

import com.basis.sge.service.dominio.IdInscricaoResposta;
import com.basis.sge.service.dominio.InscricaoResposta;
import com.basis.sge.service.repositorio.InscricaoRespostaRepositorio;
import com.basis.sge.service.servico.DTO.InscricaoRespostaDTO;
import com.basis.sge.service.servico.exception.RegraNegocioException;
import com.basis.sge.service.servico.mapper.IncricaoRespostaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class InscricaoRespostaServico {

    private final InscricaoRespostaRepositorio inscricaoRespostaRepositorio;
    private final IncricaoRespostaMapper incricaoRespostaMapper;

    public List<InscricaoRespostaDTO> listar(){
        List<InscricaoResposta> inscricaoRespostas = inscricaoRespostaRepositorio.findAll();
        return incricaoRespostaMapper.toDto(inscricaoRespostas);
    }

    public InscricaoRespostaDTO buscarPorId(IdInscricaoResposta id){
        InscricaoResposta inscricaoResposta = inscricaoRespostaRepositorio.findById(id)
                .orElseThrow(()-> new com.basis.sge.service.servico.exception.RegraNegocioException("Não encontrado"));
        return incricaoRespostaMapper.toDto(inscricaoResposta);
    }

    public InscricaoRespostaDTO salvar (InscricaoRespostaDTO inscricaoRespostaDTO){
        if (inscricaoRespostaDTO == null){
            throw new RegraNegocioException("Não pode ser salvo");
        }
        if (inscricaoRespostaDTO.getId() == null){
            throw new RegraNegocioException("Não tem id");
        }
        if (inscricaoRespostaDTO.getIdPergunta() == null){
            throw new RegraNegocioException("Não tem pergunta");
        }
        if (inscricaoRespostaDTO.getIdEvento() == null){
            throw new RegraNegocioException("Não tem evento");
        }
        if (inscricaoRespostaDTO.getIdPreInscricao() == null){
            throw new RegraNegocioException("Não tem Pré Inscrição");
        }
        if (inscricaoRespostaDTO.getResposta() == null){
            throw new RegraNegocioException("Não tem respostas");
        }

        InscricaoResposta inscricaoResposta = inscricaoRespostaRepositorio.save(incricaoRespostaMapper.toEntity(inscricaoRespostaDTO));
        return incricaoRespostaMapper.toDto(inscricaoResposta);

    }

    public void delete (IdInscricaoResposta id){
        if (!inscricaoRespostaRepositorio.existsById(id)){
            throw new RegraNegocioException("Não existe");
        }
        inscricaoRespostaRepositorio.deleteById(id);
    }
}
