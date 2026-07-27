<script setup lang="ts">
import { nextTick, onMounted, reactive, ref } from 'vue'
import { ElMessage, ElMessageBox, type FormInstance, type FormRules } from 'element-plus'
import { Plus, Search } from '@element-plus/icons-vue'
import {
  createCategory,
  deleteCategory,
  getCategories,
  updateCategory,
  type Category,
  type CategoryPayload,
} from '@/api/categories'
import { isNotImplementedError } from '@/api/http'

const loading = ref(false)
const submitting = ref(false)
const unavailable = ref(false)
const categories = ref<Category[]>([])
const total = ref(0)
const dialogVisible = ref(false)
const editingId = ref<number>()
const formRef = ref<FormInstance>()

const query = reactive({ page: 1, size: 10, name: '', status: undefined as number | undefined })
const form = reactive<CategoryPayload>({ name: '', sortNo: 0, status: 1 })
const rules: FormRules<CategoryPayload> = {
  name: [
    { required: true, message: '请输入分类名称', trigger: 'blur' },
    { max: 80, message: '分类名称不能超过 80 个字符', trigger: 'blur' },
  ],
  sortNo: [{ required: true, message: '请输入排序号', trigger: 'change' }],
  status: [{ required: true, message: '请选择状态', trigger: 'change' }],
}

async function loadCategories() {
  loading.value = true
  try {
    const result = await getCategories(query)
    categories.value = result.records
    total.value = result.total
    unavailable.value = false
  } catch (error) {
    unavailable.value = isNotImplementedError(error)
  } finally {
    loading.value = false
  }
}

function search() {
  query.page = 1
  void loadCategories()
}

function resetQuery() {
  query.name = ''
  query.status = undefined
  search()
}

function resetForm() {
  form.name = ''
  form.sortNo = 0
  form.status = 1
}

function openCreate() {
  editingId.value = undefined
  resetForm()
  dialogVisible.value = true
  void nextTick(() => formRef.value?.clearValidate())
}

function openEdit(category: Category) {
  editingId.value = category.id
  form.name = category.name
  form.sortNo = category.sortNo
  form.status = category.status
  dialogVisible.value = true
  void nextTick(() => formRef.value?.clearValidate())
}

async function submit() {
  if (!(await formRef.value?.validate())) return
  submitting.value = true
  try {
    const payload = { ...form, name: form.name.trim() }
    if (editingId.value) {
      await updateCategory(editingId.value, payload)
      ElMessage.success('分类修改成功')
    } else {
      await createCategory(payload)
      ElMessage.success('分类新增成功')
    }
    dialogVisible.value = false
    await loadCategories()
  } finally {
    submitting.value = false
  }
}

async function remove(category: Category) {
  await ElMessageBox.confirm(`确定删除分类“${category.name}”吗？`, '删除确认', {
    type: 'warning',
    confirmButtonText: '删除',
    cancelButtonText: '取消',
  })
  await deleteCategory(category.id)
  ElMessage.success('分类删除成功')
  await loadCategories()
}

onMounted(loadCategories)
</script>

<template>
  <div class="management-page">
    <el-alert v-if="unavailable" class="learning-alert" title="分类分页接口尚未实现" type="warning" :closable="false" show-icon>
      <template #default>完成 LEARNING-1 的 GET 接口后，本页面会自动展示真实数据。</template>
    </el-alert>

    <el-card shadow="never" class="filter-card">
      <el-form :model="query" inline>
        <el-form-item label="分类名称">
          <el-input v-model="query.name" placeholder="输入名称筛选" clearable :prefix-icon="Search" @keyup.enter="search" />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="query.status" placeholder="全部状态" clearable>
            <el-option label="启用" :value="1" />
            <el-option label="停用" :value="0" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="search">查询</el-button>
          <el-button @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-card shadow="never" class="table-card">
      <template #header>
        <div class="table-heading">
          <div><strong>图书分类</strong><span>维护分类名称、展示顺序和启停状态</span></div>
          <el-button type="primary" :icon="Plus" @click="openCreate">新增分类</el-button>
        </div>
      </template>
      <el-table v-loading="loading" :data="categories" stripe empty-text="暂无分类数据">
        <el-table-column prop="id" label="ID" width="90" />
        <el-table-column prop="name" label="分类名称" min-width="220" />
        <el-table-column prop="sortNo" label="排序号" width="120" />
        <el-table-column label="状态" width="120">
          <template #default="scope">
            <el-tag :type="scope.row.status === 1 ? 'success' : 'info'" effect="plain">
              {{ scope.row.status === 1 ? '启用' : '停用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="scope">
            <el-button link type="primary" @click="openEdit(scope.row)">编辑</el-button>
            <el-button link type="danger" @click="remove(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination v-model:current-page="query.page" v-model:page-size="query.size" :total="total" layout="total, sizes, prev, pager, next" @current-change="loadCategories" @size-change="search" />
    </el-card>

    <el-dialog v-model="dialogVisible" :title="editingId ? '编辑分类' : '新增分类'" width="480px" destroy-on-close>
      <el-form ref="formRef" :model="form" :rules="rules" label-position="top">
        <el-form-item label="分类名称" prop="name"><el-input v-model="form.name" maxlength="80" show-word-limit placeholder="例如：哲学" /></el-form-item>
        <el-form-item label="排序号" prop="sortNo"><el-input-number v-model="form.sortNo" :min="0" :max="9999" controls-position="right" /></el-form-item>
        <el-form-item label="状态" prop="status"><el-radio-group v-model="form.status"><el-radio-button :value="1">启用</el-radio-button><el-radio-button :value="0">停用</el-radio-button></el-radio-group></el-form-item>
      </el-form>
      <template #footer><el-button @click="dialogVisible = false">取消</el-button><el-button type="primary" :loading="submitting" @click="submit">保存</el-button></template>
    </el-dialog>
  </div>
</template>
