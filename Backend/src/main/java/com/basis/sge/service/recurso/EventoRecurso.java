package com.basis.sge.service.recurso;

import com.basis.sge.service.servico.dto.EventoDTO;
import com.basis.sge.service.servico.EventoServico;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/eventos")
@RequiredArgsConstructor
public class EventoRecurso {

    private final EventoServico eventoServico;

    @GetMapping
    public ResponseEntity<List<EventoDTO>> listar(){
        return ResponseEntity.ok(eventoServico.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoDTO> buscarPorId(@PathVariable Integer id){
        return ResponseEntity.ok(eventoServico.buscarPorId(id));
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<EventoDTO> salvar(@RequestBody EventoDTO eventoDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(eventoServico.salvar(eventoDTO));
    }

    @PutMapping
    public ResponseEntity<EventoDTO> atualizar(@RequestBody EventoDTO eventoDTO){
        return ResponseEntity.ok(eventoServico.atualizar(eventoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Integer id){
        eventoServico.remover(id);
        return ResponseEntity.ok().build();
    }
}
