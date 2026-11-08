import { Component, inject } from '@angular/core';
import { AsyncPipe, CurrencyPipe, DatePipe } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { catchError, map, Observable, of, startWith, switchMap } from 'rxjs';
import { Account, DataState, RequestStatus } from '../model/account.model';
import { AccountService } from '../services/account.service';

@Component({
  imports: [AsyncPipe, DatePipe, CurrencyPipe, RouterLink],
  selector: 'app-account-details',
  templateUrl: './account-details.html',
})
export class AccountDetails {
  private route = inject(ActivatedRoute);
  private accountService = inject(AccountService);
  protected readonly RequestStatus = RequestStatus;

  account$: Observable<DataState<Account>> = this.route.paramMap.pipe(
    switchMap((params) =>
      this.accountService.getAccountById(params.get('id')!).pipe(
        map((data) => ({ data, status: RequestStatus.SUCCESS })),
        startWith({ status: RequestStatus.LOADING }),
        catchError((error) =>
          of({ status: RequestStatus.ERROR, errorMessage: error.message || error.statusText }),
        ),
      ),
    ),
  );
}
