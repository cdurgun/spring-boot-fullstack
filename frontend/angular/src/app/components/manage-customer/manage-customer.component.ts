import {Component, EventEmitter, Input, Output} from '@angular/core';
import {CustomerRegisterationRequest} from "../../models/customer-registeration-request";

@Component({
  selector: 'app-manage-customer',
  templateUrl: './manage-customer.component.html',
  styleUrl: './manage-customer.component.scss'
})
export class ManageCustomerComponent {
  @Input()
  customer: CustomerRegisterationRequest  = {}
  @Output()
  submit: EventEmitter<CustomerRegisterationRequest> = new EventEmitter<CustomerRegisterationRequest>();

  get isCustomerValid():boolean {
    return this.hasLength(this.customer.name) &&
      this.hasLength(this.customer.email) &&
      this.hasLength(this.customer.password) &&
      this.hasLength(this.customer.gender) &&
      this.customer.age !== undefined && this.customer.age > 0;

  }

  private hasLength(input:string | undefined ):boolean {
    return input !== null && input !== undefined && input.length > 0;
  }

  onSubmit() {
    this.submit.emit(this.customer);
  }
}
