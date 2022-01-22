import { Injectable } from '@angular/core';
import { Apartment } from '../models/apartment';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http'
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

  getApartments(): Observable<Apartment[]>{
    return this.http.get<Apartment[]>(this.urls["all"])
  }

  getByBuilding(id: number): Observable<Apartment[]>{
    return this.http.get<Apartment[]>(`http://localhost:8080/apartments/byBuilding/${id}`)
  }

  deleteApartment(apartment: Apartment): Observable<HttpResponse<any>> {
    // Still must subscribe for the delete request to go through
    return this.http.delete<any>(`http://localhost:8080/apartments/${apartment.id}`, {observe: 'response'});
  }

  addApartment(formSubmission: FormData): Observable<HttpResponse<any>> {
    console.log(formSubmission)
    return this.http.post('http://localhost:8080/apartments/', formSubmission, {observe: 'response'});
  }

  editApartment(formSubmission: FormData): Observable<HttpResponse<any>> {
    console.log("Editing", formSubmission)
    return this.http.put(`http://localhost:8080/apartments/`, formSubmission, {observe: 'response'});
  }

}
