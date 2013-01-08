package com.domain.dto;

import com.domain.service.IWorkflow;

import javax.faces.model.SelectItem;
import java.util.List;

public class EnviarCoordenador extends ProximoPasso {

    public EnviarCoordenador(IWorkflow workflow) {
        super(workflow);
        temProximoPasso = true;
        permiteCancelar = false;
        permiteEditar = true;
        concluir = false;
    }

    @Override
    protected List<SelectItem> populaEscolhas() {
        return retornaItemAPartirDeUser(workflow.getUserService().recuperaCoordenadores());
    }

    @Override
    public void proximo(JustificativaPontoDTO justificativa) {
        workflow.enviarCoordenador(justificativa, getId());
    }

}
