import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { Inputs } from 'src/app/shared/models/inputs.model';
import { PrivilegesService } from 'src/app/shared/services/privileges-service/privileges.service';
import { PRIVILEGES } from 'src/app/shared/statics/privileges';
import { PricesService } from '../../services/prices.service';

@Component({
  selector: 'app-prices-list',
  templateUrl: './prices-list.component.html',
  styleUrls: ['./prices-list.component.scss'],
})
export class PricesListComponent implements OnInit, OnDestroy {
  inputs: Inputs[] = [
    {
      label: 'Price Group',
      controlName: 'priceGroup',
      type: 'text',
    },
    {
      label: 'PG Group',
      controlName: 'pgGroup',
      type: 'text',
    },
  ];
  listInputs!: Inputs[];
  pgSubcription!: Subscription;
  enableUpdate!: boolean;
  constructor(private pricesService: PricesService, private privilegesService:PrivilegesService) {}

  ngOnInit(): void {
    this.enableUpdate = this.privilegesService.checkPrivileges(
      PRIVILEGES.PRICE_GROUP.UPDATE
    );
    this.pgSubcription = this.pricesService
      .getAll('pgPrices')
      .subscribe((res: any) => {
        console.log(
          'get All pgPrices For dropdown',
          res.payload.allPriceGroupGroupsResponses
        );
        this.listInputs = [
          {
            label: 'Price Group',
            controlName: 'priceGroup',
            type: 'text',
          },
          {
            label: 'PG Group',
            controlName: 'pgGroup',
            type: 'text',
          },
          {
            label: 'PG Group Key',
            controlName: 'pgKey',
            type: 'dropDown',
            options: res.payload.allPriceGroupGroupsResponses.map(
              (item: any) => {
                let option = {
                  id: item.pgGroup,
                  value: item.pgGroupKey,
                };
                return option;
              }
            ), // get options from retrieve All pg Groups Api
          },
        ];
      });
  }

  ngOnDestroy(): void {
    this.pgSubcription?.unsubscribe();
  }
}
