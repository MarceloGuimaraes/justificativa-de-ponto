package com.managed.bean.helper;

/**
 * User: xonda
 * Date: 26/07/13
 * Time: 13:45
 */
public interface BeanWrapper<W,T> {
    W wrap(T t);
}
