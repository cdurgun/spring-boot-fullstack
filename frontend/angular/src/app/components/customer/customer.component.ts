import {Component, OnInit} from '@angular/core';
import {CustomerDTO} from "../../models/customer-dto";
import {CustomerService} from "../../services/customer/customer.service";
import {CustomerRegisterationRequest} from "../../models/customer-registeration-request";
import {MessageService} from "primeng/api";

@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrl: './customer.component.scss'
})
export class CustomerComponent implements OnInit {
  display: boolean = false;
  customers: Array<CustomerDTO> = [];
  customer: CustomerRegisterationRequest = {};

  constructor(
    private customerService: CustomerService,
    private messageService: MessageService
  ) {}

  ngOnInit(): void {
    this.findAllCustomers();
  }

  private findAllCustomers() {
    this.customerService.findAll()
      .subscribe({
        next: (data: CustomerDTO[]) => {
          this.customers = data;
          console.log(data);
        }
      })
  }

  save(customer: CustomerRegisterationRequest) {
    if (customer) {
      this.customerService.registerCustomer(customer)
        .subscribe({
          next: () => {
            this.findAllCustomers();
            this.display = false;
            this.customer = {};
            this.messageService.add(
              {severity:'success',
                summary: 'Customer saved',
                detail: `Customer ${customer.name} was successfully saved` }
            );
          }
      });
    }

  }
}
