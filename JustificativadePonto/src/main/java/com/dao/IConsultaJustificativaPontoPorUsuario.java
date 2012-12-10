package com.dao;

import com.model.JustificativaPonto;
import com.model.User;

import java.util.List;

public interface IConsultaJustificativaPontoPorUsuario {
    List<JustificativaPonto> todosPorData(int startIndex, int pageSize, User user);
    int count(User user);
}
