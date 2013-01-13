package com.domain.service.fluxo;

import com.domain.dto.CadastroUsuario;
import com.domain.dto.JustificativaPontoDTO;
import com.managed.bean.handler.HandlerProximoPassoManagedBean;
import com.model.JustificativaPonto;

import javax.faces.model.SelectItem;
import java.util.List;

public class PassoSemAcesso extends ProximoPasso {

    public PassoSemAcesso() {
    }

    @Override
    public boolean isIntercepted(JustificativaPonto justificativa) {
        return true;
    }

    @Override
    public void proximo(JustificativaPontoDTO justificativa, Integer id) {
        //Objeto nulo
    }

    @Override
    public List<CadastroUsuario> listaCandidatos() {
        return null;
    }

    @Override
    public HandlerProximoPassoManagedBean retornaHandler() {
        return new HandlerProximoPassoManagedBean(false,false,false,false,"nulo");
    }

}
