import { Component, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ChatMessage } from '../model/account.model';
import { ChatService } from '../services/chat.service';

@Component({
  imports: [FormsModule],
  selector: 'app-chat',
  templateUrl: './chat.html',
  styleUrl: './chat.css',
})
export class Chat {
  private chatService = inject(ChatService);

  query = '';
  messages = signal<ChatMessage[]>([]);
  loading = signal(false);

  send() {
    const question = this.query.trim();
    if (!question || this.loading()) return;
    this.messages.update((m) => [...m, { from: 'user', content: question }]);
    this.query = '';
    this.loading.set(true);
    this.chatService.ask(question).subscribe({
      next: (answer) => {
        this.messages.update((m) => [...m, { from: 'bot', content: answer }]);
        this.loading.set(false);
      },
      error: (err) => {
        this.messages.update((m) => [
          ...m,
          { from: 'bot', content: 'Error : ' + (err.message || err.statusText) },
        ]);
        this.loading.set(false);
      },
    });
  }
}
