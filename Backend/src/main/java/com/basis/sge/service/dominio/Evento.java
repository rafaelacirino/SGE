package com.basis.sge.service.dominio;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="evento")
@Getter
@Setter
public class Evento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_evento")
    @SequenceGenerator(name = "sq_evento",sequenceName = "sq_evento",allocationSize = 1)
    @Column(name = "id_evento")
    private Integer id;

    @Column(name = "titulo", nullable = false)
    private String titulo;
    //LocalDateTime
    @Column(name = "periodo_inicio", nullable = false)
    private LocalDateTime periodoInicio;

    //LocalDateTime
    @Column(name = "periodo_fim", nullable = false)
    private LocalDateTime periodoFim;

    @Column(name = "tipo_insc", nullable = false)
    private Boolean tipoInsc;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "qtd_vagas")
    private Integer qtdVagas;

    @Column(name = "valor")
    private Double valor;

    @Column(name = "local")
    private String local;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL},  mappedBy = "evento")
    private List<EventoPergunta> perguntas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_evento")
    private TipoEvento tipoEvento;

}
