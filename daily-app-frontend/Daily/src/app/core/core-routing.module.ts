import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from '../authentication/guards/auth.guard';
import { PrivilegesGuard } from '../authentication/guards/privileges.guard';
import { PRIVILEGES } from '../shared/statics/privileges';
import { AccessDeniedComponent } from './layout/access-denied/access-denied.component';
import { MainLayoutComponent } from './layout/main-layout/main-layout.component';

const routes: Routes = [
  {
    path: '',
    loadChildren: () =>
      import('../authentication/authentication.module').then(
        (mod) => mod.AuthenticationModule
      ),
  },
  {
    path: 'daily',
    component: MainLayoutComponent,
    children: [
      { path: 'accessDenied', component: AccessDeniedComponent },
      {
        path: 'users',
        loadChildren: () =>
          import('../users/users.module').then((mod) => mod.UsersModule),
        canActivate: [PrivilegesGuard, AuthGuard],
        data: { privilege: PRIVILEGES.USERS_MANAGMENT.GET_ALL },
      },
      {
        path: 'profiles',
        loadChildren: () =>
          import('../profiles/profiles.module').then(
            (mod) => mod.ProfilesModule
          ),
        canActivate: [PrivilegesGuard, AuthGuard],
        data: { privilege: PRIVILEGES.PROFILES_MANAGMENT.GET_ALL },
      },
      {
        path: 'services',
        loadChildren: () =>
          import('../services-tariffs/services-tariffs.module').then(
            (mod) => mod.ServicesTariffsModule
          ),
        canActivate: [AuthGuard],
      },
      {
        path: 'rateplans',
        loadChildren: () =>
          import('../rateplans/rateplans.module').then(
            (mod) => mod.RateplansModule
          ),
        canActivate: [AuthGuard],
      },
      {
        path: 'prices',
        loadChildren: () =>
          import('../prices/prices.module').then((mod) => mod.PricesModule),
        canActivate: [AuthGuard],
      },
      {
        path: 'validation',
        loadChildren: () =>
          import('../validation/validation.module').then(
            (mod) => mod.ValidationModule
          ),
        canActivate: [PrivilegesGuard, AuthGuard],
        data: { privilege: PRIVILEGES.VALIDATION.GET_ALL },
      },
      {
        path: 'manual',
        loadChildren: () =>
          import('../manual-adjustment/manual-adjustment.module').then(
            (mod) => mod.ManualAdjustmentModule
          ),
        canActivate: [PrivilegesGuard, AuthGuard],
        data: { privilege: PRIVILEGES.MANUAL_ADJUSTMENT.GET_ALL },
      },
      {
        path: 'transfer',
        loadChildren: () =>
          import('../transfer-adjustment/transfer-adjustment.module').then(
            (mod) => mod.TransferAdjustmentModule
          ),
        canActivate: [PrivilegesGuard, AuthGuard],
        data: { privilege: PRIVILEGES.TRANSFER_ADJUSTMENT.GET_ALL },
      },
      {
        path: 'home',
        loadChildren: () =>
          import('../home-page/home-page.module').then(
            (mod) => mod.HomePageModule
          ),
        canActivate: [AuthGuard],
      },
      {
        path: 'footprint',
        loadChildren: () =>
          import('../footprint-logs/footprint-logs.module').then(
            (mod) => mod.FootprintLogsModule
          ),
        canActivate: [PrivilegesGuard, AuthGuard],
        data: { privilege: PRIVILEGES.LOGS.GET_ALL },
      },
      {
        path: 'systemConfig',
        loadChildren: () =>
          import('../config-system/config-system.module').then(
            (mod) => mod.ConfigSystemModule
          ),
        canActivate: [PrivilegesGuard, AuthGuard],
        data: { privilege: PRIVILEGES.CONFIGRATION.GET_ALL },
      },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class CoreRoutingModule {}
