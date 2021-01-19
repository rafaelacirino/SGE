package com.basis.sge.service.recurso;

import com.basis.sge.service.servico.DTO.SituacaoPreInscricaoDTO;
import com.basis.sge.service.servico.SituacaoPreInscricaoServico;
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
public class SituacaoPreInscricaoRecurso {
    private final SituacaoPreInscricaoServico situacaoPreInscricaoServico;

    @GetMapping
    public ResponseEntity<List<SituacaoPreInscricaoDTO>> listar(){
        return ResponseEntity.ok(situacaoPreInscricaoServico.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SituacaoPreInscricaoDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(situacaoPreInscricaoServico.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<SituacaoPreInscricaoDTO> salvar (@RequestBody SituacaoPreInscricaoDTO situacaoPreInscricaoDTO){
        return ResponseEntity.ok(situacaoPreInscricaoServico.salvar(situacaoPreInscricaoDTO));
    }

    @PutMapping
    public ResponseEntity<SituacaoPreInscricaoDTO> atulizar(@PathVariable Integer id, @RequestBody SituacaoPreInscricaoDTO situacaoPreInscricaoDTO){
        return ResponseEntity.ok(situacaoPreInscricaoServico.atualizar(id, situacaoPreInscricaoDTO));
    }

    @DeleteMapping
    public ResponseEntity<Void> remover(@PathVariable Integer id){
        return ResponseEntity.ok().build();
    }

}
