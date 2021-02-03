package com.basis.sge.service.recurso;

import com.basis.sge.service.servico.DTO.ChaveUsuarioDTO;
import com.basis.sge.service.servico.DTO.UsuarioDTO;
import com.basis.sge.service.servico.UsuarioServico;
import lombok.RequiredArgsConstructor;

import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioRecurso {

    private final UsuarioServico usuarioServico;

    @GetMapping
    private ResponseEntity<List<UsuarioDTO>> listar(){

        return ResponseEntity.ok( usuarioServico.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO>obterPorID(@PathVariable Integer id){
        return ResponseEntity.ok(usuarioServico.obterPorID(id));
    }

    @PostMapping("/login")
    public ResponseEntity<UsuarioDTO>obterPorChave(@RequestBody ChaveUsuarioDTO chaveUsuarioDTO){
        return ResponseEntity.ok(usuarioServico.obterUsuarioPorChave(chaveUsuarioDTO));
    }
    @SneakyThrows
    @PostMapping
    public ResponseEntity<UsuarioDTO>salvar(@RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.created(new URI("/api/usuarios")).body(usuarioServico.salvar(usuarioDTO));
    }

    @PutMapping
    public ResponseEntity<UsuarioDTO> editar( @RequestBody UsuarioDTO usuarioDTO ){
        return ResponseEntity.ok(usuarioServico.editar(usuarioDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>remover(@PathVariable Integer id){
        usuarioServico.remover(id);
        return ResponseEntity.ok().build();
    }
}
