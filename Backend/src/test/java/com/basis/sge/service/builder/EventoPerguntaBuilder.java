package com.basis.sge.service.builder;

import com.basis.sge.service.dominio.Evento;
import com.basis.sge.service.dominio.EventoPergunta;
import com.basis.sge.service.dominio.Pergunta;
import com.basis.sge.service.dominio.PreInscricao;
import com.basis.sge.service.servico.EventoServico;
import com.basis.sge.service.servico.mapper.EventoMapper;
import com.basis.sge.service.servico.mapper.EventoPerguntaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;


@Component
public class EventoPerguntaBuilder extends ConstrutorDeEntidade<EventoPergunta> {

    @Autowired
    private PerguntaBuilder perguntaBuilder;

    @Override
    public EventoPergunta construirEntidade() throws ParseException {

        EventoPergunta eventoPergunta = new EventoPergunta();
        eventoPergunta.setEvento(null);
        eventoPergunta.setPergunta(this.perguntaBuilder.construirEntidade());

        return eventoPergunta;
    }

    @Override
    protected EventoPergunta persistir(EventoPergunta entidade) {
        return null;
    }

    @Override
    protected Collection<EventoPergunta> obterTodos() {
        return null;
    }

    @Override
    protected EventoPergunta obterPorId(Integer id) {
        return null;
    }

}
