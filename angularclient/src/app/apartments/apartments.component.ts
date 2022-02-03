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
    this.route.url.subscribe(p => {
      if(p[0].path === "byBuilding"){
        // Go to apartments by building
        this.route.params.subscribe( params => this.getByBuilding(params['id']))
      } else {
        // Go to all apartments
        this.getApartments()
      }

    })

    let date = new Date()
    this.apartmentService.getApartmentsWithLatePayments(date.getMonth() + 1).subscribe(
      response => {
        this.apartmentsWithLatePayments = response.body;
        console.log(response.body);
      });
  }

  apartmentForm = new FormGroup ({
    number: new FormControl("",[
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
      } else {
        // Do nothing
      }
    });
  }

  // When 201 response is received, adds the model to the model list.
  add(postform: FormGroupDirective): void {

    // Get buildingId for the page from the url.  There may be a more efficient way to do this
    this.route.params.subscribe( params => this.buildingId = params['id']);
    this.apartmentForm.get("buildingId")?.setValue(this.buildingId);
    
    this.apartmentService.addApartment(this.apartmentForm.value).subscribe(response => {
      if (response.status == 201) {
        this.apartments.push(response.body);
    // Return form to empty
    postform.resetForm();
      } else {
        // Do nothing
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
      postform.form.get("buildingId")?.setValue(apartment?.buildingId);

      console.log("Form1:", postform.form.get("buildingId")?.value);
      this.edit(postform);

      // Close form
      this.selectedApartment = undefined;
    }
  }

  edit(postform: FormGroupDirective): void {

    // Edited address is only necessary so that the add address portion 
    // of the form isn't populated with existing address when editing.
    let editedNumber: string = this.apartmentForm.get("editnumber")?.value;
    postform.form.get("number")?.setValue(editedNumber);
    postform.form.removeControl("editnumber");

    console.log("Form:", postform.form.get("buildingId")?.value);

    this.apartmentService.editApartment(postform.value).subscribe(response => {
      if (response.status == 200) {
        this.apartments = this.apartments.filter(el => !(el.id == response.body.id))
        this.apartments.push(response.body);
        
      } else {
        console.log("Something went wrong...")
        // Do nothing
      }
    });

    // Return form to empty
    postform.resetForm();

    // postform.form.get("address")?.setValue("");

  }

  
  selectForEditing(apartment: Apartment): void {

    // Edited address is only necessary so that the add address portion 
    // of the form isn't populated with existing address when editing.
    this.apartmentForm.setControl("editnumber", new FormControl(apartment.number))
    this.apartmentForm.get("editnumber")?.setValidators( 
      [Validators.required])
    // this.buildingForm.get("editaddress")?.setValue(building.address)
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
