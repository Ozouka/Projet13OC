import { Component, OnInit, OnDestroy } from '@angular/core';
import { ChatService, ChatMessage } from './services/chat.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy {
  message = '';
  messages: ChatMessage[] = [];
  user1 = 'Toto';
  user2 = 'Bob';
  currentUser = 'Toto';
  private subscription: Subscription = new Subscription();

  constructor(private chatService: ChatService) {}

  ngOnInit() {
    this.subscription.add(
      this.chatService.messages$.subscribe(messages => {
        this.messages = messages;
        this.scrollToBottom();
      })
    );
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  sendMessage() {
    if (this.message.trim() && this.currentUser) {
      const chatMessage = {
        from: this.currentUser,
        content: this.message.trim()
      };
      
      console.log('Envoi du message:', chatMessage);
      
      this.chatService.sendMessage(chatMessage).subscribe({
        next: (response) => {
          console.log('Message envoyé avec succès:', response);
          this.message = '';
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