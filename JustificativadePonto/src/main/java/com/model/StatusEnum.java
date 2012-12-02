package com.model;

import com.util.IEnumeracaoComCodigoDescricao;
import java.io.Serializable;

public enum StatusEnum implements IEnumeracaoComCodigoDescricao, Serializable {

    ELABORACAO(1, "status.descricao.emelaboracao"),
    APROVCOORD(2, "status.descricao.aguardandoaprovacaocoordenador"),
    REPROVADOCOORD(3, "status.descricao.reprovacoord"),
    APROVSUPERINTENDENTE(4, "status.descricao.aprovsuperintendente"),
    REPROVADOSUPERINTENDENTE(5, "status.descricao.reprovadosuperintendente"),
    EXECUCAORH(6,"status.descricao.execucaorh"),
    CONCLUIDO(7,"status.descricao.concluido"),
    CANCELADO(8, "status.descricao.cancelado");

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


}

	

