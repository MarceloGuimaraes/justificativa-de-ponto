package com.managed.bean;

import com.jsf.model.WrappedBeanPaginadaDataModel;
import com.managed.bean.wrapper.WrapperJustificativa;

import java.io.Serializable;

/**
 * User: xonda
 * Date: 26/07/13
 * Time: 13:11
 */
public class AprovacaoJustificativaManagedBean implements Serializable {
    private WrappedBeanPaginadaDataModel<WrapperJustificativa> justificativas;

    public AprovacaoJustificativaManagedBean(WrappedBeanPaginadaDataModel<WrapperJustificativa> justificativas) {
        this.justificativas = justificativas;
    }

    public WrappedBeanPaginadaDataModel<WrapperJustificativa> getJustificativas() {
        return justificativas;
    }
}
