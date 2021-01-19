package com.basis.sge.service.recurso;

import com.basis.sge.service.dominio.IdPerguntaEventoInscricao;
import com.basis.sge.service.servico.DTO.RespostaPerguntaEventoDTO;
import com.basis.sge.service.servico.RespostaPerguntaEventoServico;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/respostaperguntaeventorecurso")
@RequiredArgsConstructor
public class RespostaPerguntaEventoRecurso {
    private final RespostaPerguntaEventoServico respostaPerguntaEventoServico;

    @GetMapping
    public ResponseEntity<List<RespostaPerguntaEventoDTO>> listar(){
        return  ResponseEntity.ok(respostaPerguntaEventoServico.listar());
    }

    @GetMapping ("/{id}")
    public ResponseEntity<RespostaPerguntaEventoDTO> buscarPorId(@PathVariable IdPerguntaEventoInscricao id){
        return ResponseEntity.ok(respostaPerguntaEventoServico.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<RespostaPerguntaEventoDTO> salvar (@RequestBody RespostaPerguntaEventoDTO respostaPerguntaEventoDTO){
        return ResponseEntity.ok(respostaPerguntaEventoServico.salvar(respostaPerguntaEventoDTO));
    }

    @DeleteMapping
    public ResponseEntity<Void> remover(@PathVariable Integer id){
        return ResponseEntity.ok().build();
    }
}
