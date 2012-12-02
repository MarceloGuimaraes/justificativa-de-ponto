package com.model;

import com.util.IEnumeracaoComCodigoDescricao;
import java.io.Serializable;


public enum TipoBancoHorasEnum implements IEnumeracaoComCodigoDescricao, Serializable {

	HORAEXTRA(0,"bancohoras.descricao.horaextra"),
    GOZO(1,"bancohoras.descricao.gozo");

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

    public static TipoBancoHorasEnum fromSigla(Integer codigo){
        for(TipoBancoHorasEnum t : values()){
            if(t.getCodigo().equals(codigo)){
                return t;
            }
        }
        throw new IllegalArgumentException("Tipo invalido!");
    }

}