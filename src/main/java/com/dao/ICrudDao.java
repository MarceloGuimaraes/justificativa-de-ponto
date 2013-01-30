package com.dao;

import java.io.Serializable;

public interface ICrudDao<T> {
    T recuperar(Serializable id);
    Serializable adicionar(T t);
    T atualizar(T t);
    void deletar(T t);
    void initialize(Object o);
}
