export interface CustomerRegisterationRequest {
  id?: number;
  name?: string;
  email?: string;
  password?: string;
  age?: number;
  gender?: 'M' | 'F';
}
