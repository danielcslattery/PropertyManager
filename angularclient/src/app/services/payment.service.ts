import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { Payment } from '../models/payment';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {
  private urls = {
    "all": 'http://localhost:8080/payments/all',
    "delete": 'http://localhost:8080/payments'
  };

  constructor(private http: HttpClient) { }

  getPayments(): Observable<HttpResponse<any>>{
    return this.http.get(this.urls["all"], {observe: 'response'});
  }

  getByApartment(apartmentId: number): Observable<HttpResponse<any>>{
    return this.http.get(`http://localhost:8080/payments/byApartment/${apartmentId}`, {observe: 'response'});
  }

  deletePayment(payment: Payment): Observable<HttpResponse<any>> {
    // Still must subscribe for the delete request to go through
    return this.http.delete(`http://localhost:8080/payments/${payment.id}`, {observe: 'response'});
  }

  addPayment(formSubmission: FormData): Observable<HttpResponse<any>> {
    console.log(formSubmission)
    return this.http.post('http://localhost:8080/payments/', formSubmission, {observe: 'response'});
  }

  editPayment(formSubmission: FormData): Observable<HttpResponse<any>> {
    console.log("Editing", formSubmission)
    return this.http.put(`http://localhost:8080/payments/`, formSubmission, {observe: 'response'});
  }


}
