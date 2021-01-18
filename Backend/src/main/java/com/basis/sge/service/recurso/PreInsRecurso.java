package com.basis.sge.service.recurso;

import com.basis.sge.service.servico.DTO.PreInsDTO;
import com.basis.sge.service.servico.PreInsServico;
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
@RequestMapping("/api/preinscricao")
@RequiredArgsConstructor
public class PreInsRecurso {
    private final PreInsServico preInsServico;

    @GetMapping
    public ResponseEntity<List<PreInsDTO>> listar(){
        return  ResponseEntity.ok(preInsServico.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PreInsDTO> buscarPorId(@PathVariable Integer id){
        return ResponseEntity.ok(preInsServico.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<PreInsDTO> salvar (@RequestBody PreInsDTO preInsDTO){
        return ResponseEntity.ok(preInsServico.salvar(preInsDTO));
    }

    @DeleteMapping
    public ResponseEntity<Void> remover(@PathVariable Integer id){
        return ResponseEntity.ok().build();
    }

}
