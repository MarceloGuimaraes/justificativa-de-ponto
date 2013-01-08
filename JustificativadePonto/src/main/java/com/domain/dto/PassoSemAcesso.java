package com.domain.dto;

import com.domain.service.IWorkflow;

import javax.faces.model.SelectItem;
import java.util.List;

public class PassoSemAcesso extends ProximoPasso {

    public PassoSemAcesso() {
        temProximoPasso = false;
        permiteCancelar = false;
        permiteEditar = false;
    }

    @Override
    protected List<SelectItem> populaEscolhas() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void proximo(JustificativaPontoDTO justificativa) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
