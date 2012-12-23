package com.model;

import com.util.IEnumeracaoComCodigoDescricao;

import java.io.Serializable;

public enum TipoDecisaoEnum implements IEnumeracaoComCodigoDescricao, Serializable {

    AUTORIZADO_ABONAR(1,"tipodecisao.descricao.autorizadoabonar"),
    AUTORIZADO_BANCOHORAS(2, "tipodecisao.descricao.autorizadobancohoras"),
    NAO_AUTORIZADO(3, "tipodecisao.descricao.naoautorizadodescontar");

    private final Integer codigo;
    private final String descricao;

    private TipoDecisaoEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    @Override
    public Integer getCodigo() {
        return codigo;
    }

    @Override
    public String getDescricao() {
        return descricao;
    }

    public static TipoDecisaoEnum fromSigla(Integer codigo){
        for(TipoDecisaoEnum t : values()){
            if(t.getCodigo().equals(codigo)){
                return t;
            }
        }
        throw new IllegalArgumentException("Tipo invalido!");
    }

}
