import { Component, inject } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {AsyncPipe} from '@angular/common';
import {AccountListState, Account, RequestStatus} from './model/account.model';
import {catchError, map, Observable, of} from 'rxjs';

@Component({
  imports: [
    AsyncPipe
  ],
  selector: 'app-accounts',
  styleUrl: './accounts.css',
  templateUrl: './accounts.html',
})
export class Accounts {
  private http = inject(HttpClient);

  accouts$: Observable<AccountListState> = this.http.get<Account[]>
  ("http://localhost:9999/EBANK-SERVICE/accounts").pipe(
    map(value => {
      return {accounts:value , status:RequestStatus.SUCCESS};
    }),
    catchError((error, caught) => {
      return of({status: RequestStatus.ERROR , errorMessage: error.statusText});
    })
  )

}
