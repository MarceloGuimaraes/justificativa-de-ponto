package com.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "HistoricoJustificativaPonto")
public class HistoricoJustificativaPonto implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historico_justificativa_ponto")
    private Long id;

    @Column(name = "data", nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date data;

    @ManyToOne(targetEntity = User.class, optional = false)
    @JoinColumn(name = "id_usuario")
    private User usuario;

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

    public HistoricoJustificativaPonto() {}

    public HistoricoJustificativaPonto(User usuario,
                                       JustificativaPonto justificativaPonto,
                                       TipoEventoJustificativaPontoEnum tipoEvento) {

        this.usuario = usuario;
        this.justificativaPonto = justificativaPonto;
        this.tipoEvento = tipoEvento;
        data = new Date();

    }

    public Long getId() {
        return id;
    }

    public Date getData() {
        return data;
    }

    public User getUsuario() {
        return usuario;
    }

    public JustificativaPonto getJustificativaPonto() {
        return justificativaPonto;
    }

    public TipoEventoJustificativaPontoEnum getTipoEvento() {
        return tipoEvento;
    }
}
