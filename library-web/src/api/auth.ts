import http from './http'
import type { ApiResponse, CurrentUser } from '@/types/api'

export interface LoginPayload {
  username: string
  password: string
}

export interface LoginResult {
  accessToken: string
  tokenType: string
  expiresIn: number
}

export async function login(payload: LoginPayload): Promise<LoginResult> {
  const response = await http.post<ApiResponse<LoginResult>>('/api/auth/login', payload)
  return response.data.data
}

export async function getCurrentUser(): Promise<CurrentUser> {
  const response = await http.get<ApiResponse<CurrentUser>>('/api/auth/me')
  return response.data.data
}
