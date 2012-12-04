package com.managed.bean;

import com.model.JustificativaPonto;
import com.model.StatusEnum;
import com.model.User;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;


@SessionScoped
@ManagedBean(name = "PermissoesBean")
public class PermissoesBean implements Serializable {

    private static final String USUARIO_LOGADO = "usuarioLogado";

    private User usuarioLogado;
    private boolean isUsuarioLogado;

    public PermissoesBean() {
        usuarioLogado = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(USUARIO_LOGADO);
        isUsuarioLogado = usuarioLogado!=null;
    }

    public User getUsuarioLogado() {
        return usuarioLogado;
    }

    /*
    * Solicitante poderá:
    *  - ação 'Enviar para o coordenador'
    *  - corpo da justificativa de ponto até o campo Justificativa
    *  -
    */
    public boolean editElaboracao(JustificativaPonto justificativa) {

        if (justificativa == null || !isUsuarioLogado) {return false;}
        if (justificativa.getStatus() == null) {return false;}
        if (justificativa.getStatus().equals(StatusEnum.ELABORACAO) && justificativa.getSolicitante().equals(usuarioLogado)) {
            return true;
        } else {
            return false;
        }
    }

    /*
    *  Coordenador poderá:
    *  - ação 'Enviar para superintendente" e "cancelar"'
    *  - selecionar o superintendente
    *
    */
    public boolean editAguardaAprovCoord(JustificativaPonto justificativa,User usuarioLogado) {
        if (justificativa == null || !isUsuarioLogado) {return false;}
        if (justificativa.getStatus() == null) {return false;}
        if (justificativa.getStatus().equals(StatusEnum.APROVCOORD) && justificativa.getCoordenador().equals(usuarioLogado)) {
            return true;
        } else {
            return false;
        }
    }

    /*
    *  Superintendente poderá:
    *  - ação 'Enviar para superintendente" e "cancelar"'
    *  - selecionar o superintendente
    *
    */
    public boolean editAguardaAprovSuperintendente(JustificativaPonto justificativa) {
        if (justificativa == null || !isUsuarioLogado) {return false;}
        if (justificativa.getStatus() == null) {return false;}
        if (justificativa.getStatus().equals(StatusEnum.APROVSUPERINTENDENTE) && justificativa.getSuperintendente().equals(usuarioLogado)) {
            return true;
        } else {
            return false;
        }
    }


    public Boolean isAdmin(){
        if(!isUsuarioLogado){ return false; }
        /*if (usuarioLogado.)
          */
        return true;
    }

}
