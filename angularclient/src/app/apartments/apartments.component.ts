import { Component, OnInit } from '@angular/core';
import { ApartmentService } from '../services/apartment.service';
import { Apartment } from '../models/apartment';
import { ActivatedRoute, RouterState } from '@angular/router';
import { AbstractControl, FormControl, FormGroup, FormGroupDirective, Validators } from '@angular/forms';

@Component({
  selector: 'app-apartments',
  templateUrl: './apartments.component.html',
  styleUrls: ['./apartments.component.css']
})
export class ApartmentsComponent implements OnInit {

  constructor(private apartmentService: ApartmentService,
              private route: ActivatedRoute) {
               }

  ngOnInit(): void {

    // Wait for the url, then act based on the url
    this.route.url.subscribe(url => {
      // Go to apartments by building
      this.route.params.subscribe( params => {
        this.getByBuilding(params['buildingId']);
        this.apartmentForm.get("buildingId")?.setValue(params['buildingId']);
      })

    })

    let date = new Date()
    this.apartmentService.getApartmentsWithLatePayments(date.getMonth() + 1).subscribe(
      response => {
        this.apartmentsWithLatePayments = response.body;
      });
  }

  apartmentForm = new FormGroup ({
    number: new FormControl("",[
      Validators.required
    ]),
    editnumber: new FormControl({
      value:"", disable: true},[
      Validators.required
    ]),
    id: new FormControl(""),
    buildingId: new FormControl("")
  })

  // Takes in selected building when edit button is hit.
  selectedApartment?: Apartment;

  apartments: Apartment[] = []
  apartmentsWithLatePayments: Apartment[] = []
  buildingId: number = 0

  getApartments(): void {
    this.apartmentService.getApartments().subscribe(response => this.apartments = response.body)
  }

  getByBuilding(buildingId: number): void {
    this.apartmentService.getByBuilding(buildingId).subscribe(response => this.apartments = response.body)
  }

  delete(apartment: Apartment): void {
    this.apartmentService.deleteApartment(apartment).subscribe(response => {
      if (response.status == 200) {
        this.apartments = this.apartments.filter(el => !(el.id == response.body.id))
      }
    });
  }

  // When 201 response is received, adds the model to the model list.
  add(postform: FormGroupDirective): void {    
    this.apartmentService.addApartment(this.apartmentForm.value).subscribe(response => {
      if (response.status == 201) {
        this.apartments.push(response.body);
    // Return form to empty
    postform.resetForm();
      } 
    });
  }

  handleFormSubmission( buttonClicked: string,
    postform: FormGroupDirective,
    apartment?: Apartment): void{


    if (buttonClicked == "add"){
      this.add(postform);
    } else if (buttonClicked == "edit"){

      postform.form.get("id")?.setValue(apartment?.id);

      console.log("Form1:", postform.form.get("buildingId")?.value);
      this.edit(postform);

      // Close form
      this.selectedApartment = undefined;
    }
  }

  edit(postform: FormGroupDirective): void {

    // Enable these components of the form so form can be submitted.
    this.apartmentForm.get("number")?.enable();

    // Edited address is only necessary so that the add address portion 
    // of the form isn't populated with existing address when editing.
    let editedNumber: string = this.apartmentForm.get("editnumber")?.value;
    postform.form.get("number")?.setValue(editedNumber);

    this.apartmentService.editApartment(postform.value).subscribe(response => {
      if (response.status == 200) {
        this.apartments = this.apartments.filter(el => !(el.id == response.body.id))
        this.apartments.push(response.body);
      } else {
        // Do nothing
      }
    });

    // Return form to empty and disable editing fields again
    this.apartmentForm.get("editnumber")?.disable();
    postform.resetForm();
  }

  
  selectForEditing(apartment: Apartment): void {

    // Disable inputs related to adding when user is editing
    this.apartmentForm.get("number")?.disable();

    // Edited number is only necessary so that the add address portion 
    // of the form isn't populated with existing address when editing.
    this.apartmentForm.get("editnumber")?.enable();

    this.apartmentForm.get("editnumber")?.setValue(apartment.number);

    this.selectedApartment = apartment;
  }

  // Function is called for each apartment squared
  // The page works as intended but can be fixed later.
  checkForLatePayments(apartment: Apartment): boolean {
    return this.apartmentsWithLatePayments.some(
      el => el.id == apartment.id
    );
  }

}
