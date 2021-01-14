package com.basis.sge.service.servico.DTO;

import com.basis.sge.service.dominio.Pergunta;
import com.basis.sge.service.dominio.PreInscricao;
import com.basis.sge.service.dominio.TipoEvento;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
public class EventoDTO implements Serializable {

    private Integer id;

    private String titulo;

    private Timestamp periodoInicio;

    private Timestamp periodoFim;

    private Boolean tipoInsc;

    private String descricao;

    private Integer qtdVagas;

    private TipoEvento tipoEvento;

    private Double valor;

    private String local;

    private List<Pergunta> perguntas;

    private List<PreInscricao> preInscricao;
}
