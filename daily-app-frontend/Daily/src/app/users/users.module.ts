import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UsersRoutingModule } from './users-routing.module';
import { UsersListComponent } from './components/users-list/users-list.component';
import { UsersHeaderComponent } from './components/users-header/users-header.component';
import { UsersLayoutComponent } from './layout/users-layout/users-layout.component';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  declarations: [UsersListComponent, UsersHeaderComponent, UsersLayoutComponent],
  imports: [CommonModule, UsersRoutingModule, SharedModule],
})
export class UsersModule { }
