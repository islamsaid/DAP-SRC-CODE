import {
  AfterContentChecked,
  ChangeDetectorRef,
  Component,
  Input,
  OnDestroy,
  OnInit,
  ViewChild,
} from '@angular/core';
import {
  AbstractControl,
  FormArray,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { RateplansService } from 'src/app/rateplans/services/rateplans.service';
import { Table } from 'primeng/table';
import { Service } from 'src/app/services-tariffs/models/service.model';
import { TariffsClassesService } from 'src/app/services-tariffs/services/tariffs-classes.service';
import { Inputs } from '../../models/inputs.model';
import { map, merge, Subject, Subscription, takeUntil } from 'rxjs';
import { PricesService } from 'src/app/prices/services/prices.service';
import { Router } from '@angular/router';
import { ConfirmationService } from 'primeng/api';
import { FootprintService } from '../../services/footprint-service/footprint.service';
import { HeaderService } from 'src/app/core/services/header-service/header.service';
@Component({
  selector: 'app-services-list',
  templateUrl: './services-list.component.html',
  styleUrls: ['./services-list.component.scss'],
})
export class ServicesListComponent
  implements OnInit, AfterContentChecked, OnDestroy
{
  servicesList!: any[];
  @Input('source') source!: string;
  @Input('inputs') inputs!: Inputs[];
  @Input('logs') logs!: any[];
  @Input('enableUpdate') enableUpdate!: boolean;
  @Input('enableDelete') enableDelete!: boolean;
  @Input('viewDetails') viewDetails!: boolean;
  @ViewChild('dt') dt: Table | undefined;
  loading: boolean = false;
  pageSize: number = 5;
  servicesForm!: FormGroup;
  tableList: Service[] = [];
  searchSubscription!: Subscription;
  tableListclone!: any[];
  clearSubscription!: Subscription;
  tableName!: string;
  changesUnsubscribe = new Subject<any>();
  Changeditems: any[] = [];
  rowIndex!: number;
  targetElement!: any;
  footPrintSubscription!: Subscription;
  epochDate!: any;
  showDialog!: boolean;
  constructor(
    private cdr: ChangeDetectorRef,
    private tariffsClasses: TariffsClassesService,
    private rateplansService: RateplansService,
    private pricesService: PricesService,
    private confirmationService: ConfirmationService,
    private router: Router,
    private priceService: PricesService,
    private footprintService: FootprintService
  ) {}

  ngOnInit(): void {
    this.initForm();
    this.getALLServices();

    this.searchSubscription = this.tariffsClasses.searchSubject.subscribe(
      (filters: any) => {
        if (this.source !== 'footprint') {
          this.servicesList = this.tableListclone;
          for (const key in filters) {
            if (filters[key]) {
              this.servicesList = this.servicesList.filter((el: any) => {
                if(typeof el[key] == 'string'){
                filters[key] = filters[key].toLowerCase();
                let searchStr = el[key].toLowerCase();
                return searchStr.includes(filters[key])
                } return el[key] === filters[key];
              });
            }
          }
          this.clearFormArray();
          this.fillFormArray();
        } else {
          this.clearFormArray();
          this.epochDate = filters.date ? filters.date.getTime() : null;
          this.getAllFootprints(this.epochDate, filters.userId);
        }
      }
    );
    this.clearSubscription = this.tariffsClasses.clearSubject.subscribe(
      (flag) => {
        this.servicesList = this.tableListclone;
        this.clearFormArray();
        this.fillFormArray();
      }
    );
  }
  getALLServices() {
    if (this.source == 'rateplans') {
      this.rateplansService.getAllRatePlans().subscribe((resp) => {
        let oldratePlan = this.footprintService.handleRateplansoldValue(
          resp.payload
        );
        this.footprintService.handleOldValue(oldratePlan, 'ratePlanCode');
        this.servicesList = resp.payload;
        this.tableListclone = this.servicesList;
        this.fillFormArray();
      });
    } else if (this.source == 'rateplansGroups') {
      this.rateplansService.getAllRatePlanGroups().subscribe((resp) => {
        this.servicesList = resp.payload;
        this.tableListclone = this.servicesList;
        this.fillFormArray();
      });
    } else if (this.source == 'classes' || this.source == 'tariffs') {
      this.tariffsClasses.getAllServices(this.source).subscribe((res) => {
        this.servicesList =
          this.source === 'classes'
            ? res.payload.serviceClasseList
            : res.payload.tariffModelList;
        this.tableListclone =
          this.source === 'classes'
            ? res.payload.serviceClasseList
            : res.payload.tariffModelList;
        let key =
          this.source === 'classes' ? 'serviceClassCode' : 'tariffModelCode'; // to filter changed old values (footprint service )
        this.footprintService.handleOldValue(this.tableListclone, key);
        this.fillFormArray();
      });
    } else if (this.source == 'prices' || this.source == 'pgGroups') {
      this.pricesService.getAll(this.source).subscribe((res) => {
        if (this.source === 'prices')
          this.footprintService.handlePriceGroupOldValue(res);
        this.servicesList =
          this.source === 'prices'
            ? this.handlePriceGroupResponse(res)
            : res.payload.allPriceGroupGroupsResponses;
        this.tableListclone =
          this.source === 'prices'
            ? this.handlePriceGroupResponse(res)
            : res.payload.allPriceGroupGroupsResponses;
        this.fillFormArray();
      });
    } else if (this.source == 'footprint') {
      this.getAllFootprints();
    }
  }

  handlePriceGroupResponse(response: any) {
    return response.payload.priceGroupModel.map((item: any) => {
      item.pgGroup = item.pgGroupKey ? item.pgGroupKey.pgGroup : null;
      item.pgKey = item.pgGroupKey ? item.pgGroupKey.pgGroupKey : null;
      return item;
    });
  }

  clearFormArray() {
    while ((<FormArray>this.servicesForm.get('servicesArray')).length !== 0) {
      (<FormArray>this.servicesForm.get('servicesArray')).removeAt(0);
    }
  }

  initForm() {
    this.servicesForm = new FormGroup({
      servicesArray: new FormArray([]),
    });
  }

  ngAfterContentChecked() {
    this.cdr.detectChanges();
  }

  getControls() {
    return (<FormArray>this.servicesForm.get('servicesArray')).controls;
  }

  fillFormArray() {
    this.servicesList.forEach((item: any) => {
      switch (this.source) {
        case 'tariffs':
          this.tableName = 'tariffModelName';
          (<FormArray>this.servicesForm.get('servicesArray')).push(
            new FormGroup({
              tariffModelCode: new FormControl(item.tariffModelCode),
              tariffModelName: new FormControl(item.tariffModelName),
              tariffModelType: new FormControl(item.tariffModelType),
              hybird: new FormControl(item.hybird),
              activationSource: new FormControl(item.activationSource),
              contractType: new FormControl(item.contractType),
              bundleType: new FormControl(item.bundleType),
              deactivationSourceFlag: new FormControl(
                item.deactivationSourceFlag
              ),
            })
          );

          break;
        case 'classes':
          this.tableName = 'serviceClassName';
          (<FormArray>this.servicesForm.get('servicesArray')).push(
            new FormGroup({
              serviceClassCode: new FormControl(item.serviceClassCode),
              serviceClassName: new FormControl(item.serviceClassName),
              serviceClassType: new FormControl(item.serviceClassType),
              hybird: new FormControl(item.hybird),
              activationSource: new FormControl(item.activationSource),
              contractType: new FormControl(item.contractType),
              bundleType: new FormControl(item.bundleType),
              deacSourceFlag: new FormControl(item.deacSourceFlag),
            })
          );
          break;
        case 'rateplans':
          this.tableName = 'ratePlan';
          (<FormArray>this.servicesForm.get('servicesArray')).push(
            new FormGroup({
              ratePlan: new FormControl({
                value: item.ratePlan,
                disabled: true,
              }),
              ratePlanType: new FormControl({
                value: item.ratePlanType,
                disabled: true,
              }),
              activationSourceFlag: new FormControl({
                value: item.activationSourceFlag,
                disabled: true,
              }),
              contractType: new FormControl({
                value: item.contractType,
                disabled: true,
              }),
              showFlag: new FormControl(item.showFlag),
              ratePlanGroupKey: new FormControl(item.ratePlanGroupKey),
            })
          );
          break;
        case 'rateplansGroups':
          this.tableName = 'ratePlanGroup';
          (<FormArray>this.servicesForm.get('servicesArray')).push(
            new FormGroup({
              description: new FormControl(item.description),
              ratePlanGroup: new FormControl(item.ratePlanGroup),
              showFlag: new FormControl({
                value: item.showFlag,
                disabled: true,
              }),
            })
          );

          break;
        case 'prices':
          (<FormArray>this.servicesForm.get('servicesArray')).push(
            new FormGroup({
              priceGroup: new FormControl(item.priceGroup),
              pgGroup: new FormControl(item.pgGroup),
              pgKey: new FormControl(item.pgKey),
            })
          );
          break;
        case 'pgGroups':
          (<FormArray>this.servicesForm.get('servicesArray')).push(
            new FormGroup({
              pgGroupKey: new FormControl(item.pgGroupKey),
              pgGroup: new FormControl(item.pgGroup),
              description: new FormControl(item.description),
              showFlag: new FormControl(item.showFlag),
            })
          );
          break;
        case 'footprint':
          (<FormArray>this.servicesForm.get('servicesArray')).push(
            new FormGroup({
              userName: new FormControl(item.userName),
              userId: new FormControl(item.userId),
              pageName: new FormControl(item.pageName),
              date: new FormControl(item.date),
              objectIdentifier: new FormControl(item.objectIdentifier),
              actionName: new FormControl(item.actionName),
            })
          );
          break;
      }
    });
    this.tableList = [...this.servicesForm.controls['servicesArray'].value];
    this.detectFormArryChanges();
  }

  updateList() {
    if (this.source == 'rateplans') {
      // console.log(this.Changeditems);
      this.Changeditems = this.Changeditems
        ? this.Changeditems.map((item) => {
            return item.data;
          })
        : [];
      this.footprintService.handleNewValue(this.Changeditems);
      this.rateplansService
        .updateRateplansList(this.Changeditems)
        .subscribe((resp) => {
          this.Changeditems = [];
          this.clearFormArray();
          this.getALLServices();
        });
    } else if (this.source === 'classes' || this.source === 'tariffs') {
      this.Changeditems = this.Changeditems
        ? this.Changeditems.map((item) => {
            console.log('Changeditems', item.data);

            return item.data;
          })
        : [];
      this.footprintService.handleNewValue(this.Changeditems);
      this.tariffsClasses
        .updateServices(this.source, this.Changeditems)
        .subscribe((res) => {
          this.Changeditems = [];
          this.clearFormArray();
          this.getALLServices();
        });
    } else if (this.source == 'prices' || this.source == 'pgGroups') {
      this.Changeditems = this.Changeditems.map((item) => {
        item.data = {
          priceGroupCode: item.data.priceGroupCode,
          pgGroupKey: item.data.pgKey,
        };
        return item.data;
      });
      this.footprintService.handleNewValue(this.Changeditems);
      this.pricesService
        .updatePriceGroups(this.source, this.Changeditems)
        .subscribe((res) => {
          this.Changeditems = [];
          this.clearFormArray();
          this.getALLServices();
        });
      // console.log('Changeditems updated list', this.Changeditems);
    }
  }

  manageEdit(row: any, index: number) {
    if (this.source === 'pgGroups') {
      // console.log('row', row.value.pgGroupKey);
      this.editPgGroup(row.value.pgGroupKey);
    } else if (this.source === 'rateplansGroups')
      this.editRateplaneGroup(index);
  }

  manageDelete(event: any, row: any, index: number) {
    this.confirmationService.confirm({
      target: event.target,
      message:
        this.source === 'rateplansGroups'
          ? 'Are you sure that you want to delete this Rateplan Group ?'
          : 'Are you sure that you want to delete this PG Group ?',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        //confirm action
        if (this.source === 'rateplansGroups') {
          let ratePlanGroupKey = this.servicesList[index].ratePlanGroupKey;
          this.footprintService.objectIdentifier = ratePlanGroupKey;
          this.rateplansService
            .deletRateplanGroup(ratePlanGroupKey)
            .subscribe((resp) =>
              this.rateplansService.getAllRatePlanGroups().subscribe((resp) => {
                this.servicesList = resp.payload;
                this.tableListclone = this.servicesList;
                (<FormArray>this.servicesForm.get('servicesArray')).clear();
                this.fillFormArray();
              })
            );
        } else if (this.source == 'pgGroups') {
          let id = row.value.pgGroupKey;
          this.footprintService.objectIdentifier = id;
          this.priceService.deletePriceGroup(id).subscribe((resp) => {
            this.priceService.getAll('PG').subscribe((resp) => {
              this.servicesList = resp.payload.allPriceGroupGroupsResponses;
              this.tableListclone = this.servicesList;
              (<FormArray>this.servicesForm.get('servicesArray')).clear();
              this.fillFormArray();
            });
          });
        }
      },
      reject: () => {
        //reject action
      },
    });
  }
  editRateplaneGroup(index: any) {
    let id = this.servicesList[index].ratePlanGroupKey;
    let url =
      this.viewDetails && !this.enableUpdate
        ? 'daily/rateplans/rateplansGroups/view/'
        : 'daily/rateplans/rateplansGroups/edit/';
    url = url + id;
    this.router.navigateByUrl(url);
  }

  editPgGroup(id: number) {
    let url =
      this.viewDetails && !this.enableUpdate
        ? '/daily/prices/view/'
        : '/daily/prices/edit/';
    url = url + id;
    this.router.navigateByUrl(url);
  }

  detectFormArryChanges() {
    this.changesUnsubscribe.next(true);
    merge(
      ...(<FormArray>this.servicesForm.get('servicesArray')).controls.map(
        (control: AbstractControl, index: number) =>
          control.valueChanges.pipe(
            takeUntil(this.changesUnsubscribe),
            map((value) => ({ rowIndex: index, control: control, data: value }))
          )
      )
    ).subscribe((changes) => {
      console.log('changes', changes);
      this.rowIndex = changes.rowIndex;
      this.onValueChanged(changes);
    });
  }

  onValueChanged(changes: any) {
    let existing: boolean = false;
    if (this.Changeditems) {
      this.Changeditems?.map((item: any) => {
        if (item.rowIndex === changes.rowIndex) {
          existing = true;
          if (this.source == 'rateplans') {
            changes.data = {
              ...changes.data,
              ratePlanCode: this.servicesList[this.rowIndex].ratePlanCode,
            };
          } else if (this.source === 'prices') {
            // console.log(
            //   'servicesList',
            //   this.servicesList[this.rowIndex].priceGroupCode
            // );
            changes.data = {
              ...changes.data,
              priceGroupCode: this.servicesList[this.rowIndex].priceGroupCode,
            };
          }
          console.log('changes exist item', item);
          return (item.data = JSON.parse(JSON.stringify(changes.data)));
        }
      });
      console.log('changesItem', this.Changeditems);
    }
    if (this.source == 'rateplans') {
      changes.data = JSON.parse(
        JSON.stringify({
          ...changes.data,
          ratePlanCode: this.servicesList[this.rowIndex].ratePlanCode,
        })
      );
    } else if (this.source === 'prices') {
      changes.data = JSON.parse(
        JSON.stringify({
          ...changes.data,
          priceGroupCode: this.servicesList[this.rowIndex].priceGroupCode,
        })
      );
    }
    if (!existing) console.log('changes first time', changes);
    if (!existing) this.Changeditems.push(changes);
  }

  getAllFootprints(date?: number, userId?: number) {
    let bodyRequest = {
      userId: userId ? userId : null, //can be null get u current user
      echoDate: date ? date : this.footprintService.getTodayInTime(),
    };
    this.footPrintSubscription = this.footprintService
      .getFootprintsRequest(bodyRequest)
      .subscribe((res) => {
        // console.log('logs', res.payload.logModelList);
        this.servicesList = res.payload.logModelList;
        this.tableListclone = res.payload.logModelList;
        this.fillFormArray();
      });
  }

  openLogDetails(index: number) {
    let data = this.servicesList[index].transactionUserDetails;
    let emptyVal = data.length > 0 ? false : true;
    // console.log('footdata', data);
    let dialogDetails = {
      show: true,
      empty: emptyVal,
      data: data,
    };
    this.footprintService.openDialog.next(dialogDetails);
  }

  ngOnDestroy(): void {
    this.searchSubscription?.unsubscribe();
    this.clearSubscription?.unsubscribe();
    this.footPrintSubscription?.unsubscribe();
  }
}
