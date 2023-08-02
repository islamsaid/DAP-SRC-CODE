import {
  Component,
  Input,
  OnChanges,
  OnInit,
  SimpleChanges,
} from '@angular/core';
import { MenuItem } from 'primeng/api';
import { PrivilegesService } from 'src/app/shared/services/privileges-service/privileges.service';
import { PRIVILEGES } from 'src/app/shared/statics/privileges';

@Component({
  selector: 'app-new-services',
  templateUrl: './new-services.component.html',
  styleUrls: ['./new-services.component.scss'],
})
export class NewServicesComponent implements OnInit, OnChanges {
  @Input('tarrifsModels') tarrifsModels: any;
  @Input('serviceClasses') serviceClasses: any;
  items!: MenuItem[];
  activeItem!: MenuItem;
  data: any[] = [];
  viewClasses!: boolean;
  viewTariffs!: boolean;
  pageSize = 3;
  constructor(private privilegesService: PrivilegesService) {}

  ngOnInit(): void {
    this.viewClasses = this.privilegesService.checkPrivileges(
      PRIVILEGES.SERVICES_CLASSES.GET_ALL
    );
    this.viewTariffs = this.privilegesService.checkPrivileges(
      PRIVILEGES.SERVICES_TARIFFS.GET_ALL
    );
    this.items = [
      {
        label: 'Service Classes',
        command: (event) => {
          this.activeItem = this.items[0];
          this.onSubmit();
        },
        visible: this.viewClasses,
      },
      {
        label: 'Tariffs',
        command: (event) => {
          this.activeItem = this.items[1];
          this.onSubmit();
        },
        visible: this.viewTariffs,
      },
    ];
    this.activeItem = this.items[0];
    this.onSubmit();
  }
  onSubmit() {
    if (this.activeItem.label === 'Service Classes' && this.viewClasses) {
      this.data = this.serviceClasses;
    }
    if (this.activeItem.label === 'Tariffs' && this.viewTariffs) {
      this.data = this.tarrifsModels;
    }
  }
  ngOnChanges(changes: SimpleChanges): void {
    this.items = [
      {
        label: 'Service Classes',
        command: (event) => {
          this.activeItem = this.items[0];
          this.onSubmit();
        },
        visible: this.viewClasses,
      },
      {
        label: 'Tariffs',
        command: (event) => {
          this.activeItem = this.items[1];
          this.onSubmit();
        },
        visible: this.viewTariffs,
      },
    ];
    this.activeItem = this.items[0];
    this.onSubmit();
  }
}
