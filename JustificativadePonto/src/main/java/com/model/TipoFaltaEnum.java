package com.model;

import com.util.IEnumeracaoComCodigoDescricao;
import java.io.Serializable;


public enum TipoFaltaEnum implements IEnumeracaoComCodigoDescricao, Serializable {

    ATESTADOMEDICO(0,"tipofalta.descricao.atestadomedico"),
    CASAMENTO(1,"tipofalta.descricao.casamento"),
    LICENCAPATERNIDADE(2,"tipofalta.descricao.licencapaternidade"),
    LUTO(3,"tipofalta.descricao.luto"),
    OUTROS(4,"tipofalta.descricao.outros");

    private final Integer codigo;
    private final String descricao;

    private TipoFaltaEnum(Integer codigo, String descricao) {

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