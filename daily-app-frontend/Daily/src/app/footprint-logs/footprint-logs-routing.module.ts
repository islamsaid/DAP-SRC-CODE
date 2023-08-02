import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FootprintComponent } from './layout/footprint/footprint.component';

const routes: Routes = [
  {path:'',component:FootprintComponent}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FootprintLogsRoutingModule { }
