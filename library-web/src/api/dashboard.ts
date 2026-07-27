import http from './http'
import type { ApiResponse } from '@/types/api'

export interface PopularBook {
  bookId: number
  isbn: string
  title: string
  borrowCount: number
}

export interface DashboardSummary {
  bookTypeCount: number
  totalBookCount: number
  readerCount: number
  borrowedCount: number
  overdueCount: number
  popularBooks: PopularBook[]
}

export async function getDashboardSummary(): Promise<DashboardSummary> {
  const response = await http.get<ApiResponse<DashboardSummary>>('/api/dashboard/summary')
  return response.data.data
}
