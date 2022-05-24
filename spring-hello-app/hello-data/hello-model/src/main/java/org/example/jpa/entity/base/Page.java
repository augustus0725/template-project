package org.example.jpa.entity.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"list"})
public class Page<T> {
    private long total;
    private int currentPage;
    private int pageSize;
    private List<T> list;

    // ----------------------------------------------

    public static boolean isEmpty(Page<?> page) {
        return page == null || CollectionUtils.isEmpty(page.getList());
    }

    public static <R> Page<R> emptyPage() {
        return of(0L, Collections.emptyList(), 0, 0);
    }

    public static <R> Page<R> of(Long total, List<R> list, int currentPage, int pageSize) {
        Page<R> page = new Page<>();
        page.setTotal(total);
        page.setList(list);
        page.setCurrentPage(currentPage);
        page.setPageSize(pageSize);
        return page;
    }

    public static <R> Page<R> of(org.springframework.data.domain.Page<R> page, int currentPage, int pageSize) {
        return of(page.getTotalElements(), page.getContent(), currentPage, pageSize);
    }

    public static <R, U> Page<R> of(Page<U> page, Function<List<? extends U>, List<R>> voMapper) {
        return of(page.getTotal(), voMapper.apply(page.getList()), page.getCurrentPage(), page.getPageSize());
    }
}
