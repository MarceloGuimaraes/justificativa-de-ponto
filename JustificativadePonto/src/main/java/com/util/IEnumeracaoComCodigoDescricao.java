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
     * Recupera a descrição do ENUM.
     *
     * @returna descrição do ENUM.
     */
    String getDescricao();
}
