package com.basis.sge.service.servico.DTO;

import com.basis.sge.service.dominio.PreInscricao;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class UsuarioDTO {
    private Integer id;

    private String chaveUnica;

    private String cpf;

    private String nome;

    private String email;

    private String telefone;

    private Date dataNascimento;

//    private List<PreInscricao> preInscricao;
}
