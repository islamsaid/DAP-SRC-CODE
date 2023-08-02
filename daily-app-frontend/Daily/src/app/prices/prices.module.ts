import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PricesRoutingModule } from './prices-routing.module';
import { PricesLayoutComponent } from './layout/prices-layout/prices-layout.component';
import { PricesListComponent } from './components/prices-list/prices-list.component';
import { PricesGroupComponent } from './components/prices-group/prices-group.component';
import { SharedModule } from '../shared/shared.module';
import { CreatePgGroupsComponent } from './components/create-pg-groups/create-pg-groups.component';

@NgModule({
  declarations: [
    PricesLayoutComponent,
    PricesListComponent,
    PricesGroupComponent,
    CreatePgGroupsComponent,
  ],
  imports: [CommonModule, PricesRoutingModule, SharedModule],
})
export class PricesModule {}
