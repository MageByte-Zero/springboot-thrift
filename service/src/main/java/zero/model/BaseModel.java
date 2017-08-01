package zero.model;

import java.io.Serializable;

public class BaseModel implements Serializable {
    private int page = 1;

    private int pageSize = 10;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "BaseModel{" +
                "page=" + page +
                ", pageSize=" + pageSize +
                '}';
    }
}
