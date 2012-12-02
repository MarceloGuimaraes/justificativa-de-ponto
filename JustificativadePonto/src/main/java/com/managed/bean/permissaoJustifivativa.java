package com.managed.bean;

import java.util.List;

import javax.faces.context.FacesContext;

import com.model.JustificativaPonto;
import com.model.StatusEnum;
import com.model.User;

public class permissaoJustifivativa {

	private JustificativaPonto justifivativa;

	public JustificativaPonto getJustifivativa() {
		return justifivativa;
	}

	public void setJustifivativa(JustificativaPonto justifivativa) {
		this.justifivativa = justifivativa;
	}

	public User usuarioLogado = (User) FacesContext.getCurrentInstance()
			.getExternalContext().getSessionMap().get("usuarioLogado");

	
	public boolean editElaboracao() {
		if (this.justifivativa.getStatus() == null) {
			return false;
		}
	if (this.justifivativa.getStatus().equals(StatusEnum.ELABORACAO) && this.justifivativa.getSolicitante().equals(usuarioLogado)) {
			return true;
		} else {
			return false;
		}
	}


	
	
	
	
	
	private List<String> testeContains;

	public List<String> getTesteContains() {
		return testeContains;
	}

	public void setTesteContains(List<String> testeContains) {
		this.testeContains = testeContains;
	}

	public void testaPermissao() {

	}

}
