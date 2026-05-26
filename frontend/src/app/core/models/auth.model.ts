export interface LoginRequest {
  email: string;
  password: string;
}

export interface LoginResponse {
  token: string;
  type: string;
  id: number;
  email: string;
  fullName: string;
  role: string;
  message: string;
}

export enum UserRole {
  ADMIN = 'ADMIN',
  PM = 'PM',
  BA = 'BA',
  SA = 'SA',
  DEVELOPER = 'DEVELOPER',
  QA = 'QA',
  TEAM_LEAD = 'TEAM_LEAD',
  CUSTOMER = 'CUSTOMER',
  FINANCE = 'FINANCE',
  MA_SUPPORT = 'MA_SUPPORT',
  VIEWER = 'VIEWER'
}