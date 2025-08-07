import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

interface ChatMessage {
  id: number;
  userId: number;
  from: string;
  content: string;
  sentAt: string;
  isRead: boolean;
}

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  message = '';
  messages: ChatMessage[] = [];
  user1 = 'Toto';
  user2 = 'Bob';
  currentUser = 'Toto';
  private readonly API_BASE_URL = 'http://localhost:8080/api/chat';

  private readonly USER_ID_MAPPING = {
    'Toto': 804821614,
    'Bob': -820333692
  };

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.loadMessages();
  }

  loadMessages() {
    console.log('Chargement des messages...');
    this.http.get<ChatMessage[]>(`${this.API_BASE_URL}/messages`)
      .subscribe({
        next: (messages) => {
          console.log('Messages reçus:', messages);
          this.messages = messages.sort((a, b) => 
            new Date(a.sentAt).getTime() - new Date(b.sentAt).getTime()
          );
          this.scrollToBottom();
        },
        error: (error) => {
          console.error('Erreur lors du chargement des messages:', error);
        }
      });
  }

  sendMessage() {
    if (this.message.trim() && this.currentUser) {
      const chatMessage = {
        from: this.currentUser,
        content: this.message.trim()
      };
      
      console.log('Envoi du message:', chatMessage);
      
      this.http.post<ChatMessage>(`${this.API_BASE_URL}/send`, chatMessage)
        .subscribe({
          next: (response) => {
            console.log('Message envoyé avec succès:', response);
            this.message = '';
            this.loadMessages();
          },
          error: (error) => {
            console.error('Erreur lors de l\'envoi du message:', error);
          }
        });
    }
  }

  switchUser(user: string) {
    console.log('Changement d\'utilisateur vers:', user);
    this.currentUser = user;
  }

  private scrollToBottom() {
    setTimeout(() => {
      const chatArea = document.querySelector('.chat-area');
      if (chatArea) {
        chatArea.scrollTop = chatArea.scrollHeight;
      }
    }, 100);
  }
} 