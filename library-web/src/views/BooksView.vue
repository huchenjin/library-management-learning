<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue'
import { Search } from '@element-plus/icons-vue'
import { getBook, getBooks, getCategoryOptions, type Book, type CategoryOption } from '@/api/books'

const loading = ref(false)
const books = ref<Book[]>([])
const categories = ref<CategoryOption[]>([])
const total = ref(0)
const drawerVisible = ref(false)
const detail = ref<Book>()
const query = reactive<{ page: number; size: number; keyword?: string; categoryId?: number; status?: number }>({ page: 1, size: 10 })

async function loadBooks() {
  loading.value = true
  try {
    const result = await getBooks(query)
    books.value = result.records
    total.value = result.total
  } finally {
    loading.value = false
  }
}

async function showDetail(id: number) {
  detail.value = await getBook(id)
  drawerVisible.value = true
}

function search() { query.page = 1; void loadBooks() }
function reset() {
  query.keyword = undefined
  query.categoryId = undefined
  query.status = undefined
  search()
}

onMounted(async () => {
  categories.value = await getCategoryOptions()
  await loadBooks()
})
</script>

<template>
  <div class="books-page">
    <el-card shadow="never" class="filter-card">
      <el-form :model="query" inline>
        <el-form-item label="关键词"><el-input v-model="query.keyword" placeholder="书名 / ISBN / 作者" clearable :prefix-icon="Search" @keyup.enter="search" /></el-form-item>
        <el-form-item label="分类"><el-select v-model="query.categoryId" placeholder="全部分类" clearable><el-option v-for="item in categories" :key="item.id" :label="item.name" :value="item.id" /></el-select></el-form-item>
        <el-form-item label="状态"><el-select v-model="query.status" placeholder="全部状态" clearable><el-option label="上架" :value="1" /><el-option label="停用" :value="0" /></el-select></el-form-item>
        <el-form-item><el-button type="primary" @click="search">查询</el-button><el-button @click="reset">重置</el-button></el-form-item>
      </el-form>
    </el-card>

    <el-card shadow="never" class="table-card">
      <template #header><div class="table-heading"><div><strong>馆藏图书</strong><span>这是完整的前后端分页查询示例</span></div><el-tag type="info">新增、编辑、删除留作 LEARNING-2</el-tag></div></template>
      <el-table v-loading="loading" :data="books" stripe>
        <el-table-column prop="isbn" label="ISBN" width="145" />
        <el-table-column prop="title" label="书名" min-width="190" show-overflow-tooltip />
        <el-table-column prop="author" label="作者" width="120" />
        <el-table-column prop="categoryName" label="分类" width="100" />
        <el-table-column label="库存" width="110"><template #default="scope"><span :class="{ warning: scope.row.availableStock === 0 }">{{ scope.row.availableStock }} / {{ scope.row.totalStock }}</span></template></el-table-column>
        <el-table-column prop="location" label="馆藏位置" width="110" />
        <el-table-column label="状态" width="90"><template #default="scope"><el-tag :type="scope.row.status === 1 ? 'success' : 'info'" effect="plain">{{ scope.row.status === 1 ? '上架' : '停用' }}</el-tag></template></el-table-column>
        <el-table-column label="操作" width="90" fixed="right"><template #default="scope"><el-button link type="primary" @click="showDetail(scope.row.id)">详情</el-button></template></el-table-column>
      </el-table>
      <el-pagination v-model:current-page="query.page" v-model:page-size="query.size" :total="total" layout="total, sizes, prev, pager, next" @current-change="loadBooks" @size-change="search" />
    </el-card>

    <el-drawer v-model="drawerVisible" title="图书详情" size="420px">
      <el-descriptions v-if="detail" :column="1" border>
        <el-descriptions-item label="书名">{{ detail.title }}</el-descriptions-item>
        <el-descriptions-item label="ISBN">{{ detail.isbn }}</el-descriptions-item>
        <el-descriptions-item label="作者">{{ detail.author }}</el-descriptions-item>
        <el-descriptions-item label="出版社">{{ detail.publisher || '-' }}</el-descriptions-item>
        <el-descriptions-item label="出版日期">{{ detail.publishDate || '-' }}</el-descriptions-item>
        <el-descriptions-item label="分类">{{ detail.categoryName }}</el-descriptions-item>
        <el-descriptions-item label="库存">{{ detail.availableStock }} / {{ detail.totalStock }}</el-descriptions-item>
        <el-descriptions-item label="馆藏位置">{{ detail.location || '-' }}</el-descriptions-item>
      </el-descriptions>
    </el-drawer>
  </div>
</template>
