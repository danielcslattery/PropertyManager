import { Injectable } from '@angular/core';
import { Apartment } from '../models/apartment';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { Building } from '../models/building';

@Injectable({
  providedIn: 'root'
})
export class ApartmentService {
  private urls = {
    "all": 'http://localhost:8080/apartments/allRest',
    "delete": 'http://localhost:8080/apartments'
  };

  private allApartmentsUrl = 'http://localhost:8080/apartments/allRest'


  constructor(private http: HttpClient) { }

  getApartments(): Observable<Apartment[]>{
    return this.http.get<Apartment[]>(this.urls["all"])
  }

  getByBuilding(buildingId: number): Observable<Apartment[]>{
    return this.http.get<Apartment[]>(`http://localhost:8080/apartments/byBuildingRest/${buildingId}`)
  }

  deleteApartment(apartment: Apartment): void {
    // Still must subscribe for the delete request to go through
    this.http.delete(`http://localhost:8080/apartments/${apartment.apartmentId}`).subscribe();
  }

  addApartment(formSubmission: FormData): void {
    console.log(formSubmission)
    this.http.post('http://localhost:8080/apartments/', formSubmission).subscribe();
  }


}
