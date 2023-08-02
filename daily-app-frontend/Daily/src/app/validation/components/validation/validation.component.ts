import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { HeaderService } from 'src/app/core/services/header-service/header.service';
import { TableModel } from 'src/app/shared/models/table.model';
import { PrivilegesService } from 'src/app/shared/services/privileges-service/privileges.service';
import { PRIVILEGES } from 'src/app/shared/statics/privileges';
import { ValidationService } from '../../services/validation.service';

@Component({
  selector: 'app-validation',
  templateUrl: './validation.component.html',
  styleUrls: ['./validation.component.scss'],
})
export class ValidationComponent implements OnInit {
  validationTable = new TableModel();
  DateSubscriber!: Subscription;
  epochDate!: any;
  headers = [
    { fieldId: 'ratePlan', title: 'Rate Plan' },
    { fieldId: 'dwhStatus', title: 'Status' },
    { fieldId: 'opening', title: 'Opening', classStyle: 'green tab-style' },
    { fieldId: 'closing', title: 'Closing', classStyle: 'blue tab-style' },
    { fieldId: 'variance', title: 'Variance', classStyle: 'red tab-style' },
  ];
  tableData = [
    {
      ratePlan: 'rateplan',
      dwhStatus: 'valid',
      opening: 155,
      closing: 55,
      variance: 10,
      inSubs: 7,
      outSubs: 10,
    },
  ];
  extraCols = [
    {
      fieldId: 'inSubs',
      title: 'Export as Excel',
      fieldTitle: 'In_Subs',
      show: true,
      type: 'dropdown',
      data: [],
      optionLabel: '',
      placeholder: 'In_Subs',
    },
    {
      fieldId: 'outSubs',
      fieldTitle: 'Out_Subs',
      title: 'search',
      type: 'dropdown',
      data: [],
      optionLabel: '',
      show: true,
      placeholder: 'Out_Subs',
    },
  ];
  enableUpdatePatch!: boolean;
  originalData!: any[];
  constructor(
    private validationService: ValidationService,
    private headerService: HeaderService,
    private privilegesService: PrivilegesService
  ) {}

  ngOnInit(): void {
    this.enableUpdatePatch = this.privilegesService.checkPrivileges(
      PRIVILEGES.VALIDATION.UPDATE
    );
    this.epochDate = localStorage.getItem('date');
    this.validationService.retrieveTransactionTypes().subscribe((resp) => {
      this.extraCols[0].data = resp.payload.transactionModelList;
      this.extraCols[0].optionLabel = this.extraCols[1].optionLabel =
        'trxTypeName';
      this.extraCols[1].data = resp.payload.transactionModelList;
    });
    this.validationService
      .getvalidationList(this.epochDate)
      .subscribe((resp) => {
        if (resp.payload) {
          this.tableData = resp.payload?.list;
          this.tableData.map((item) => {
            item.variance = item.closing - item.opening;
          });
          this.fillTable(this.tableData);
       }
      });
    console.log(this.epochDate);
    this.DateSubscriber = this.headerService.DateSubject.subscribe((date) => {
      this.epochDate = date;
      this.validationService
        .getvalidationList(this.epochDate)
        .subscribe((resp) => {
          if (resp.payload) {
            this.tableData = resp.payload?.list;
            this.tableData.map((item) => {
              item.variance = item.closing - item.opening;
            });
            console.log(this.tableData);
            this.fillTable(this.tableData);
          }
        });
    });
  }

  fillTable(tableData: any) {
    this.originalData = JSON.parse(JSON.stringify(tableData));
    this.validationTable.cols = this.headers;
    this.validationTable.extracols = this.extraCols;
    this.validationTable.pagination = true;
    this.validationTable.name = 'validation';
    this.validationTable.data = tableData;
    this.validationTable.lockFlag = false;
    this.validationTable.editFlag = false;
    this.validationTable.deleteFlag = false;
    this.validationTable.hasSubmit = true;
    this.validationTable.globalFilterFields = [
      'ratePlan',
      'dwhStatus',
      'opening',
      'closing',
      'variance',
    ];
  }
  ngOnDestroy(): void {
    this.DateSubscriber.unsubscribe();
  }
}
