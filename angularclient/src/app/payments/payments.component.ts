import { Component, OnInit } from '@angular/core';
import { PaymentService } from '../services/payment.service';
import { Payment } from '../models/payment';
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
    if(p[0].path === "byApartment"){
      // Go to payment by building
      this.route.params.subscribe( params => {
        this.getByApartment(params['id']);
        this.paymentForm.get("apartmentId")?.setValue(params['id']);
      })
    } else {
      // Go to all payment
      this.getPayments()
    }
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
  id: new FormControl(""),
  apartmentId: new FormControl("")
})

  // Takes in selected building when edit button is hit.
  selectedPayment?: Payment;
  payments: Payment[] = [];
  apartmentId: number = 0;

  getPayments(): void {
  this.paymentService.getPayments().subscribe(response => this.payments = response.body)
  }

  getByApartment(apartmentId: number): void {
  this.paymentService.getByApartment(apartmentId).subscribe(response => this.payments = response.body)
  }

  delete(payment: Payment): void {
    this.paymentService.deletePayment(payment).subscribe(response => {
      if (response.status == 200) {
        this.payments = this.payments.filter(el => !(el.id == response.body.id))
      }
    });
  }


  add(postform: FormGroupDirective): void {
    
    this.paymentService.addPayment(this.paymentForm.value).subscribe(response => {
      if (response.status == 201) {
        this.payments.push(response.body);
    // Return form to empty
    postform.resetForm();
      } else {
        // Do nothing
      }
    });
  }

  handleFormSubmission( buttonClicked: string,
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
    postform.form.removeControl("editamount");

    let editedMonth: string = this.paymentForm.get("editmonth")?.value;
    postform.form.get("month")?.setValue(editedMonth);
    postform.form.removeControl("editmonth");

    this.paymentService.editPayment(postform.value).subscribe(response => {
      if (response.status == 200) {
        this.payments = this.payments.filter(el => !(el.id == response.body.id))
        this.payments.push(response.body);
      } else {
        // Do nothing
      }
    });

    // Return form to empty
    postform.resetForm();
  }
  
  selectForEditing(payment: Payment): void {
    // Disable inputs related to adding when user is editing
    this.paymentForm.get("amount")?.disable();
    this.paymentForm.get("month")?.disable();

    // Edited amount and month is only necessary so that the add amount/month portion 
    // of the form isn't populated with existing amount/month when editing.
    this.paymentForm.setControl("editamount", new FormControl(payment.amount))
    this.paymentForm.get("editamount")?.setValidators( 
      [
        Validators.required,
        Validators.min(0)
      ])

    this.paymentForm.setControl("editmonth", new FormControl(payment.month))
    this.paymentForm.get("editmonth")?.setValidators( 
      [
        Validators.required,
        Validators.min(1),
        Validators.max(12)
      ])

    this.selectedPayment = payment;
  }
}
