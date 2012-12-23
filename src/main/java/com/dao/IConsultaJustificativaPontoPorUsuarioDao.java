package com.dao;

import com.model.JustificativaPonto;
import com.model.User;

import java.util.List;

public interface IConsultaJustificativaPontoPorUsuarioDao {
    List<JustificativaPonto> todos(int startIndex, int pageSize, User user);
    int count(User user);
}
