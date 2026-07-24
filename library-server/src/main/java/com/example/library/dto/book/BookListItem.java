package com.example.library.dto.book;

import java.time.LocalDate;

public class BookListItem {
    private Long id;
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private LocalDate publishDate;
    private Long categoryId;
    private String categoryName;
    private Integer totalStock;
    private Integer availableStock;
    private String location;
    private Integer status;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }
    public LocalDate getPublishDate() { return publishDate; }
    public void setPublishDate(LocalDate publishDate) { this.publishDate = publishDate; }
    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }
    public String getCategoryName() { return categoryName; }
    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
    public Integer getTotalStock() { return totalStock; }
    public void setTotalStock(Integer totalStock) { this.totalStock = totalStock; }
    public Integer getAvailableStock() { return availableStock; }
    public void setAvailableStock(Integer availableStock) { this.availableStock = availableStock; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
}
