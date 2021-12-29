import { Component, OnInit } from '@angular/core';
import { Building } from '../models/building';
import { BuildingService } from '../services/building.service';
import { FormControl, FormGroup, FormGroupDirective } from '@angular/forms';

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

  buildingForm = new FormGroup ({
    address: new FormControl("")
  })

  // Takes in selected building when edit button is hit.
  selectedBuilding?: Building;
 
  buildings: Building[] = [];

  getBuildings(): void {
    this.buildingService.getBuildings().subscribe(buildings => this.buildings = buildings)
  }

  delete(building: Building): void {
    console.log("Deleting!" + building.buildingId)
    this.buildingService.deleteBuilding(building);
  }

  handleFormSubmission( buttonClicked: string,
                        postform: FormGroupDirective,
                        building?: Building): void{
    let submissionFormData = postform.form;

    console.log("Form Data: ", submissionFormData)

    if (buttonClicked == "add"){
      this.add(postform.form);
    } else if (buttonClicked == "edit"){
      submissionFormData.addControl("buildingId", new FormControl(building?.buildingId))
      this.edit(postform.form);
    }
  }

  // Do not need to pass parameters to add()
  add(postform: FormGroup): void {
    this.buildingService.addBuilding(postform.value);
  }

  edit(postform: FormGroup): void {
    this.buildingService.editBuilding(postform.value);
  }

  selectForEditing(building: Building): void {
    this.buildingForm.get("address")?.setValue(building.address)
    this.selectedBuilding = building;
    console.log("Editing: ", this.selectedBuilding.address)
  }




}
