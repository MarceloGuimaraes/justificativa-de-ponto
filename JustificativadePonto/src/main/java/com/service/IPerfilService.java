package com.service;

import java.util.List;

import com.model.Perfil;

public interface IPerfilService {
	
	public void addPerfil(Perfil perfil);
	public void updatePerfil(Perfil perfil);
	public void deletePerfil(Perfil perfil);
	public Perfil getPerfilById(Perfil perfil);
	public Perfil getPerfilByTipo(Perfil perfil);
	public List<Perfil> getPerfils();
	public abstract boolean isExitePerfil(Perfil perfil);
	public List<Perfil> getTiposPerfil();
	
}
