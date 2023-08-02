import { ChangeDetectorRef, Component, Input, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { TariffsClassesService } from 'src/app/services-tariffs/services/tariffs-classes.service';
import { Inputs } from '../../models/inputs.model';

@Component({
  selector: 'app-service-form',
  templateUrl: './service-form.component.html',
  styleUrls: ['./service-form.component.scss'],
})
export class ServiceFormComponent implements OnInit {
  @Input('source') source!: string;
  @Input('inputs') inputs!: Inputs[];
  @Input('isInline') isInline!:boolean;
  serviceType: string = 'Service classes';
  searchForm!: FormGroup;
  constructor(private tariffClassesService: TariffsClassesService) {}

  ngOnInit(): void {
    this.initForm();
  }

  initForm() {
    switch (this.source) {
      case 'tariffs':
        this.searchForm = new FormGroup({
          tariffModelName: new FormControl(),
          tariffModelType: new FormControl(),
          hybird: new FormControl(),
          contractType: new FormControl(),
          activationSource: new FormControl(),
          bundleType: new FormControl(),
          deactivationSourceFlag: new FormControl(),
        });

        break;
      case 'classes':
        this.searchForm = new FormGroup({
          serviceClassName: new FormControl(),
          serviceClassType: new FormControl(),
          hybird: new FormControl(),
          contractType: new FormControl(),
          activationSource: new FormControl(),
          bundleType: new FormControl(),
          deacSourceFlag: new FormControl(),
        });
        break;
      case 'rateplans':
        this.searchForm = new FormGroup({
          ratePlan: new FormControl(),
          ratePlanType: new FormControl(),
          activationSourceFlag: new FormControl(),
          contractType: new FormControl(),
          showFlag: new FormControl(),
          ratePlanGroupKey: new FormControl(),
        });
        break;
      case 'rateplansGroups':
        this.searchForm = new FormGroup({
          description: new FormControl(),
          ratePlanGroup: new FormControl(),
          showFlag: new FormControl(),
        });
        break;

      case 'prices':
        this.searchForm = new FormGroup({
          priceGroup: new FormControl(),
          pgGroup: new FormControl(),
        });
        break;
      case 'pgGroups':
        this.searchForm = new FormGroup({
          pgGroup: new FormControl(),
          description: new FormControl(),
          showFlag: new FormControl(),
        });
        break;
      case 'footprint':
        this.searchForm = new FormGroup({
          userId: new FormControl(),
          date: new FormControl(),
        });
        break;
    }
  }

  onSubmit() {
    this.tariffClassesService.searchSubject.next(this.searchForm.value);
  }
  

  resetForm() {
    this.searchForm.reset();
    this.tariffClassesService.clearSubject.next(true);
  }
}
