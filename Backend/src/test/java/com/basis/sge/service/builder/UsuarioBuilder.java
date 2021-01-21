package com.basis.sge.service.builder;

import com.basis.sge.service.dominio.Usuario;
import com.basis.sge.service.servico.DTO.UsuarioDTO;
import com.basis.sge.service.servico.UsuarioServico;
import com.basis.sge.service.servico.mapper.UsuarioMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;

@Component
public class UsuarioBuilder extends ConstrutorDeEntidade<Usuario> {

    @Autowired
    private UsuarioServico usuarioServico;

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Override
    public Usuario construirEntidade() throws ParseException {

        Usuario usuario = new Usuario();
        usuario.setCpf("37128105042");
        usuario.setDataNascimento(new Date());
        usuario.setEmail("teste@gmail.com");
        usuario.setNome("Usuário de teste");
        usuario.setTelefone("999999999");

        return usuario;
    }

    @Override
    protected Usuario persistir(Usuario entidade) {
        UsuarioDTO usuarioDTO = usuarioServico.salvar(usuarioMapper.toDto(entidade));
        return usuarioMapper.toEntity(usuarioDTO);
    }

    @Override
    protected Collection<Usuario> obterTodos() {
        return usuarioMapper.toEntity(usuarioServico.listar());
    }

    @Override
    protected Usuario obterPorId(Integer id) {
        return usuarioMapper.toEntity(usuarioServico.obterPorID(id));
    }
}
