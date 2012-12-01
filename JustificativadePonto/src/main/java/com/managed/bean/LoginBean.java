package com.managed.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
//import javax.servlet.http.HttpSession;

import com.model.User;
import com.service.IUserService;
import com.util.Message;

@SessionScoped
@ManagedBean(name = "loginController")
public class LoginBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String SUCCESS = "welcome";
    private static final String TROCASENHA = "";

    // Spring User Service is injected...
    @ManagedProperty(value = "#{UserService}")
    IUserService userService;

    private User user;
    private String senhaNova;
    private String confirmaSenhaNova;

    public String getConfirmaSenhaNova() {
        return confirmaSenhaNova;
    }

    public void setConfirmaSenhaNova(String confirmaSenhaNova) {
        this.confirmaSenhaNova = confirmaSenhaNova;
    }

    public String getSenhaNova() {
        return senhaNova;
    }

    public void setSenhaNova(String senhaNova) {
        this.senhaNova = senhaNova;
        // System.out.println("NOVA SENHA:" + senhaNova);
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String efetuaLogin() {
        if (user.getEmail() == null || user.getSenha() == null) {
            Message.addMessage("login.userEpassw.required");
            return null;
        }

        if (getUserService().buscaPorLogin(this.user)) {

            this.user = getUserService().getUserByEmail(this.user);

            System.out.println("CPF DO USER => " + this.user.getCpf());

            FacesContext.getCurrentInstance().getExternalContext()
                    .getSessionMap().put("usuarioLogado", this.user);

            // recuperando p/testar se esta sendo adicionado na sess�o
            // User currentUser =
            // (User)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuarioLogado");
            // System.out.println("currentUser => " + currentUser.getCpf());

            // verifica se contem 5 caracteres conforme a senha default
            if (this.user.getSenha().length() == 5) {
                if (this.user.getSenha().equals(
                        this.user.getSenha().replace(".", "").replace("-", "")
                                .substring(0, 5))) {
                    // return TROCASENHA;
                    Message.addMessageConfig("cadastroUsuario.senha.senhaDefault");
                    return "/pages/adm/senha.jsf?faces-redirect=true";
                } else {
                    return SUCCESS;
                }
            } else {
                return SUCCESS;
            }

        } else {
            Message.addMessage("login.invalido");
            return null;
        }
    }

    public String alteraSenha() {

        if (this.user.getSenha() == "") {
            Message.addMessage("login.passw.atual");
            return null;
            // /dao.existe(this.user).getId() == null
        } else if (!getUserService().buscaPorLogin(this.user)) {
            Message.addMessage("login.passw.atual");
            return null;
        } else if (getSenhaNova() == "") {
            Message.addMessage("login.passw.nova");
            return null;

        } else if (getConfirmaSenhaNova() == "") {
            Message.addMessage("login.passw.confirm");
            return null;
        } else if (!getSenhaNova().equals(getConfirmaSenhaNova())) {
            Message.addMessage("login.passw.confirmError");
            return null;
        } else {
            // user.setSenha(Criptografia.encodePassword(getSenhaNova()));
            user.setSenha(getSenhaNova());
            // /dao.altera(user);
            getUserService().updateUser(this.user);
            Message.addMessageConfig("login.passw.confirmOk");
            return "/pages/wellcome.jsf?faces-redirect=true";
            //	return "/pages/adm/senha.jsf?faces-redirect=true";
            // return SUCCESS;
        }
    }

    /*
      * public boolean isLogado() {
      * System.out.println("==========================Usuario: " +
      * user.getEmail()); return user.getEmail() != null; }
      */

    public String logOut() {

        this.user = new User();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext context = facesContext.getExternalContext();
        context.invalidateSession();
        facesContext.getExternalContext().getSessionMap()
                .remove("usuarioLogado");
        return "/pages/login?faces-redirect=true";

        /*
           *
           * this.user = new User();
           * FacesContext.getCurrentInstance().getExternalContext
           * ().getSessionMap() .remove("loginController"); return
           * "/pages/login?faces-redirect=true"; //return "login.xhtml";
           *
           *
           * this.usuario = new Usuario(); FacesContext facesContext =
           * FacesContext.getCurrentInstance(); HttpSession session =
           * (HttpSession) facesContext.getExternalContext().getSession(false);
           * session.invalidate();
           * facesContext.getExternalContext().getSessionMap(
           * ).remove("loginController"); return "login";
           */
    }

    public LoginBean() {
        if (this.user == null) {
            this.user = new User();
        }
    }

    public IUserService getUserService() {
        System.out.println("SERVICE getUserService");
        return userService;
    }

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }
}
