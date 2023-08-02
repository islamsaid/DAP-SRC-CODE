import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ManualAdjustmentComponent } from './layout/manual-adjustment/manual-adjustment.component';

const routes: Routes = [
  { path: '', redirectTo: 'adjustment', pathMatch: 'full' },
  { path: 'adjustment', component: ManualAdjustmentComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ManualAdjustmentRoutingModule { }
