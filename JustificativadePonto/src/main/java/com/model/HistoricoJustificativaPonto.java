package com.model;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipoEventoHistorico", discriminatorType = DiscriminatorType.CHAR)
@DiscriminatorValue(value = "A")
@Table(name = "HistoricoJustificativaPonto")
public class HistoricoJustificativaPonto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historico_justificativa_ponto")
    private Long id;

    @Column(name = "data", nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date data;

    @Embedded
    private Identificacao usuario;

    @ManyToOne(targetEntity = JustificativaPonto.class, optional = false)
    @JoinColumn(name = "id_justificativa_ponto")
    private JustificativaPonto justificativaPonto;

    @Column(name = "tipo_evento", nullable = false)
    @Type(
            type = "com.util.hibernate.GenericEnumUserType",
            parameters = {
                    @org.hibernate.annotations.Parameter(
                            name  = "enumClass",
                            value = "com.model.TipoEventoJustificativaPontoEnum"),
                    @org.hibernate.annotations.Parameter(
                            name  = "identifierMethod",
                            value = "getCodigo"),
                    @org.hibernate.annotations.Parameter(
                            name  = "valueOfMethod",
                            value = "fromSigla")
            }
    )
    private TipoEventoJustificativaPontoEnum tipoEvento;

    @Column(name = "observacao", length = 1000)
    private String observacao;

    public HistoricoJustificativaPonto() {}

    public HistoricoJustificativaPonto(Identificacao usuario,
                                       JustificativaPonto justificativaPonto,
                                       TipoEventoJustificativaPontoEnum tipoEvento) {

        this.usuario = usuario;
        this.justificativaPonto = justificativaPonto;
        this.tipoEvento = tipoEvento;
        data = new Date();

    }

    public HistoricoJustificativaPonto(Identificacao usuario,
                                       JustificativaPonto justificativaPonto,
                                       TipoEventoJustificativaPontoEnum tipoEvento,
                                       String observacao) {
        this(usuario, justificativaPonto, tipoEvento);
        this.observacao = observacao;
    }

    public Long getId() {
        return id;
    }

    public Date getData() {
        return data;
    }

    public Identificacao getUsuario() {
        return usuario;
    }

    public JustificativaPonto getJustificativaPonto() {
        return justificativaPonto;
    }

    public TipoEventoJustificativaPontoEnum getTipoEvento() {
        return tipoEvento;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setUsuario(Identificacao usuario) {
        this.usuario = usuario;
    }

    public void setJustificativaPonto(JustificativaPonto justificativaPonto) {
        this.justificativaPonto = justificativaPonto;
    }

    public void setTipoEvento(TipoEventoJustificativaPontoEnum tipoEvento) {
        this.tipoEvento = tipoEvento;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
