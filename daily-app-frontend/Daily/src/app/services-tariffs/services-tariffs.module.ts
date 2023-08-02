import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ServicesTariffsRoutingModule } from './services-tariffs-routing.module';
import { ServicesLayoutComponent } from './layout/services-layout/services-layout.component';
import { TariffsComponent } from './components/tariffs/tariffs.component';
import { SharedModule } from '../shared/shared.module';
import { ClassesComponent } from './components/classes/classes.component';

@NgModule({
  declarations: [
    ServicesLayoutComponent,
    TariffsComponent,
    ClassesComponent,
  ],
  imports: [CommonModule, ServicesTariffsRoutingModule, SharedModule],
})
export class ServicesTariffsModule {}
