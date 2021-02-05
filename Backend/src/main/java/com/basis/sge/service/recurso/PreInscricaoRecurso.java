package com.basis.sge.service.recurso;

import com.basis.sge.service.servico.dto.CancelarInscricaoDTO;
import com.basis.sge.service.servico.dto.ListagemInscricoesDTO;
import com.basis.sge.service.servico.dto.PreInscricaoDTO;
import com.basis.sge.service.servico.PreInscricaoServico;
import com.basis.sge.service.servico.dto.PreinscricaoUsuarioDTO;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<PreInscricaoDTO> salvar(@RequestBody PreInscricaoDTO preInscricaoDTO)  {
        return ResponseEntity.status(HttpStatus.CREATED).body(preInscricaoServico.salvar(preInscricaoDTO));
    }

    @PutMapping
    public ResponseEntity<PreInscricaoDTO> atualizar(@RequestBody PreInscricaoDTO preInscricaoDTO){
        return ResponseEntity.ok(preInscricaoServico.atualizar(preInscricaoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Integer id){
        preInscricaoServico.remover(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/cancelar")
    public ResponseEntity<Void> cancelarIncricao(@RequestBody CancelarInscricaoDTO cancelarInscricaoDTO){
        preInscricaoServico.editarInscricaoCancelada(cancelarInscricaoDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/eventoinscricoes/{id}")
    public ResponseEntity<List<ListagemInscricoesDTO>> buscarPreInscricaoPorEvento(@PathVariable Integer id){
        return ResponseEntity.ok(preInscricaoServico.buscarPreIncricoesPoEvento(id));
    }

    @GetMapping("/usuarioinscricoes/{id}")
    public ResponseEntity<List<PreinscricaoUsuarioDTO>> buscarPreInscricaoPorUsuario(@PathVariable Integer id){
        return ResponseEntity.ok(preInscricaoServico.buscarPreinscricaoPorIdUsuario(id));
    }


}
