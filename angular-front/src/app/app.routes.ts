import { Routes } from '@angular/router';
import { Accounts } from './accounts/accounts';
import { AccountDetails } from './account-details/account-details';
import { Customers } from './customers/customers';
import { Chat } from './chat/chat';

export const routes: Routes = [
  { path: '', redirectTo: 'accounts', pathMatch: 'full' },
  { path: 'accounts', component: Accounts },
  { path: 'accounts/:id', component: AccountDetails },
  { path: 'customers', component: Customers },
  { path: 'chat', component: Chat },
  { path: '**', redirectTo: 'accounts' },
];
