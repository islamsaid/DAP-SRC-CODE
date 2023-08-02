import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { Subscription } from 'rxjs';
import { HeaderService } from 'src/app/core/services/header-service/header.service';
import { Rateplan } from 'src/app/rateplans/models/rateplan.model';
import { ValidationService } from 'src/app/validation/services/validation.service';

@Component({
  selector: 'app-rateplans-numbers',
  templateUrl: './rateplans-numbers.component.html',
  styleUrls: ['./rateplans-numbers.component.scss'],
})
export class RateplansNumbersComponent implements OnInit {
  items!: MenuItem[];
  activeItem!: MenuItem;
  data!: any[];
  epochDate!: number;
  dateSubscription!: Subscription;
  minimumList!: Rateplan[];
  maximumList!: Rateplan[];

  constructor(
    private validationService: ValidationService,
    private headerService: HeaderService
  ) {}

  ngOnInit(): void {
    this.items = [
      {
        label: 'Highest In-Subs',
        command: (event) => {
          this.activeItem = this.items[0];
          this.onSubmit();
        },
      },
      {
        label: 'Lowest In-Subs',
        command: (event) => {
          this.activeItem = this.items[1];
          this.onSubmit();
        },
      },
    ];
    this.activeItem = this.items[0];
    this.epochDate = Number(localStorage.getItem('date'));
    this.getData();
    this.dateSubscription = this.headerService.DateSubject.subscribe((date) => {
      this.epochDate = date;
      this.getData();
    });
  }
  onSubmit() {
    if (this.activeItem.label === 'Highest In-Subs') {
      console.log('hello Highest In-Subs ');
      this.data = this.maximumList;
    }
    if (this.activeItem.label === 'Lowest In-Subs') {
      console.log('hello Lowest In-Subs ');
      this.data = this.minimumList;
    }
  }
  getData() {
    this.validationService
      .getMAxMinRatePlans(this.epochDate)
      .subscribe((resp) => {
        this.minimumList = resp.payload.minimum;
        this.maximumList = resp.payload.maximum;
        this.onSubmit();
      });
  }
}
