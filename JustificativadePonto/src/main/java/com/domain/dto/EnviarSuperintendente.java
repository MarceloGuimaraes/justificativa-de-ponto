package com.domain.dto;

import com.domain.service.IWorkflow;

import javax.faces.model.SelectItem;
import java.util.List;

public class EnviarSuperintendente extends ProximoPasso {

    public EnviarSuperintendente(IWorkflow workflow) {
        super(workflow);
        temProximoPasso = true;
        permiteEditar = false;
        permiteCancelar = true;
        concluir = false;
    }

    @Override
    protected List<SelectItem> populaEscolhas() {
        return retornaItemAPartirDeUser(workflow.getUserService().recuperaSuperintendentes());
    }

    @Override
    public void proximo(JustificativaPontoDTO justificativa) {
        workflow.enviarSuperintendente(justificativa, getId());
    }
}
