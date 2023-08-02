import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { FootprintLogsRoutingModule } from './footprint-logs-routing.module';
import { FootprintComponent } from './layout/footprint/footprint.component';
import { SharedModule } from '../shared/shared.module';


@NgModule({
  declarations: [
    FootprintComponent
  ],
  imports: [
    CommonModule,
    FootprintLogsRoutingModule,
    SharedModule
  ]
})
export class FootprintLogsModule { }
