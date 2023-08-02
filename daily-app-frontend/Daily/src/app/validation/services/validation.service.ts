import { HttpClient } from '@angular/common/http';
import { Injectable, OnDestroy } from '@angular/core';
import { Subject, Subscription } from 'rxjs';
import { ApiResponse } from 'src/app/shared/statics/apiResponse';
import { Defines } from 'src/app/shared/statics/defines';
import { environment } from 'src/environments/environment';
import { AggregationData } from '../models/aggregation-data.model';

@Injectable({
  providedIn: 'root',
})
export class ValidationService {
  updateBalance = new Subject<object>();
  pathUrl: string =
    environment.url +
    Defines.VALIDATION_ENDPOINTS.REPORT_SERVICE +
    Defines.VALIDATION_ENDPOINTS.VALIDATION_ENGINE;
  constructor(private http: HttpClient) {}
  public getvalidationList(epochDate: number) {
    let pathUrl =
      this.pathUrl +
      Defines.VALIDATION_ENDPOINTS.AGGREGATION_DATA +
      Defines.CRUD.GET_ALL;
    return this.http.post<ApiResponse<AggregationData[]>>(pathUrl, {
      date: epochDate,
    });
  }
  public retrieveBalance(epochDate: number) {
    let pathUrl =
      this.pathUrl +
      Defines.VALIDATION_ENDPOINTS.BALANCES +
      Defines.CRUD.GET_ALL;
    return this.http.post<ApiResponse<any>>(pathUrl, {
      date: epochDate,
    });
  }
  public submitData(data: AggregationData[]) {
    let pathUrl = this.pathUrl + Defines.CRUD.UPDATE;
    return this.http.post<ApiResponse<any>>(pathUrl, {
      aggregationLists: data,
    });
  }
  public retrieveTransactionTypes() {
    let pathUrl =
      this.pathUrl +
      Defines.VALIDATION_ENDPOINTS.TRANSACTION_TYPE +
      Defines.CRUD.GET_ALL;
    return this.http.get<ApiResponse<any>>(pathUrl, {});
  }
  public getMAxMinRatePlans(epochDate: number) {
    let pathUrl =
      environment.url +
      Defines.VALIDATION_ENDPOINTS.REPORT_SERVICE +
      Defines.VALIDATION_ENDPOINTS.DASH_BOARD +
      Defines.VALIDATION_ENDPOINTS.AGGREGATION_DATA +
      Defines.CRUD.GET;
    return this.http.post<ApiResponse<any>>(pathUrl, {
      date: epochDate,
    });
  }
}
