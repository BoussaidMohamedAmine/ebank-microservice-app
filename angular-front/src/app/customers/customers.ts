import { Component, inject } from '@angular/core';
import { AsyncPipe, CurrencyPipe, DatePipe } from '@angular/common';
import { RouterLink } from '@angular/router';
import { catchError, map, Observable, of, startWith } from 'rxjs';
import { Account, Customer, DataState, RequestStatus } from '../model/account.model';
import { CustomerService } from '../services/customer.service';
import { AccountService } from '../services/account.service';

@Component({
  imports: [AsyncPipe, CurrencyPipe, DatePipe, RouterLink],
  selector: 'app-customers',
  templateUrl: './customers.html',
})
export class Customers {
  private customerService = inject(CustomerService);
  private accountService = inject(AccountService);
  protected readonly RequestStatus = RequestStatus;

  selectedCustomer?: Customer;
  accounts$?: Observable<DataState<Account[]>>;

  customers$: Observable<DataState<Customer[]>> = this.customerService.getCustomers().pipe(
    map((data) => ({ data, status: RequestStatus.SUCCESS })),
    startWith({ status: RequestStatus.LOADING }),
    catchError((error) =>
      of({ status: RequestStatus.ERROR, errorMessage: error.message || error.statusText }),
    ),
  );

  selectCustomer(customer: Customer) {
    this.selectedCustomer = customer;
    this.accounts$ = this.accountService.getAccountsByCustomer(customer.id!).pipe(
      map((data) => ({ data, status: RequestStatus.SUCCESS })),
      startWith({ status: RequestStatus.LOADING }),
      catchError((error) =>
        of({ status: RequestStatus.ERROR, errorMessage: error.message || error.statusText }),
      ),
    );
  }
}
