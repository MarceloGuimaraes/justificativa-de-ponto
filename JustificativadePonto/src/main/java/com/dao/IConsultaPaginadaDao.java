package com.dao;

import java.util.List;

public interface IConsultaPaginadaDao<T> {
    List<T> todos(int startIndex, int pageSize);
    int count();
}
