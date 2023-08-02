import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Inputs } from 'src/app/shared/models/inputs.model';
import { PrivilegesService } from 'src/app/shared/services/privileges-service/privileges.service';
import { PRIVILEGES } from 'src/app/shared/statics/privileges';

@Component({
  selector: 'app-rateplan-groups-list',
  templateUrl: './rateplan-groups-list.component.html',
  styleUrls: ['./rateplan-groups-list.component.scss'],
})
export class RateplanGroupsListComponent implements OnInit {
  inputs: Inputs[] = [
    {
      label: 'Rateplan Group',
      controlName: 'ratePlanGroup',
      type: 'text',
    },
    {
      label: 'Description',
      controlName: 'description',
      type: 'text',
    },
    {
      label: 'Show Flag',
      controlName: 'showFlag',
      type: 'checkbox',
      readOnly: true,
    },
  ];

  enableUpdate!: boolean;
  enableAdd!: boolean;
  enableDelete!: boolean;
  viewDetails!: boolean;
  constructor(private router:Router,private privilegesService:PrivilegesService) {}

  ngOnInit(): void {
    this.enableAdd = this.privilegesService.checkPrivileges(
      PRIVILEGES.RATE_PLANS_GROUP.ADD
    );
    this.enableUpdate = this.privilegesService.checkPrivileges(
      PRIVILEGES.RATE_PLANS_GROUP.UPDATE
    );
    this.enableDelete = this.privilegesService.checkPrivileges(
      PRIVILEGES.RATE_PLANS_GROUP.DELETE
    );
    this.viewDetails = this.privilegesService.checkPrivileges(
      PRIVILEGES.RATE_PLANS_GROUP.GET_BY_ID
    );
  }
  addRateplaneGroup() {
    this.router.navigateByUrl('daily/rateplans/rateplansGroups/add');
  }
}
