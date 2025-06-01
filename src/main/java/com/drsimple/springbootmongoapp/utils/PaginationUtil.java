package com.drsimple.springbootmongoapp.utils;


import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Map;

public class PaginationUtil {

    public static Pageable createPageable(Integer page, Integer size, String sortBy, String sortDirection) {
        // Convert 1-based page input to 0-based internally
        int pageNumber = (page != null && page > 0) ? page - 1 : 0;
        int pageSize = (size != null && size > 0) ? size : 10;

        Sort.Direction direction = (sortDirection != null && sortDirection.equalsIgnoreCase("desc"))
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        if (sortBy == null || sortBy.isBlank()) {
            sortBy = "id";
        }

        return PageRequest.of(pageNumber, pageSize, Sort.by(direction, sortBy));
    }


    public static Criteria createFilterCriteria(Map<String, String> filters) {
        if (filters == null || filters.isEmpty()) {
            return new Criteria(); // will be handled differently in `buildQueryWithFilterAndPageable`
        }

        List<Criteria> criteriaList = new ArrayList<>();
        filters.forEach((key, value) -> {
            if (value != null && !value.isBlank()) {
                criteriaList.add(Criteria.where(key).regex(".*" + value + ".*", "i"));
            }
        });

        return criteriaList.isEmpty() ? new Criteria() : new Criteria().andOperator(criteriaList.toArray(new Criteria[0]));
    }


    public static Query buildQueryWithFilterAndPageable(Map<String, String> filters, Pageable pageable) {
        Query query = new Query().with(pageable);

        if (filters != null && !filters.isEmpty()) {
            Criteria criteria = createFilterCriteria(filters);
            query.addCriteria(criteria);
        }

        return query;
    }


}