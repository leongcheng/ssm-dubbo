package com.ssm.common.service;

public interface Function<E, T> {

    public T execute(E e);

}
