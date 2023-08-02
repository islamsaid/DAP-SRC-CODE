import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ManualAdjustmentRoutingModule } from './manual-adjustment-routing.module';
import { ManualAdjustmentComponent } from './layout/manual-adjustment/manual-adjustment.component';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  declarations: [ManualAdjustmentComponent],
  imports: [CommonModule, ManualAdjustmentRoutingModule, SharedModule],
})
export class ManualAdjustmentModule {}
