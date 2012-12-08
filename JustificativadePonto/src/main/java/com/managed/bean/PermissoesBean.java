package com.managed.bean;

import com.model.JustificativaPonto;
import com.model.MotivoEnum;
import com.model.PerfilEnum;
import com.model.StatusEnum;
import com.model.User;
import com.util.JsfUtil;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;


@SessionScoped
@ManagedBean(name = "PermissoesBean")
public class PermissoesBean implements Serializable {

    private User usuarioLogado;

    private boolean isUsuarioLogado;

    public PermissoesBean() {
        usuarioLogado = JsfUtil.getValueExpression(User.class, "#{usuarioLogado}");
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
    public boolean editAguardaAprovCoord(JustificativaPonto justificativa) {
        if (justificativa == null || !isUsuarioLogado) {return false;}
        if (justificativa.getStatus() == null) {return false;}
        //como o coordenador já foi selecionado, não faço validação
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

    
    /*
    *  Rh poderá:
    *  - ação 'executar(concluir)" e "cancelar"'
    *
    */
    public boolean editAguardaAprovRh(JustificativaPonto justificativa) {
        if (justificativa == null || !isUsuarioLogado) {return false;}
        if (justificativa.getStatus() == null) {return false;}
        if (justificativa.getStatus().equals(StatusEnum.EXECUCAORH) && justificativa.getRh().equals(usuarioLogado)) {
            return true;
        } else {
            return false;
        }
    }
    

    public Boolean isAdmin(){
        if(!isUsuarioLogado){ return false; }
        if (usuarioLogado.getPerfil().equals(PerfilEnum.ADMINISTRADOR)){
        	return true;
        } else {
        	return false;
        }
        
    }
    
    
    
    
    
    /***************** REGRAs  DE OCULTAÇÃO*********************/
    
    /*
     * CAMPO 'Causa da falta:'
     */
    public boolean showFldCausadaFalta(JustificativaPonto justificativa){
        if (justificativa == null || !isUsuarioLogado) {return false;}
    	if (justificativa.getMotivo().equals(MotivoEnum.FALTAS)){
    		  return true;
        } else {
            return false;
        }
    }
  
    /*
     * CAMPO 'Hora final'
     */
    public boolean hideFldHoraFinal(JustificativaPonto justificativa){
        if (justificativa == null || !isUsuarioLogado) {return false;}
    	if (justificativa.getMotivo().equals(MotivoEnum.FALTADEMARCACAO)){
    		  return false;
        } else {
            return true;
        }
    }
    
    /*
     * EXIBE AS OPÇÕES DO MOTIVO SELECIONADO BANCO DE HORAS
     *  'HORA EXTRA' E 'GOZO'
     */
    public boolean showOpcoesBancoHoras(JustificativaPonto justificativa){
        if (justificativa == null || !isUsuarioLogado) {return false;}
    	if (justificativa.getMotivo().equals(MotivoEnum.BANCODEHORAS)){
    		  return true;
        } else {
            return false;
        }
    }
    
    
    
    
    
    

}
