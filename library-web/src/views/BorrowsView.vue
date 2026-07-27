<script setup lang="ts">
import { nextTick, onMounted, reactive, ref } from 'vue'
import { ElMessage, ElMessageBox, type FormInstance, type FormRules } from 'element-plus'
import { Plus, Search } from '@element-plus/icons-vue'
import { createBorrow, getBorrows, returnBorrow, type BorrowPayload, type BorrowRecord, type BorrowStatus } from '@/api/borrows'
import { isNotImplementedError } from '@/api/http'

const loading = ref(false)
const submitting = ref(false)
const unavailable = ref(false)
const records = ref<BorrowRecord[]>([])
const total = ref(0)
const dialogVisible = ref(false)
const formRef = ref<FormInstance>()
const query = reactive({ page: 1, size: 10, keyword: '', status: undefined as BorrowStatus | undefined })
const form = reactive<BorrowPayload>({ readerId: 1, bookId: 1, dueAt: '' })
const rules: FormRules<BorrowPayload> = {
  readerId: [{ required: true, message: '请输入读者 ID', trigger: 'change' }],
  bookId: [{ required: true, message: '请输入图书 ID', trigger: 'change' }],
  dueAt: [{ required: true, message: '请选择应还时间', trigger: 'change' }],
}

function defaultDueAt() {
  const date = new Date()
  date.setDate(date.getDate() + 30)
  date.setHours(23, 59, 0, 0)
  const pad = (value: number) => String(value).padStart(2, '0')
  return `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(date.getDate())}T${pad(date.getHours())}:${pad(date.getMinutes())}:${pad(date.getSeconds())}`
}

function statusLabel(status: BorrowStatus) {
  return { BORROWED: '借阅中', RETURNED: '已归还', OVERDUE: '已逾期' }[status]
}
function statusType(status: BorrowStatus) {
  return { BORROWED: 'primary', RETURNED: 'success', OVERDUE: 'danger' }[status] as 'primary' | 'success' | 'danger'
}
function formatTime(value?: string) {
  return value ? new Date(value).toLocaleString('zh-CN', { hour12: false }) : '-'
}
async function loadBorrows() {
  loading.value = true
  try {
    const result = await getBorrows(query)
    records.value = result.records
    total.value = result.total
    unavailable.value = false
  } catch (error) {
    unavailable.value = isNotImplementedError(error)
  } finally {
    loading.value = false
  }
}
function search() { query.page = 1; void loadBorrows() }
function resetQuery() { query.keyword = ''; query.status = undefined; search() }
function openBorrow() {
  Object.assign(form, { readerId: 1, bookId: 1, dueAt: defaultDueAt() })
  dialogVisible.value = true
  void nextTick(() => formRef.value?.clearValidate())
}
async function submit() {
  if (!(await formRef.value?.validate())) return
  submitting.value = true
  try {
    await createBorrow({ ...form })
    ElMessage.success('借阅办理成功')
    dialogVisible.value = false
    await loadBorrows()
  } finally {
    submitting.value = false
  }
}
async function returnBook(record: BorrowRecord) {
  await ElMessageBox.confirm(`确认归还《${record.bookTitle}》吗？`, '归还确认', { type: 'info', confirmButtonText: '确认归还', cancelButtonText: '取消' })
  await returnBorrow(record.id)
  ElMessage.success('归还办理成功')
  await loadBorrows()
}

onMounted(loadBorrows)
</script>

<template>
  <div class="management-page">
    <el-alert v-if="unavailable" class="learning-alert" title="借阅与归还后端尚未实现" type="warning" :closable="false" show-icon>
      <template #default>完成 LEARNING-4 / LEARNING-5 的事务逻辑后，本页面即可直接联调。</template>
    </el-alert>
    <el-card shadow="never" class="filter-card">
      <el-form :model="query" inline>
        <el-form-item label="关键词"><el-input v-model="query.keyword" placeholder="读者 / 书名 / ISBN" clearable :prefix-icon="Search" @keyup.enter="search" /></el-form-item>
        <el-form-item label="借阅状态"><el-select v-model="query.status" placeholder="全部状态" clearable><el-option label="借阅中" value="BORROWED" /><el-option label="已归还" value="RETURNED" /><el-option label="已逾期" value="OVERDUE" /></el-select></el-form-item>
        <el-form-item><el-button type="primary" @click="search">查询</el-button><el-button @click="resetQuery">重置</el-button></el-form-item>
      </el-form>
    </el-card>
    <el-card shadow="never" class="table-card">
      <template #header><div class="table-heading"><div><strong>借阅记录</strong><span>办理借出、归还并追踪逾期状态</span></div><el-button type="primary" :icon="Plus" @click="openBorrow">办理借阅</el-button></div></template>
      <el-table v-loading="loading" :data="records" stripe empty-text="暂无借阅记录">
        <el-table-column prop="id" label="记录号" width="90" />
        <el-table-column label="读者" min-width="150"><template #default="scope"><div class="primary-cell">{{ scope.row.readerName }}</div><div class="secondary-cell">{{ scope.row.readerNo }}</div></template></el-table-column>
        <el-table-column label="图书" min-width="220"><template #default="scope"><div class="primary-cell">{{ scope.row.bookTitle }}</div><div class="secondary-cell">{{ scope.row.isbn }}</div></template></el-table-column>
        <el-table-column label="借出时间" width="170"><template #default="scope">{{ formatTime(scope.row.borrowedAt) }}</template></el-table-column>
        <el-table-column label="应还时间" width="170"><template #default="scope"><span :class="{ warning: scope.row.status === 'OVERDUE' }">{{ formatTime(scope.row.dueAt) }}</span></template></el-table-column>
        <el-table-column label="状态" width="100"><template #default="scope"><el-tag :type="statusType(scope.row.status)" effect="plain">{{ statusLabel(scope.row.status) }}</el-tag></template></el-table-column>
        <el-table-column label="操作" width="100" fixed="right"><template #default="scope"><el-button v-if="scope.row.status !== 'RETURNED'" link type="primary" @click="returnBook(scope.row)">办理归还</el-button><span v-else class="secondary-cell">已完成</span></template></el-table-column>
      </el-table>
      <el-pagination v-model:current-page="query.page" v-model:page-size="query.size" :total="total" layout="total, sizes, prev, pager, next" @current-change="loadBorrows" @size-change="search" />
    </el-card>
    <el-dialog v-model="dialogVisible" title="办理借阅" width="500px" destroy-on-close>
      <el-alert title="目前使用 ID 演示接口参数；完成读者查询接口后可替换为可搜索下拉框。" type="info" :closable="false" show-icon />
      <el-form ref="formRef" class="dialog-form" :model="form" :rules="rules" label-position="top">
        <div class="form-grid">
          <el-form-item label="读者 ID" prop="readerId"><el-input-number v-model="form.readerId" :min="1" controls-position="right" /></el-form-item>
          <el-form-item label="图书 ID" prop="bookId"><el-input-number v-model="form.bookId" :min="1" controls-position="right" /></el-form-item>
        </div>
        <el-form-item label="应还时间" prop="dueAt"><el-date-picker v-model="form.dueAt" type="datetime" value-format="YYYY-MM-DDTHH:mm:ss" placeholder="请选择应还时间" style="width: 100%" /></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible = false">取消</el-button><el-button type="primary" :loading="submitting" @click="submit">确认借出</el-button></template>
    </el-dialog>
  </div>
</template>
