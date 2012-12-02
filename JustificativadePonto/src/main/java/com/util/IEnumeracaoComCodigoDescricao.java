package com.util;
import java.io.Serializable;

public interface IEnumeracaoComCodigoDescricao  extends Serializable {

    /**
     * Recupera o código do ENUM.
     *
     * @return o código do ENUM.
     */
    Integer getCodigo();

    /**
     * Recupera a descricaoo do ENUM.
     *
     * @returna descricao do ENUM.
     */
    String getDescricao();
}
