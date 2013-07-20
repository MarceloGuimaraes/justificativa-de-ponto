package com.dao;

import java.util.List;

public interface IConsultaFiltradaPaginadaDao<T,F> {
    List<T> todos(F filtro, int startIndex, int pageSize);
    long count(F filtro);
}
