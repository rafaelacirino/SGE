package com.basis.sge.service.recurso;

import com.basis.sge.service.servico.DTO.STPDTO;
import com.basis.sge.service.servico.STPServico;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/situacaopreinscricao")
@RequiredArgsConstructor
public class STPRecurso {
    private final STPServico stpServico;

    @GetMapping
    public ResponseEntity<List<STPDTO>> listar(){
        return ResponseEntity.ok(stpServico.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<STPDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(stpServico.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<STPDTO> salvar (@RequestBody STPDTO stpdto){
        return ResponseEntity.ok(stpServico.salvar(stpdto));
    }

    @PutMapping
    public ResponseEntity<STPDTO> atulizar(@PathVariable Integer id, @RequestBody STPDTO stpdto){
        return ResponseEntity.ok(stpServico.atualizar(id, stpdto));
    }

    @DeleteMapping
    public ResponseEntity<Void> remover(@PathVariable Integer id){
        return ResponseEntity.ok().build();
    }

}
