package com.service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface IConsultaPaginadaService<T> {
    List<T> todas(int startIndex, int pageSize);
    long count();
}
