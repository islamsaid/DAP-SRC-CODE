import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ValidationComponent } from './components/validation/validation.component';
import { ValidationLayoutComponent } from './layout/validation-layout/validation-layout.component';

const routes: Routes = [
  { path: '', redirectTo: 'list', pathMatch: 'full' },
  { path: 'list', component:ValidationLayoutComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ValidationRoutingModule { }
