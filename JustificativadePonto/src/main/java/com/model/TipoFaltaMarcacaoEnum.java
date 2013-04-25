package com.model;

import com.util.IEnumeracaoComCodigoDescricao;
import java.io.Serializable;


public enum TipoFaltaMarcacaoEnum implements IEnumeracaoComCodigoDescricao, Serializable {

	ENTRADA(0,"faltamarcacao.descricao.entrada"),
    SAIDA(1,"faltamarcacao.descricao.saida");

    private final Integer codigo;
    private final String descricao;

    private TipoFaltaMarcacaoEnum(Integer codigo, String descricao) {

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

    public static TipoFaltaMarcacaoEnum fromSigla(Integer codigo){
        for(TipoFaltaMarcacaoEnum t : values()){
            if(t.getCodigo().equals(codigo)){
                return t;
            }
        }
        throw new IllegalArgumentException("Tipo invalido!");
    }

}