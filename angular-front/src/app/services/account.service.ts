import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Account } from '../model/account.model';
import { EBANK_SERVICE_URL } from './api.config';

@Injectable({ providedIn: 'root' })
export class AccountService {
  private http = inject(HttpClient);

  getAccounts(): Observable<Account[]> {
    return this.http.get<Account[]>(`${EBANK_SERVICE_URL}/accounts`);
  }

  getAccountById(id: string): Observable<Account> {
    return this.http.get<Account>(`${EBANK_SERVICE_URL}/accounts/${id}`);
  }

  getAccountsByCustomer(customerId: number): Observable<Account[]> {
    return this.http.get<Account[]>(`${EBANK_SERVICE_URL}/accounts/customer/${customerId}`);
  }

  saveAccount(account: Account): Observable<Account> {
    return this.http.post<Account>(`${EBANK_SERVICE_URL}/accounts`, account);
  }
}
