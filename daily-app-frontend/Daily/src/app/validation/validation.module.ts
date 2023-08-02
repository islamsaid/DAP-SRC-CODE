import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ValidationRoutingModule } from './validation-routing.module';
import { ValidationComponent } from './components/validation/validation.component';
import { SharedModule } from '../shared/shared.module';
import { ValidationLayoutComponent } from './layout/validation-layout/validation-layout.component';


@NgModule({
  declarations: [
    ValidationComponent,
    ValidationLayoutComponent
  ],
  imports: [
    CommonModule,
    ValidationRoutingModule,
    SharedModule
  ]
})
export class ValidationModule { }
