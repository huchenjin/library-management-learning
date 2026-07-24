export interface ApiResponse<T> {
  code: number
  message: string
  data: T
}

export interface PageResponse<T> {
  records: T[]
  total: number
  page: number
  size: number
}

export interface CurrentUser {
  id: number
  username: string
  displayName: string
  role: string
}
