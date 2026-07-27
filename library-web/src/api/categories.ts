import http from './http'
import type { ApiResponse, PageResponse } from '@/types/api'

export interface Category {
  id: number
  name: string
  sortNo: number
  status: number
}

export interface CategoryQuery {
  page: number
  size: number
  name?: string
  status?: number
}

export interface CategoryPayload {
  name: string
  sortNo: number
  status: number
}

export async function getCategories(params: CategoryQuery): Promise<PageResponse<Category>> {
  const response = await http.get<ApiResponse<PageResponse<Category>>>('/api/categories', { params })
  return response.data.data
}

export async function createCategory(payload: CategoryPayload): Promise<void> {
  await http.post<ApiResponse<null>>('/api/categories', payload)
}

export async function updateCategory(id: number, payload: CategoryPayload): Promise<void> {
  await http.put<ApiResponse<null>>(`/api/categories/${id}`, payload)
}

export async function deleteCategory(id: number): Promise<void> {
  await http.delete<ApiResponse<null>>(`/api/categories/${id}`)
}
