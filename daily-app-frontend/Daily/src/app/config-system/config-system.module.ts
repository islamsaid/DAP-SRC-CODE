import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ConfigListComponent } from './components/config-list/config-list.component';
import { ConfigSystemLayoutComponent } from './layout/config-system-layout/config-system-layout.component';
import { ConfigSystemRoutingModule } from './config-system-routing.module';
import { SharedModule } from '../shared/shared.module';



@NgModule({
  declarations: [
    ConfigListComponent,
    ConfigSystemLayoutComponent
  ],
  imports: [
    CommonModule,SharedModule,ConfigSystemRoutingModule
  ]
})
export class ConfigSystemModule { }
