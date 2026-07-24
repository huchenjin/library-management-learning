<script setup lang="ts">
const tasks = [
  { id: 'LEARNING-1', title: '分类管理', text: 'CRUD、唯一约束与关联删除校验', level: '入门' },
  { id: 'LEARNING-2', title: '图书维护', text: '参数校验、库存规则与逻辑删除', level: '入门' },
  { id: 'LEARNING-3', title: '读者管理', text: '业务编号、状态与借阅记录约束', level: '进阶' },
  { id: 'LEARNING-4/5', title: '借阅归还', text: 'Spring 事务、库存一致性与异常处理', level: '核心' },
  { id: 'LEARNING-6', title: '统计看板', text: '自定义 SQL、聚合查询与接口设计', level: '进阶' },
  { id: 'LEARNING-7', title: '并发与审计', text: '乐观锁、并发测试与操作日志', level: '挑战' },
]
</script>

<template>
  <div class="dashboard">
    <section class="hero-card">
      <div>
        <span class="status-pill">脚手架已就绪</span>
        <h2>先读懂示例，再完成你的第一条业务链路</h2>
        <p>建议从图书查询的 Controller → Service → Mapper XML 顺序阅读，然后开始 LEARNING-1。</p>
      </div>
      <RouterLink to="/books" class="primary-link">查看完整示例 →</RouterLink>
    </section>
    <section>
      <div class="section-heading"><div><h2>学习路线</h2><p>每一项都有接口契约、TODO 和禁用测试。</p></div><span>共 7 个阶段</span></div>
      <div class="task-grid">
        <article v-for="(task, index) in tasks" :key="task.id" class="task-card">
          <div class="task-index">{{ String(index + 1).padStart(2, '0') }}</div>
          <div><div class="task-meta"><code>{{ task.id }}</code><span>{{ task.level }}</span></div><h3>{{ task.title }}</h3><p>{{ task.text }}</p></div>
        </article>
      </div>
    </section>
  </div>
</template>
