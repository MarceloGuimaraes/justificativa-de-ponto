package com.service;

import java.io.Serializable;
import java.util.List;

import com.domain.dto.CadastroSenha;
import com.domain.dto.CadastroUsuario;
import com.domain.dto.UsuarioLogado;
import com.domain.dto.UsuarioLogin;
import com.model.User;

public interface IUserService {

    void atualizar(CadastroUsuario usuario);
    CadastroUsuario adicionar(CadastroUsuario usuario);
    CadastroUsuario recuperar(Serializable id);
    void apagar(CadastroUsuario usuario);
    List<CadastroUsuario> todos();

	void addUser(User user);
	void updateUser(User user);
    User recuperar(Integer id);
    String resetaSenha(UsuarioLogin usuarioLogin);
    boolean isExisteUser(CadastroUsuario usuario);
    UsuarioLogado buscaPorLogin(UsuarioLogin usuarioLogin);
    List<CadastroUsuario> recuperaCoordenadores();
    List<CadastroUsuario> recuperaSuperintendentes();
    List<CadastroUsuario> recuperaRH();

    void alteraSenha(CadastroSenha cadastroSenha);
}
