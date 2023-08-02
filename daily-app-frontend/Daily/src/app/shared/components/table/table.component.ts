import {
  Component,
  ElementRef,
  HostListener,
  Injectable,
  Input,
  OnDestroy,
  OnInit,
  QueryList,
  ViewChild,
  ViewChildren,
} from '@angular/core';
import { Router } from '@angular/router';
import {
  ConfirmationService,
  Header,
  PrimeNGConfig,
  SortEvent,
} from 'primeng/api';
import { TableModel } from '../../models/table.model';
import { Table } from 'primeng/table';
import { UsersService } from 'src/app/users/services/users.service';
import { DialogData } from '../../models/dialog.model';
import { ProfilesService } from 'src/app/profiles/services/profiles.service';
import { Profile } from 'src/app/profiles/models/profile.model';
import { Subscription } from 'rxjs';
import { AggregationData } from 'src/app/validation/models/aggregation-data.model';
import { HeaderService } from 'src/app/core/services/header-service/header.service';
import { ValidationService } from 'src/app/validation/services/validation.service';
import { ManualAdjustment } from 'src/app/manual-adjustment/models/manual-adjustment.model';
import { AdjustmentService } from 'src/app/manual-adjustment/services/adjustment.service';
import { Transfer } from 'src/app/transfer-adjustment/models/transferAdjus.model';
import { TransferAdjustmentService } from 'src/app/transfer-adjustment/services/transfer-adjustment.service';
import { FootprintService } from '../../services/footprint-service/footprint.service';
import { ExportExcelService } from '../../services/exportExcel-service/export-excel.service';
import { User } from 'src/app/users/models/user';
import { ClipboardService } from 'ngx-clipboard';
import { Calc } from 'src/app/validation/models/calculation.model';

export enum KEY_CODE {
  UP_ARROW = 38,
  DOWN_ARROW = 40,
  RIGHT_ARROW = 39,
  LEFT_ARROW = 37,
  TAB_KEY = 9,
}
@Component({
  selector: 'app-table',
  templateUrl: './table.component.html',
  styleUrls: ['./table.component.scss'],
})
@Injectable()
export class TableComponent implements OnInit, OnDestroy {
  @Input() table!: TableModel;
  @ViewChild('dt') dt: Table | undefined;
  @Input() globalFilterFields!: string[];
  @Input('enableUpdate') enableUpdate!: boolean;
  @Input('enableUpdatePatch') enableUpdatePatch!: boolean;
  @Input('enableDelete') enableDelete!: boolean;
  @Input('viewDetails') viewDetails!: boolean;
  @Input('originalData') originalData!: any[];
  @ViewChildren('input') inputs!: QueryList<ElementRef>;
  @ViewChildren('dropdownField') dropdownFields!: QueryList<ElementRef>;
  loading: boolean = false;
  pageSize: number = 100;
  lockUser: string = 'Lock the ';
  unlockUser: string = 'Unlock the ';
  openDialog!: boolean;
  lck!: any;
  dialogConfig!: DialogData;
  profileSubscription!: Subscription;
  aggregationList: AggregationData[] = [];
  manualAdjustList: ManualAdjustment[] = [];
  transferAdjustList: Transfer[] = [];
  epochDate!: any;
  max!: boolean;
  targetRow!: number;
  transferSubscription!: Subscription;
  adjustNotValid!: boolean;
  rateplanNotValid!: boolean;
  editableRowId!: number;
  viewMode!: boolean;
  selectedData: any;
  prevSortStatus!: SortEvent | undefined; // to reset sort after third click
  constructor(
    private primengConfig: PrimeNGConfig,
    private usersService: UsersService,
    private profilesService: ProfilesService,
    private router: Router,
    private confirmationService: ConfirmationService,
    private headerService: HeaderService,
    private validationService: ValidationService,
    private manualAgjustService: AdjustmentService,
    private transferAdjustService: TransferAdjustmentService,
    private footPrintService: FootprintService,
    private exportExcelService: ExportExcelService,
    private clipboardApi: ClipboardService
  ) {}

  ngOnInit(): void {
    this.primengConfig.ripple = true;
    this.viewMode = this.viewDetails && !this.enableUpdate ? true : false;
  }
  openUserDialog(id?: number) {
    this.usersService.openUserDialog(id, this.viewMode);
    this.openDialog = this.usersService.openDialog;
    this.dialogConfig = this.usersService.dialogConfig;
  }
  applyFilterGlobal($event: any, stringVal: any) {
    this.dt!.filterGlobal(($event.target as HTMLInputElement).value, stringVal);
  }
  edit(id: number) {
    if (this.table.name === 'users') {
      this.openUserDialog(id);
    } else if (this.table.name === 'profiles') {
      this.profilesService.showSubject.next(false);
      let url = this.viewMode ? 'daily/profiles/view/' : 'daily/profiles/edit/';
      url = url + id;
      this.router.navigateByUrl(url);
    }
  }
  updateLockFlag(row: any) {
    if (this.table.name == 'users') {
      this.footPrintService.objectIdentifier = row.id;
      let oldUser: User = {
        lockFlag: this.lck == true ? 0 : 1,
      };
      this.footPrintService.handleOldValue(oldUser);
      let newUser: User = {
        lockFlag: this.lck == true ? 1 : 0,
      };
      this.footPrintService.handleNewValue(newUser);
      this.usersService.updateUser({
        name: row.name,
        userId: row.userId,
        username: row.username,
        lockFlag: this.lck == true ? 1 : 0,
        profileId: row.profileId,
      });
      this.lck = null;
    } else if (this.table.name == 'profiles') {
      let profile: Profile = row;
      profile.isActive = this.lck == true ? 1 : 0;
      this.footPrintService.objectIdentifier = row.id;
      let oldProfile: Profile = {
        isActive: this.lck == true ? 0 : 1,
      };
      this.footPrintService.handleOldValue(oldProfile);
      let newProfile: Profile = {
        isActive: this.lck == true ? 1 : 0,
      };
      this.footPrintService.handleNewValue(newProfile);
      this.profileSubscription = this.profilesService
        .manageProfile(profile, 'edit')
        .subscribe((resp) => {});
    }
  }
  delete(event: any, id: number) {
    this.confirmationService.confirm({
      target: event.target,
      message:
        this.table.name === 'users'
          ? 'Are you sure that you want to delete this user ?'
          : 'Are you sure that you want to delete this profile ?',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        //confirm action
        this.footPrintService.objectIdentifier = id;
        if (this.table.name === 'users') {
          this.usersService.deleteUser(id);
        }
        if (this.table.name === 'profiles') {
          this.profilesService.deleteProfile(id).subscribe((resp) => {
            this.profilesService.getProfilesList().subscribe((resp) => {
              this.table.data = resp['payload']['profilesList'];
            });
          });
        }
      },
      reject: () => {
        //reject action
      },
    });
  }
  getFlag(flag: any) {
    if (flag == 1) {
      this.lck = true;
    } else {
      this.lck = false;
    }
  }
  onTabInput(i: any) {
    console.log('e', i, this.inputs);
    // if(this.table.name  == 'manualAdjustment' || this.table.name == 'transferAdjustment'){
    this.inputs.map((el: any, index: number) => {
      if (i + 1 === index) {
        setTimeout(() => {
          el.nativeElement.focus();
        });
      }
    });
    // }
  }
  onTabEvent(event: any, i: number) {
    console.log('e', i, this.dropdownFields);
    if (
      this.table.name == 'manualAdjustment' ||
      this.table.name == 'transferAdjustment'
    ) {
      this.dropdownFields.map((el: any, index: number) => {
        if (i + 1 === index) {
          setTimeout(() => {
            el.focus();
          });
        }
      });
    }
    if (this.table.name == 'validation') {
      let focused: boolean = false;
      this.dropdownFields.map((el: any, index: number) => {
        if (event.id === el.id) {
          focused = true;
          return;
        }
        if (focused) {
          setTimeout(() => {
            el.focus();
          });
          focused = false;
        }
      });
    }
  }
  ChangeData(
    event: any,
    feild: string,
    type: string,
    data: any,
    rowIndex: number
  ) {
    console.log('event', event.value);
    this.epochDate = localStorage.getItem('date');
    if (this.table.name == 'validation') {
      if (feild == 'inSubs') {
        data.trxInSubsValue = +event.value?.value;
      } else if (feild == 'outSubs') {
        data.trxOutSubsValue = +event.value?.value;
      }
      let dataToCalculate: Calc = {
        trxInSubs: data.trxInSubsValue ? +data.trxInSubsValue : 0,
        trxOutSubs: data.trxOutSubsValue ? +data.trxOutSubsValue : 0,
        inSubs: +data['inSubs'],
        outSubs: +data['outSubs'],
      };
      this.calculationValidations(data.ratePlanKey, dataToCalculate);
      if (this.aggregationList.length) {
        let obj = this.aggregationList.find((item) => {
          return (
            item.ratePlanKey === data.ratePlanKey &&
            data.dwhStatusKey === item.dwhStatusKey
          );
        });
        if (obj) {
          if (feild == 'inSubs') {
            obj.inAdjustFlag = event.value !== null ? 1 : 0;
            obj.inSubTransactionTypeKey = event.value?.trxTypeKey;
          }
          if (feild == 'outSubs') {
            obj.outAdjustFlag = event.value !== null ? 1 : 0;
            obj.outSubTransactionTypeKey = event.value?.trxTypeKey;
          }
        } else {
          this.fillList(event, data, feild);
        }
      } else {
        this.fillList(event, data, feild);
      }
    }
    if (this.table.name == 'manualAdjustment') {
      this.targetRow = data.ratePlanKey;
      type == 'input' && event.value > 3000
        ? (this.max = true)
        : (this.max = false);
      if (type === 'input') data.numberOfSubs = event.value;
      if (this.manualAdjustList.length) {
        let obj = this.manualAdjustList.find((item) => {
          return item.ratePlanKey == data.ratePlanKey;
        });
        if (obj) {
          type == 'dropdown'
            ? (obj.trxTypeKey =
                event.value == null ? 0 : +event.value.trxTypeKey)
            : '';
          type == 'input'
            ? (obj.numberOfSubs = +event.value == undefined ? 0 : +event.value)
            : '';
          if (obj.trxTypeKey && obj.numberOfSubs) {
            let trxData = this.table.extracols[0].data;
            let trxValue = trxData.find(
              (item: any) => item.trxTypeKey == obj?.trxTypeKey
            );
            data.calculated = true;
            this.calculation(
              data.ratePlanKey,
              +trxValue.value,
              +obj.numberOfSubs
            );
          } else {
            this.table.data[rowIndex].closing =
              this.originalData[rowIndex].closing;
            this.table.data[rowIndex].variance =
              this.originalData[rowIndex].variance;
          }
        } else {
          this.fillList(event, data);
          this.handleManualAdjusment(event, feild, type, data, rowIndex);
        }
      } else {
        this.fillList(event, data);
        this.handleManualAdjusment(event, feild, type, data, rowIndex);
      }
    }
    if (this.table.name == 'transferAdjustment') {
      type == 'dropdown' ? (data.ratePlanObj = event.value) : '';
      type == 'input' ? (data.adjusts = +event.value) : '';
      this.adjustNotValid = data.ratePlanObj && !data.adjusts ? true : false;
      this.rateplanNotValid = !data.ratePlanObj && data.adjusts ? true : false;
      this.editableRowId = rowIndex;
      let obj = this.transferAdjustList.find(item=>item.trxTypeKey == data.trxTypeKey);
      if (this.transferAdjustList.length) {
        if(obj){
        this.transferAdjustList.map((item, i) => {
          if (item.trxTypeKey == data.trxTypeKey) {
            item.numberOfSubs = data.adjusts ? data.adjusts : 0;
            item.ratePlanKey = data.ratePlanObj
              ? data.ratePlanObj.ratePlanKey
              : 0;
            item.ratePlanGroupKey = data.ratePlanObj
              ? data.ratePlanObj.ratePlanGroupKey
              : 0;
            item.ratePlanType = data.ratePlanObj
              ? data.ratePlanObj.ratePlanType
              : 0;
          }
        });
      } else this.fillList(event,data)
      } else this.fillList(event,data)
    }
  }
  handleManualAdjusment(
    event: any,
    feild: string,
    type: string,
    data: any,
    rowIndex: number
  ) {
    let obj = this.manualAdjustList.find((item) => {
      return item.ratePlanKey == data.ratePlanKey;
    });
    if (obj) {
      type == 'dropdown'
        ? (obj.trxTypeKey = event.value == null ? 0 : +event.value.trxTypeKey)
        : '';
      type == 'input'
        ? (obj.numberOfSubs = +event.value == undefined ? 0 : +event.value)
        : '';
      if (obj.trxTypeKey && obj.numberOfSubs) {
        let trxData = this.table.extracols[0].data;
        let trxValue = trxData.find(
          (item: any) => item.trxTypeKey == obj?.trxTypeKey
        );
        data.calculated = true;
        this.calculation(data.ratePlanKey, +trxValue.value, +obj.numberOfSubs);
      } else {
        this.table.data[rowIndex].closing = this.originalData[rowIndex].closing;
        this.table.data[rowIndex].variance =
          this.originalData[rowIndex].variance;
      }
    }
  }

  calculation(ratePlanKey: number, trxType: number, oldSubs: number) {
    console.log(
      'ratePlanKey:',
      ratePlanKey,
      'trxType:',
      trxType,
      'oldSubs',
      oldSubs,
      'Math.abs(oldSubs)',
      Math.abs(oldSubs)
    );
    let originalrow = this.originalData.find(
      (item) => item.ratePlanKey == ratePlanKey
    );
    let closing = +originalrow.closing;
    this.table.data.map((item: any) => {
      if (item.ratePlanKey == ratePlanKey) {
        item.closing = closing + oldSubs;
        item.variance = item.closing - item.opening;
      }
    });
  }

  calculationValidations(ratePlanKey: number, dataToCalculate: Calc) {
    const filtered = this.table.data.filter(
      (item) => item.ratePlanKey === ratePlanKey
    );
    let originalrow = this.originalData.find(
      (item) => item.ratePlanKey == ratePlanKey
    );
    let closing = +originalrow.closing;
    if (filtered.length > 1) {
      let oldCalc = 0;
      let newCalc = 0;
      this.table.data.map((item: any, i: number) => {
        let inSubs =
          item.trxInSubsValue && item.inSubs
            ? item.trxInSubsValue * Math.abs(item.inSubs)
            : 0;
        let outSubs =
          item.trxOutSubsValue && item.outSubs
            ? item.trxOutSubsValue * Math.abs(item.outSubs)
            : 0;
        if (item.ratePlanKey == ratePlanKey) {
          oldCalc = item.calc ? oldCalc + item.calc : oldCalc;
          item.calc = inSubs + outSubs;
          newCalc = newCalc + item.calc;
        }
        if (this.table.data.length - 1 === i)
          this.validationService.updateBalance.next({
            oldCalc: oldCalc,
            newCalc: newCalc,
          });
      });
      this.table.data.map((item) => {
        if (item.ratePlanKey == ratePlanKey) {
          item.closing = closing + newCalc;
          item.variance = item.closing - item.opening;
        }
      });
    } else {
      let inSubs =
        dataToCalculate.trxInSubs && dataToCalculate.inSubs
          ? dataToCalculate.trxInSubs * Math.abs(dataToCalculate.inSubs)
          : 0;
      let outSubs =
        dataToCalculate.trxOutSubs && dataToCalculate.outSubs
          ? dataToCalculate.trxOutSubs * Math.abs(dataToCalculate.outSubs)
          : 0;
      this.table.data.map((item: any) => {
        if (item.ratePlanKey == ratePlanKey) {
          let oldCalc = item.calc ? item.calc : 0;
          item.closing = closing + inSubs + outSubs;
          item.variance = item.closing - item.opening;
          if (!inSubs && !outSubs) {
            item.closing = originalrow.closing;
            item.variance = originalrow.variance;
          }
          item.calc = inSubs + outSubs;
          this.validationService.updateBalance.next({
            oldCalc: oldCalc,
            newCalc: item.calc,
          });
        }
      });
    }
  }

  fillList(event: any, data: any, feild?: string) {
    if (this.table.name == 'manualAdjustment') {
      this.manualAdjustList.push({
        dateKey: data.dateKey,
        dwhStatusKey: 200,
        numberOfSubs: data.numberOfSubs == undefined ? 0 : data.numberOfSubs,
        trxTypeKey: event.value.trxTypeKey ? +event.value.trxTypeKey : 0,
        ratePlanKey: data.ratePlanKey,
        ratePlanGroupKey: data.ratePlanGroupKey,
        ratePlanType: data.ratePlanType,
      });
      console.log(this.manualAdjustList)
    } else if (this.table.name == 'validation') {
      this.aggregationList.push({
        dateKey: this.epochDate,
        inAdjustFlag: feild == 'inSubs' && event.value !== null ? 1 : 0,
        outAdjustFlag: feild == 'outSubs' && event.value !== null ? 1 : 0,
        ratePlanKey: data.ratePlanKey,
        dwhStatusKey: data.dwhStatusKey,
        dayDateKey: data.dateKey,
        inSubTransactionTypeKey:
          feild == 'inSubs' ? event?.value?.trxTypeKey : '',
        outSubTransactionTypeKey:
          feild == 'outSubs' ? event?.value?.trxTypeKey : '',
      });
    } else if (this.table.name == 'transferAdjustment') {
      this.transferAdjustList.push({
        numberOfSubs: data.adjusts ? data.adjusts : 0, // Adjusts
        dataKey: +data.dateKey,
        ratePlanKey: data.ratePlanObj ? data.ratePlanObj.ratePlanKey : 0,
        ratePlanGroupKey: data.ratePlanObj
          ? data.ratePlanObj.ratePlanGroupKey
          : 0,
        trxTypeKey: +data.trxTypeKey,
        dwhStatusKey: 1,
        pgGroupKey: 1,
        ratePlanType: data.ratePlanObj ? data.ratePlanObj.ratePlanType : 0,
      });
    }
  }
  update() {
    if (this.table.name == 'validation') {
      this.footPrintService.handleNewValue(this.aggregationList, 'ratePlanKey');
      console.log('aggregationList', this.aggregationList);
      this.validationService
        .submitData(this.aggregationList)
        .subscribe((resp) => {
          this.aggregationList = [];
          this.footPrintService.refreshFlag = true;
          this.validationService
            .getvalidationList(this.epochDate)
            .subscribe((resp) => {
              this.table.data = resp['payload']['list'];
              this.originalData = JSON.parse(
                JSON.stringify(resp['payload']['list'])
              );
            });
        });
    }
    if (this.table.name == 'manualAdjustment') {
      this.footPrintService.handleNewValue(
        this.manualAdjustList,
        'ratePlanKey'
      );
      // this.manualAdjustList.map((item:any)=>  item.numberOfSubs=Math.abs(item.numberOfSubs))
      this.manualAgjustService
        .updateManualAdjustment(this.manualAdjustList)
        .subscribe((resp) => {
          this.manualAdjustList = [];
          this.footPrintService.refreshFlag = true;

          this.manualAgjustService
            .getManualAdjustment(this.epochDate)
            .subscribe((resp) => {
              this.table.data = resp['payload'];
              this.table.data?.map((item) => {
                item.numberOfSubs = -item.variance;
              });
              this.originalData = JSON.parse(JSON.stringify(this.table.data));
            });
        });
    }
    if (this.table.name == 'transferAdjustment') {
      this.footPrintService.handleNewValue(
        this.transferAdjustList,
        'trxTypeKey'
      );
      this.transferSubscription = this.transferAdjustService
        .updateTransferAdjustment(this.transferAdjustList)
        .subscribe((resp) => {
          this.transferAdjustList = [];
          this.footPrintService.refreshFlag = true;

          this.transferAdjustService
            .getTransferAdjustment(this.epochDate)
            .subscribe(
              (resp) =>
                (this.table.data = resp['payload']['getTransferAdjustmentList'])
            );
        });
    }
  }

  exportExcel() {
    let data;
    if (this.table.name == 'transferAdjustment') {
      data = this.table.data.map((item: any) => {
        return { trxTypeName: item.trxTypeName, subs: item.subs };
      });
    } else data = this.table.data;
    this.exportExcelService.exportAsExcelFile(data, 'report');
  }

  copyToClipboard() {
    let copiedData = this.selectedData.map((item: any) => {
      item = [item.trxTypeName, item.subs];
      console.log(item);
      return item;
    });
    copiedData = copiedData.map((lines: any) => lines.join('\t')).join('\n');
    this.clipboardApi.copyFromContent(copiedData);
  }

  sortColumn(event: SortEvent) {
    // to reset sort after third click
    console.log('sort', event);
    console.log('sort', this.table.data);
    if (
      this.prevSortStatus?.field == event.field &&
      this.prevSortStatus?.order == -1
    ) {
      this.dt?.reset();
    }
    if (event.order == -1) this.prevSortStatus = event;
    else this.prevSortStatus = undefined;
  }

  ngOnDestroy(): void {
    this.profileSubscription?.unsubscribe();
    this.transferSubscription?.unsubscribe();
  }
}