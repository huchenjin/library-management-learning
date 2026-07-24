INSERT INTO sys_user (username, password_hash, display_name, role, status)
VALUES ('admin', '$2y$10$QNjTSU/3LhLVIsw2UXtm8uXU7Ks.7OdLl1sSB6B1Akn9LvzUGcvz.', '图书管理员', 'ADMIN', 1);

INSERT INTO book_category (name, sort_no, status) VALUES
('计算机', 10, 1),
('文学', 20, 1),
('历史', 30, 1),
('科学', 40, 1);

INSERT INTO book (isbn, title, author, publisher, publish_date, category_id,
                  total_stock, available_stock, location, status)
VALUES
('9787115546081', 'Java 核心技术（卷 I）', '凯·霍斯特曼', '人民邮电出版社', '2022-01-01', 1, 5, 5, 'A-01-01', 1),
('9787115428028', '深入理解 Java 虚拟机', '周志明', '机械工业出版社', '2019-12-01', 1, 4, 4, 'A-01-02', 1),
('9787020002207', '红楼梦', '曹雪芹', '人民文学出版社', '1996-12-01', 2, 3, 3, 'B-02-01', 1),
('9787101003048', '史记', '司马迁', '中华书局', '2014-08-01', 3, 2, 2, 'C-01-01', 1),
('9787535732309', '时间简史', '史蒂芬·霍金', '湖南科学技术出版社', '2018-03-01', 4, 3, 3, 'D-01-01', 1);

INSERT INTO reader (reader_no, name, phone, email, max_borrow_count, status)
VALUES
('R20260001', '张同学', '13800000001', 'zhang@example.com', 5, 1),
('R20260002', '李同学', '13800000002', 'li@example.com', 5, 1);
