import { Component, OnInit } from '@angular/core';
import { Building } from '../models/building';
import { BuildingService } from '../services/building.service';
import { FormControl } from '@angular/forms';

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


  addressForm = new FormControl("");

  buildings: Building[] = [];

  getBuildings(): void {
    this.buildingService.getBuildings().subscribe(buildings => this.buildings = buildings)
  }

  delete(building: Building): void {
    console.log("Deleting!" + building.buildingId)
    this.buildingService.deleteBuilding(building);
  }

  add(): void {
    this.buildingService.addBuilding(this.addressForm.value);
  }




}
