package com.basis.sge.service.recurso;

import com.basis.sge.service.servico.DTO.PreInscricaoDTO;
import com.basis.sge.service.servico.PreInscricaoServico;
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
@RequestMapping("/api/preinscricao")
@RequiredArgsConstructor
public class PreInscricaoRecurso {
    private final PreInscricaoServico preInscricaoServico;

    @GetMapping
    public ResponseEntity<List<PreInscricaoDTO>> listar(){
        return  ResponseEntity.ok(preInscricaoServico.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PreInscricaoDTO> buscarPorId(@PathVariable Integer id){
        return ResponseEntity.ok(preInscricaoServico.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<PreInscricaoDTO> salvar (@RequestBody PreInscricaoDTO preInscricaoDTO){
        return ResponseEntity.ok(preInscricaoServico.salvar(preInscricaoDTO));
    }

    @DeleteMapping
    public ResponseEntity<Void> remover(@PathVariable Integer id){
        return ResponseEntity.ok().build();
    }

}