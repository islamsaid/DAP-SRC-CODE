import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { FootprintService } from 'src/app/shared/services/footprint-service/footprint.service';
import { Rateplan } from '../../models/rateplan.model';
import { RateplanGroup } from '../../models/rateplanGroup.model';
import { RateplansService } from '../../services/rateplans.service';

@Component({
  selector: 'app-manage-rateplan-group',
  templateUrl: './manage-rateplan-group.component.html',
  styleUrls: ['./manage-rateplan-group.component.scss'],
})
export class ManageRateplanGroupComponent implements OnInit {
  rateplanGroupKey!: any;
  pageMode: string = 'Add';
  rateplaneGroupForm!: FormGroup;
  sourceList: Rateplan[] = [];
  targetList: Rateplan[] = [];
  viewMode!: boolean;
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private rateplansService: RateplansService,
    private footprintService: FootprintService
  ) {}

  ngOnInit(): void {
    if (this.route.snapshot.params['id']) {
      this.rateplanGroupKey = this.route.snapshot.params['id'];
      this.viewMode = this.route.snapshot.url[1].path === 'view';
      this.pageMode = this.viewMode ? 'View ' : 'Edit';
    }
    this.rateplansService.getAllRatePlans().subscribe((resp) => {
      this.sourceList = resp.payload.filter((element: any) => {
        return !element.ratePlanGroupKey;
      });
      console.log(this.sourceList);
    });
    this.initForm();

    this.pageMode == 'Edit' ? this.getRateplaneGroupByKey() : '';
  }

  initForm() {
    this.rateplaneGroupForm = new FormGroup({
      ratePlanGroup: new FormControl(null, Validators.required),
      description: new FormControl(null),
      showFlag: new FormControl(null),
    });
  }
  getRateplaneGroupByKey() {
    this.rateplansService
      .getRateplaneGroup(this.rateplanGroupKey)
      .subscribe((resp) => {
        let rateplanGroup = resp.payload;
        let cloneData = JSON.parse(JSON.stringify(resp.payload));
        console.log('get rateplan by key', cloneData);
        this.footprintService.objectIdentifier = this.rateplanGroupKey;
        this.footprintService.handleOldValue(cloneData);
        this.rateplaneGroupForm.patchValue({
          description: rateplanGroup.description,
          ratePlanGroup: rateplanGroup.ratePlanGroup,
          showFlag: rateplanGroup.showFlag,
        });
        this.targetList = rateplanGroup.ratePlans;
        if (this.viewMode) this.rateplaneGroupForm.disable();
      });
  }
  onSubmit() {
    let rateplanGroup = {
      ...this.rateplaneGroupForm.value,
      showFlag: this.rateplaneGroupForm.value.showFlag == null ? 0 : 1,
      ratePlans: this.targetList.map((item) => {
        return {
          ratePlanCode: item.ratePlanCode,
        };
      }),
    };

    if (this.pageMode == 'Edit') {
      rateplanGroup = {
        ratePlanGroupKey: parseInt(this.rateplanGroupKey),
        ...this.rateplaneGroupForm.value,
        ratePlans: this.targetList.map((item) => {
          return {
            ratePlanCode: item.ratePlanCode,
            ratePlanGroupKey: parseInt(this.rateplanGroupKey),
          };
        }),
      };
      this.footprintService.handleNewValue(rateplanGroup);
      this.rateplansService
        .updateRatePlanGroup(rateplanGroup)
        .subscribe((resp) => {
          this.router.navigateByUrl('daily/rateplans/rateplansGroups');
        });
    } else {
      this.footprintService.handleNewValue(rateplanGroup);
      this.rateplansService
        .createRateplanGroup(rateplanGroup)
        .subscribe((resp) => {
          this.router.navigateByUrl('daily/rateplans/rateplansGroups');
        });
    }
  }
}
