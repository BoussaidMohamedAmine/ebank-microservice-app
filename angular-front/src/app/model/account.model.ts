export interface Customer {
  id?: number;
  name?: string;
  email?: string;
}

export interface Account {
  id?: string;
  balance?: number;
  createdAt?: string;
  type?: string;
  customerId?: number;
  customer?: Customer;
}

export enum RequestStatus {
  LOADING = 0,
  SUCCESS = 1,
  ERROR = 2,
}

export interface DataState<T> {
  data?: T;
  status: RequestStatus;
  errorMessage?: string;
}

export interface AccountListState {
  accounts?: Account[];
  status?: RequestStatus;
  errorMessage?: string;
}

export interface ChatMessage {
  from: 'user' | 'bot';
  content: string;
}
