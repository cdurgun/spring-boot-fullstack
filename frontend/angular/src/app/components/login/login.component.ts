import { Component } from '@angular/core';
import {AuthenticationRequest} from "../../models/authentication-request";
import {AuthenticationService} from "../../services/authentication/authentication.service";
import { Message } from 'primeng/api';
import { MessagesModule } from 'primeng/messages';
import {Router} from "@angular/router";




@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  authenticationRequest: AuthenticationRequest = {};
  messages: Message[] = [];
  errorMsg = '';


  constructor(
    private authenticationService: AuthenticationService,
    private router: Router
  ) {
  }

  login() {
    this.messages = [];
    this.authenticationService.login(this.authenticationRequest)
      .subscribe({
        next: (authenticationResponse) => {
          localStorage.setItem('user', JSON.stringify(authenticationResponse));
          this.router.navigate(['customers'])
        }, error: (err) => {
            if (err.error.statusCode === 401) {
              /*this.messages = [
                { severity: 'error', detail: 'Login and / or password is incorrect' }
              ];*/
              this.errorMsg='Login and / or password is incorrect';
            }
          console.log(err.error.statusCode);
        }
      });
  }

  register() {
    this.router.navigate(['register']);
  }
}
