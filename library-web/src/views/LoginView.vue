<script setup lang="ts">
import { reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import type { FormInstance, FormRules } from 'element-plus'
import { useAuthStore } from '@/stores/auth'

const router = useRouter()
const route = useRoute()
const auth = useAuthStore()
const formRef = ref<FormInstance>()
const loading = ref(false)
const form = reactive({ username: 'admin', password: 'admin123' })
const rules: FormRules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
}

async function submit() {
  if (!(await formRef.value?.validate())) return
  loading.value = true
  try {
    await auth.login(form.username, form.password)
    await router.replace(String(route.query.redirect || '/dashboard'))
  } finally {
    loading.value = false
  }
}
</script>

<template>
  <main class="login-page">
    <section class="login-intro">
      <div class="eyebrow">JAVA 21 · SPRING BOOT 4 · VUE 3</div>
      <h1>从一个真实的小项目，<br />走完整套开发流程。</h1>
      <p>脚手架替你处理重复配置，把精力放在业务建模、事务、SQL、测试与调试上。</p>
      <div class="stack-list">
        <span>JWT 鉴权</span><span>MyBatis-Plus</span><span>MySQL / Flyway</span><span>Element Plus</span>
      </div>
    </section>
    <section class="login-card">
      <div>
        <div class="brand-mark large">L</div>
        <h2>欢迎回来</h2>
        <p>登录图书管理后台，开始今天的练习。</p>
      </div>
      <el-form ref="formRef" :model="form" :rules="rules" label-position="top" @keyup.enter="submit">
        <el-form-item label="用户名" prop="username"><el-input v-model="form.username" size="large" /></el-form-item>
        <el-form-item label="密码" prop="password"><el-input v-model="form.password" type="password" show-password size="large" /></el-form-item>
        <el-button type="primary" size="large" :loading="loading" class="login-button" @click="submit">登录后台</el-button>
      </el-form>
      <el-alert title="开发账号：admin / admin123" type="info" :closable="false" show-icon />
    </section>
  </main>
</template>
