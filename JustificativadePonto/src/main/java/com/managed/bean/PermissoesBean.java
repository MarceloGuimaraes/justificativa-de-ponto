package com.managed.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.swing.text.StyledEditorKit.BoldAction;

import com.model.JustificativaPonto;
import com.model.StatusEnum;
import com.model.User;


@SessionScoped
@ManagedBean(name = "PermissoesBean")
public class PermissoesBean {

	/*
	 * Solicitante poderá: 
	 *  - ação 'Enviar para o coordenador'
	 *  - corpo da justificativa de ponto até o campo Justificativa
	 *  - 
	 */
    public boolean editElaboracao(JustificativaPonto justificativa,User usuarioLogado) {
       if (justificativa == null || usuarioLogado == null) {return false;}
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
        if (justificativa == null || usuarioLogado == null) {return false;}
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
    public boolean editAguardaAprovSuperintendente(JustificativaPonto justificativa,User usuarioLogado) {
        if (justificativa == null || usuarioLogado == null) {return false;}
        if (justificativa.getStatus() == null) {return false;}
        if (justificativa.getStatus().equals(StatusEnum.APROVSUPERINTENDENTE) && justificativa.getSuperintendente().equals(usuarioLogado)) {
          return true;
         } else {
          return false;
         }
        }
    
    
    public Boolean isAdmin(User usuarioLogado){
    	if(usuarioLogado == null ){ return false; }
    	/*if (usuarioLogado.)
    	*/
    	return true;
    }
	 
}
