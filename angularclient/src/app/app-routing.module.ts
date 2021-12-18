import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BuildingsComponent } from './buildings/buildings.component';
import { ApartmentsComponent } from './apartments/apartments.component';
import { PaymentsComponent } from './payments/payments.component';

const routes: Routes = [
  { path: "", component: BuildingsComponent},
  { path: "byBuilding/:id", component: ApartmentsComponent},
  { path: "apartments", component: ApartmentsComponent},
  { path: "byApartment/:id", component: PaymentsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
