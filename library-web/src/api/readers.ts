import http from './http'
import type { ApiResponse, PageResponse } from '@/types/api'

export interface Reader {
  id: number
  readerNo: string
  name: string
  phone?: string
  email?: string
  maxBorrowCount: number
  status: number
}

export interface ReaderQuery {
  page: number
  size: number
  keyword?: string
  status?: number
}

export interface ReaderPayload {
  readerNo: string
  name: string
  phone?: string
  email?: string
  maxBorrowCount: number
  status: number
}

export async function getReaders(params: ReaderQuery): Promise<PageResponse<Reader>> {
  const response = await http.get<ApiResponse<PageResponse<Reader>>>('/api/readers', { params })
  return response.data.data
}

export async function createReader(payload: ReaderPayload): Promise<void> {
  await http.post<ApiResponse<null>>('/api/readers', payload)
}

export async function updateReader(id: number, payload: ReaderPayload): Promise<void> {
  await http.put<ApiResponse<null>>(`/api/readers/${id}`, payload)
}

export async function deleteReader(id: number): Promise<void> {
  await http.delete<ApiResponse<null>>(`/api/readers/${id}`)
}
