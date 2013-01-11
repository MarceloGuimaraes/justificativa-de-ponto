package com.domain.service.fluxo;

import com.domain.dto.JustificativaPontoDTO;
import com.model.JustificativaPonto;

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
        return null;
    }

    @Override
    public boolean isIntercepted(JustificativaPonto justificativa) {
        return true;
    }

    @Override
    public void proximo(JustificativaPontoDTO justificativa) {
        //Objeto nulo
    }
}
