package com.basis.sge.service.builder;

import com.basis.sge.service.dominio.Pergunta;
import com.basis.sge.service.dominio.PreInscricao;
import com.basis.sge.service.dominio.Usuario;
import com.basis.sge.service.servico.DTO.PerguntaDTO;
import com.basis.sge.service.servico.DTO.PreInscricaoDTO;
import com.basis.sge.service.servico.PerguntaServico;
import com.basis.sge.service.servico.PreInscricaoServico;
import com.basis.sge.service.servico.UsuarioServico;
import com.basis.sge.service.servico.mapper.PerguntaMapper;
import com.basis.sge.service.servico.mapper.PreInscricaoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Collection;

@Component
public class PreInscricaoBuilder extends ConstrutorDeEntidade<PreInscricao> {

    @Autowired
    private PreInscricaoServico preInscricaoServico;

    @Autowired
    private PreInscricaoMapper preInscricaoMapper;


    @Override
    public PreInscricao construirEntidade() throws ParseException {

        PreInscricao preInscricao = new PreInscricao();
        preInscricao.setUsuario();
        preInscricao.setEvento();
        preInscricao.setSituacaoPreInscricao();
        preInscricao.setInscricaoRespostas();

        return preInscricao;
    }

    @Override
    protected PreInscricao persistir(PreInscricao entidade) {
        PreInscricaoDTO preInscricaoDTO = preInscricaoServico.salvar(preInscricaoMapper.toDto(entidade));
        return preInscricaoMapper.toEntity(preInscricaoDTO);
    }

    @Override
    protected Collection<PreInscricao> obterTodos() {
        return preInscricaoMapper.toEntity(preInscricaoServico.listar());
    }

    @Override
    protected PreInscricao obterPorId(Integer id) {
        return preInscricaoMapper.toEntity(preInscricaoServico.buscarPorId(id));
    }


}