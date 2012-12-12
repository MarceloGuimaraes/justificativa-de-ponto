package com.managed.bean;

import com.domain.dto.CadastroUsuario;
import com.jsf.ds.impl.ComboPerfisDatasourceImpl;
import com.service.IUserService;
import com.util.Message;

import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.List;

public class UserManagedBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String SUCCESS = "cadUser";
    private static final String EDIT = "editUsuer";

    private CadastroUsuario usuario;

    IUserService userService;

    public UserManagedBean(IUserService userService) {
        this.userService = userService;

        usuario = new CadastroUsuario();

        perfilList = new ComboPerfisDatasourceImpl().findObjects();
    }

    public String salvar() {

        if (usuario.getId() != null && usuario.getId() != 0) {

            userService.atualizar(usuario);

            return SUCCESS;

        } else if (!userService.isExisteUser(usuario)) {

            userService.adicionar(usuario);

            return SUCCESS;

        } else {
            Message.addMessage("cadastroUsuario.existente");
            return null;
        }
    }

    public String editar(){
        return EDIT;
    }

    public String deletar(){
        userService.apagar(usuario);
        return SUCCESS;
    }

    private List<SelectItem> perfilList;

    public String getLabelCadastro() {
        if (usuario.getId() == null) {
            return Message.getBundleMessage("cadastroUsuario.label.titulo");
        } else {
            return Message.getBundleMessage("cadastroUsuario.label.alteraUsuario");
        }
    }

    public CadastroUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(CadastroUsuario usuario) {
        this.usuario = usuario;
    }

    public void limpar() {
        usuario = new CadastroUsuario();
    }

    public List<SelectItem> getPerfilList() {
        return perfilList;
    }

    public List<CadastroUsuario> getUserList() {
        return userService.todos();
    }

}
