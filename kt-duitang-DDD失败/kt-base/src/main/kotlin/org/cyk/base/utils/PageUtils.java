package org.cyk.base.utils;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public class PageUtils {

    public static <T> Page<T> pageDesc(
            Integer start,
            Integer limit,
            String orderColumn, //根据什么字段排序
            Class<T> entityClass
    ) {
        Page<T> page = Page.of(start, limit);
        page.addOrder(OrderItem.desc(orderColumn));
        return page;
    }

}
