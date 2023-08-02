import {
  Component,
  Input,
  OnChanges,
  OnInit,
  SimpleChanges,
} from '@angular/core';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-new-price-group',
  templateUrl: './new-price-group.component.html',
  styleUrls: ['./new-price-group.component.scss'],
})
export class NewPriceGroupComponent implements OnInit, OnChanges {
  @Input('priceGroups') priceGroups!: any[];
  pageSize = 3;
  // data: any[] = [
  //   { name: 'price 1', date: '1 Day Ago' },
  //   { name: 'price 2', date: '1 Day Ago' },
  //   { name: 'price 3', date: '1 Day Ago' },
  //   { name: 'price 4', date: '1 Day Ago' },
  // ];
  constructor() {}

  ngOnInit(): void {}
  ngOnChanges(changes: SimpleChanges): void {}
}
