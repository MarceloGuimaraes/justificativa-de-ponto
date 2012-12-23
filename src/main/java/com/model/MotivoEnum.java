package com.model;

import java.io.Serializable;
import com.util.IEnumeracaoComCodigoDescricao;

public enum MotivoEnum implements IEnumeracaoComCodigoDescricao, Serializable  {

	FALTAS(0,"motivo.descricao.faltas"),
    FALTADEMARCACAO(1,"motivo.descricao.faltamarcacao"),
    ATIVIDADEFORA(2,"motivo.descricao.atividadefora"),
    BANCODEHORAS(3,"motivo.descricao.bancodehoras"),
    ATRASOS(4,"motivo.descricao.atrasos"),
    SAIDAANTECIPADA(5,"motivo.descricao.saidaantecipada");


    private final Integer codigo;
    private final String descricao;

    private MotivoEnum(Integer codigo, String descricao) {

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

    public static MotivoEnum fromSigla(Integer codigo){
        for(MotivoEnum t : values()){
            if(t.getCodigo().equals(codigo)){
                return t;
            }
        }
        throw new IllegalArgumentException("Tipo invalido!");
    }

}
