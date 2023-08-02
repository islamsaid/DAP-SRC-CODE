import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { HeaderService } from 'src/app/core/services/header-service/header.service';
import { RateplansService } from 'src/app/rateplans/services/rateplans.service';
import { TableModel } from 'src/app/shared/models/table.model';
import { FootprintService } from 'src/app/shared/services/footprint-service/footprint.service';
import { PrivilegesService } from 'src/app/shared/services/privileges-service/privileges.service';
import { PRIVILEGES } from 'src/app/shared/statics/privileges';
import { TransferAdjustmentService } from '../../services/transfer-adjustment.service';

@Component({
  selector: 'app-transfer-adjustment',
  templateUrl: './transfer-adjustment.component.html',
  styleUrls: ['./transfer-adjustment.component.scss'],
})
export class TransferAdjustmentComponent implements OnInit {
  epochDate!: any;
  transferTable = new TableModel();
  headers = [
    { fieldId: 'trxTypeName', title: 'Transaction Type Name', classStyle: '' },
    {
      fieldId: 'subs',
      title: 'Currrent Subs',
      classStyle: 'blue tab-style width-num',
    },
  ];

  extraCols = [
    {
      fieldId: 'adjust',
      title: 'Export as Excel',
      fieldTitle: 'Adjusts',
      show: true,
      data: [],
      placeholder: 'Adjusts',
      type: 'input',
      inputType: 'number',
    },
    {
      fieldId: 'ratePlan',
      title: 'search',
      fieldTitle: 'Rate plan',
      show: true,
      type: 'dropdown',
      data: [],
      optionLabel: '',
      placeholder: 'Rate plan',
    },
  ];
  ratePlanSubscription!: Subscription;
  transferSubscription!: Subscription;
  dateSubscription!: Subscription;
  enableUpdatePatch!: boolean;

  constructor(
    private headerService: HeaderService,
    private transferService: TransferAdjustmentService,
    private ratePlansService: RateplansService,
    private privilegesService: PrivilegesService,
    private footprintService: FootprintService
  ) {}

  ngOnInit(): void {
    this.enableUpdatePatch = this.privilegesService.checkPrivileges(
      PRIVILEGES.TRANSFER_ADJUSTMENT.UPDATE
    );
    this.epochDate = localStorage.getItem('date');
    this.getTransferAgjustmentList(this.epochDate);
    this.getRatePlans();
    this.dateSubscription = this.headerService.DateSubject.subscribe((date) => {
      this.epochDate = date;
      this.getTransferAgjustmentList(this.epochDate);
    });
  }

  getRatePlans() {
    this.ratePlanSubscription = this.ratePlansService
      .getAllRatePlans()
      .subscribe((response) => {
        console.log(response);
        this.extraCols[1].data = response.payload;
        this.extraCols[1].optionLabel = 'ratePlan';
      });
  }

  getTransferAgjustmentList(date: number) {
    this.transferSubscription = this.transferService
      .getTransferAdjustment(date)
      .subscribe((response) => {
        let data = response?.payload
          ? response.payload['getTransferAdjustmentList']
          : [];
        this.fillTable(data);
      });
  }

  fillTable(tableData: any) {
    this.transferTable.cols = this.headers;
    this.transferTable.extracols = this.extraCols;
    this.transferTable.pagination = true;
    this.transferTable.name = 'transferAdjustment';
    this.transferTable.data = tableData;
    this.transferTable.lockFlag = false;
    this.transferTable.editFlag = false;
    this.transferTable.deleteFlag = false;
    this.transferTable.hasSubmit = true;
    this.transferTable.globalFilterFields = ['trxTypeName', 'subs'];
  }
  ngOnDestroy(): void {
    this.dateSubscription?.unsubscribe();
    this.ratePlanSubscription?.unsubscribe();
    this.transferSubscription?.unsubscribe();
  }
}
