package com.basis.sge.service.builder;


import com.basis.sge.service.dominio.TipoEvento;
import com.basis.sge.service.servico.dto.TipoEventoDTO;
import com.basis.sge.service.servico.TipoEventoServico;
import com.basis.sge.service.servico.mapper.TipoEventoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.text.ParseException;
import java.util.Collection;

@Component
public class TipoEventoBuilder extends ConstrutorDeEntidade<TipoEvento> {

    @Autowired
    private TipoEventoServico tipoEventoServico;

    @Autowired
    private TipoEventoMapper tipoEventoMapper;

    @Override
    public TipoEvento construirEntidade() throws ParseException {

        TipoEvento tipoEvento = new TipoEvento();
        tipoEvento.setId(1);
        tipoEvento.setDescricao("Evento 123342234");

        return tipoEvento;
    }

    @Override
    protected TipoEvento persistir(TipoEvento entidade) {
        TipoEventoDTO eventoDTO = tipoEventoServico.salvar(tipoEventoMapper.toDto(entidade));
        return tipoEventoMapper.toEntity(eventoDTO);
    }

    @Override
    protected Collection<TipoEvento> obterTodos() {
        return null;
    }

    @Override
    protected TipoEvento obterPorId(Integer id) {
        return null;
    }
}