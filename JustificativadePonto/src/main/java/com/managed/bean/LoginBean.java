package com.managed.bean;

import com.domain.dto.UsuarioLogado;
import com.domain.dto.UsuarioLogin;
import com.service.IUserService;
import com.service.mail.IMailService;
import com.util.Message;
import org.primefaces.context.RequestContext;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import java.io.Serializable;

public class LoginBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String REDIRECT_TROCA_SENHA = "/pages/adm/senha.jsf?faces-redirect=true";

	private static final String REDIREC_LOGOUT = "/pages/login?faces-redirect=true";

	private static final String PAGE_WELCOME = "/pages/welcome.jsf?faces-redirect=true";

	private static final String REDIRECT_JUSTIFICATIVA = "/pages/justificativa.jsf?faces-redirect=true&id=";

	private IUserService userService;
    private IMailService mailService;

    private IPermissoesBean permissoesBean;
	
	private UsuarioLogin usuarioLogin;

	private String id;

	public LoginBean(IPermissoesBean permissoes,
                     IUserService userService,
                     IMailService mailService) {
		this.permissoesBean = permissoes;
		this.userService = userService;
        this.mailService = mailService;
		usuarioLogin = new UsuarioLogin();
		id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");
	}

	public UsuarioLogin getUsuarioLogin() {
		return usuarioLogin;
	}

	public void setUsuarioLogin(UsuarioLogin usuarioLogin) {
		this.usuarioLogin = usuarioLogin;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String efetuaLogin() {
		if (usuarioLogin.getEmail() == null || usuarioLogin.getSenha() == null) {
			Message.addMessage("login.loginEpassw.required");
			return null;
		}

		final UsuarioLogado usuarioLogado = userService.buscaPorLogin(usuarioLogin);

		if (usuarioLogado != null) {

			permissoesBean.setUsuarioLogado(usuarioLogado);

			// verifica se contem 5 caracteres conforme a senha default
			if(usuarioLogin.getSenha()==null){
				Message.addMessageConfig("cadastroUsuario.senha.senhaDefault");
				return REDIRECT_TROCA_SENHA;
			}
			if (usuarioLogin.getSenha().equals(userService.getDefaultPassword(usuarioLogado.getCpf()))) {
                Message.addMessageConfig("cadastroUsuario.senha.senhaDefault");
                return REDIRECT_TROCA_SENHA;
            }
			if(id==null || "".equals(id)){
				return PAGE_WELCOME;
			} else {
				return REDIRECT_JUSTIFICATIVA +id;
			}

		} else {
			Message.addMessage("login.invalido");
			return null;
		}
	}

	public String logOut() {

		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

		context.invalidateSession();

		permissoesBean.logOut();

		return REDIREC_LOGOUT;

	}

	public void resetaSenha(ActionEvent event) {
        final RequestContext context = RequestContext.getCurrentInstance();
        try{
            if (usuarioLogin.getEmail() == null || usuarioLogin.getEmail().equals("")) {
                Message.addMessage("login.email.require");
                context.addCallbackParam("sucesso", false);
                return;
            }
            final String novaSenha = userService.resetaSenha(usuarioLogin);
            if(novaSenha==null){
                Message.addMessage("login.resetasenha.usuarionaoencontrado");
                context.addCallbackParam("sucesso", false);
                return;
            }
            mailService.resetaSenha(usuarioLogin, novaSenha);
            context.addCallbackParam("sucesso", true);
        } catch (Exception e){
            Message.addMessage("dialog.cancelar.erro.inesperado.semusuario");
            context.addCallbackParam("sucesso", false);
        }
	}
}
