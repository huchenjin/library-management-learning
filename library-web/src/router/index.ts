import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '@/stores/auth'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    { path: '/login', name: 'login', component: () => import('@/views/LoginView.vue'), meta: { public: true } },
    {
      path: '/',
      component: () => import('@/layouts/AdminLayout.vue'),
      redirect: '/dashboard',
      children: [
        { path: 'dashboard', name: 'dashboard', component: () => import('@/views/DashboardView.vue'), meta: { title: '学习看板' } },
        { path: 'books', name: 'books', component: () => import('@/views/BooksView.vue'), meta: { title: '图书管理' } },
        { path: 'categories', name: 'categories', component: () => import('@/views/CategoriesView.vue'), meta: { title: '分类管理', task: 'LEARNING-1' } },
        { path: 'readers', name: 'readers', component: () => import('@/views/ReadersView.vue'), meta: { title: '读者管理', task: 'LEARNING-3' } },
        { path: 'borrows', name: 'borrows', component: () => import('@/views/BorrowsView.vue'), meta: { title: '借阅管理', task: 'LEARNING-4 / LEARNING-5' } },
        { path: 'statistics', name: 'statistics', component: () => import('@/views/StatisticsView.vue'), meta: { title: '统计看板', task: 'LEARNING-6' } },
      ],
    },
    { path: '/:pathMatch(.*)*', redirect: '/' },
  ],
})

router.beforeEach(async (to) => {
  const auth = useAuthStore()
  if (to.meta.public) return auth.isAuthenticated ? { name: 'dashboard' } : true
  if (!auth.isAuthenticated) return { name: 'login', query: { redirect: to.fullPath } }
  if (!auth.user) {
    try {
      await auth.fetchUser()
    } catch {
      auth.logout()
      return { name: 'login' }
    }
  }
  return true
})

router.afterEach((to) => {
  document.title = `${String(to.meta.title || '图书管理后台')} - 图书管理后台`
})

export default router
