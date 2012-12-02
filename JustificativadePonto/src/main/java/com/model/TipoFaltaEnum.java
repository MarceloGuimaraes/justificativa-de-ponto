package com.model;

import com.util.IEnumeracaoComCodigoDescricao;
import java.io.Serializable;


public enum TipoFaltaEnum implements IEnumeracaoComCodigoDescricao, Serializable {

    ATESTADOMEDICO(0,"Atestado Médico"), CASAMENTO(1,"Casamento"),
    LICENCAPATERNIDADE(2,"Licença Paternidade"), LUTO(3,"Luto"), OUTROS(4,"Outros");

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