import { Injectable } from '@angular/core';
import { Building } from '../models/building';
import { Observable, of } from 'rxjs';
import { HttpClient, HttpHeaders, HttpResponse } from '@angular/common/http'

@Injectable({
  providedIn: 'root',
})
export class BuildingService {
  private HOST = 'http://localhost:8080';

  constructor(private http: HttpClient) {}

  getBuildings(): Observable<HttpResponse<any>> {
    let url = `${this.HOST}/buildings`
    return this.http.get(url, { observe: 'response' });
  }

  deleteBuilding(building: Building): Observable<HttpResponse<any>> {
    let url = `${this.HOST}/buildings/${building.id}`

    return this.http.delete(url, {
      observe: 'response',
    });
  }

  addBuilding(building: Building): Observable<HttpResponse<any>> {
    let url = `${this.HOST}/buildings}`
    return this.http.post(url, building, {
      observe: 'response',
    });
  }

  editBuilding(building: Building): Observable<HttpResponse<any>> {
    let url = `${this.HOST}/buildings/${building.id}`
    return this.http.put(url, building, {
      observe: 'response',
    });
  }


  // Store to and retreive the current building in local storage so it's available everywhere
  getCurrentBuilding(): Building | undefined {
    let string: string | null = localStorage.getItem('building');
    if (string) {
      let building: Building = JSON.parse(string) as Building;
      return building;
    } else {
      return undefined;
    }
  }

  setCurrentBuilding(building: Building): void {
    localStorage.setItem('building', JSON.stringify(building));
  }
}
