package com.dao;

import com.model.PerfilEnum;
import com.model.User;

import java.util.EnumSet;
import java.util.List;

public interface IUserDAO extends ICrudDao<User> {

    User recuperar(User user);
    User recuperarPorCpf(User user);
    User recuperarPorEmail(User user);
    Boolean buscaPorLogin(User user);
    List<User> todos();
    List<User> listar(EnumSet<PerfilEnum> perfis);
}
