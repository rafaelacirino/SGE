package com.basis.sge.service.recurso;

import com.basis.sge.service.dominio.IdInscricaoResposta;
import com.basis.sge.service.servico.DTO.InscricaoRespostaDTO;
import com.basis.sge.service.servico.InscricaoRespostaServico;
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
public class InscricaoRespostaRecurso {
    private final InscricaoRespostaServico inscricaoRespostaServico;

    @GetMapping
    public ResponseEntity<List<InscricaoRespostaDTO>> listar(){
        return  ResponseEntity.ok(inscricaoRespostaServico.listar());
    }

    @GetMapping ("/{id}")
    public ResponseEntity<InscricaoRespostaDTO> buscarPorId(@PathVariable IdInscricaoResposta id){
        return ResponseEntity.ok(inscricaoRespostaServico.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<InscricaoRespostaDTO> salvar (@RequestBody InscricaoRespostaDTO inscricaoRespostaDTO){
        return ResponseEntity.ok(inscricaoRespostaServico.salvar(inscricaoRespostaDTO));
    }

    @DeleteMapping
    public ResponseEntity<Void> remover(@PathVariable Integer id){
        return ResponseEntity.ok().build();
    }
}
