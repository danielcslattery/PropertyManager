import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { Payment } from '../models/payment';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {
  private urls = {
    "home": 'http://localhost:8080/payments/',
    "byApartment": 'http://localhost:8080/payments/byApartment/'
  };

  constructor(private http: HttpClient) { }

  getPayments(): Observable<HttpResponse<any>>{
    return this.http.get(this.urls["home"], {observe: 'response'});
  }

  getByApartment(apartmentId: number): Observable<HttpResponse<any>>{
    return this.http.get(`${this.urls["byApartment"]}${apartmentId}`, {observe: 'response'});
  }

  deletePayment(payment: Payment): Observable<HttpResponse<any>> {
    return this.http.delete(`${this.urls["home"]}${payment.id}`, {observe: 'response'});
  }

  addPayment(formSubmission: FormData): Observable<HttpResponse<any>> {
    return this.http.post(this.urls["home"], formSubmission, {observe: 'response'});
  }

  editPayment(formSubmission: FormData): Observable<HttpResponse<any>> {
    return this.http.put(this.urls["home"], formSubmission, {observe: 'response'});
  }
}
