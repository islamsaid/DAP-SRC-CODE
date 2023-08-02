import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TransferAdjustmentComponent } from './layout/transfer-adjustment/transfer-adjustment.component';

const routes: Routes = [
  { path: '', redirectTo: 'adjustment', pathMatch: 'full' },
  { path: 'adjustment', component: TransferAdjustmentComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class TransferAdjustmentRoutingModule {}
