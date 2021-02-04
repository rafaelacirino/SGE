package com.basis.sge.service.builder;

import com.basis.sge.service.dominio.Evento;
import com.basis.sge.service.dominio.EventoPergunta;
import com.basis.sge.service.servico.dto.EventoDTO;
import com.basis.sge.service.servico.EventoServico;
import com.basis.sge.service.servico.mapper.EventoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class EventoBuilder extends ConstrutorDeEntidade<Evento> {

    @Autowired
    private EventoServico eventoServico;

    @Autowired
    private EventoMapper eventoMapper;

    @Autowired
    private TipoEventoBuilder tipoEventoBuilder;

    @Override
    public Evento construirEntidade() throws ParseException {

        List<EventoPergunta> eventoPerguntas = new ArrayList<>();

        Evento evento = new Evento();
        evento.setPerguntas(eventoPerguntas);
        evento.setTitulo("Titulo");
        evento.setPeriodoInicio(LocalDateTime.now());
        evento.setPeriodoFim(LocalDateTime.now());
        evento.setTipoInsc(true);
        evento.setDescricao("Descrição");
        evento.setQtdVagas(100);
        evento.setValor(30.00);
        evento.setLocal("Casa");
        evento.setTipoEvento(this.tipoEventoBuilder.construirEntidade());

        return evento;
    }

    @Override
    protected Evento persistir(Evento entidade) {
        EventoDTO eventoDTO = eventoServico.salvar(eventoMapper.toDto(entidade));
        return eventoMapper.toEntity(eventoDTO);
    }

    @Override
    protected Collection<Evento> obterTodos() {
        return eventoMapper.toEntity(eventoServico.listar());
    }

    @Override
    protected Evento obterPorId(Integer id) {
       return eventoMapper.toEntity(eventoServico.buscarPorId(id));
    }
}
