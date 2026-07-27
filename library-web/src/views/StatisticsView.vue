<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { Collection, Reading, Refresh, Tickets, User } from '@element-plus/icons-vue'
import { getDashboardSummary, type DashboardSummary } from '@/api/dashboard'
import { isNotImplementedError } from '@/api/http'

const loading = ref(false)
const unavailable = ref(false)
const summary = ref<DashboardSummary>({ bookTypeCount: 0, totalBookCount: 0, readerCount: 0, borrowedCount: 0, overdueCount: 0, popularBooks: [] })

async function loadSummary() {
  loading.value = true
  try {
    summary.value = await getDashboardSummary()
    unavailable.value = false
  } catch (error) {
    unavailable.value = isNotImplementedError(error)
  } finally {
    loading.value = false
  }
}

onMounted(loadSummary)
</script>

<template>
  <div v-loading="loading" class="statistics-page">
    <el-alert v-if="unavailable" class="learning-alert" title="统计接口尚未实现" type="warning" :closable="false" show-icon>
      <template #default>完成 LEARNING-6 的聚合 SQL 后，以下卡片和热门排行会自动展示真实数据。</template>
    </el-alert>
    <div class="statistics-toolbar">
      <div><h2>馆藏运营概览</h2><p>快速掌握图书、读者和借阅情况</p></div>
      <el-button :icon="Refresh" @click="loadSummary">刷新数据</el-button>
    </div>
    <section class="metric-grid">
      <article class="metric-card teal"><div class="metric-icon"><Collection /></div><div><span>有效图书种类</span><strong>{{ summary.bookTypeCount }}</strong><small>个 ISBN 品种</small></div></article>
      <article class="metric-card blue"><div class="metric-icon"><Reading /></div><div><span>馆藏总册数</span><strong>{{ summary.totalBookCount }}</strong><small>册可管理馆藏</small></div></article>
      <article class="metric-card violet"><div class="metric-icon"><User /></div><div><span>有效读者</span><strong>{{ summary.readerCount }}</strong><small>位启用读者</small></div></article>
      <article class="metric-card amber"><div class="metric-icon"><Tickets /></div><div><span>当前借出</span><strong>{{ summary.borrowedCount }}</strong><small>笔未归还记录</small></div></article>
    </section>
    <section class="statistics-grid">
      <el-card shadow="never" class="ranking-card">
        <template #header><div class="table-heading"><div><strong>近 30 天热门图书</strong><span>按借阅次数从高到低排列</span></div><el-tag type="info" effect="plain">TOP 5</el-tag></div></template>
        <div v-if="summary.popularBooks.length" class="ranking-list">
          <div v-for="(book, index) in summary.popularBooks" :key="book.bookId" class="ranking-item">
            <div class="rank" :class="`rank-${index + 1}`">{{ index + 1 }}</div>
            <div class="ranking-book"><strong>{{ book.title }}</strong><span>{{ book.isbn }}</span></div>
            <div class="ranking-count"><strong>{{ book.borrowCount }}</strong><span>次借阅</span></div>
          </div>
        </div>
        <el-empty v-else description="暂无热门图书数据" :image-size="90" />
      </el-card>
      <el-card shadow="never" class="overdue-card">
        <template #header><div class="table-heading"><div><strong>逾期提醒</strong><span>需要优先跟进的借阅记录</span></div></div></template>
        <div class="overdue-value">{{ summary.overdueCount }}</div>
        <p>笔借阅已经超过应还时间</p>
        <el-progress :percentage="summary.borrowedCount ? Math.min(100, Math.round(summary.overdueCount / summary.borrowedCount * 100)) : 0" status="exception" :stroke-width="10" />
        <div class="overdue-tip">逾期率按“逾期数 ÷ 当前借出数”计算</div>
      </el-card>
    </section>
  </div>
</template>
