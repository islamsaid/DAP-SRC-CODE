import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { CalendarModule } from 'primeng/calendar';
import { BadgeModule } from 'primeng/badge';
import { TableComponent } from './components/table/table.component';
import { TableModule } from 'primeng/table';
import { MultiSelectModule } from 'primeng/multiselect';
import { DropdownModule } from 'primeng/dropdown';
import { InputTextModule } from 'primeng/inputtext';
import { PickListModule } from 'primeng/picklist';
import { TabMenuModule } from 'primeng/tabmenu';
import { InputSwitchModule } from 'primeng/inputswitch';
import { DialogComponent } from './components/dialog/dialog.component';
import { DialogModule } from 'primeng/dialog';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ConfirmationService } from 'primeng/api';
import { ServicesListComponent } from './components/services-list/services-list.component';
import { ServiceFormComponent } from './components/service-form/service-form.component';
import { CheckboxModule } from 'primeng/checkbox';
import { ColorfulHeaderComponent } from './components/colorful-header/colorful-header.component';
import { ChartModule } from 'primeng/chart';
import { OverlayPanelModule } from 'primeng/overlaypanel';
import { AccordionModule } from 'primeng/accordion';
import { ListboxModule } from 'primeng/listbox';
import { ExportExcelService } from './services/exportExcel-service/export-excel.service';
import { ClipboardModule } from 'ngx-clipboard';


@NgModule({
  declarations: [
    TableComponent,
    DialogComponent,
    ServicesListComponent,
    ServiceFormComponent,
    ColorfulHeaderComponent,
  ],
  imports: [
    CommonModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    ButtonModule,
    CalendarModule,
    BadgeModule,
    TableModule,
    MultiSelectModule,
    DropdownModule,
    PickListModule,
    InputTextModule,
    TabMenuModule,
    InputSwitchModule,
    DialogModule,
    ConfirmDialogModule,
    CheckboxModule,
    ChartModule,
    OverlayPanelModule,
    AccordionModule,
    ListboxModule,
    ClipboardModule
  ],
  exports: [
    FormsModule,
    ReactiveFormsModule,
    ButtonModule,
    CalendarModule,
    BadgeModule,
    TableModule,
    TableComponent,
    DialogComponent,
    MultiSelectModule,
    DropdownModule,
    PickListModule,
    InputTextModule,
    TabMenuModule,
    InputSwitchModule,
    DialogModule,
    ConfirmDialogModule,
    ServicesListComponent,
    ServiceFormComponent,
    CheckboxModule,
    ColorfulHeaderComponent,
    ChartModule,
    OverlayPanelModule,
    AccordionModule,
    ListboxModule,
    ClipboardModule
  ],
  providers: [ConfirmationService,ExportExcelService],
})
export class SharedModule {}
