package com.basis.sge.service.recurso;

import com.basis.sge.service.servico.dto.SituacaoPreInscricaoDTO;
import com.basis.sge.service.servico.SituacaoPreInscricaoServico;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
}
