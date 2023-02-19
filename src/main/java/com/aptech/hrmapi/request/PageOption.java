package com.aptech.hrmapi.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageOption {
    private int pageIndex = 1;
    private int pageSize = 10;
    private Sort.Direction sortType = Sort.Direction.DESC;
    private String sortBy = "modifiedDate";
}
