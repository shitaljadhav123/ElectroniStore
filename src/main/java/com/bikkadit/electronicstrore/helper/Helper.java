package com.bikkadit.electronicstrore.helper;

import com.bikkadit.electronicstrore.dtos.PageableResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class Helper {


    public static <U, V> PageableResponse<V> getPageableResponse(Page<U> page, Class<V> type) {
        List<U> users = page.getContent();
        List<V> userDtoList = users.stream().map(object -> new ModelMapper().map(object, type)).collect(Collectors.toList());

        PageableResponse<V> pageableResponse = new PageableResponse();
        pageableResponse.setContent(userDtoList);
        pageableResponse.setPageNumber(page.getNumber());
        pageableResponse.setPageSize(page.getSize());
        pageableResponse.setLastPage(page.isLast());
        pageableResponse.setTotalPage(page.getTotalPages());
        pageableResponse.setTotalElements(page.getTotalElements());
        return pageableResponse;
    }
}
