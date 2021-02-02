package com.basis.sge.service.servico.DTO;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class UsuarioDTO implements Serializable {

    private Integer id;
    private String cpf;
    private String nome;
    private String email;
    private String telefone;
    private LocalDate dataNascimento;
    private Boolean admin;

}
