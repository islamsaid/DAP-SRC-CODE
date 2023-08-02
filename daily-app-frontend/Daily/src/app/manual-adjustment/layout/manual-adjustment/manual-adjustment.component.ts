import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { HeaderService } from 'src/app/core/services/header-service/header.service';
import { Tab } from 'src/app/shared/models/tab.model';
import { TableModel } from 'src/app/shared/models/table.model';
import { PrivilegesService } from 'src/app/shared/services/privileges-service/privileges.service';
import { PRIVILEGES } from 'src/app/shared/statics/privileges';
import { ValidationService } from 'src/app/validation/services/validation.service';
import { ManualAdjustment } from '../../models/manual-adjustment.model';
import { AdjustmentService } from '../../services/adjustment.service';

@Component({
  selector: 'app-manual-adjustment',
  templateUrl: './manual-adjustment.component.html',
  styleUrls: ['./manual-adjustment.component.scss'],
})
export class ManualAdjustmentComponent implements OnInit, OnDestroy {
  title: string = 'Manual Adjustment';
  tabs!: Tab[];
  adjustmentTable = new TableModel();
  dateSubscription!: Subscription;
  epochDate!: any;
  manualSubscr!: Subscription;
  headers = [
    { fieldId: 'ratePlan', title: 'Rate Plan' },
    { fieldId: 'opening', title: 'Opening', classStyle: 'green tab-style' },
    { fieldId: 'closing', title: 'Closing', classStyle: 'blue tab-style' },
    { fieldId: 'variance', title: 'Variance', classStyle: 'red tab-style' },
  ];
  tableData!: ManualAdjustment[];
  extraCols = [
    {
      fieldId: 'numberOfSubs',
      label: 'Num Of Subs',
      title: 'Export as Excel',
      show: true,
      type: 'input',
      inputType: 'number',
    },
    {
      fieldId: 'trxTypeKey',
      title: 'search',
      type: 'dropdown',
      data: [],
      optionLabel: '',
      show: true,
      placeholder: 'Transaction Type',
    },
  ];
  enableUpdatePatch!: boolean;
  originalData!: any[];
  constructor(
    private headerService: HeaderService,
    private validationService: ValidationService,
    private adjustmentService: AdjustmentService,
    private privilegesService: PrivilegesService
  ) { }

  ngOnInit(): void {
    this.enableUpdatePatch = this.privilegesService.checkPrivileges(
      PRIVILEGES.MANUAL_ADJUSTMENT.UPDATE
    );
    this.validationService.retrieveTransactionTypes().subscribe((resp) => {
      this.extraCols[0].data = resp.payload.transactionModelList;
      this.extraCols[0].optionLabel = this.extraCols[1].optionLabel =
        'trxTypeName';
      this.extraCols[1].data = resp.payload.transactionModelList;
    });
    this.epochDate = localStorage.getItem('date');

    this.getData();
    this.dateSubscription = this.headerService.DateSubject.subscribe((date) => {
      this.epochDate = date;
      this.getData();
    });
  }

  fillTable(tableData: any) {
    this.originalData = JSON.parse(JSON.stringify(tableData));
    this.adjustmentTable.cols = this.headers;
    this.adjustmentTable.extracols = this.extraCols;
    this.adjustmentTable.pagination = true;
    this.adjustmentTable.name = 'manualAdjustment';
    this.adjustmentTable.data = tableData;
    this.adjustmentTable.lockFlag = false;
    this.adjustmentTable.editFlag = false;
    this.adjustmentTable.deleteFlag = false;
    this.adjustmentTable.hasSubmit = true;
    this.adjustmentTable.globalFilterFields = [
      'ratePlan',
      'opening',
      'closing',
      'variance',
    ];
  }
  getData() {
    this.manualSubscr = this.adjustmentService
      .getManualAdjustment(this.epochDate)
      .subscribe((resp) => {
        this.tableData = resp.payload;
        this.tableData?.map((item:any) => {
          item.numberOfSubs = -item.variance;
        });
        this.fillTable(this.tableData);
      });
  }
  ngOnDestroy(): void {
    this.manualSubscr.unsubscribe();
    this.dateSubscription.unsubscribe();
  }
}
