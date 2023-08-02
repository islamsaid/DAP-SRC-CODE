import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApiResponse } from 'src/app/shared/statics/apiResponse';
import { Defines } from 'src/app/shared/statics/defines';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class PricesService {
  constructor(private http: HttpClient) {}

  getAll(type: string) {
    let serviceType =
      type === 'prices' ? Defines.LOOKUPS.PRICES : Defines.LOOKUPS.PG;
    return this.http.get<ApiResponse<any>>(
      environment.url +
        Defines.LOOKUPS.LOOKUP_PATH +
        serviceType +
        Defines.CRUD.GET_ALL,
      {}
    );
  }
  managePgGroup(body: any, editMode?: boolean) {
    let status = editMode ? Defines.CRUD.UPDATE : Defines.CRUD.ADD;
    return this.http.post<ApiResponse<any>>(
      environment.url +
        Defines.LOOKUPS.LOOKUP_PATH +
        Defines.LOOKUPS.PG +
        status,
      body
    );
  }

  getPgGroupById(id: number) {
    let body = { pgGroupId: id };
    return this.http.post<ApiResponse<any>>(
      environment.url +
        Defines.LOOKUPS.LOOKUP_PATH +
        Defines.LOOKUPS.PG +
        Defines.CRUD.GET,
      body
    );
  }
  
  updatePriceGroups(type: string, list: any) {
    let body = { priceGroupList: list };
    return this.http.post<ApiResponse<any>>(
      environment.url +
        Defines.LOOKUPS.LOOKUP_PATH +
        Defines.LOOKUPS.PRICES +
        Defines.CRUD.UPDATE_BATCH,
      body
    );
  }
  deletePriceGroup(id: any) {
    let body = { pgGroupId: id };
    return this.http.post<ApiResponse<any>>(
      environment.url +
        Defines.LOOKUPS.LOOKUP_PATH +
        Defines.LOOKUPS.PG +
        Defines.CRUD.DELETE,
      body
    );
  }
}
