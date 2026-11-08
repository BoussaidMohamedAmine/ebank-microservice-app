import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EBANK_BOT_URL } from './api.config';

@Injectable({ providedIn: 'root' })
export class ChatService {
  private http = inject(HttpClient);

  // The bot answers in text/plain, so we ask for a text response instead of JSON.
  ask(query: string): Observable<string> {
    const params = new HttpParams().set('query', query);
    return this.http.get(`${EBANK_BOT_URL}/chat`, { params, responseType: 'text' });
  }
}
