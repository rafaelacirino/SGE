package com.basis.sge.service.recurso;

import com.basis.sge.service.servico.DTO.TipoEventoDTO;
import com.basis.sge.service.servico.TipoEventoServico;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tipoevento")
@AllArgsConstructor
public class TipoEventoRecurso {

    private final TipoEventoServico tipoEventoServico;

    @GetMapping
    public ResponseEntity<List<TipoEventoDTO>> listar(){
        return ResponseEntity.ok(tipoEventoServico.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoEventoDTO> buscarPorId(@PathVariable Integer id){
        return ResponseEntity.ok(tipoEventoServico.buscarPorId(id));
    }

}
