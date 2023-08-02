import { ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { FootprintService } from 'src/app/shared/services/footprint-service/footprint.service';
import { PrivilegesService } from 'src/app/shared/services/privileges-service/privileges.service';
import { PRIVILEGES } from 'src/app/shared/statics/privileges';
import { Config } from '../../models/config.model';
import { ConfigSystemService } from '../../services/configSystem.service';

@Component({
  selector: 'app-config-list',
  templateUrl: './config-list.component.html',
  styleUrls: ['./config-list.component.scss'],
})
export class ConfigListComponent implements OnInit {
  systemConfigForm: FormGroup = new FormGroup({});
  systemConfigs!: any[];
  changedList: Config[] = [];
  enableUpdate!: boolean;
  constructor(
    private configSystemService: ConfigSystemService,
    private privilegesService: PrivilegesService,
    private footprintService: FootprintService
  ) {}

  ngOnInit(): void {
    this.enableUpdate = this.privilegesService.checkPrivileges(
      PRIVILEGES.CONFIGRATION.UPDATE
    );
    this.getData();
  }
  getData() {
    this.configSystemService.getAllConfiguration().subscribe((resp) => {
      this.systemConfigs = resp.payload.systemSettingModelForApplicationHashMap;
      console.log('Config System:', this.systemConfigs);
      let settArr: any = {};
      let patchArr: any = {};
      for (const k in this.systemConfigs) {
        this.systemConfigs[k].map((item: any) => {
          let code = item.code;
          settArr[code] = new FormControl(item.value, []);
          item.type == 1
            ? (patchArr[code] = +item.value)
            : (patchArr[code] = item.value);
        });
      }
      console.log(settArr);
      console.log(patchArr);
      let oldValue = JSON.parse(JSON.stringify(patchArr));
      this.footprintService.handleOldValue(oldValue);
      this.systemConfigForm = new FormGroup(settArr);
      this.systemConfigForm.patchValue(patchArr);
    });
  }
  changeDetected(value: any, elem: Config, itemKey: string) {
    if (this.changedList.length) {
      let obj = this.changedList.find((item) => {
        return item.code == elem.code;
      });
      if (obj) {
        obj.value = value;
      } else {
        this.fillList(elem, itemKey);
      }
    } else {
      this.fillList(elem, itemKey);
    }
  }
  fillList(elem: Config, itemKey: string) {
    this.changedList.push({
      code: elem.code,
      value: elem.value,
      applicationName: itemKey,
    });
  }
  intValue(value: any) {
    let val = Number(value);
    return val ? true : false;
  }
  submit() {
    console.log(this.systemConfigForm.value);
    this.footprintService.handleNewValue(this.systemConfigForm.value);
    this.configSystemService
      .updateConfiguration(this.changedList)
      .subscribe((resp) => {
        console.log(resp);
        this.configSystemService.refresh().subscribe();
        this.getData();
      });
  }
}
