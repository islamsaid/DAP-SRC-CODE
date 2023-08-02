import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Inputs } from 'src/app/shared/models/inputs.model';
import { PrivilegesService } from 'src/app/shared/services/privileges-service/privileges.service';
import { PRIVILEGES } from 'src/app/shared/statics/privileges';

@Component({
  selector: 'app-prices-group',
  templateUrl: './prices-group.component.html',
  styleUrls: ['./prices-group.component.scss'],
})
export class PricesGroupComponent implements OnInit {
  inputs: Inputs[] = [
    {
      label: 'PG Group',
      controlName: 'pgGroup',
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
      disabled:true
    },
  ];
  enableUpdate!: boolean;
  enableAdd!: boolean;
  enableDelete!: boolean;
  viewDetails!: boolean;
  constructor(
    private router: Router,
    private privilegesService: PrivilegesService
  ) {}

  ngOnInit(): void {
    this.enableAdd = this.privilegesService.checkPrivileges(
      PRIVILEGES.PG_GROUP.ADD
    );
    this.enableUpdate = this.privilegesService.checkPrivileges(
      PRIVILEGES.PG_GROUP.UPDATE
    );
    this.viewDetails = this.privilegesService.checkPrivileges(
      PRIVILEGES.PG_GROUP.GET_BY_ID
    );
    this.enableDelete = this.privilegesService.checkPrivileges(
      PRIVILEGES.PG_GROUP.DELETE
    );
  }

  addPgGroup() {
    this.router.navigateByUrl('daily/prices/add');
  }
}
