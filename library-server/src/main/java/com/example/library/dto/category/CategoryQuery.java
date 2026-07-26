package com.example.library.dto.category;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class CategoryQuery {
    @Min(value = 1, message = "页码不能小于 1")
    private long page = 1;
    @Min(value = 1, message = "每页数量不能小于 1")
    @Max(value = 100, message = "每页数量不能超过 100")
    private long size = 10;

    public long getPage() { return page; }
    public void setPage(long page) { this.page = page; }
    public long getSize() { return size; }
    public void setSize(long size) { this.size = size; }
}
