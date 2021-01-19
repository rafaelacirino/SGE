package com.basis.sge.service.dominio;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
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
    @Column (name = "id_evento")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "evento")
    @SequenceGenerator(name = "evento",sequenceName = "sq_evento",initialValue = 1,allocationSize = 1)
    @Column(name = "id_evento")
    private Integer id;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "periodo_inicio", nullable = false)
    private LocalDateTime periodoInicio;

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

    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, targetEntity = EventoPergunta.class, mappedBy = "evento")
    private List<EventoPergunta> perguntas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo")
    private TipoEvento tipoEvento;

}
