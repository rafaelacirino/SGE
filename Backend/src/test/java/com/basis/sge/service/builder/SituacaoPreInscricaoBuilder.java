package com.basis.sge.service.builder;


import com.basis.sge.service.dominio.SituacaoPreInscricao;
import com.basis.sge.service.servico.dto.SituacaoPreInscricaoDTO;
import com.basis.sge.service.servico.SituacaoPreInscricaoServico;
import com.basis.sge.service.servico.mapper.SituacaoPreInscricaoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.text.ParseException;
import java.util.Collection;

@Component
public class SituacaoPreInscricaoBuilder extends ConstrutorDeEntidade<SituacaoPreInscricao> {

    @Autowired
    private SituacaoPreInscricaoServico situacaoPreInscricaoServico;

    @Autowired
    private SituacaoPreInscricaoMapper situacaoPreInscricaoMapper;

    @Override
    public SituacaoPreInscricao construirEntidade() throws ParseException {

        SituacaoPreInscricao situacaoPreInscricao = new SituacaoPreInscricao();
        situacaoPreInscricao.setId(1);
        situacaoPreInscricao.setDescricao("Descrito");

        return situacaoPreInscricao;
    }

    @Override
    protected SituacaoPreInscricao persistir(SituacaoPreInscricao entidade) {
        SituacaoPreInscricaoDTO situacaoPreInscricaoDTO = situacaoPreInscricaoServico.salvar(situacaoPreInscricaoMapper.toDto(entidade));
        return situacaoPreInscricaoMapper.toEntity(situacaoPreInscricaoDTO);
    }

    @Override
    protected Collection<SituacaoPreInscricao> obterTodos() {
        return null;
    }

    @Override
    protected SituacaoPreInscricao obterPorId(Integer id) {
        return null;
    }
}