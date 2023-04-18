import { Component, OnInit } from '@angular/core';
import { PaymentService } from '../services/payment.service';
import { Payment } from '../models/payment';
import { Apartment } from '../models/apartment';
import { ActivatedRoute, RouterState } from '@angular/router';
import { AbstractControl, FormControl, FormGroup, FormGroupDirective, Validators } from '@angular/forms';



@Component({
  selector: 'app-payments',
  templateUrl: './payments.component.html',
  styleUrls: ['./payments.component.css']
})
export class PaymentsComponent implements OnInit {


  constructor(private paymentService: PaymentService,
    private route: ActivatedRoute) {
     }

ngOnInit(): void {

  // Wait for the url, then act based on the url
  this.route.url.subscribe(p => {
    // Go to payment by building
    this.route.params.subscribe( params => {
      this.apartmentId = params['apartmentId'];
      this.buildingId = params['buildingId'];
      this.getByApartment(this.apartmentId);
      this.paymentForm.get("apartmentId")?.setValue(this.apartmentId);
    })
  })

}

paymentForm = new FormGroup ({
  amount: new FormControl("",[
    Validators.required,
    Validators.min(0)
  ]),
  month: new FormControl("",[
    Validators.required,
    Validators.min(1),
    Validators.max(12)
  ]),
  editamount: new FormControl({
    value:"", disable: true}, [
    Validators.required,
    Validators.min(0)
  ]),
  editmonth: new FormControl({
    value:"", disable: true},[
    Validators.required,
    Validators.min(1),
    Validators.max(12)
  ]),
  id: new FormControl(""),
  apartmentId: new FormControl("")
})

  // Takes in selected building when edit button is hit.
  selectedPayment?: Payment;
  payments: Payment[] = [];
  apartmentId: number = 0;
  buildingId: number = 0;

  getByApartment(apartmentId: number): void {

    // Using as placeholder for Apartment object generated from ids.  
    // Will be replaced with actual object.
    let apartment: Apartment = {
      id: apartmentId,
      buildingId: this.buildingId,
      number: "0",
    }

    this.paymentService.getByApartment(apartment).subscribe(response => this.payments = response.body)
  }

  delete(payment: Payment): void {
    this.paymentService.deletePayment(payment).subscribe(response => {
      if (response.status == 200) {
        this.payments = this.payments.filter(el => !(el.id == response.body.id))
      }
    });
  }

  add(postform: FormGroupDirective): void {

    let payment: Payment = {
      id: this.paymentForm.value.id,
      apartmentId: this.paymentForm.value.apartmentId,
      buildingId: this.buildingId,
      amount: this.paymentForm.value.amount,
      month: this.paymentForm.value.month,
    };

    this.paymentService.addPayment(payment).subscribe(response => {
      if (response.status == 201) {
        this.payments.push(response.body);
    // Return form to empty
    postform.resetForm();
      } else {
        // Do nothing
      }
    });
  }

  handleFormSubmission( 
    buttonClicked: string,
    postform: FormGroupDirective,
    payment?: Payment): void{

    if (buttonClicked == "add"){
      this.add(postform);
    } else if (buttonClicked == "edit"){
      postform.form.get("id")?.setValue(payment?.id);

      this.edit(postform);

      // Close form
      this.selectedPayment = undefined;
    }
  }


  edit(postform: FormGroupDirective): void {
    // Enable these components of the form so form can be submitted.
    this.paymentForm.get("amount")?.enable();
    this.paymentForm.get("month")?.enable();

    // Edited amount and month is only necessary so that the add amount/month portion 
    // of the form isn't populated with existing amount/month when editing.
    let editedAmount: string = this.paymentForm.get("editamount")?.value;
    postform.form.get("amount")?.setValue(editedAmount);

    let editedMonth: string = this.paymentForm.get("editmonth")?.value;
    postform.form.get("month")?.setValue(editedMonth);

    let payment: Payment = {
      id: postform.value.id,
      apartmentId: this.apartmentId,
      buildingId: this.buildingId,
      amount: postform.value.amount,
      month: postform.value.month,
    };

    this.paymentService.editPayment(payment).subscribe(response => {
      if (response.status == 200) {
        this.payments = this.payments.filter(el => !(el.id == response.body.id))
        this.payments.push(response.body);
      } else {
        // Do nothing
      }
    });
    // Return form to empty and disable editing fields again
    this.paymentForm.get("editamount")?.disable();
    this.paymentForm.get("editmonth")?.disable();
    postform.resetForm();
  }
  
  selectForEditing(payment: Payment): void {
    // Disable inputs related to adding when user is editing
    // Edited amount and month is only necessary so that the add amount/month portion 
    // of the form isn't populated with existing amount/month when editing.
    this.paymentForm.get("amount")?.disable();
    this.paymentForm.get("month")?.disable();

    this.paymentForm.get("editamount")?.enable();
    this.paymentForm.get("editmonth")?.enable();

    this.paymentForm.get("editamount")?.setValue(payment.amount);
    this.paymentForm.get("editmonth")?.setValue(payment.month);

    this.selectedPayment = payment;
  }
}
