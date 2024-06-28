import {Component, EventEmitter, Input, Output} from '@angular/core';
import {FormsModule} from "@angular/forms";
import {NgForOf, NgIf} from "@angular/common";

@Component({
  selector: 'app-my-first-component',
  standalone: true,
  imports: [
    FormsModule,
    NgIf,
    NgForOf
  ],
  templateUrl: './my-first.component.html',
  styleUrl: './my-first.component.scss'
})
export class MyFirstComponent {

  @Input()
  inputValue: string = 'Hello';
  @Output()
  childClicked: EventEmitter<void> = new EventEmitter<void>();

  @Output()
  elementCreated: EventEmitter<string> = new EventEmitter<string>();



  displayMsg:boolean = false;
  msgList: Array<string> = [];

 // msgListComposed: any[] = [];

  clickMe(): void {
    this.msgList.push(this.inputValue);
   /* this.msgListComposed.push({
      name: this.inputValue,
      visible: true
    });*/
    console.log(this.msgList);
    this.childClicked.emit();
    this.elementCreated.emit(this.inputValue);
    this.inputValue = '';

  }
}
