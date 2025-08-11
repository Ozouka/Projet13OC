import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, timer } from 'rxjs';
import { switchMap, tap } from 'rxjs/operators';

export interface ChatMessage {
  id: number;
  userId: number;
  from: string;
  content: string;
  sentAt: string;
  isRead: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class ChatService {
  private readonly API_BASE_URL = 'http://localhost:8080/api/chat';
  private messagesSubject = new BehaviorSubject<ChatMessage[]>([]);
  public messages$ = this.messagesSubject.asObservable();

  constructor(private http: HttpClient) {
    timer(0, 2000).pipe(
      switchMap(() => this.http.get<ChatMessage[]>(`${this.API_BASE_URL}/messages`))
    ).subscribe({
      next: (messages) => {
        const sortedMessages = messages.sort((a, b) => 
          new Date(a.sentAt).getTime() - new Date(b.sentAt).getTime()
        );
        this.messagesSubject.next(sortedMessages);
      },
      error: (error) => {
        console.error('Erreur lors du polling des messages:', error);
      }
    });
  }

  sendMessage(message: { from: string; content: string }): Observable<ChatMessage> {
    return this.http.post<ChatMessage>(`${this.API_BASE_URL}/send`, message).pipe(
      tap(() => {
        this.refreshMessages();
      })
    );
  }

  private refreshMessages(): void {
    this.http.get<ChatMessage[]>(`${this.API_BASE_URL}/messages`).subscribe({
      next: (messages) => {
        const sortedMessages = messages.sort((a, b) => 
          new Date(a.sentAt).getTime() - new Date(b.sentAt).getTime()
        );
        this.messagesSubject.next(sortedMessages);
      },
      error: (error) => {
        console.error('Erreur lors du rafraîchissement des messages:', error);
      }
    });
  }

  getCurrentMessages(): ChatMessage[] {
    return this.messagesSubject.value;
  }
}
