package com.managed.bean;

import com.domain.dto.CadastroSenha;
import com.domain.dto.exception.BusinessException;
import com.service.IUserService;
import com.util.Message;

import java.io.Serializable;

public class AlteraSenhaManagedBean implements Serializable {

    private CadastroSenha cadastroSenha;

    private IUserService userService;

    public AlteraSenhaManagedBean(IUserService userService, IPermissoesBean permissoes) {
        this.userService = userService;
        cadastroSenha = new CadastroSenha();
        cadastroSenha.setId(permissoes.getUsuarioLogado().getId());
    }

    public CadastroSenha getCadastroSenha() {
        return cadastroSenha;
    }

    public void setCadastroSenha(CadastroSenha cadastroSenha) {
        this.cadastroSenha = cadastroSenha;
    }

    public String preparaAlteracaoSenha(){
        return "alterasenha";
    }

    public String alteraSenha() {

        if(cadastroSenha.getId()==null || cadastroSenha.getId()==0){
            throw new IllegalArgumentException("O usuário informado não existe");
        }

        if (!cadastroSenha.isSenhaInformada()) {
            Message.addMessage("login.passw.atual");
            return null;
        }

        if (!cadastroSenha.isNovaSenhaInformada()) {
            Message.addMessage("login.passw.nova");
            return null;
        }

        if (!cadastroSenha.isConfirmaNovaSenhaInformada()) {
            Message.addMessage("login.passw.confirm");
            return null;
        }

        if (!cadastroSenha.isSenhaConfirmada()) {
            Message.addMessage("login.passw.confirmError");
            return null;
        }

        try {
            userService.alteraSenha(cadastroSenha);
        } catch (BusinessException be) {
            Message.addMessage("login.passw.atualInvalida");
            return null;
        }

        Message.addMessageConfig("login.passw.confirmOk");

        return "senhaalterada";

    }

}
