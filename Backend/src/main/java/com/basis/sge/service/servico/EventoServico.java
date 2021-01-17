package com.basis.sge.service.servico;

import com.basis.sge.service.dominio.Evento;
import com.basis.sge.service.dominio.Usuario;
import com.basis.sge.service.repositorio.EventoRepositorio;
import com.basis.sge.service.servico.DTO.EmailDTO;
import com.basis.sge.service.servico.DTO.EventoDTO;
import com.basis.sge.service.servico.exception.RegraNegocioException;
import com.basis.sge.service.servico.mapper.EventoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EventoServico {

    private final EventoRepositorio eventoRepositorio;
    private final EventoMapper eventoMapper;

    public List<EventoDTO> listar(){
        List<Evento> eventos = eventoRepositorio.findAll();
        return eventoMapper.toDto(eventos);
    }

    public EventoDTO buscarPorId(Integer id){
        Evento evento = eventoRepositorio.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Evento não encontrado"));
        return eventoMapper.toDto(evento);
    }

    public EventoDTO salvar(EventoDTO eventoDTO){
        if(eventoDTO.getTitulo() == null){
            throw new RegraNegocioException("Titulo do evento não pode ser vazio");
        }
        if(eventoDTO.getPeriodoInicio() == null){
            throw new RegraNegocioException("Periodo inicio não pode ser vazio");
        }
        if(eventoDTO.getPeriodoFim() == null){
            throw new RegraNegocioException("Periodo inicio não pode ser vazio");
        }
        if(eventoDTO.getTipoInsc() == null){
            throw new RegraNegocioException("Tipo Inscricao não pode ser vazio");
        }
        return eventoMapper.toDto(eventoRepositorio.save(eventoMapper.toEntity(eventoDTO)));
    }

    public EventoDTO atualizar(EventoDTO eventoDTO){
        EmailDTO emailDTO = new EmailDTO();

        if(!eventoRepositorio.existsById(eventoDTO.getId())){
            throw new RegraNegocioException("Evento não existe na base de dados");
        }
        Evento evento = eventoRepositorio.save(eventoMapper.toEntity(eventoDTO));

        return eventoMapper.toDto(evento);
    }

    public void delete(Integer id){
        if(!eventoRepositorio.existsById(id)){
            throw new RegraNegocioException("Evento com esse id não existe");
        }
        eventoRepositorio.deleteById(id);
    }


}
