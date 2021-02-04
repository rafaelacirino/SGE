package com.basis.sge.service.builder;

import com.basis.sge.service.dominio.Evento;
import com.basis.sge.service.dominio.PreInscricao;
import com.basis.sge.service.servico.dto.PreInscricaoDTO;
import com.basis.sge.service.servico.PreInscricaoServico;
import com.basis.sge.service.servico.mapper.PreInscricaoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Component
public class PreInscricaoBuilder extends ConstrutorDeEntidade<PreInscricao> {

    @Autowired
    private PreInscricaoServico preInscricaoServico;

    @Autowired
    private PreInscricaoMapper preInscricaoMapper;

    @Autowired
    private EventoBuilder eventoBuilder;

    @Autowired
    private UsuarioBuilder usuarioBuilder;

    @Autowired
    private SituacaoPreInscricaoBuilder situacaoPreInscricaoBuilder;

    @Override
    public PreInscricao construirEntidade() throws ParseException {

        PreInscricao preInscricao = new PreInscricao();
        Evento evento = this.eventoBuilder.construir();

        preInscricao.setUsuario(this.usuarioBuilder.construir());
        preInscricao.setEvento(evento);
        preInscricao.setSituacaoPreInscricao(this.situacaoPreInscricaoBuilder.construir());

        preInscricao.setInscricaoRespostas(new ArrayList<>());

        return preInscricao;
    }

    @Override
    protected PreInscricao persistir(PreInscricao entidade) {
        PreInscricaoDTO preInscricaoDTO = preInscricaoServico.salvar(preInscricaoMapper.toDto(entidade));
        return preInscricaoMapper.toEntity(preInscricaoDTO);
    }

    @Override
    protected List<PreInscricao> obterTodos() {
        return preInscricaoMapper.toEntity(preInscricaoServico.listar());
    }

    @Override
    protected PreInscricao obterPorId(Integer id) {
        return preInscricaoMapper.toEntity(preInscricaoServico.buscarPorId(id));
    }
}