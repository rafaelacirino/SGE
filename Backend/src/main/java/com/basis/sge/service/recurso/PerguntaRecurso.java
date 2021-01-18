package com.basis.sge.service.recurso;


import com.basis.sge.service.servico.DTO.PerguntaDTO;
import com.basis.sge.service.servico.PerguntaServico;
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
@RequestMapping("/api/perguntas")
@RequiredArgsConstructor
public class PerguntaRecurso {

    private final PerguntaServico perguntaServico;


    @GetMapping
    public ResponseEntity<List<PerguntaDTO>> listar()
    {
     return ResponseEntity.ok(perguntaServico.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerguntaDTO> obterPorId(@PathVariable Integer id){
        return ResponseEntity.ok(perguntaServico.obterPorId(id));
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<PerguntaDTO> salvar( @RequestBody PerguntaDTO perguntaDto){
        return ResponseEntity.created(new URI("/api/evento")).body(perguntaServico.salvar(perguntaDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PerguntaDTO> editar(@PathVariable Integer id, @RequestBody PerguntaDTO perguntaDTO){

        return ResponseEntity.ok(perguntaServico.editar(id, perguntaDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover (@PathVariable Integer id){
        perguntaServico.remover(id);
        return ResponseEntity.ok().build();

    }
}
