import {Component, OnInit} from '@angular/core';
import {CustomerDTO} from "../../models/customer-dto";
import {CustomerService} from "../../services/customer/customer.service";
import {CustomerRegisterationRequest} from "../../models/customer-registeration-request";
import {ConfirmationService, MessageService} from "primeng/api";

@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrl: './customer.component.scss'
})
export class CustomerComponent implements OnInit {
  display: boolean = false;
  customers: Array<CustomerDTO> = [];
  customer: CustomerRegisterationRequest = {};
  operation: 'create' | 'update' = 'create';

  constructor(
    private customerService: CustomerService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
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
      if (this.operation === 'create') {
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
      } else if (this.operation === 'update') {
        this.customerService.updateCustomer(customer.id, customer).subscribe(
          {
            next: () => {
              this.findAllCustomers();
              this.display = false;
              this.customer = {};
              this.messageService.add({
                  severity:'success',
                  summary: 'Customer updated',
                  detail: `Customer ${customer.name} was successfully updated`
              });
            }
          }
        )
      }

    }

  }

  deleteCustomer(customer: CustomerDTO) {
    if (customer) {
      this.confirmationService.confirm({
        header: 'Delete customer',
        message: `Are you sure you want to delete ${customer.name}? You can\'t undo this afterwards`,
        accept: () => {
          this.customerService.deleteCustomer(customer.id)
            .subscribe({
              next: () => {
                this.findAllCustomers();
                this.messageService.add(
                  {
                    severity:'success',
                    summary: 'Customer deleted',
                    detail: `Customer ${customer.name} was successfully deleted`
                  }
                );
              }
          });
        }
      });
    }
  }

  updateCustomer(customerDTO: CustomerDTO) {
    this.display = true;
    this.customer  = customerDTO;
    this.operation = 'update';
  }

  createCustomer() {
    this.display = true;
    this.customer = {};
    this.operation = 'create';
  }

  cancel() {
    this.display = false;
    this.customer = {};
    this.operation = 'create';
  }
}
