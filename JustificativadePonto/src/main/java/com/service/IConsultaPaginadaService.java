package com.service;

import java.util.List;

public interface IConsultaPaginadaService<T> {
    List<T> todas(int startIndex, int pageSize);
    int count();
}
