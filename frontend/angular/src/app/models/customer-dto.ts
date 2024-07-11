export interface CustomerDTO {
  id?: number;
  name?: string;
  email?: string;
  gender?: 'M' | 'F';
  age?: number;
  roles?: string[];
  username?: string;
}
