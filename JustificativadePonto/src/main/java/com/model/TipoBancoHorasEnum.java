package com.model;

import com.util.IEnumeracaoComCodigoDescricao;
import java.io.Serializable;


public enum TipoBancoHorasEnum implements IEnumeracaoComCodigoDescricao, Serializable {

	HORAEXTRA(0,"Hora Extra"),
    GOZO(1,"Gozo");

    private final Integer codigo;
    private final String descricao;

    private TipoBancoHorasEnum(Integer codigo, String descricao) {

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