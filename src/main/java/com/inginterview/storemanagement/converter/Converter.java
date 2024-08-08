package com.inginterview.storemanagement.converter;

public interface Converter<T, U> {

    U dtoToEntity(T t);

    T entityToDto(U u);
}
