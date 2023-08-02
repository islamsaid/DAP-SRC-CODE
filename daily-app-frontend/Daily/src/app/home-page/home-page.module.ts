import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeLayoutComponent } from './layout/home-layout/home-layout.component';
import { ChartBarComponent } from './components/chart-bar/chart-bar.component';
import { SharedModule } from '../shared/shared.module';
import { HomePageRoutingModule } from './home-page-routing.module';
import { RateplansNumbersComponent } from './components/rateplans-numbers/rateplans-numbers.component';
import { NewPriceGroupComponent } from './components/new-price-group/new-price-group.component';
import { NewServicesComponent } from './components/new-services/new-services.component';

@NgModule({
  declarations: [ HomeLayoutComponent, ChartBarComponent, RateplansNumbersComponent, NewPriceGroupComponent, NewServicesComponent],
  imports: [CommonModule, SharedModule,HomePageRoutingModule],
})
export class HomePageModule {}
