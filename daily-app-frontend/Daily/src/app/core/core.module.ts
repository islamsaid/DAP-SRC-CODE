import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CoreRoutingModule } from './core-routing.module';
import { HeaderComponent } from './components/header/header.component';
import { MainLayoutComponent } from './layout/main-layout/main-layout.component';
import { SharedModule } from '../shared/shared.module';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { HttpRequestInterceptor } from './interceptors/http-request.interceptor';
import { SidebarComponent } from './components/sidebar/sidebar.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AccessDeniedComponent } from './layout/access-denied/access-denied.component';
import { NgxLoadingModule, ngxLoadingAnimationTypes } from 'ngx-loading';

const httpRequestInterceptor = [
  { provide: HTTP_INTERCEPTORS, useClass: HttpRequestInterceptor, multi: true },
];
@NgModule({
  declarations: [
    HeaderComponent,
    MainLayoutComponent,
    SidebarComponent,
    AccessDeniedComponent,
  ],
  imports: [
    CommonModule,
    CoreRoutingModule,
    SharedModule,
    BrowserAnimationsModule,
    NgxLoadingModule.forRoot({
      animationType: ngxLoadingAnimationTypes.doubleBounce,
    }),
  ],
  providers: [httpRequestInterceptor],
})
export class CoreModule {}
