package com.domain.dto;

import com.domain.service.IWorkflow;

import javax.faces.model.SelectItem;
import java.util.List;

public class Concluir extends ProximoPasso {

    public Concluir(IWorkflow workflow) {
        super(workflow);
        temProximoPasso = false;
        permiteCancelar = true;
        permiteEditar = false;
        concluir = true;
    }

    @Override
    protected List<SelectItem> populaEscolhas() {
        return null;
    }

    @Override
    public void proximo(JustificativaPontoDTO justificativa) {
        workflow.concluir(justificativa);
    }

}
