import { Component } from '@angular/core';
import {CustomerRegisterationRequest} from "../../models/customer-registeration-request";
import {CustomerService} from "../../services/customer/customer.service";
import {Router} from "@angular/router";
import {AuthenticationService} from "../../services/authentication/authentication.service";
import {AuthenticationRequest} from "../../models/authentication-request";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent {

  constructor(
    private customerService: CustomerService,
    private router: Router,
    private authenticationService: AuthenticationService

  ) {}

  customer: CustomerRegisterationRequest  = {};
  errorMsg = '';

  createAccount() {
    this.customerService.registerCustomer(this.customer)
      .subscribe({
        next: () => {
          const authReq : AuthenticationRequest = {
            username : this.customer.email,
            password: this.customer.password
          }
          this.authenticationService.login(authReq)
          .subscribe({
            next: (authenticationResponse) => {
              localStorage.setItem('user', JSON.stringify(authenticationResponse));
              this.router.navigate(['customers'])
            }, error: (err) => {
              if (err.error.statusCode === 401) {
                this.errorMsg='Login and / or password is incorrect';
              }
            }

          });
        }
      });
  }

  login() {
    this.router.navigate(['login']);
  }
}
