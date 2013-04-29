package org.dao.util;

import java.util.ArrayList;

/**
 * @author mac
 * @company qm
 */
@SuppressWarnings({"unused", "unchecked"})
public class Page {

    private static int DEFAULT_PAGE_SIZE = 20;
    private int pageSize = DEFAULT_PAGE_SIZE;
    private long start;
    private Object data;
    private long totalCount;

    public Page() {
        this(0, 0, 20, new ArrayList());
    }

    public Page(long start, long totalCount, int pageSize, Object data) {
        this.start = start;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.data = data;
    }

    public long getTotalCount() {
        return this.totalCount;
    }

    public Object getResult() {
        return this.data;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public long getTotalPageCount() {
        return totalCount % pageSize == 0 ? totalCount / pageSize : totalCount
                / pageSize + 1;
    }

    public long getCurrentPageNo() {
        return start % pageSize + 1;
    }

    public boolean hasNextPage() {
        return this.getCurrentPageNo() < this.getTotalPageCount() - 1;
    }

    public boolean hasPreviousPage() {
        return this.getCurrentPageNo() > 1;
    }

    protected static int getStartOfPage(int pageNo) {
        return getStartOfPage(pageNo, DEFAULT_PAGE_SIZE);
    }

    public static int getStartOfPage(int pageNo, int pageSize) {
        return (pageNo - 1) * pageSize;
    }
}
