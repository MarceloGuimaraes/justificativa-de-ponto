package com.model;

import com.util.IEnumeracaoComCodigoDescricao;
import java.io.Serializable;

public enum StatusEnum implements IEnumeracaoComCodigoDescricao, Serializable {

	ELABORACAO(1, "Em elabora��o"),
    APROVCOORD(2, "Aguardando aprova��o do coordenador"),
    REPROVADOCOORD(3, "Reprovado pelo coordenador"),
    APROVSUPERINTENDENTE(4, "Aguardando aprova��o do superintendente"),
    REPROVADOSUPERINTENDENTE(5, "Reprovado pelo superintendente"),
    EXECUCAORH(6,"Aguardando execu��o do RH"),
    CONCLUIDO(7,"Conclu�do"),
    CANCELADO(8, "Cancelado");

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

	

