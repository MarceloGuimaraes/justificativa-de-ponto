package com.model;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@DiscriminatorValue(value = "E")
public class EncaminhamentoJustificativaPonto extends HistoricoJustificativaPonto implements Serializable {

    @Embedded
    @AttributeOverrides(
            {
                    @AttributeOverride( name = "id", column = @Column(name = "respId_user")),
                    @AttributeOverride( name = "nome", column = @Column(name = "respNome", length = 50)),
                    @AttributeOverride( name = "cpf", column = @Column(name = "respCpf", length = 14)),
                    @AttributeOverride( name = "email", column = @Column(name = "respEmail", length = 50))
            }
    )
    private Identificacao responsavel;

    public EncaminhamentoJustificativaPonto() {
    }

    public EncaminhamentoJustificativaPonto(
            Identificacao usuario,
            JustificativaPonto justificativaPonto,
            TipoEventoJustificativaPontoEnum tipoEvento,
            Identificacao responsavel) {
        super(usuario, justificativaPonto, tipoEvento);
        this.responsavel = responsavel;
    }

    public Identificacao getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Identificacao responsavel) {
        this.responsavel = responsavel;
    }
}
