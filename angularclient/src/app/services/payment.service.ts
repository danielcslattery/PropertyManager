import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { Payment } from '../models/payment';
import { Apartment } from '../models/apartment';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {
  private HOST = 'http://localhost:8080'

  constructor(private http: HttpClient) { }

  getByApartment(apartment: Apartment): Observable<HttpResponse<any>>{
    let url = `${this.HOST}/buildings/${apartment.buildingId}/apartments/${apartment.id}/payments`;

    // let params = new HttpParams().set('apartmentId', apartmentId);
    return this.http.get(url, {
      observe: 'response',
    });
  }

  deletePayment(payment: Payment): Observable<HttpResponse<any>> {
    let url = `${this.HOST}/buildings/${payment.buildingId}/apartments/${payment.apartmentId}/payments/${payment.id}`;

    return this.http.delete(url, {observe: 'response'});
  }

  addPayment(payment: Payment): Observable<HttpResponse<any>> {
    let url = `${this.HOST}/buildings/${payment.buildingId}/apartments/${payment.apartmentId}/payments`;
    return this.http.post(url, payment, {observe: 'response'});
  }

  editPayment(payment: Payment): Observable<HttpResponse<any>> {
    let url = `${this.HOST}/buildings/${payment.buildingId}/apartments/${payment.apartmentId}/payments/${payment.id}`;
    return this.http.put(url, payment, {observe: 'response'});
  }
}
