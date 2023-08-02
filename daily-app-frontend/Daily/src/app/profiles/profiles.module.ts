import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ProfilesRoutingModule } from './profiles-routing.module';
import { ProfilesManagementComponent } from './layouts/profiles-management/profiles-management.component';
import { SharedModule } from '../shared/shared.module';
import { ProfilesListComponent } from './components/profiles-list/profiles-list.component';
import { AddProfileComponent } from './components/add-profile/add-profile.component';


@NgModule({
  declarations: [
    ProfilesManagementComponent,
    ProfilesListComponent,
    AddProfileComponent
  ],
  imports: [
    CommonModule,
    ProfilesRoutingModule,
    SharedModule
  ]
})
export class ProfilesModule { }
