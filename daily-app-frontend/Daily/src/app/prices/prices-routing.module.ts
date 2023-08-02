import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PrivilegesGuard } from '../authentication/guards/privileges.guard';
import { PRIVILEGES } from '../shared/statics/privileges';
import { CreatePgGroupsComponent } from './components/create-pg-groups/create-pg-groups.component';
import { PricesGroupComponent } from './components/prices-group/prices-group.component';
import { PricesListComponent } from './components/prices-list/prices-list.component';
import { PricesLayoutComponent } from './layout/prices-layout/prices-layout.component';

const routes: Routes = [
  {
    path: '',
    component: PricesLayoutComponent,
    children: [
      { path: '', redirectTo: 'groups', pathMatch: 'full' },
      {
        path: 'groups',
        component: PricesListComponent,
        canActivate: [PrivilegesGuard],
        data: { privilege: PRIVILEGES.PRICE_GROUP.GET_ALL },
      },
      {
        path: 'pgGroups',
        component: PricesGroupComponent,
        canActivate: [PrivilegesGuard],
        data: { privilege: PRIVILEGES.PG_GROUP.GET_ALL },
      },
      {
        path: 'add',
        component: CreatePgGroupsComponent,
        canActivate: [PrivilegesGuard],
        data: { privilege: PRIVILEGES.PG_GROUP.ADD },
      },
      {
        path: 'edit/:id',
        component: CreatePgGroupsComponent,
        canActivate: [PrivilegesGuard],
        data: { privilege: PRIVILEGES.PG_GROUP.UPDATE },
      },
      {
        path: 'view/:id',
        component: CreatePgGroupsComponent,
        canActivate: [PrivilegesGuard],
        data: { privilege: PRIVILEGES.PG_GROUP.GET_BY_ID },
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PricesRoutingModule {}
