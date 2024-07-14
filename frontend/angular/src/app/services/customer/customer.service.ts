import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {CustomerDTO} from "../../models/customer-dto";
import {environment} from "../../../environments/environment";
import {AuthenticationResponse} from "../../models/authentication-response";
import {CustomerRegisterationRequest} from "../../models/customer-registeration-request";


@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  private readonly customerUrl =  `${environment.api.baseUrl}/${environment.api.customerUrl}`

  constructor(
    private http: HttpClient
  ) { }

  findAll() : Observable<CustomerDTO[]> {
    let headers = CustomerService.getHeaders();

    return this.http.get<CustomerDTO[]>(
      this.customerUrl
      );
  }

  registerCustomer(customer: CustomerRegisterationRequest) :Observable<void> {
    let headers = CustomerService.getHeaders();
    return  this.http.post<void>(this.customerUrl, customer)
  }

  private static getHeaders() {
    let headers: HttpHeaders = new HttpHeaders();
    const storedUser = localStorage.getItem("user");
    if (storedUser) {
      const authResponse: AuthenticationResponse = JSON.parse(storedUser);
      const token = authResponse.token;
      headers = headers.set('Authorization', 'Bearer ' + token);
    }
    return headers;
  }

}
