import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Footprint } from '../../models/footprint';
import { ApiResponse } from '../../statics/apiResponse';
import { Defines } from '../../statics/defines';

@Injectable({
  providedIn: 'root',
})
export class FootprintService {
  oldValue!: any;
  newValue!: any;
  objectIdentifier!: any;
  filterKey!: string;
  filterKeyNew!: string;
  filterValues!: any[];
  footprintSubject = new Subject<any>();
  openDialog = new Subject<{ show: boolean; empty: boolean; data?: any }>();
  refreshFlag!: boolean;
  constructor(private http: HttpClient) {}
  footprintRequest(footprint: Footprint) {
    let body = footprint;
    return this.http.post<ApiResponse<any>>(
      environment.url +
        Defines.FOOTPRINT.LOGGING_SERVICE +
        Defines.FOOTPRINT.LOGGING +
        Defines.FOOTPRINT.ADD_LOG,
      body
    );
  }

  getFootprintsRequest(body: any) {
    return this.http.post<ApiResponse<any>>(
      environment.url +
        Defines.FOOTPRINT.LOGGING_SERVICE +
        Defines.FOOTPRINT.LOGGING +
        Defines.FOOTPRINT.GET_LOGS,
      body
    );
  }

  handleOldValue(value: any, key?: string) {
    if (Array.isArray(value)) {
      this.oldValue = [];
      this.filterKey = key ? key : '';
      this.oldValue = value;
    } else this.oldValue = value;
  }
  handleNewValue(value: any, key?: string) {
    if (Array.isArray(value)) {
      this.newValue = [];
      this.filterKeyNew = key ? key : '';
      this.newValue = value;
    } else this.newValue = value;
  }

  filterOldValues() {
    // this method to return only old data before changed (update patch case)
    this.filterValues = [];
    if (Array.isArray(this.oldValue)) {
      this.oldValue.map((val) => {
        let changedVal = this.newValue.filter(
          (item: any) => item[this.filterKey] == val[this.filterKey]
        );
        let identifierId = val[this.filterKey];
        let allValues = {
          oldValues: val,
          newValues: changedVal[0],
          identifier: identifierId,
        };
        if (changedVal.length) this.filterValues.push(allValues);
      });
    }
    return this.filterValues;
  }

  addMultipeNewVal() {
    this.filterValues = [];
    if (Array.isArray(this.newValue)) {
      this.newValue.map((val) => {
        let identifierId = val[this.filterKeyNew];
        let allValues = {
          oldValues: {},
          newValues: val,
          identifier: identifierId,
        };
        this.filterValues.push(allValues);
      });
    }
    return this.filterValues;
  }
  handlePriceGroupOldValue(response: any) {
    let oldpriceGroups = response.payload.priceGroupModel.map((item: any) => {
      let oldValue = {
        priceGroupCode: item.priceGroupCode,
        pgGroupKey: item.pgGroupKey?.pgGroupKey,
      };
      return oldValue;
    });
    this.handleOldValue(oldpriceGroups, 'priceGroupCode');
  }

  handleRateplansoldValue(ratePlans: any[]) {
    let requriedValues = ratePlans.map((item) => {
      return {
        ratePlanCode: item.ratePlanCode,
        ratePlanGroupKey: item.ratePlanGroupKey,
        showFlag: item.showFlag,
      };
    });
    return requriedValues;
  }

  getTodayInTime() {
    let epochDate: any = new Date();
    epochDate = Date.parse(epochDate);
    return epochDate;
  }
}
