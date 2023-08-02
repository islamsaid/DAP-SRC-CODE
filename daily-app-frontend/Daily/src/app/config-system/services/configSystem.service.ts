import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApiResponse } from 'src/app/shared/statics/apiResponse';
import { Defines } from 'src/app/shared/statics/defines';
import { environment } from 'src/environments/environment';
import { Config } from '../models/config.model';

@Injectable({
  providedIn: 'root',
})
export class ConfigSystemService {
  constructor(private http: HttpClient) {}

  getAllConfiguration() {
    return this.http.get<ApiResponse<any>>(
      environment.url +
        Defines.SYSTEM_CONFIG_ENDPOINTS.CONFIG_SERVICE +
        Defines.SYSTEM_CONFIG_ENDPOINTS.SYSTEM_SETTING +
        Defines.CRUD.GET_ALL,
      {}
    );
  }
  updateConfiguration(data: Config[]) {
    let body = {
      updateSystemSettingModelRequestList: data,
    };
    return this.http.post<ApiResponse<any>>(
      environment.url +
        Defines.SYSTEM_CONFIG_ENDPOINTS.CONFIG_SERVICE +
        Defines.SYSTEM_CONFIG_ENDPOINTS.SYSTEM_SETTING +
        Defines.CRUD.UPDATE,
      body
    );
  }
  refresh() {
    return this.http.post<ApiResponse<any>>(
      environment.url +
        Defines.SYSTEM_CONFIG_ENDPOINTS.CONFIG_SERVICE +
        Defines.SYSTEM_CONFIG_ENDPOINTS.ACTUATOR +
        Defines.SYSTEM_CONFIG_ENDPOINTS.REFRESH,
      {}
    );
  }
}
