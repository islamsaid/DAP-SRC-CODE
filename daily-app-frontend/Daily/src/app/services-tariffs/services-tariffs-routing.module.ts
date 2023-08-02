import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PrivilegesGuard } from '../authentication/guards/privileges.guard';
import { PRIVILEGES } from '../shared/statics/privileges';
import { ClassesComponent } from './components/classes/classes.component';
import { TariffsComponent } from './components/tariffs/tariffs.component';
import { ServicesLayoutComponent } from './layout/services-layout/services-layout.component';

const routes: Routes = [
  {path:'',component:ServicesLayoutComponent,children:[
    {path:'' , redirectTo:'classes',pathMatch:'full'},
    {path:'classes',component:ClassesComponent,canActivate: [PrivilegesGuard],
    data: { privilege: PRIVILEGES.SERVICES_CLASSES.GET_ALL },},
    {path:'tariffs',component:TariffsComponent,
    canActivate: [PrivilegesGuard],
    data: {privilege: PRIVILEGES.SERVICES_TARIFFS.GET_ALL }},
  ]}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ServicesTariffsRoutingModule { }
