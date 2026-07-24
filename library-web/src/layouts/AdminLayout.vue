<script setup lang="ts">
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { Collection, DataAnalysis, House, Reading, Tickets, User } from '@element-plus/icons-vue'
import { useAuthStore } from '@/stores/auth'

const route = useRoute()
const router = useRouter()
const auth = useAuthStore()
const activeMenu = computed(() => route.path)

async function logout() {
  auth.logout()
  await router.replace({ name: 'login' })
}
</script>

<template>
  <el-container class="admin-shell">
    <el-aside width="220px" class="sidebar">
      <div class="brand">
        <div class="brand-mark">L</div>
        <div>
          <strong>Library Lab</strong>
          <small>Java 学习项目</small>
        </div>
      </div>
      <el-menu :default-active="activeMenu" router class="sidebar-menu">
        <el-menu-item index="/dashboard"><el-icon><House /></el-icon><span>学习看板</span></el-menu-item>
        <el-menu-item index="/books"><el-icon><Reading /></el-icon><span>图书管理</span></el-menu-item>
        <el-menu-item index="/categories"><el-icon><Collection /></el-icon><span>分类管理</span></el-menu-item>
        <el-menu-item index="/readers"><el-icon><User /></el-icon><span>读者管理</span></el-menu-item>
        <el-menu-item index="/borrows"><el-icon><Tickets /></el-icon><span>借阅管理</span></el-menu-item>
        <el-menu-item index="/statistics"><el-icon><DataAnalysis /></el-icon><span>统计看板</span></el-menu-item>
      </el-menu>
    </el-aside>
    <el-container>
      <el-header class="topbar">
        <div>
          <h1>{{ route.meta.title }}</h1>
          <span>循序渐进完成每个 TODO，熟悉完整 Java 开发流程</span>
        </div>
        <el-dropdown trigger="click">
          <button class="user-button">
            <span class="avatar">{{ auth.user?.displayName?.slice(0, 1) || '管' }}</span>
            <span>{{ auth.user?.displayName || auth.user?.username }}</span>
          </button>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </el-header>
      <el-main class="content"><RouterView /></el-main>
    </el-container>
  </el-container>
</template>
