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
    "all": 'http://localhost:8080/apartments/all',
    "delete": 'http://localhost:8080/apartments'
  };

  private allApartmentsUrl = 'http://localhost:8080/apartments/all'


  constructor(private http: HttpClient) { }

  getApartments(): Observable<HttpResponse<any>>{
    return this.http.get(this.urls["all"], {observe: 'response'});
  }

  getByBuilding(id: number): Observable<HttpResponse<any>>{
    return this.http.get(`http://localhost:8080/apartments/byBuilding/${id}`, {observe: 'response'});
  }

  deleteApartment(apartment: Apartment): Observable<HttpResponse<any>> {
    return this.http.delete(`http://localhost:8080/apartments/${apartment.id}`, {observe: 'response'});
  }

  addApartment(formSubmission: FormData): Observable<HttpResponse<any>> {
    console.log(formSubmission)
    return this.http.post('http://localhost:8080/apartments/', formSubmission, {observe: 'response'});
  }

  editApartment(formSubmission: FormData): Observable<HttpResponse<any>> {
    console.log("Editing", formSubmission)
    return this.http.put(`http://localhost:8080/apartments/`, formSubmission, {observe: 'response'});
  }

  getApartmentsWithLatePayments(month: number): Observable<HttpResponse<any>> {
    console.log("Checking month: ", month)
    let params = new HttpParams().set("month", month);
    return this.http.get(`http://localhost:8080/apartments/latePayments`, 
      {observe: 'response', params: params});
  }

}
