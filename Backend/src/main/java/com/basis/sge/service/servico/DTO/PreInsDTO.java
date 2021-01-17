package com.basis.sge.service.servico.DTO;

import com.basis.sge.service.dominio.Evento;
import com.basis.sge.service.dominio.SituacaoPreInscricao;
import com.basis.sge.service.dominio.Usuario;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter

public class PreInsDTO implements Serializable {
    private Integer id;
    private Usuario usuario;
    private Evento evento;
    private SituacaoPreInscricao situacaoPreInscricao;
}
