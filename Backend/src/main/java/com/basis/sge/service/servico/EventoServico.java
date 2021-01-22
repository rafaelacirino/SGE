package com.basis.sge.service.servico;

import com.basis.sge.service.dominio.Evento;
import com.basis.sge.service.dominio.EventoPergunta;
import com.basis.sge.service.repositorio.EventoPerguntaRepositorio;
import com.basis.sge.service.repositorio.EventoRepositorio;
import com.basis.sge.service.servico.DTO.EmailDTO;
import com.basis.sge.service.servico.DTO.EventoDTO;
import com.basis.sge.service.servico.DTO.PreInscricaoDTO;
import com.basis.sge.service.servico.DTO.UsuarioDTO;
import com.basis.sge.service.servico.exception.RegraNegocioException;
import com.basis.sge.service.servico.mapper.EventoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EventoServico {

    private final EventoRepositorio eventoRepositorio;
    private final EventoMapper eventoMapper;
    private final EmailServico emailServico;
    private final PreInscricaoServico preInscricaoServico;
    private final UsuarioServico usuarioServico;
    private final EventoPerguntaRepositorio eventoPerguntaRepositorio;

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
        if(eventoDTO.getIdTipoEvento() == null){
            throw new RegraNegocioException("O tipo do evento não pode ser vazio");
        }

        Evento evento = eventoMapper.toEntity(eventoDTO);
        List<EventoPergunta> perguntas = evento.getPerguntas();

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
        Evento evento = eventoRepositorio.save(eventoMapper.toEntity(eventoDTO));
        List<PreInscricaoDTO> preInscricaoDTOS = preInscricaoServico.buscarPreinscricaoPorIdEvento(eventoDTO.getId());
        List<UsuarioDTO> usuariosDtos = new ArrayList<>();

        for (PreInscricaoDTO preInscricao: preInscricaoDTOS) {
            usuariosDtos.add(usuarioServico.obterPorID(preInscricao.getIdUsuario()));
        }

        enviarEmail(usuariosDtos, eventoDTO.getTitulo());

        return eventoMapper.toDto(evento);
    }

    public void delete(Integer id){
        if(!eventoRepositorio.existsById(id)){
            throw new RegraNegocioException("Evento com esse id não existe");
        }
        eventoRepositorio.deleteById(id);
    }

    private void enviarEmail(List<UsuarioDTO> usuarioDTOS, String titulo){
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setAssunto("Aviso");
        emailDTO.setCorpo("O evento "+ titulo +" foi editado");
        emailDTO.setCopias(new ArrayList<>());

        for (UsuarioDTO usuarioDTO: usuarioDTOS) {
            emailDTO.setDestinatario(usuarioDTO.getEmail());
            emailServico.sendMail(emailDTO);
        }
    }

    /*public void criarEmailEventoEditar(String email, Evento evento){

        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setAssunto("Alteração do Evento");
        emailDTO.setCorpo("O Evento " + evento.getTitulo() + "sofreu alteração, confira demais informações no sistema.");
        emailDTO.setDestinatario(email);
        emailDTO.setCopias(new ArrayList<>());
        emailDTO.getCopias().add(emailDTO.getDestinatario());
        emailServico.sendMail(emailDTO);
    }*/
}