import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RateplansRoutingModule } from './rateplans-routing.module';
import { RateplansLayoutComponent } from './layout/rateplans-layout/rateplans-layout.component';
import { RateplansListComponent } from './components/rateplans-list/rateplans-list.component';
import { SharedModule } from '../shared/shared.module';
import { RateplanGroupsListComponent } from './components/rateplan-groups-list/rateplan-groups-list.component';
import { ManageRateplanGroupComponent } from './components/manage-rateplan-group/manage-rateplan-group.component';

@NgModule({
  declarations: [RateplansLayoutComponent, RateplansListComponent, RateplanGroupsListComponent, ManageRateplanGroupComponent],
  imports: [CommonModule, RateplansRoutingModule, SharedModule],
})
export class RateplansModule {}
