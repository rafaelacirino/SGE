package com.basis.sge.service.dominio;

import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Table(name="evento")
@Getter
@Setter
public class Evento implements Serializable {

    @Id
    @Column(name="id_evento")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_evento")
    @SequenceGenerator(name = "sq_evento", initialValue = 1)
    private Integer id_evento;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "periodo_inicio", nullable = false)
    private Timestamp periodo_inicio;

    @Column(name = "periodo_fim", nullable = false)
    private Timestamp periodo_fim;

    @Column(name = "tipo_insc", nullable = false)
    private Boolean tipo_insc;

    @Column(name = "descricao", nullable = true)
    private String descricao;

    @Column(name = "qtd_vagas", nullable = true)
    private Integer qtd_vagas;

    @ManyToOne
    @JoinColumn(name = "id_tipo_evento", nullable = true, referencedColumnName = "id_tipo")
    private Integer id_tipo_evento;

    @Column(name = "valor", nullable = true)
    private Double valor;

    @Column(name = "local", nullable = true)
    private String local;
}
