import { Injectable } from '@angular/core';
import { Apartment } from '../models/apartment';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders, HttpParams, HttpResponse } from '@angular/common/http'
import { Building } from '../models/building';

@Injectable({
  providedIn: 'root',
})
export class ApartmentService {
  private HOST = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  getByBuilding(id: number): Observable<HttpResponse<any>> {
    let url = `${this.HOST}/buildings/${id}/apartments`;
    return this.http.get(url, {
      observe: 'response',
    });
  }

  deleteApartment(apartment: Apartment): Observable<HttpResponse<any>> {
    let url = `${this.HOST}/buildings/${apartment.buildingId}/apartments/${apartment.id}`

    return this.http.delete(url, {observe: 'response'});
  }

  addApartment(apartment: Apartment): Observable<HttpResponse<any>> {
    let url = `${this.HOST}/buildings/${apartment.buildingId}/apartments`;

    return this.http.post(url, apartment, {
      observe: 'response',
    });
  }

  editApartment(apartment: Apartment): Observable<HttpResponse<any>> {
    let url = `${this.HOST}/buildings/${apartment.buildingId}/apartments/${apartment.id}`

    return this.http.put(url, apartment, {
      observe: 'response',
    });
  }

  getApartmentsWithLatePayments(month: number): Observable<HttpResponse<any>> {
    let params = new HttpParams().set('month', month);
    let url = `${this.HOST}/buildings/0/apartments/latePayments`
    return this.http.get(url, {
      observe: 'response',
      params: params,
    });
  }
}
