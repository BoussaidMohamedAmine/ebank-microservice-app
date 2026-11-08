import { Component, inject } from '@angular/core';
import { AsyncPipe, CurrencyPipe, DatePipe } from '@angular/common';
import { RouterLink } from '@angular/router';
import { BehaviorSubject, catchError, map, Observable, of, startWith, switchMap } from 'rxjs';
import { AccountListState, RequestStatus } from '../model/account.model';
import { AccountService } from '../services/account.service';

@Component({
  imports: [AsyncPipe, DatePipe, CurrencyPipe, RouterLink],
  selector: 'app-accounts',
  styleUrl: './accounts.css',
  templateUrl: './accounts.html',
})
export class Accounts {
  private accountService = inject(AccountService);
  private refresh$ = new BehaviorSubject<void>(undefined);
  protected readonly RequestStatus = RequestStatus;

  accounts$: Observable<AccountListState> = this.refresh$.pipe(
    switchMap(() =>
      this.accountService.getAccounts().pipe(
        map((accounts) => ({ accounts, status: RequestStatus.SUCCESS })),
        startWith({ status: RequestStatus.LOADING }),
        catchError((error) =>
          of({ status: RequestStatus.ERROR, errorMessage: error.message || error.statusText }),
        ),
      ),
    ),
  );

  reload() {
    this.refresh$.next();
  }
}
