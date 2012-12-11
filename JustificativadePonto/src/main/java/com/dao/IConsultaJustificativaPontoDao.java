package com.dao;

import com.model.JustificativaPonto;

import java.util.List;

public interface IConsultaJustificativaPontoDao {
    List<JustificativaPonto> todos(int startIndex, int pageSize);
    int count();
}
