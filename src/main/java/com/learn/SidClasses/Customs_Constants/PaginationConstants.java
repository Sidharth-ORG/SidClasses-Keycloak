package com.learn.SidClasses.Customs_Constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaginationConstants<T> {
    private  int  pageNo;
    private int  pageSize;
    private long totalElements;
    private int  totalPages;
    private List<T> content;
    private boolean isLast;
}
