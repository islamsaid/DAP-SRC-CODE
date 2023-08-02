import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApiResponse } from 'src/app/shared/statics/apiResponse';
import { Defines } from 'src/app/shared/statics/defines';
import { environment } from 'src/environments/environment';
import { Transfer } from '../models/transferAdjus.model';

@Injectable({
  providedIn: 'root',
})
export class TransferAdjustmentService {
  constructor(private http: HttpClient) {}

  getTransferAdjustment(date: number) {
    let body = { date: date };
    return this.http.post<ApiResponse<any>>(
      environment.url +
        Defines.TRANSFER_ENDPOINTS.REPORT_SERVICE +
        Defines.TRANSFER_ENDPOINTS.TRANSFER_ADJUSTMENT +
        Defines.TRANSFER_ENDPOINTS.AGGREGATION_DATA +
        Defines.CRUD.GET_ALL,
      body
    );
  }

  updateTransferAdjustment(data: Transfer[]) {
    let body = { addTransferAdjustmentRequests: data };
    return this.http.post<ApiResponse<any>>(
      environment.url +
        Defines.TRANSFER_ENDPOINTS.REPORT_SERVICE +
        Defines.TRANSFER_ENDPOINTS.TRANSFER_ADJUSTMENT +
        Defines.TRANSFER_ENDPOINTS.AGGREGATION_DATA +
        Defines.CRUD.ADD,
      body
    );
  }
}
