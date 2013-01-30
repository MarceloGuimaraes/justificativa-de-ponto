package com.model;

import com.util.IEnumeracaoComCodigoDescricao;
import java.io.Serializable;

public enum StatusEnum implements IEnumeracaoComCodigoDescricao, Serializable {

    ELABORACAO(1, "status.descricao.emelaboracao"),
    APROVCOORD(2, "status.descricao.aguardandoaprovacaocoordenador"),
    APROVSUPERINTENDENTE(3, "status.descricao.aprovsuperintendente"),
    EXECUCAORH(4,"status.descricao.execucaorh"),
    CONCLUIDO(5,"status.descricao.concluido"),
    CANCELADO(6, "status.descricao.cancelado");

    private final Integer codigo;
    private final String descricao;

    private StatusEnum(Integer codigo, String descricao) {

        this.codigo = codigo;
        this.descricao = descricao;
    }

    /** {@inheritDoc} */
    @Override
    public String getDescricao() {

        return this.descricao;
    }

    /** {@inheritDoc} */
    @Override
    public Integer getCodigo() {

        return this.codigo;
    }

    public static StatusEnum fromSigla(Integer codigo){
        for(StatusEnum t : values()){
            if(t.getCodigo().equals(codigo)){
                return t;
            }
        }
        throw new IllegalArgumentException("Tipo invalido!");
    }

}

	

