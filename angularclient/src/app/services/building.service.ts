import { Injectable } from '@angular/core';
import { Building } from '../models/building';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http'

@Injectable({
  providedIn: 'root'
})
export class BuildingService {
  private urls = {
    "home": 'http://localhost:8080/buildings/'
  };

  constructor(private http: HttpClient) { }

  getBuildings(): Observable<HttpResponse<any>>{
    return this.http.get(this.urls["home"], {observe: 'response'});
  }

  deleteBuilding(building: Building): Observable<HttpResponse<any>> {
    return this.http.delete(`${this.urls["home"]}${building.id}`, {observe: 'response'});
  }

  addBuilding(formSubmission: FormData): Observable<HttpResponse<any>> {
    console.log("Adding address: ", formSubmission)
    return this.http.post(this.urls["home"], formSubmission, {observe: 'response'})
  }

  editBuilding(formSubmission: FormData): Observable<HttpResponse<any>> {
    console.log("Editing address: ", formSubmission)
    return this.http.put(this.urls["home"], formSubmission, {observe: 'response'});
  }


}
