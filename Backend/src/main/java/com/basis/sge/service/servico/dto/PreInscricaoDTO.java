package com.basis.sge.service.servico.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter

public class PreInscricaoDTO implements Serializable {

    private Integer id;
    @NotNull(message = "Usuario não pode ser nulo")
    private Integer idUsuario;
    @NotNull(message = "Evento não pode ser nulo")
    private Integer idEvento;
    @NotNull(message = "Situação não pode ser nulo")
    private Integer idSituacaoPreInscricao;
    private List<InscricaoRespostaDTO> inscricaoRespostas;
}
