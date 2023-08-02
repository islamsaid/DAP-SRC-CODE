import { NgModule } from '@angular/core';
import { ExtraOptions, RouterModule, Routes } from '@angular/router';

const routes: Routes = [];
const extraOptions: ExtraOptions
  = { enableTracing: false, onSameUrlNavigation: 'reload', useHash: true };

@NgModule({
  imports: [RouterModule.forRoot(routes, extraOptions)],
  exports: [RouterModule],
})
export class AppRoutingModule { }
