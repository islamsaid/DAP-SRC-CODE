import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { FootprintService } from 'src/app/shared/services/footprint-service/footprint.service';
import { Defines } from 'src/app/shared/statics/defines';
import { PGGroup } from '../../models/pgGroup.model';
import { PriceGroup } from '../../models/priceGroup.model';
import { PricesService } from '../../services/prices.service';

@Component({
  selector: 'app-create-pg-groups',
  templateUrl: './create-pg-groups.component.html',
  styleUrls: ['./create-pg-groups.component.scss'],
})
export class CreatePgGroupsComponent implements OnInit {
  sourceList: PriceGroup[] = [];
  targetList: PriceGroup[] = [];
  pgGroupForm!: FormGroup;
  pageMode: string = 'Add';
  pgGroupId!: number;
  getSubscription!: Subscription;
  getpricesSubscription!: Subscription;
  urlRoute: string =
    Defines.ROUTES.DAILY +
    '/' +
    Defines.ROUTES.PRICES +
    '/' +
    Defines.ROUTES.PGGROUPS;
  viewMode!: boolean;

  constructor(
    private pricesService: PricesService,
    private route: ActivatedRoute,
    private router: Router,
    private footprintService: FootprintService
  ) {}

  ngOnInit(): void {
    this.getPricesGroups();
    if (this.route.snapshot.params['id']) {
      this.pgGroupId = this.route.snapshot.params['id'];
      this.viewMode = this.route.snapshot.url[0].path === 'view';
      this.pageMode = this.viewMode ? 'View ' : 'Edit';
    }
    this.initForm();
    this.pageMode == 'Edit' ? this.getPgGroupByID() : '';
  }
  priceGroup: any;
  getPricesGroups() {
    this.getpricesSubscription = this.pricesService
      .getAll('prices')
      .subscribe((response) => {
        this.sourceList = response.payload.priceGroupModel.filter(
          (item: any) => {
            if (item.pgGroupKey === null) {
              this.priceGroup = {
                priceGroupKey: item.priceGroupKey,
                priceGroup: item.priceGroup,
                priceGroupCode: item.priceGroupCode,
                pgGroupKey: item.pgGroupKey,
              };
            }
            return this.priceGroup;
          }
        );
        console.log('unassigned priceGroup', this.sourceList);
      });
  }

  getPgGroupByID() {
    this.getSubscription = this.pricesService
      .getPgGroupById(this.pgGroupId)
      .subscribe((response: any) => {
        console.log('pg', response['payload']);
        let pgGroup = response['payload']['pgGroupModel'];
        let oldData = response['payload']['pgGroupModel'];
        delete oldData['pgGroupKey'];
        let pgClone = JSON.parse(
          JSON.stringify({
            ...oldData,
            priceGroupModels: response['payload']['priceGroupModelList'],
          })
        );
        this.footprintService.objectIdentifier = this.pgGroupId;
        this.footprintService.handleOldValue(pgClone);
        this.targetList = response['payload']['priceGroupModelList']
          ? response['payload']['priceGroupModelList']
          : [];
        this.pgGroupForm.patchValue({
          pgGroup: pgGroup.pgGroup,
          description: pgGroup.description,
          showFlag: pgGroup.showFlag,
          pgGroupKey: pgGroup.pgGroupKey,
        });
        if (this.viewMode) this.pgGroupForm.disable();
      });
  }

  initForm() {
    this.pgGroupForm = new FormGroup({
      pgGroup: new FormControl(null, Validators.required),
      description: new FormControl(null),
      showFlag: new FormControl(),
      pgGroupKey: new FormControl(),
    });
  }

  onSubmit() {
    let pgGroup: PGGroup = {
      pgGroup: this.pgGroupForm.value.pgGroup,
      description: this.pgGroupForm.value.description,
      showFlag: this.pgGroupForm.value.showFlag,
    };
    let newValue = { ...pgGroup, priceGroupModels: this.targetList };
    this.footprintService.handleNewValue(newValue);
    if (this.pageMode == 'Edit') {
      pgGroup.pgGroupKey = +this.pgGroupId;
      let body = { pgGroupModel: pgGroup, priceGroupModels: this.targetList };
      console.log('body', body);
      this.pricesService.managePgGroup(body, true).subscribe((response) => {
        console.log('add profile response', response);
        if (response.statusCode == Defines.RESPONSE_STATUS.SUCCESS) {
          this.router.navigateByUrl(this.urlRoute);
        }
      });
    } else {
      let body = { pgGroupModel: pgGroup, priceGroupModels: this.targetList };
      this.pricesService.managePgGroup(body).subscribe((response) => {
        console.log('add profile response', response);
        if (response.statusCode == Defines.RESPONSE_STATUS.SUCCESS) {
          this.router.navigateByUrl(this.urlRoute);
        }
      });
    }
  }

  ngOnDestroy(): void {
    this.getSubscription?.unsubscribe();
    this.getpricesSubscription?.unsubscribe();
  }
}
