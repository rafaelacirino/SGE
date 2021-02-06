package com.basis.sge.service.servico;

import com.basis.sge.service.dominio.Evento;
import com.basis.sge.service.dominio.EventoPergunta;
import com.basis.sge.service.dominio.Pergunta;
import com.basis.sge.service.repositorio.EventoPerguntaRepositorio;
import com.basis.sge.service.repositorio.EventoRepositorio;
import com.basis.sge.service.repositorio.PerguntaRepositorio;
import com.basis.sge.service.repositorio.PreInscricaoRepositorio;
import com.basis.sge.service.servico.dto.EmailDTO;
import com.basis.sge.service.servico.dto.EventoDTO;
import com.basis.sge.service.servico.dto.PreInscricaoDTO;
import com.basis.sge.service.servico.dto.UsuarioDTO;
import com.basis.sge.service.servico.exception.RegraNegocioException;
import com.basis.sge.service.servico.mapper.EventoMapper;
import com.basis.sge.service.servico.producer.SgeProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EventoServico {

    private final EventoRepositorio eventoRepositorio;
    private final EventoMapper eventoMapper;
    private final PreInscricaoServico preInscricaoServico;
    private final UsuarioServico usuarioServico;
    private final EventoPerguntaRepositorio eventoPerguntaRepositorio;
    private final PreInscricaoRepositorio preInscricaoRepositorio;
    private final PerguntaRepositorio perguntaRepositorio;
    private final SgeProducer sgeProducer;

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

        Evento evento = eventoMapper.toEntity(eventoDTO);
        List<EventoPergunta> perguntas = evento.getPerguntas();

        if (eventoDTO.getPeriodoFim().isBefore(eventoDTO.getPeriodoInicio())
                || eventoDTO.getPeriodoInicio().isBefore(LocalDateTime.now())){
            throw new RegraNegocioException("Data do evento inválida");
        }
        evento.setPerguntas(new ArrayList<>());
        eventoRepositorio.save(evento);

        perguntas.forEach(pergunta -> pergunta.setEvento(evento));

        eventoPerguntaRepositorio.saveAll(perguntas);
        return eventoMapper.toDto(evento);
    }

    public EventoDTO atualizar(EventoDTO eventoDTO){
        if(!eventoRepositorio.existsById(eventoDTO.getId())){
            throw new RegraNegocioException("Evento não existe na base de dados");
        }

        Evento eventopergunta = eventoRepositorio.findById(eventoDTO.getId()).orElseThrow(()
                        -> new RegraNegocioException("Evento não encontrado"));

        List<Pergunta> listPergunta = new ArrayList<>();
        eventopergunta.getPerguntas().forEach(eventoPergunta -> listPergunta.add(perguntaRepositorio.
                findById(eventoPergunta.getPergunta().getId()).orElseThrow(
                        () -> new RegraNegocioException("Pergunta não encontrado"))));

        Evento evento = eventoRepositorio.save(eventoMapper.toEntity(eventoDTO));
        List<PreInscricaoDTO> preInscricaoDTOS = preInscricaoServico.buscarPreinscricaoPorIdEvento(eventoDTO.getId());
        List<UsuarioDTO> usuariosDtos = new ArrayList<>();

        if (eventoDTO.getPeriodoFim().isBefore(eventoDTO.getPeriodoInicio())
                || eventoDTO.getPeriodoInicio().isBefore(LocalDateTime.now())){
            throw new RegraNegocioException("Data do evento inválida");
        }
        for (PreInscricaoDTO preInscricao: preInscricaoDTOS) {
            usuariosDtos.add(usuarioServico.obterPorID(preInscricao.getIdUsuario()));
        }

        enviarEmail(usuariosDtos, eventoDTO.getTitulo());
        return eventoMapper.toDto(evento);
    }

    public void remover(Integer id){
        if(!eventoRepositorio.existsById(id)){
            throw new RegraNegocioException("Evento com esse id não existe");
        }

        preInscricaoRepositorio.deleteAllByEvento(eventoRepositorio.findById(id).orElseThrow(()
                -> new RegraNegocioException("O evento não foi cadastrado")));
        eventoRepositorio.deleteById(id);
    }

    private void enviarEmail(List<UsuarioDTO> usuarioDTOS, String titulo){
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setAssunto("Aviso");
        emailDTO.setCorpo("O evento "+ titulo +" foi editado");
        emailDTO.setCopias(new ArrayList<>());

        for (UsuarioDTO usuarioDTO: usuarioDTOS) {
            emailDTO.setDestinatario(usuarioDTO.getEmail());
            this.sgeProducer.sendMail(emailDTO);
        }
    }
}