package com.basis.sge.service.servico.exception.dtoErros;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ObjectError {

    private final String message;
    private final String field;
    private final Object parameter;
}