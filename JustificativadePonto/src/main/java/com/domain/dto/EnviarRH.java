package com.domain.dto;

import com.domain.service.IWorkflow;

import javax.faces.model.SelectItem;
import java.util.List;

public class EnviarRH extends ProximoPasso {

    public EnviarRH(IWorkflow workflow) {
        super(workflow);
        temProximoPasso = true;
        permiteEditar = false;
        permiteCancelar = true;
        concluir = false;
    }

    @Override
    protected List<SelectItem> populaEscolhas() {
        return retornaItemAPartirDeUser(workflow.getUserService().recuperaRH());
    }

    @Override
    public void proximo(JustificativaPontoDTO justificativa) {
        workflow.enviarRh(justificativa, getId());
    }

}
