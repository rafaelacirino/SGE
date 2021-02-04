package com.basis.sge.service.builder;

import com.basis.sge.service.dominio.Pergunta;
import com.basis.sge.service.servico.dto.PerguntaDTO;
import com.basis.sge.service.servico.PerguntaServico;
import com.basis.sge.service.servico.mapper.PerguntaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.text.ParseException;
import java.util.Collection;

@Component
public class PerguntaBuilder extends ConstrutorDeEntidade<Pergunta> {

    @Autowired
    private PerguntaServico perguntaServico;

    @Autowired
    private PerguntaMapper perguntaMapper;

    @Override
    public Pergunta construirEntidade() throws ParseException {

        Pergunta pergunta = new Pergunta();
        pergunta.setTitulo("Titulo");
        pergunta.setObrigatorio(true);

        return pergunta;
    }

    @Override
    protected Pergunta persistir(Pergunta entidade) {
        PerguntaDTO perguntaDTO = perguntaServico.salvar(perguntaMapper.toDto(entidade));
        return perguntaMapper.toEntity(perguntaDTO);
    }

    @Override
    protected Collection<Pergunta> obterTodos() {
        return perguntaMapper.toEntity(perguntaServico.listar());
    }

    @Override
    protected Pergunta obterPorId(Integer id) {
        return perguntaMapper.toEntity(perguntaServico.obterPorId(id));
    }


}