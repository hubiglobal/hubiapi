package org.hubi.api.contract.domain;

import lombok.Data;

@Data
public class Result<T> {
    private int status;
    private String message;
    private T result;
}
