package com.example.library.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.library.dto.book.BookListItem;
import com.example.library.dto.book.BookQuery;
import com.example.library.entity.Book;
import org.apache.ibatis.annotations.Param;

public interface BookMapper extends BaseMapper<Book> {
    Page<BookListItem> selectBookPage(Page<BookListItem> page, @Param("query") BookQuery query);
    BookListItem selectBookDetail(@Param("id") Long id);
}
