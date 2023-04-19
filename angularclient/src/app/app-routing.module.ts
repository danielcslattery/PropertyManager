import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BuildingsComponent } from './buildings/buildings.component';
import { ApartmentsComponent } from './apartments/apartments.component';
import { PaymentsComponent } from './payments/payments.component';

const routes: Routes = [
  { path: '', redirectTo: '/buildings', pathMatch: 'full' },
  { path: 'buildings', component: BuildingsComponent },
  { path: 'buildings/:buildingId/apartments', component: ApartmentsComponent },
  { path: 'buildings/:buildingId/apartments/:apartmentId/payments', component: PaymentsComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
