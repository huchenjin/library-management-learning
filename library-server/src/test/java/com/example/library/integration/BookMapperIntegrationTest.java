package com.example.library.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.library.dto.book.BookListItem;
import com.example.library.dto.book.BookQuery;
import com.example.library.mapper.BookMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers(disabledWithoutDocker = true)
@SpringBootTest
class BookMapperIntegrationTest {
    @Container
    static final MySQLContainer<?> MYSQL = new MySQLContainer<>("mysql:8.4")
            .withDatabaseName("library")
            .withUsername("library")
            .withPassword("library123");

    @DynamicPropertySource
    static void databaseProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MYSQL::getJdbcUrl);
        registry.add("spring.datasource.username", MYSQL::getUsername);
        registry.add("spring.datasource.password", MYSQL::getPassword);
    }

    @Autowired
    BookMapper mapper;

    @Test
    void flywayShouldSeedAndMapperShouldFilterBooks() {
        BookQuery query = new BookQuery();
        query.setKeyword("Java");
        Page<BookListItem> result = mapper.selectBookPage(new Page<>(1, 10), query);
        assertEquals(2, result.getTotal());
        assertFalse(result.getRecords().isEmpty());
    }
}
