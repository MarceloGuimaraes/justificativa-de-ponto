package com.service;

import java.util.List;

/**
 * User: xonda
 * Date: 20/07/13
 * Time: 13:43
 */
public interface IConsultaFiltradaPaginadaService<T,F> {
    List<T> todas(F filtro, int startIndex, int pageSize);
    long count(F filtro);
}
