package com.pagination;

import org.springframework.data.domain.Pageable;

public interface Pagination {
    Pageable create(
            int page,
            int size,
            String sortBy,
            String direction
    );
}
