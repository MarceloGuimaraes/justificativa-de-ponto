package com.domain.service.fluxo;

import com.domain.dto.CadastroUsuario;
import com.domain.dto.JustificativaPontoDTO;
import com.model.JustificativaPonto;

import java.util.List;

public class PassoSemAcesso extends ProximoPasso {

    public PassoSemAcesso() {
    }

    @Override
    public boolean isIntercepted(JustificativaPonto justificativa) {
        return true;
    }

    @Override
    public void proximo(JustificativaPontoDTO justificativa) {
        //Objeto nulo
    }

    @Override
    public List<CadastroUsuario> listaCandidatos() {
        return null;
    }

}
