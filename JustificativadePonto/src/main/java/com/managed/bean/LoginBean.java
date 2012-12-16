package com.managed.bean;

import com.domain.dto.UsuarioLogado;
import com.domain.dto.UsuarioLogin;
import com.service.IUserService;
import com.util.Message;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.Serializable;

public class LoginBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String SUCCESS = "welcome";

    private static final String REDIRECT_TROCA_SENHA = "/pages/adm/senha.jsf?faces-redirect=true";

    private static final String REDIREC_LOGOUT = "/pages/login?faces-redirect=true";

    private IUserService userService;

    private IPermissoesBean permissoesBean;

    private UsuarioLogin usuarioLogin;

    private String id;

    public LoginBean(IPermissoesBean permissoes,
                     IUserService userService) {

        this.permissoesBean = permissoes;

        this.userService = userService;

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
            Message.addMessage("login.userEpassw.required");
            return null;
        }

        UsuarioLogado usuarioLogado = userService.buscaPorLogin(usuarioLogin);

        if (usuarioLogado != null) {

            permissoesBean.setUsuarioLogado(usuarioLogado);

            // verifica se contem 5 caracteres conforme a senha default
            if(usuarioLogin.getSenha()==null){
                Message.addMessageConfig("cadastroUsuario.senha.senhaDefault");
                return REDIRECT_TROCA_SENHA;
            }
            if (usuarioLogin.getSenha().length() == 5) {
                if (usuarioLogin.getSenha().equals(
                        usuarioLogin.getSenha().replace(".", "").replace("-", "")
                                .substring(0, 5))) {
                    Message.addMessageConfig("cadastroUsuario.senha.senhaDefault");
                    return REDIRECT_TROCA_SENHA;
                }
            }
            if(id==null){
                return SUCCESS;
            } else {
                return "/pages/justificativa.jsf?faces-redirect=true&id="+id;
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

}
