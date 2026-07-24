import { computed, ref } from 'vue'
import { defineStore } from 'pinia'
import { getCurrentUser, login as loginApi } from '@/api/auth'
import { TOKEN_KEY } from '@/api/http'
import type { CurrentUser } from '@/types/api'

export const useAuthStore = defineStore('auth', () => {
  const token = ref(localStorage.getItem(TOKEN_KEY) || '')
  const user = ref<CurrentUser | null>(null)
  const isAuthenticated = computed(() => Boolean(token.value))

  async function login(username: string, password: string) {
    const result = await loginApi({ username, password })
    token.value = result.accessToken
    localStorage.setItem(TOKEN_KEY, result.accessToken)
    await fetchUser()
  }

  async function fetchUser() {
    if (!token.value) return
    user.value = await getCurrentUser()
  }

  function logout() {
    token.value = ''
    user.value = null
    localStorage.removeItem(TOKEN_KEY)
  }

  return { token, user, isAuthenticated, login, fetchUser, logout }
})
