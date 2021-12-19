import { Injectable } from '@angular/core';
import { Building } from '../models/building';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http'

@Injectable({
  providedIn: 'root'
})
export class BuildingService {
  private urls = {
    "all": 'http://localhost:8080/buildings/allRest',
    "delete": 'http://localhost:8080/buildings'
  };

  private allBuildingsUrl = 'http://localhost:8080/buildings/allRest'


  constructor(private http: HttpClient) { }

  getBuildings(): Observable<Building[]>{
    return this.http.get<Building[]>(this.urls["all"])
  }

  deleteBuilding(building: Building): void {

    // Still must subscribe for the delete request to go through
    this.http.delete(`http://localhost:8080/buildings/${building.buildingId}`).subscribe();
  }

  addBuilding(addressStr: string): void {
    console.log("Adding address: ", addressStr)
    this.http.post('http://localhost:8080/buildings/', { address: addressStr}).subscribe();
  }


}
