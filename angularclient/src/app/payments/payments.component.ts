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
      this.route.params.subscribe( params => this.getByApartment(params['id']))
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
  payments: Payment[] = []
  apartmentId: number = 0

  getPayments(): void {
  this.paymentService.getPayments().subscribe(payment => this.payments = payment)
  }

  getByApartment(apartmentId: number): void {
  this.paymentService.getByApartment(apartmentId).subscribe(payment => this.payments = payment)
  }

  delete(payment: Payment): void {
    this.paymentService.deletePayment(payment).subscribe(response => {
      if (response.status == 200) {
        // The for loop is slow but ensures the correct building is deleted.
        for(let i = 0; i < this.payments.length; i++){
          if (this.payments[i].id == response.body.id){
            console.log(this.payments[i]);  
            this.payments.splice(i, 1);
          }
        }
      } else {
        // Do nothing
      }
    });
  }


  add(postform: FormGroupDirective): void {
    // Get buildingId for the page from the url.  There may be a more efficient way to do this
    this.route.params.subscribe( params => this.apartmentId = params['id']);
    this.paymentForm.get("apartmentId")?.setValue(this.apartmentId);
    
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

      console.log(payment)
      postform.form.get("id")?.setValue(payment?.id);
      postform.form.get("apartmentId")?.setValue(payment?.apartmentId);

      console.log("Form1:", postform.form.get("apartmentId")?.value);
      this.edit(postform);

      // Close form
      this.selectedPayment = undefined;
    }
  }


  edit(postform: FormGroupDirective): void {

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
        // The for loop is slow but ensures the correct apartment is updated.
        for(let i = 0; i < this.payments.length; i++){
          if (this.payments[i].id == response.body.id){
            this.payments.splice(i, 1);
            this.payments.push(response.body);
          }

        }
      } else {
        console.log("Something went wrong...")
        // Do nothing
      }
    });

    // Return form to empty
    postform.resetForm();

    // postform.form.get("address")?.setValue("");

  }
  
  selectForEditing(payment: Payment): void {

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
