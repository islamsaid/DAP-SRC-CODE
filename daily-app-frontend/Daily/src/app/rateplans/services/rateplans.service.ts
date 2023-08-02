import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApiResponse } from 'src/app/shared/statics/apiResponse';
import { Defines } from 'src/app/shared/statics/defines';
import { environment } from 'src/environments/environment';
import { Rateplan } from '../models/rateplan.model';
import { RateplanGroup } from '../models/rateplanGroup.model';

@Injectable({
  providedIn: 'root',
})
export class RateplansService {
  baseUrl: string = environment.url + Defines.LOOKUPS.LOOKUP_PATH;
  constructor(private http: HttpClient) {}

  public getAllRatePlans() {
    return this.http.post<ApiResponse<any>>(
      this.baseUrl +
        Defines.RATEPLANS_ENDPOINTS.RATEPALN +
        Defines.CRUD.GET_ALL,
      {}
    );
  }
  public updateRatePlan(rateplans: Rateplan[]) {
    let updatedrateplans: any[] = [];
    for (let rateplan of rateplans) {
      updatedrateplans.push({
        ratePlanCode: rateplan.ratePlanCode,
        showFlag: rateplan.showFlag,
        ratePlanGroupKey: rateplan.ratePlanGroupKey,
      });
    }
    return this.http.post<ApiResponse<any>>(
      this.baseUrl + Defines.RATEPLANS_ENDPOINTS.RATEPALN + Defines.CRUD.UPDATE,
      {
        ...updatedrateplans,
      }
    );
  }
  public updateRateplansList(rateplans: Rateplan[]) {
    console.log(rateplans);
    let updatedrateplans: any[] = [];
    for (let rateplan of rateplans) {
      updatedrateplans.push({
        ratePlanCode: rateplan.ratePlanCode,
        showFlag: rateplan.showFlag,
        ratePlanGroupKey: rateplan.ratePlanGroupKey,
      });
    }
    return this.http.post<ApiResponse<any>>(
      this.baseUrl +
        Defines.RATEPLANS_ENDPOINTS.RATEPALN +
        Defines.CRUD.UPDATE_BATCH,
      {
        ratePlans: [...updatedrateplans],
      }
    );
  }
  public getRateplaneGroup(key: any) {
    return this.http.post<ApiResponse<any>>(
      this.baseUrl +
        Defines.RATEPLANS_ENDPOINTS.RATEPALNGROUPS +
        Defines.CRUD.GET,
      { ratePlanGroupKey: parseInt(key) }
    );
  }
  public getAllRatePlanGroups() {
    return this.http.post<ApiResponse<any>>(
      this.baseUrl +
        Defines.RATEPLANS_ENDPOINTS.RATEPALNGROUPS +
        Defines.CRUD.GET_ALL,
      {}
    );
  }
  public updateRatePlanGroup(rateplanGroup: RateplanGroup) {
    return this.http.post<ApiResponse<any>>(
      this.baseUrl +
        Defines.RATEPLANS_ENDPOINTS.RATEPALNGROUPS +
        Defines.CRUD.UPDATE,
      { ...rateplanGroup }
    );
  }
  public createRateplanGroup(rateplaneGroup: RateplanGroup) {
    return this.http.post<ApiResponse<any>>(
      this.baseUrl +
        Defines.RATEPLANS_ENDPOINTS.RATEPALNGROUPS +
        Defines.CRUD.ADD,
      rateplaneGroup
    );
  }
  public deletRateplanGroup(ratePlanGroupKey: number) {
    return this.http
      .post<ApiResponse<any>>(
        this.baseUrl +
          Defines.RATEPLANS_ENDPOINTS.RATEPALNGROUPS +
          Defines.CRUD.DELETE,
        { ratePlanGroupKey }
      )
  }

}
