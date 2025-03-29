package com.deinsoft.puntoventa.business.dto;

import org.springframework.beans.BeanUtils;

public class BaseDTO<E> {
    public BaseDTO(E entity) {
        BeanUtils.copyProperties(entity, this);
    }
}