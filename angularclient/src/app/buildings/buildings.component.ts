import { Component, OnInit } from '@angular/core';
import { Building } from '../models/building';
import { BuildingService } from '../services/building.service';


@Component({
  selector: 'app-buildings',
  templateUrl: './buildings.component.html',
  styleUrls: ['./buildings.component.css']
})
export class BuildingsComponent implements OnInit {

  constructor(private buildingService: BuildingService) { }

  ngOnInit(): void {
    this.getBuildings();
  }


  buildings: Building[] = [];

  getBuildings(): void {
    this.buildingService.getBuildings().subscribe(buildings => this.buildings = buildings)
  }

  delete(building: Building): void {
    console.log("Deleting!" + building.buildingId)
    this.buildingService.deleteBuilding(building);
  }

  add(addressStr: string): void {
    this.buildingService.addBuilding(addressStr);
  }




}
