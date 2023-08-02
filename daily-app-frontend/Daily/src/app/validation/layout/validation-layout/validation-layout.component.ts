import { Component, OnInit } from '@angular/core';
import { HeaderService } from 'src/app/core/services/header-service/header.service';
import { Tab } from 'src/app/shared/models/tab.model';
import { PrivilegesService } from 'src/app/shared/services/privileges-service/privileges.service';
import { PRIVILEGES } from 'src/app/shared/statics/privileges';
import { ValidationService } from '../../services/validation.service';

@Component({
  selector: 'app-validation-layout',
  templateUrl: './validation-layout.component.html',
  styleUrls: ['./validation-layout.component.scss'],
})
export class ValidationLayoutComponent implements OnInit {
  tabs!: Tab[];
  epochDate: any = Date.parse(this.headerService.calendarDate);
  showBalance!: boolean;

  constructor(
    private validationService: ValidationService,
    private headerService: HeaderService,
    private privilegesService: PrivilegesService
  ) {}

  ngOnInit(): void {
    this.showBalance = this.privilegesService.checkPrivileges(
      PRIVILEGES.VALIDATION.BALANCE
    );
    this.epochDate = localStorage.getItem('date');

    this.getValidationBalance();
    this.headerService.DateSubject.subscribe((date) => {
      this.epochDate = date;
      this.getValidationBalance();
    });
  }
  getValidationBalance() {
    this.validationService.retrieveBalance(this.epochDate).subscribe((resp) => {
      console.log(resp.payload);
      this.tabs = [
        {
          name: 'Opening',
          count: resp.payload.opening,
          icon: 'icon-timer',
          classStyle: 'green',
        },
        {
          name: 'Closing',
          count: resp.payload.closing,
          icon: 'icon-timer-off',
          classStyle: 'blue',
        },
        {
          name: 'Total Variance',
          count: resp.payload.variance,
          icon: 'icon-chart',
          classStyle: 'red',
        },
      ];
    });
  }
}
