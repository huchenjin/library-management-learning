<script setup lang="ts">
import { computed } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const title = computed(() => String(route.meta.title))
const task = computed(() => String(route.meta.task))
</script>

<template>
  <el-card class="placeholder-card" shadow="never">
    <div class="placeholder-icon">⌁</div>
    <el-tag type="warning" effect="plain">{{ task }}</el-tag>
    <h2>{{ title }}等待你来实现</h2>
    <p>对应后端接口现在会返回 HTTP 501。请先阅读 <code>docs/requirements.md</code> 中的业务规则和验收条件。</p>
    <ol>
      <li>找到 Controller 中的 <code>TODO({{ task }})</code></li>
      <li>建立 Service 方法并补充 Mapper / SQL</li>
      <li>启用对应的 <code>@Disabled</code> 测试</li>
      <li>最后补齐本页面的表格与表单</li>
    </ol>
  </el-card>
</template>
