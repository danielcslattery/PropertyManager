import { Component, OnInit } from '@angular/core';
import { Building } from '../models/building';
import { BuildingService } from '../services/building.service';
import { FormControl, FormGroup, FormGroupDirective, Validators } from '@angular/forms';

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
    address: new FormControl("",[
      Validators.required,
      Validators.minLength(4)
    ]),
    id: new FormControl("")
  })

  // Takes in selected building when edit button is hit.
  selectedBuilding?: Building;
 
  buildings: Building[] = [];

  getBuildings(): void {
    this.buildingService.getBuildings().subscribe(buildings => {
      this.buildings = buildings;
      })
    
  }

 delete(building: Building): void {
    this.buildingService.deleteBuilding(building).subscribe(response => {
      if (response.status == 200) {
        // The for loop is slow but ensures the correct building is deleted.
        for(let i = 0; i < this.buildings.length; i++){
          if (this.buildings[i].id == response.body.id){
            console.log(this.buildings[i]);
            this.buildings.splice(i, 1);
          }

        }
      } else {
        // Do nothing
      }
    });

  } 

  handleFormSubmission( buttonClicked: string,
                        postform: FormGroupDirective,
                        building?: Building): void{

    if (buttonClicked == "add"){
      this.add(postform);
    } else if (buttonClicked == "edit"){
      postform.form.get("id")?.setValue(building?.id);
      
      this.edit(postform);

      // Close form
      this.selectedBuilding = undefined;
    }

  }

  // When 201 response is received, adds the model to the model list.
  add(postform: FormGroupDirective): void {
    this.buildingService.addBuilding(postform.form.value).subscribe(response => {
      if (response.status == 201) {
        this.buildings.push(response.body);
    // Return form to empty
    postform.resetForm();
      } else {
        // Do nothing
      }
    });

  }

  edit(postform: FormGroupDirective): void {

    // Edited address is only necessary so that the add address portion 
    // of the form isn't populated with existing address when editing.
    let editedAddress: string = this.buildingForm.get("editaddress")?.value;
    postform.form.get("address")?.setValue(editedAddress);
    postform.form.removeControl("editaddress");

    this.buildingService.editBuilding(postform.value).subscribe(response => {
      if (response.status == 200) {
        // The for loop is slow but ensures the correct building is updated.
        for(let i = 0; i < this.buildings.length; i++){
          if (this.buildings[i].id == response.body.id){
            this.buildings.splice(i, 1);
            this.buildings.push(response.body);
          }

        }
      } else {
        // Do nothing
      }
    });

    // Return form to empty
    postform.resetForm();

    // postform.form.get("address")?.setValue("");

  }

  selectForEditing(building: Building): void {

    // Edited address is only necessary so that the add address portion 
    // of the form isn't populated with existing address when editing.
    this.buildingForm.setControl("editaddress", new FormControl(building.address))
    this.buildingForm.get("editaddress")?.setValidators( 
      [Validators.required,
        Validators.minLength(4)])
    // this.buildingForm.get("editaddress")?.setValue(building.address)
    this.selectedBuilding = building;
  }

}
