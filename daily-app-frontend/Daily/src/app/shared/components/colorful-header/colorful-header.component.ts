import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { ValidationService } from 'src/app/validation/services/validation.service';

@Component({
  selector: 'app-colorful-header',
  templateUrl: './colorful-header.component.html',
  styleUrls: ['./colorful-header.component.scss'],
})
export class ColorfulHeaderComponent implements OnInit, OnDestroy {
  @Input('title') title!: string;
  @Input('icon') icon!: string;
  @Input('tabs') tabs!: any[];
  @Input('showBalance') showBalance!: boolean;
  updateSubscription!: Subscription;
  constructor(private validationService: ValidationService) {}

  ngOnInit(): void {
    this.updateSubscription = this.validationService.updateBalance.subscribe(
      (val:any) => {
        this.tabs[1].count = this.tabs[1].count-val.oldCalc;
        this.tabs[1].count = this.tabs[1].count+val.newCalc;
        this.tabs[2].count=this.tabs[1].count-this.tabs[0].count;
      }
    );
  }

  ngOnDestroy(): void {
    this.updateSubscription?.unsubscribe();
  }
}
