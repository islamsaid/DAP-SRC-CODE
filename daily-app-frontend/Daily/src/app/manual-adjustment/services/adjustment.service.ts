import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApiResponse } from 'src/app/shared/statics/apiResponse';
import { Defines } from 'src/app/shared/statics/defines';
import { environment } from 'src/environments/environment';
import { ManualAdjustment } from '../models/manual-adjustment.model';

@Injectable({
  providedIn: 'root',
})
export class AdjustmentService {
  constructor(private http: HttpClient) {}

  getManualAdjustment(date: number) {
    let body = { date: date };
    return this.http.post<ApiResponse<any>>(
      environment.url +
        Defines.ADJUSTMENT_ENDPOINTS.REPORT_SERVICE +
        Defines.ADJUSTMENT_ENDPOINTS.MANUAL_ADJUSTMENT +
        Defines.CRUD.GET_ALL,
      body
    );
  }

  updateManualAdjustment(list: ManualAdjustment[]) {
    return this.http.post<ApiResponse<any>>(
      environment.url +
        Defines.ADJUSTMENT_ENDPOINTS.REPORT_SERVICE +
        Defines.ADJUSTMENT_ENDPOINTS.MANUAL_ADJUSTMENT +
        Defines.CRUD.UPDATE,
      { aggregationList: list }
    );
  }
}
