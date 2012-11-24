package com.managed.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import com.model.Perfil;
import com.service.IPerfilService;
import com.util.Message;

//@author onlinetechvision.com

@ManagedBean(name = "perfilMB")
@RequestScoped
public class PerfilManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String SUCCESS = "cadPerfil";
	private static final String EDIT = "editPerfil";
	public String labelCadastro;
	
	// private static final String ERROR = "error";
	private Perfil perfil;
	
	// Spring Perfil Service is injected...
	@ManagedProperty(value = "#{PerfilService}")
	IPerfilService perfilService;

	List<Perfil> perfilList;

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public String addPerfil() {

		// se o usuário existir atualiza
		if (perfil.getPerfilId() != 0) {
			updatePerfil(this.perfil);
			return SUCCESS;
			// valida se existe p/adicionar
		} else if (!getPerfilService().isExitePerfil(this.perfil)) {
			getPerfilService().addPerfil(this.perfil);
			return SUCCESS;
		} else {
			Message.addMessage("cadastroPerfil.existente");
			return null;
		}
	}

	public String deletePerfil(Perfil perfil) {
		getPerfilService().deletePerfil(perfil);
		return null;
	}

	public String updatePerfil(Perfil perfil) {
		getPerfilService().updatePerfil(perfil);
		return SUCCESS;
	}

	public String editPerfil(Perfil perfil) {
		this.perfil = perfil;
		return EDIT;
	}

	public String getLabelCadastro() {
		if (this.perfil.getPerfilId() == 0) {
			return Message.getBundleMessage("cadastroPerfil.label.titulo");
		} else {
			return Message.getBundleMessage("cadastroPerfil.label.alteraPerfil");
		}
	}

	public PerfilManagedBean() {
		if (this.perfil == null) {
			this.perfil = new Perfil();
		}
	}
	
	public void reset() {
		this.perfil = new Perfil();
	}

	public List<Perfil> getPerfilList() {
		perfilList = new ArrayList<Perfil>();
		perfilList.addAll(getPerfilService().getPerfils());
		return perfilList;
	}

	public IPerfilService getPerfilService() {
		return perfilService;
	}

	public void setPerfilService(IPerfilService perfilService) {
		this.perfilService = perfilService;
	}

}
