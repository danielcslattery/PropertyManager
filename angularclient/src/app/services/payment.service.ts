import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { Payment } from '../models/payment';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {
  private urls = {
    "all": 'http://localhost:8080/payments/allRest',
    "delete": 'http://localhost:8080/payments'
  };



  constructor(private http: HttpClient) { }

  getPayments(): Observable<Payment[]>{
    return this.http.get<Payment[]>(this.urls["all"])
  }

  getByApartment(apartmentId: number): Observable<Payment[]>{
    return this.http.get<Payment[]>(`http://localhost:8080/payments/byApartmentRest/${apartmentId}`)
  }

  deletePayment(payment: Payment): void {
    // Still must subscribe for the delete request to go through
    this.http.delete(`http://localhost:8080/payments/${payment.paymentId}`).subscribe();
  }

  addPayment(apartmentId: number, paymentAmount: number, month: number): void {
    this.http.post('http://localhost:8080/payments/', { apartmentId: apartmentId,
                                                        paymentAmount: paymentAmount,
                                                        month: month}).subscribe();
  }
}
