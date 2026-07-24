package com.example.library.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.library.common.BusinessException;
import com.example.library.common.ErrorCode;
import com.example.library.common.PageResponse;
import com.example.library.dto.book.BookListItem;
import com.example.library.dto.book.BookQuery;
import com.example.library.mapper.BookMapper;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    private final BookMapper bookMapper;

    public BookService(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    public PageResponse<BookListItem> page(BookQuery query) {
        Page<BookListItem> page = new Page<>(query.getPage(), query.getSize());
        return PageResponse.from(bookMapper.selectBookPage(page, query));
    }

    public BookListItem detail(Long id) {
        BookListItem item = bookMapper.selectBookDetail(id);
        if (item == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND, "图书不存在");
        }
        return item;
    }
}
