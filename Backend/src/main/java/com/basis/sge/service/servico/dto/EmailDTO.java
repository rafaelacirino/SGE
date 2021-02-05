package com.basis.sge.service.servico.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class EmailDTO implements Serializable {

    private String destinatario;
    private String corpo;
    private String assunto;
    private List<String> copias;
}