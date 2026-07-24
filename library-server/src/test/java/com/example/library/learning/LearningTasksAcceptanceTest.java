package com.example.library.learning;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class LearningTasksAcceptanceTest {
    @Disabled("完成 LEARNING-1 后启用：分类 CRUD、唯一性和关联删除")
    @Test void learning1CategoryManagement() { fail("TODO(LEARNING-1)"); }

    @Disabled("完成 LEARNING-2 后启用：图书维护与库存约束")
    @Test void learning2BookManagement() { fail("TODO(LEARNING-2)"); }

    @Disabled("完成 LEARNING-3 后启用：读者管理与删除约束")
    @Test void learning3ReaderManagement() { fail("TODO(LEARNING-3)"); }

    @Disabled("完成 LEARNING-4 后启用：借阅事务")
    @Test void learning4BorrowTransaction() { fail("TODO(LEARNING-4)"); }

    @Disabled("完成 LEARNING-5 后启用：归还幂等与库存恢复")
    @Test void learning5ReturnTransaction() { fail("TODO(LEARNING-5)"); }

    @Disabled("完成 LEARNING-6 后启用：统计 SQL")
    @Test void learning6DashboardStatistics() { fail("TODO(LEARNING-6)"); }

    @Disabled("完成 LEARNING-7 后启用：并发库存与审计")
    @Test void learning7ConcurrencyAndAudit() { fail("TODO(LEARNING-7)"); }
}
