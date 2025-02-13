package com.banco.cuenta_bancaria.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class SystemMessage implements Serializable {

    private static final long serialVersionUID = 1L;
    private String source;
    private String message;
}
