package com.dao;

import com.domain.dto.JustificativaPontoGrid;

import java.util.List;

public interface IConsultaJustificativaPontoDao {
    List<JustificativaPontoGrid> todos(int startIndex, int pageSize);
    int count();
}
