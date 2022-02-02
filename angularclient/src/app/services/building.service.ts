import { Injectable } from '@angular/core';
import { Building } from '../models/building';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http'

@Injectable({
  providedIn: 'root'
})
export class BuildingService {
  private urls = {
    "all": 'http://localhost:8080/buildings/all',
    "delete": 'http://localhost:8080/buildings'
  };

  private allBuildingsUrl = 'http://localhost:8080/buildings/all'


  constructor(private http: HttpClient) { }

  getBuildings(): Observable<HttpResponse<any>>{
    return this.http.get(this.urls["all"], {observe: 'response'});
  }

  deleteBuilding(building: Building): Observable<HttpResponse<any>> {
    // Still must subscribe for the delete request to go through
    return this.http.delete(`http://localhost:8080/buildings/${building.id}`, {observe: 'response'});
  }

  addBuilding(formSubmission: FormData): Observable<HttpResponse<any>> {
    console.log("Adding address: ", formSubmission)
    return this.http.post('http://localhost:8080/buildings/', formSubmission, {observe: 'response'})
  }

  editBuilding(formSubmission: FormData): Observable<HttpResponse<any>> {
    console.log("Editing address: ", formSubmission)
    return this.http.put(`http://localhost:8080/buildings/`, formSubmission, {observe: 'response'});
  }


}
