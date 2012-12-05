package com.managed.bean;

import com.jsf.ds.impl.ComboPerfisDatasourceImpl;
import com.model.User;
import com.service.IUserService;
import com.util.Message;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name = "userMB")
@RequestScoped
public class UserManagedBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String SUCCESS = "cadUser";
    private static final String EDIT = "editUsuer";

    private User user;

    @ManagedProperty(value = "#{UserService}")
    IUserService userService;

    public UserManagedBean() {
        if (this.user == null) {
            this.user = new User();
        }

        // montaPerfilList();
    }

    /*
      * public void montaPerfilList() { System.out.println("1"); List<PerfilEnum>
      * list; list = new ArrayList<PerfilEnum>(); try {
      *
      * System.out.println("2");
      *
      * list.addAll(getPerfilService().getPerfils()); System.out.println("3"); }
      * catch (Exception e) { System.out.println("4");
      * System.out.println(e.toString()); }
      *
      * if (list != null) { for (PerfilEnum perfilCadastro : list) {
      * System.out.println("perfilCadastro" +
      * perfilCadastro.getTipo().toString()); } } }
      */

    /*
      * private void selecaoToPerfilEnum(User usuario) {
      * System.out.println("selecaoToPerfilEnum = 1"); List<PerfilEnum> perfis = new
      * ArrayList<PerfilEnum>(); System.out.println("selecaoToPerfilEnum = 2"); if
      * (perfilList != null) { System.out.println("selecaoToPerfilEnum = 3"); for
      * (int i = 0; i < this.user.getPerfil().size(); i++) {
      * System.out.println("selecaoToPerfilEnum =" + i);
      * perfis.add(perfilList.get(i)); }
      * System.out.println("selecaoToPerfilEnum = 4"); usuario.setPerfil(perfis);
      * } }
      */


    private String getDefaultPassword(String strCpf){
        //Senha default  composto pelos 5 primeiros numeros do CPF
        return strCpf.replace(".","").replace("-", "").substring(0, 5);
    }


    public String addUser() {


        // se o usuario existir atualiza
        if (this.user.getUserId() != 0) {
            // selecaoToPerfilEnum(this.user);
            userService.updateUser(this.user);
            return SUCCESS;
            // valida se existe p/adicionar
        } else if (!userService.isExiteUser(this.user)) {

            if (this.user.getSenha() == null) {
                this.user.setSenha(getDefaultPassword(this.user.getCpf()));
            }

            // /selecaoToPerfilEnum(this.user);
            userService.addUser(this.user);
            return SUCCESS;
        } else {
            Message.addMessage("cadastroUsuario.existente");
            return null;
        }
    }

    List<SelectItem> perfilList;
    List<User> userList;

    public String deleteUser(User user) {
        userService.deleteUser(user);
        return null;
    }

    public String updateUser(User user) {
        userService.updateUser(user);
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
        userList.addAll(userService.getUsers());
        return userList;
    }

    public List<SelectItem> getPerfilList() {
        if (perfilList==null) {
            perfilList = new ComboPerfisDatasourceImpl().findObjects();
        }
        return perfilList;
    }

    public void setUserService(IUserService userService) {
        this.userService = userService;
    }
}
