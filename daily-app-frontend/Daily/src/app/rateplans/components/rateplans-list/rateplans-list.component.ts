import { Component, OnInit } from '@angular/core';
import { Inputs } from 'src/app/shared/models/inputs.model';
import { PrivilegesService } from 'src/app/shared/services/privileges-service/privileges.service';
import { PRIVILEGES } from 'src/app/shared/statics/privileges';
import { RateplansService } from '../../services/rateplans.service';

@Component({
  selector: 'app-rateplans-list',
  templateUrl: './rateplans-list.component.html',
  styleUrls: ['./rateplans-list.component.scss'],
})
export class RateplansListComponent implements OnInit {
  inputs: Inputs[] = [];
  allRatePlansGroups!: any[];
  enableUpdate!: boolean;
  constructor(private rateplansService: RateplansService,private privilegesService:PrivilegesService) {}

  ngOnInit(): void {
    this.enableUpdate = this.privilegesService.checkPrivileges(
      PRIVILEGES.RATE_PLANS.UPDATE_lIST
    );
    this.rateplansService.getAllRatePlanGroups().subscribe((resp) => {
      this.allRatePlansGroups = resp.payload;
      console.log(this.allRatePlansGroups);
      this.inputs = [
        {
          label: 'Rateplan Name',
          controlName: 'ratePlan',
          type: 'text',
        },
        {
          label: 'Type',
          controlName: 'ratePlanType',
          type: 'dropDown',
          options: [
            { id: 'Prepaid', value: 1 },
            { id: 'Postpaid', value: 2 },
          ],
          readOnly: true,
        },

        {
          label: 'Contract Type',
          controlName: 'contractType',
          type: 'dropDown',
          options: [
            { id: 'Consumer', value: 1 },
            { id: 'Enterprise', value: 2 },
            { id: 'All', value: 3 },
          ],
          readOnly: true,
        },
        {
          label: 'Rateplan Group',
          controlName: 'ratePlanGroupKey',
          type: 'dropDown',
          options: this.allRatePlansGroups,
        },
        {
          label: 'Activation Source',
          controlName: 'activationSourceFlag',
          type: 'checkbox',
          disabled: true,
          readOnly: true,
        },
        {
          label: 'Show Flag',
          controlName: 'showFlag',
          type: 'checkbox',
        },
      ];
    });
  }
}
