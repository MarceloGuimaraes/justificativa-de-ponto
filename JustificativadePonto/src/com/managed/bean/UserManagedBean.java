package com.managed.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import com.model.Perfil;
import com.model.User;
import com.service.IPerfilService;
import com.service.IUserService;
import com.util.Message;

//@author onlinetechvision.com

@ManagedBean(name = "userMB")
@RequestScoped
public class UserManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String SUCCESS = "cadUser";
	private static final String EDIT = "editUsuer";
	public String labelCadastro;

	private User user;

	@ManagedProperty(value = "#{UserService}")
	IUserService userService;

	@ManagedProperty(value = "#{PerfilService}")
	IPerfilService perfilService;

	public UserManagedBean() {
		if (this.user == null) {
			this.user = new User();
		}

		// montaPerfilList();
	}

	/*
	 * public void montaPerfilList() { System.out.println("1"); List<Perfil>
	 * list; list = new ArrayList<Perfil>(); try {
	 * 
	 * System.out.println("2");
	 * 
	 * list.addAll(getPerfilService().getPerfils()); System.out.println("3"); }
	 * catch (Exception e) { System.out.println("4");
	 * System.out.println(e.toString()); }
	 * 
	 * if (list != null) { for (Perfil perfilCadastro : list) {
	 * System.out.println("perfilCadastro" +
	 * perfilCadastro.getTipo().toString()); } } }
	 */

	/*
	 * private void selecaoToPerfilEnum(User usuario) {
	 * System.out.println("selecaoToPerfilEnum = 1"); List<Perfil> perfis = new
	 * ArrayList<Perfil>(); System.out.println("selecaoToPerfilEnum = 2"); if
	 * (perfilList != null) { System.out.println("selecaoToPerfilEnum = 3"); for
	 * (int i = 0; i < this.user.getPerfil().size(); i++) {
	 * System.out.println("selecaoToPerfilEnum =" + i);
	 * perfis.add(perfilList.get(i)); }
	 * System.out.println("selecaoToPerfilEnum = 4"); usuario.setPerfil(perfis);
	 * } }
	 */

	
	private String getDefaultPassword(String strCpf){
		//Senha default � composto pelos 5 primeiros n�meros do CPF
			return strCpf.replace(".","").replace("-", "").substring(0, 5);
	}
		
	
	public String addUser() {


		// se o usu�rio existir atualiza
		if (this.user.getUserId() != 0) {
			// selecaoToPerfilEnum(this.user);
			getUserService().updateUser(this.user);
			return SUCCESS;
			// valida se existe p/adicionar
		} else if (!getUserService().isExiteUser(this.user)) {
			
			if (this.user.getSenha() == null) {
                this.user.setSenha(getDefaultPassword(this.user.getCpf()));
			}
			
			// /selecaoToPerfilEnum(this.user);
			getUserService().addUser(this.user);
			return SUCCESS;
		} else {
			Message.addMessage("cadastroUsuario.existente");
			return null;
		}
	}

	List<Perfil> perfilList;
	List<User> userList;

	public String deleteUser(User user) {
		getUserService().deleteUser(user);
		return null;
	}

	public String updateUser(User user) {
		getUserService().updateUser(user);
		return SUCCESS;
	}

	public String editUser(User user) {
		this.user = user;
		return EDIT;
	}

	public String getLabelCadastro() {
		if (this.user.getUserId() == 0) {
			return Message.getBundleMessage("cadastroUsuario.label.titulo");
		} else {
			return Message
					.getBundleMessage("cadastroUsuario.label.alteraUsuario");
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void reset() {
		this.user = new User();
	}

	public List<User> getUserList() {
		userList = new ArrayList<User>();
		userList.addAll(getUserService().getUsers());
		return userList;
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

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
}
