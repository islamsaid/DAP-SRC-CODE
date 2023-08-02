import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoggedGuard } from './guards/logged.guard';
import { LoginLayoutComponent } from './layout/login-layout/login-layout.component';

const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full',canActivate: [LoggedGuard] },
  { path: 'login', component: LoginLayoutComponent ,canActivate: [LoggedGuard] }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AuthenticationRoutingModule { }
