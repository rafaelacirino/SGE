package com.basis.sge.service.dominio;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tipo_evento")
@Getter
@Setter
/* CLASSE USUARIO*/
public class Usuario implements Serializable {
    
    @Id
    @Column(name = "id_usuario")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "usuario")
    @SequenceGenerator(name = "usuario", sequenceName = "sq_usuario")
    private Integer idUsuario;

    @Column(name = "chave_unica")
    private  String ChaveUnica;

    @Column(name = "cpf")
    private  String cpf;

    @Column(name = "nome")
    private  String nome;

    @Column(name = "email")
    private String email;

    @Column(name = "telefone")
    private  String telefone;
    /* Date.Java*/
    @Column(name = "dt_nasc")
    private Date dataNascimento;


}
