import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { RateplansLayoutComponent } from './layout/rateplans-layout/rateplans-layout.component';
import { RateplanGroupsListComponent } from './components/rateplan-groups-list/rateplan-groups-list.component';
import { RateplansListComponent } from './components/rateplans-list/rateplans-list.component';
import { ManageRateplanGroupComponent } from './components/manage-rateplan-group/manage-rateplan-group.component';
import { PrivilegesGuard } from '../authentication/guards/privileges.guard';
import { PRIVILEGES } from '../shared/statics/privileges';

const routes: Routes = [
  {
    path: '',
    component: RateplansLayoutComponent,
    children: [
      { path: '', redirectTo: 'rateplans', pathMatch: 'full' },
      { path: 'rateplans', component: RateplansListComponent,
      canActivate: [PrivilegesGuard],
      data: { privilege: PRIVILEGES.RATE_PLANS.GET_ALL }, },
      {
        path: 'rateplansGroups',
        component: RateplanGroupsListComponent,
        canActivate: [PrivilegesGuard],
        data: { privilege: PRIVILEGES.RATE_PLANS_GROUP.GET_ALL },
      },
      {
        path: 'rateplansGroups/add',
        component: ManageRateplanGroupComponent,
        canActivate: [PrivilegesGuard],
        data: { privilege: PRIVILEGES.RATE_PLANS_GROUP.ADD },
      },
      {
        path: 'rateplansGroups/edit/:id',
        component: ManageRateplanGroupComponent,
        canActivate: [PrivilegesGuard],
        data: { privilege: PRIVILEGES.RATE_PLANS_GROUP.UPDATE },
      },
      {
        path: 'rateplansGroups/view/:id',
        component: ManageRateplanGroupComponent,
        canActivate: [PrivilegesGuard],
        data: { privilege: PRIVILEGES.RATE_PLANS_GROUP.GET_BY_ID },
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class RateplansRoutingModule {}
