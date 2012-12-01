package com.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dao.IPerfilDAO;
import com.model.Perfil;

@Transactional(readOnly = true)
public class PerfilService implements IPerfilService {

	// PerfilDAO is injected...
	IPerfilDAO perfilDAO;

	@Transactional(readOnly = false)
	public void addPerfil(Perfil perfil) {
		getPerfilDAO().addPerfil(perfil); // verifica se existe o ID cadastrado
	}

	@Override
	public boolean isExitePerfil(Perfil perfil) {
		if (getPerfilByTipo(perfil) != null)
			return true;
		else
			return false;
	}

	@Transactional(readOnly = false)
	public void deletePerfil(Perfil perfil) {
		getPerfilDAO().deletePerfil(perfil);
	}

	@Transactional(readOnly = false)
	public void updatePerfil(Perfil perfil) {
		getPerfilDAO().updatePerfil(perfil);
	}

	public Perfil getPerfilById(Perfil perfil) {
		return getPerfilDAO().getPerfilById(perfil);
	}

	public Perfil getPerfilByTipo(Perfil perfil) {
		return getPerfilDAO().getPerfilByTipo(perfil);
	}

	public List<Perfil> getPerfils() {
		return getPerfilDAO().getPerfils();
	}

	public List<Perfil> getTiposPerfil() {
		return getPerfilDAO().getTiposPerfil();
	}

	public IPerfilDAO getPerfilDAO() {
		return perfilDAO;
	}

	public void setPerfilDAO(IPerfilDAO perfilDAO) {
		this.perfilDAO = perfilDAO;
	}
}
