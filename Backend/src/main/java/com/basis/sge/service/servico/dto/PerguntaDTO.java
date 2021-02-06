package com.basis.sge.service.servico.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
public class PerguntaDTO implements Serializable {

    private Integer id;
    @NotNull(message = "Titulo não pode ser nulo")
    private String titulo;
    @NotNull(message = "Obrigatoriedade não pode ser nula")
    private Boolean obrigatorio;
}
