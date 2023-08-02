import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PrivilegesGuard } from '../authentication/guards/privileges.guard';
import { PRIVILEGES } from '../shared/statics/privileges';
import { AddProfileComponent } from './components/add-profile/add-profile.component';
import { ProfilesListComponent } from './components/profiles-list/profiles-list.component';
import { ProfilesManagementComponent } from './layouts/profiles-management/profiles-management.component';

const routes: Routes = [
  {
    path: '',
    component: ProfilesManagementComponent,
    children: [
      { path: '', redirectTo: 'list', pathMatch: 'full' },
      { path: 'list', component: ProfilesListComponent },
      {
        path: 'add',
        component: AddProfileComponent,
        canActivate: [PrivilegesGuard],
        data: { privilege: PRIVILEGES.PROFILES_MANAGMENT.ADD },
      },
      {
        path: 'edit/:id',
        component: AddProfileComponent,
        canActivate: [PrivilegesGuard],
        data: { privilege: PRIVILEGES.PROFILES_MANAGMENT.UPDATE },
      },
      {
        path: 'view/:id',
        component: AddProfileComponent,
        canActivate: [PrivilegesGuard],
        data: { privilege: PRIVILEGES.PROFILES_MANAGMENT.GET_BY_ID },
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ProfilesRoutingModule {}
