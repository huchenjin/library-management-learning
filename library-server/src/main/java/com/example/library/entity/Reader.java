package com.example.library.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("reader")
public class Reader {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String readerNo;
    private String name;
    private String phone;
    private String email;
    private Integer maxBorrowCount;
    private Integer status;
    @TableLogic
    private Integer deleted;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getReaderNo() { return readerNo; }
    public void setReaderNo(String readerNo) { this.readerNo = readerNo; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Integer getMaxBorrowCount() { return maxBorrowCount; }
    public void setMaxBorrowCount(Integer maxBorrowCount) { this.maxBorrowCount = maxBorrowCount; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public Integer getDeleted() { return deleted; }
    public void setDeleted(Integer deleted) { this.deleted = deleted; }
}
