import http from './http'
import type { ApiResponse, PageResponse } from '@/types/api'

export type BorrowStatus = 'BORROWED' | 'RETURNED' | 'OVERDUE'

export interface BorrowRecord {
  id: number
  readerId: number
  readerNo: string
  readerName: string
  bookId: number
  isbn: string
  bookTitle: string
  borrowedAt: string
  dueAt: string
  returnedAt?: string
  status: BorrowStatus
}

export interface BorrowQuery {
  page: number
  size: number
  keyword?: string
  status?: BorrowStatus
}

export interface BorrowPayload {
  readerId: number
  bookId: number
  dueAt: string
}

export async function getBorrows(params: BorrowQuery): Promise<PageResponse<BorrowRecord>> {
  const response = await http.get<ApiResponse<PageResponse<BorrowRecord>>>('/api/borrows', { params })
  return response.data.data
}

export async function createBorrow(payload: BorrowPayload): Promise<void> {
  await http.post<ApiResponse<null>>('/api/borrows', payload)
}

export async function returnBorrow(id: number): Promise<void> {
  await http.post<ApiResponse<null>>(`/api/borrows/${id}/return`)
}
