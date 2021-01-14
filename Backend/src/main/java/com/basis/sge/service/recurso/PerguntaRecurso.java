package com.basis.sge.service.recurso;


import com.basis.sge.service.servico.DTO.PerguntaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/perguntas")
@RequiredArgsConstructor
public class PerguntaRecurso {

    @GetMapping
    public ResponseEntity<List<PerguntaDTO>> listar(){
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerguntaDTO> obterPorId(){
        return null;
    }
}
