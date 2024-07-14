export interface CustomerRegisterationRequest {
  name?: string;
  email?: string;
  password?: string;
  age?: number;
  gender?: 'M' | 'F';
}
