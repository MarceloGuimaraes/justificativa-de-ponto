package com.model;

import java.io.Serializable;
import com.util.IEnumeracaoComCodigoDescricao;

public enum MotivoEnum implements IEnumeracaoComCodigoDescricao, Serializable  {

    FALTAS(0,"Faltas"),
    FALTADEMARCACAO(1,"Falta de Marca��o"),
    ATIVIDADEFORA(2,"Atividade de Fora"),
    BANCODEHORAS(3,"Banco de Horas"),
    ATRASOS(4,"Atrasos"),
    SAIDAANTECIPADA(5,"Sa�da Antecipada");

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

}
