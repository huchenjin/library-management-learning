CREATE TABLE sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL,
    password_hash VARCHAR(100) NOT NULL,
    display_name VARCHAR(100) NOT NULL,
    role VARCHAR(30) NOT NULL DEFAULT 'ADMIN',
    status TINYINT NOT NULL DEFAULT 1,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT uk_sys_user_username UNIQUE (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE book_category (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(80) NOT NULL,
    sort_no INT NOT NULL DEFAULT 0,
    status TINYINT NOT NULL DEFAULT 1,
    deleted TINYINT NOT NULL DEFAULT 0,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT uk_book_category_name UNIQUE (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE book (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    isbn VARCHAR(32) NOT NULL,
    title VARCHAR(200) NOT NULL,
    author VARCHAR(120) NOT NULL,
    publisher VARCHAR(120),
    publish_date DATE,
    category_id BIGINT NOT NULL,
    total_stock INT NOT NULL DEFAULT 0,
    available_stock INT NOT NULL DEFAULT 0,
    location VARCHAR(100),
    status TINYINT NOT NULL DEFAULT 1,
    version INT NOT NULL DEFAULT 0,
    deleted TINYINT NOT NULL DEFAULT 0,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT uk_book_isbn UNIQUE (isbn),
    CONSTRAINT fk_book_category FOREIGN KEY (category_id) REFERENCES book_category (id),
    CONSTRAINT ck_book_stock CHECK (total_stock >= 0 AND available_stock >= 0 AND available_stock <= total_stock),
    INDEX idx_book_title (title),
    INDEX idx_book_author (author),
    INDEX idx_book_category (category_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE reader (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    reader_no VARCHAR(40) NOT NULL,
    name VARCHAR(100) NOT NULL,
    phone VARCHAR(30),
    email VARCHAR(120),
    max_borrow_count INT NOT NULL DEFAULT 5,
    status TINYINT NOT NULL DEFAULT 1,
    deleted TINYINT NOT NULL DEFAULT 0,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT uk_reader_no UNIQUE (reader_no),
    CONSTRAINT ck_reader_borrow_limit CHECK (max_borrow_count > 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE borrow_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    reader_id BIGINT NOT NULL,
    book_id BIGINT NOT NULL,
    borrowed_at DATETIME NOT NULL,
    due_at DATETIME NOT NULL,
    returned_at DATETIME,
    status VARCHAR(20) NOT NULL DEFAULT 'BORROWED',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_borrow_reader FOREIGN KEY (reader_id) REFERENCES reader (id),
    CONSTRAINT fk_borrow_book FOREIGN KEY (book_id) REFERENCES book (id),
    INDEX idx_borrow_reader_status (reader_id, status),
    INDEX idx_borrow_book_status (book_id, status),
    INDEX idx_borrow_due_at (due_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
