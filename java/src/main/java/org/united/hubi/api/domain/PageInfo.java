package org.united.hubi.api.domain;

import lombok.Data;

import java.util.List;

@Data
public class PageInfo<T> {
    private List<T> content;
    private int pageSize;
    private int pageNumber;
}
