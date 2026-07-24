import http from './http'
import type { ApiResponse, PageResponse } from '@/types/api'

export interface Book {
  id: number
  isbn: string
  title: string
  author: string
  publisher?: string
  publishDate?: string
  categoryId: number
  categoryName: string
  totalStock: number
  availableStock: number
  location?: string
  status: number
}

export interface BookQuery {
  page: number
  size: number
  keyword?: string
  categoryId?: number
  status?: number
}

export interface CategoryOption {
  id: number
  name: string
}

export async function getBooks(params: BookQuery): Promise<PageResponse<Book>> {
  const response = await http.get<ApiResponse<PageResponse<Book>>>('/api/books', { params })
  return response.data.data
}

export async function getBook(id: number): Promise<Book> {
  const response = await http.get<ApiResponse<Book>>(`/api/books/${id}`)
  return response.data.data
}

export async function getCategoryOptions(): Promise<CategoryOption[]> {
  const response = await http.get<ApiResponse<CategoryOption[]>>('/api/categories/options')
  return response.data.data
}
