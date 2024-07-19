import {Component, EventEmitter, input, Input, OnInit, Output} from '@angular/core';
import {CustomerRegisterationRequest} from "../../models/customer-registeration-request";

@Component({
  selector: 'app-manage-customer',
  templateUrl: './manage-customer.component.html',
  styleUrl: './manage-customer.component.scss'
})
export class ManageCustomerComponent {
  @Input()
  customer: CustomerRegisterationRequest  = {}
  @Input()
  operation: 'create' | 'update' = 'create';

  @Output()
  submit: EventEmitter<CustomerRegisterationRequest> = new EventEmitter<CustomerRegisterationRequest>();
  @Output()
  cancel:EventEmitter<void> = new EventEmitter<void>();

  get isCustomerValid():boolean {
    return this.hasLength(this.customer.name) &&
      this.hasLength(this.customer.email) &&
      this.customer.age !== undefined && this.customer.age > 0 &&
      (
        this.operation === 'update' ||
        this.hasLength(this.customer.password) &&
        this.hasLength(this.customer.gender)
      );

  }

  private hasLength(input:string | undefined ):boolean {
    return input !== null && input !== undefined && input.length > 0;
  }

  onSubmit() {
    this.submit.emit(this.customer);
  }


  onCancel() {
    this.cancel.emit();
  }
}
