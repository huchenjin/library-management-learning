<script setup lang="ts">
import { nextTick, onMounted, reactive, ref } from 'vue'
import { ElMessage, ElMessageBox, type FormInstance, type FormRules } from 'element-plus'
import { Plus, Search } from '@element-plus/icons-vue'
import { isNotImplementedError } from '@/api/http'
import { createReader, deleteReader, getReaders, updateReader, type Reader, type ReaderPayload } from '@/api/readers'

const loading = ref(false)
const submitting = ref(false)
const unavailable = ref(false)
const readers = ref<Reader[]>([])
const total = ref(0)
const dialogVisible = ref(false)
const editingId = ref<number>()
const formRef = ref<FormInstance>()

const query = reactive({ page: 1, size: 10, keyword: '', status: undefined as number | undefined })
const form = reactive<ReaderPayload>({ readerNo: '', name: '', phone: '', email: '', maxBorrowCount: 5, status: 1 })
const rules: FormRules<ReaderPayload> = {
  readerNo: [{ required: true, message: '请输入读者编号', trigger: 'blur' }],
  name: [{ required: true, message: '请输入读者姓名', trigger: 'blur' }],
  email: [{ type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }],
  maxBorrowCount: [{ required: true, message: '请输入借阅上限', trigger: 'change' }],
  status: [{ required: true, message: '请选择状态', trigger: 'change' }],
}

async function loadReaders() {
  loading.value = true
  try {
    const result = await getReaders(query)
    readers.value = result.records
    total.value = result.total
    unavailable.value = false
  } catch (error) {
    unavailable.value = isNotImplementedError(error)
  } finally {
    loading.value = false
  }
}

function search() { query.page = 1; void loadReaders() }
function resetQuery() { query.keyword = ''; query.status = undefined; search() }
function resetForm() {
  Object.assign(form, { readerNo: '', name: '', phone: '', email: '', maxBorrowCount: 5, status: 1 })
}
function openCreate() {
  editingId.value = undefined
  resetForm()
  dialogVisible.value = true
  void nextTick(() => formRef.value?.clearValidate())
}
function openEdit(reader: Reader) {
  editingId.value = reader.id
  Object.assign(form, {
    readerNo: reader.readerNo,
    name: reader.name,
    phone: reader.phone || '',
    email: reader.email || '',
    maxBorrowCount: reader.maxBorrowCount,
    status: reader.status,
  })
  dialogVisible.value = true
  void nextTick(() => formRef.value?.clearValidate())
}
async function submit() {
  if (!(await formRef.value?.validate())) return
  submitting.value = true
  try {
    const payload = { ...form, readerNo: form.readerNo.trim(), name: form.name.trim() }
    if (editingId.value) {
      await updateReader(editingId.value, payload)
      ElMessage.success('读者修改成功')
    } else {
      await createReader(payload)
      ElMessage.success('读者新增成功')
    }
    dialogVisible.value = false
    await loadReaders()
  } finally {
    submitting.value = false
  }
}
async function remove(reader: Reader) {
  await ElMessageBox.confirm(`确定删除读者“${reader.name}”吗？`, '删除确认', { type: 'warning', confirmButtonText: '删除', cancelButtonText: '取消' })
  await deleteReader(reader.id)
  ElMessage.success('读者删除成功')
  await loadReaders()
}

onMounted(loadReaders)
</script>

<template>
  <div class="management-page">
    <el-alert v-if="unavailable" class="learning-alert" title="读者管理后端尚未实现" type="warning" :closable="false" show-icon>
      <template #default>页面交互已经就绪，完成 LEARNING-3 后即可直接联调。</template>
    </el-alert>
    <el-card shadow="never" class="filter-card">
      <el-form :model="query" inline>
        <el-form-item label="关键词"><el-input v-model="query.keyword" placeholder="编号 / 姓名 / 手机号" clearable :prefix-icon="Search" @keyup.enter="search" /></el-form-item>
        <el-form-item label="状态"><el-select v-model="query.status" placeholder="全部状态" clearable><el-option label="启用" :value="1" /><el-option label="停用" :value="0" /></el-select></el-form-item>
        <el-form-item><el-button type="primary" @click="search">查询</el-button><el-button @click="resetQuery">重置</el-button></el-form-item>
      </el-form>
    </el-card>
    <el-card shadow="never" class="table-card">
      <template #header><div class="table-heading"><div><strong>读者档案</strong><span>维护读者资料、借阅上限和账号状态</span></div><el-button type="primary" :icon="Plus" @click="openCreate">新增读者</el-button></div></template>
      <el-table v-loading="loading" :data="readers" stripe empty-text="暂无读者数据">
        <el-table-column prop="readerNo" label="读者编号" width="145" />
        <el-table-column prop="name" label="姓名" width="120" />
        <el-table-column prop="phone" label="手机号" width="145"><template #default="scope">{{ scope.row.phone || '-' }}</template></el-table-column>
        <el-table-column prop="email" label="邮箱" min-width="190"><template #default="scope">{{ scope.row.email || '-' }}</template></el-table-column>
        <el-table-column prop="maxBorrowCount" label="借阅上限" width="110" />
        <el-table-column label="状态" width="100"><template #default="scope"><el-tag :type="scope.row.status === 1 ? 'success' : 'info'" effect="plain">{{ scope.row.status === 1 ? '启用' : '停用' }}</el-tag></template></el-table-column>
        <el-table-column label="操作" width="150" fixed="right"><template #default="scope"><el-button link type="primary" @click="openEdit(scope.row)">编辑</el-button><el-button link type="danger" @click="remove(scope.row)">删除</el-button></template></el-table-column>
      </el-table>
      <el-pagination v-model:current-page="query.page" v-model:page-size="query.size" :total="total" layout="total, sizes, prev, pager, next" @current-change="loadReaders" @size-change="search" />
    </el-card>
    <el-dialog v-model="dialogVisible" :title="editingId ? '编辑读者' : '新增读者'" width="560px" destroy-on-close>
      <el-form ref="formRef" :model="form" :rules="rules" label-position="top">
        <div class="form-grid">
          <el-form-item label="读者编号" prop="readerNo"><el-input v-model="form.readerNo" placeholder="例如：R20260003" /></el-form-item>
          <el-form-item label="姓名" prop="name"><el-input v-model="form.name" /></el-form-item>
          <el-form-item label="手机号" prop="phone"><el-input v-model="form.phone" /></el-form-item>
          <el-form-item label="邮箱" prop="email"><el-input v-model="form.email" /></el-form-item>
          <el-form-item label="借阅上限" prop="maxBorrowCount"><el-input-number v-model="form.maxBorrowCount" :min="1" :max="100" controls-position="right" /></el-form-item>
          <el-form-item label="状态" prop="status"><el-radio-group v-model="form.status"><el-radio-button :value="1">启用</el-radio-button><el-radio-button :value="0">停用</el-radio-button></el-radio-group></el-form-item>
        </div>
      </el-form>
      <template #footer><el-button @click="dialogVisible = false">取消</el-button><el-button type="primary" :loading="submitting" @click="submit">保存</el-button></template>
    </el-dialog>
  </div>
</template>
