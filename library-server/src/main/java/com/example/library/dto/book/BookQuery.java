package com.example.library.dto.book;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class BookQuery {
    @Min(value = 1, message = "页码不能小于 1")
    private long page = 1;
    @Min(value = 1, message = "每页数量不能小于 1")
    @Max(value = 100, message = "每页数量不能超过 100")
    private long size = 10;
    private String keyword;
    private Long categoryId;
    private Integer status;

    public long getPage() { return page; }
    public void setPage(long page) { this.page = page; }
    public long getSize() { return size; }
    public void setSize(long size) { this.size = size; }
    public String getKeyword() { return keyword; }
    public void setKeyword(String keyword) { this.keyword = keyword; }
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
}
