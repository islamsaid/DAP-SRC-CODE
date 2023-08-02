import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { ApiResponse } from 'src/app/shared/statics/apiResponse';
import { Defines } from 'src/app/shared/statics/defines';
import { environment } from 'src/environments/environment';
import { Profile } from '../models/profile.model';

@Injectable({
  providedIn: 'root',
})
export class ProfilesService {
  showSubject = new Subject<boolean>();
  constructor(private http: HttpClient) {}
  getProfilesList() {
    return this.http.post<ApiResponse<any>>(
      environment.url +
        Defines.USERS_ENDPOINTS.USERS_MANAGEMENT_PATH +
        Defines.PROFILES_ENDPOINTS.GET_PROFILES_LIST,
      {}
    );
  }

  manageProfile(profile: Profile, reqType?: string) {
    let action: string;
    if (reqType === 'edit') {
      action = Defines.PROFILES_ENDPOINTS.UPDATE_PROFILE;
    } else {
      action = Defines.PROFILES_ENDPOINTS.CREATE_PROFILE;
    }
    let body = { profile: profile };
    return this.http.post<ApiResponse<any>>(
      environment.url + Defines.USERS_ENDPOINTS.USERS_MANAGEMENT_PATH + action,
      body
    );
  }

  getProfileById(id: number) {
    let body = { profileId: id };
    return this.http.post(
      environment.url +
        Defines.USERS_ENDPOINTS.USERS_MANAGEMENT_PATH +
        Defines.PROFILES_ENDPOINTS.GET_PROFILE_BY_ID,
      body
    );
  }

  deleteProfile(id: number) {
    return this.http
      .post(
        environment.url +
          Defines.USERS_ENDPOINTS.USERS_MANAGEMENT_PATH +
          Defines.PROFILES_ENDPOINTS.DELETE_PROFILE,
        { profileId: id }
      )
  }
  getPrivilegesList() {
    let urlPath =
      environment.url +
      Defines.USERS_ENDPOINTS.USERS_MANAGEMENT_PATH +
      Defines.PRIVILEGES_ENDPOINTS.PRIVILEGE +
      Defines.CRUD.GET_ALL;
    return this.http.post<ApiResponse<any>>(urlPath, {});
  }
}
