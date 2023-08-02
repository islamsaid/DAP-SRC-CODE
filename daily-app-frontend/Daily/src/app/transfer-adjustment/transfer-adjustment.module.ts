import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TransferAdjustmentRoutingModule } from './transfer-adjustment-routing.module';
import { TransferAdjustmentComponent } from './layout/transfer-adjustment/transfer-adjustment.component';
import { SharedModule } from '../shared/shared.module';


@NgModule({
  declarations: [
    TransferAdjustmentComponent
  ],
  imports: [
    CommonModule,
    TransferAdjustmentRoutingModule,
    SharedModule
  ]
})
export class TransferAdjustmentModule { }
