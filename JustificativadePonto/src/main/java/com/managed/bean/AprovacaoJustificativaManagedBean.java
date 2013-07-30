package com.managed.bean;

import com.domain.dto.JustificativaPontoDTO;
import com.jsf.model.WrappedBeanPaginadaDataModel;
import com.managed.bean.wrapper.WrapperJustificativa;

import java.io.Serializable;

/**
 * User: xonda
 * Date: 26/07/13
 * Time: 13:11
 */
public class AprovacaoJustificativaManagedBean implements Serializable {
    private WrappedBeanPaginadaDataModel<WrapperJustificativa, JustificativaPontoDTO> justificativas;

    public AprovacaoJustificativaManagedBean(WrappedBeanPaginadaDataModel<WrapperJustificativa, JustificativaPontoDTO> justificativas) {
        this.justificativas = justificativas;
    }

    public WrappedBeanPaginadaDataModel<WrapperJustificativa, JustificativaPontoDTO> getJustificativas() {
        return justificativas;
    }
}
