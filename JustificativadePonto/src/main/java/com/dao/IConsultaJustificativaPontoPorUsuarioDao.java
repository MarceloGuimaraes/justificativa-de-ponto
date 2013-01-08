package com.dao;

import com.domain.dto.JustificativaPontoGrid;
import com.model.User;

import java.util.List;

public interface IConsultaJustificativaPontoPorUsuarioDao {
    List<JustificativaPontoGrid> todos(int startIndex, int pageSize, User user);
    int count(User user);
}
