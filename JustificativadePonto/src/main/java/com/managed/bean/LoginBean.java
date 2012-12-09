package com.managed.bean;

import com.model.User;
import com.service.IUserService;
import com.util.JsfUtil;
import com.util.Message;
import org.springframework.beans.factory.annotation.Autowired;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.Serializable;

@SessionScoped
@ManagedBean(name = "loginController")
public class LoginBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String SUCCESS = "welcome";

    private static final String USUARIO_LOGADO = "usuarioLogado";

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
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String efetuaLogin() {
        if (user.getEmail() == null || user.getSenha() == null) {
            Message.addMessage("login.userEpassw.required");
            return null;
        }

        if (userService.buscaPorLogin(this.user)) {

            this.user = userService.getUserByEmail(this.user);

            JsfUtil.setSessionValue(USUARIO_LOGADO, this.user);

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


    public boolean verificaIdQueryString(){

        return false;
    }



    public String alteraSenha() {

        if (this.user.getSenha() == "") {
            Message.addMessage("login.passw.atual");
            return null;
            // /dao.existe(this.user).getId() == null
        } else if (!userService.buscaPorLogin(this.user)) {
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
            user.setSenha(getSenhaNova());
            userService.updateUser(this.user);
            Message.addMessageConfig("login.passw.confirmOk");
            return "/pages/wellcome.jsf?faces-redirect=true";
        }
    }

    public String logOut() {

        this.user = new User();

        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

        context.invalidateSession();

        JsfUtil.removeFromSession(USUARIO_LOGADO);

        return "/pages/login?faces-redirect=true";

    }

    public LoginBean() {
        if (this.user == null) {
            this.user = new User();
        }
    }

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

}
