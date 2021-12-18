import { Component, OnInit } from '@angular/core';
import { ApartmentService } from '../services/apartment.service';
import { Apartment } from '../models/apartment';
import { ActivatedRoute, RouterState } from '@angular/router';

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

  }

  apartments: Apartment[] = []

  getApartments(): void {
    this.apartmentService.getApartments().subscribe(apartment => this.apartments = apartment)
  }

  getByBuilding(buildingId: number): void {
    this.apartmentService.getByBuilding(buildingId).subscribe(apartment => this.apartments = apartment)
  }

  delete(apartment: Apartment): void {
    this.apartmentService.deleteApartment(apartment);
  }


  //TODO: Change parameters of add()
  // add(addressStr: string): void {
  //   this.apartmentService.addApartment(addressStr);
  // }

}
