package com.dao;

import java.util.List;
import com.model.Perfil;


public interface IPerfilDAO {

	public void addPerfil(Perfil perfil);
	public void updatePerfil(Perfil perfil);
	public void deletePerfil(Perfil perfil);
	public Perfil getPerfilById(Perfil perfil);
	public Perfil getPerfilByTipo(Perfil perfil);
	public List<Perfil> getPerfils();
	public List<Perfil> getTiposPerfil();
	
}