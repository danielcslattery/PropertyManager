import { Component, OnInit } from '@angular/core';
import { PaymentService } from '../services/payment.service';
import { ActivatedRoute } from '@angular/router';
import { Payment } from '../models/payment';

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

  payments: Payment[] = []

  getPayments(): void {
  this.paymentService.getPayments().subscribe(payment => this.payments = payment)
  }

  getByApartment(apartmentId: number): void {
  this.paymentService.getByApartment(apartmentId).subscribe(payment => this.payments = payment)
  }

  delete(payment: Payment): void {
  console.log("Deleting!" + payment.paymentId)
  this.paymentService.deletePayment(payment);
  }


  //TODO: Change parameters of add()
  // add(addressStr: string): void {
  //   this.paymentService.addPayment(addressStr);
  // }

}
