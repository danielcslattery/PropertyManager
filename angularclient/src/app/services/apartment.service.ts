import { Injectable } from '@angular/core';
import { Apartment } from '../models/apartment';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders, HttpParams, HttpResponse } from '@angular/common/http'
import { Building } from '../models/building';

@Injectable({
  providedIn: 'root'
})
export class ApartmentService {
  private urls = {
    "home": 'http://localhost:8080/apartments/',
    "byBuilding": 'http://localhost:8080/apartments/byBuilding/',
    "latePayments": `http://localhost:8080/apartments/latePayments`
  };

  constructor(private http: HttpClient) { }

  getApartments(): Observable<HttpResponse<any>>{
    return this.http.get(this.urls["home"], {observe: 'response'});
  }

  getByBuilding(id: number): Observable<HttpResponse<any>>{
    return this.http.get(`${this.urls["byBuilding"]}${id}`, {observe: 'response'});
  }

  deleteApartment(apartment: Apartment): Observable<HttpResponse<any>> {
    return this.http.delete(this.urls["home"], {observe: 'response', body: apartment});
  }

  addApartment(formSubmission: FormData): Observable<HttpResponse<any>> {
    return this.http.post(this.urls["home"], formSubmission, {observe: 'response'});
  }

  editApartment(formSubmission: FormData): Observable<HttpResponse<any>> {
    return this.http.put(this.urls["home"], formSubmission, {observe: 'response'});
  }

  getApartmentsWithLatePayments(month: number): Observable<HttpResponse<any>> {
    let params = new HttpParams().set("month", month);
    return this.http.get(this.urls["latePayments"], 
      {observe: 'response', params: params});
  }

}
