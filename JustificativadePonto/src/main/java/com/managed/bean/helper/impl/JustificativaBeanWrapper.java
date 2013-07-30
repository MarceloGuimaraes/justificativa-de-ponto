package com.managed.bean.helper.impl;

import com.domain.dto.JustificativaPontoDTO;
import com.domain.service.IWorkflowResolver;
import com.managed.bean.helper.BeanWrapper;
import com.managed.bean.wrapper.WrapperJustificativa;

/**
 * User: xonda
 * Date: 26/07/13
 * Time: 14:18
 */
public class JustificativaBeanWrapper implements BeanWrapper<WrapperJustificativa, JustificativaPontoDTO> {

    private IWorkflowResolver workflow;

    public JustificativaBeanWrapper(IWorkflowResolver workflow) {
        this.workflow = workflow;
    }

    @Override
    public WrapperJustificativa wrap(JustificativaPontoDTO justificativaPontoDTO) {
        return new WrapperJustificativa(justificativaPontoDTO, workflow);
    }
}
