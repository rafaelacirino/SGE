package com.basis.sge.service.servico.dto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PreinscricaoUsuarioDTO {
   private String titulo;
   private LocalDateTime periodoInicio;
   private LocalDateTime periodoFim;
   private String descricao;
   private String situacao;

   public PreinscricaoUsuarioDTO(String titulo, LocalDateTime inicio, LocalDateTime fim, String desc, String situacao){
      this.titulo = titulo;
      this.periodoInicio = inicio;
      this.periodoFim = fim;
      this.descricao = desc;
      this.situacao = situacao;
   }


}
