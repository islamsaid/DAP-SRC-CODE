import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { ApiResponse } from 'src/app/shared/statics/apiResponse';
import { Defines } from 'src/app/shared/statics/defines';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TariffsClassesService {
  searchSubject = new Subject<any>();
  clearSubject = new Subject<any>();
  constructor(private http:HttpClient) { }

  getAllServices(type:string) {
    let serviceType = type === "tariffs"? Defines.LOOKUPS.TARIFFS:Defines.LOOKUPS.CLASSES;
    return this.http.post<ApiResponse<any>>(
      environment.url +
        Defines.LOOKUPS.LOOKUP_PATH+serviceType+Defines.CRUD.GET_ALL,
      {}
    );
  }
  updateServices(type:string,list:any) {
    let serviceType = type === "tariffs"? Defines.LOOKUPS.TARIFFS:Defines.LOOKUPS.CLASSES;
    let body = type === "tariffs"? {tariffModelList:list}:{serviceClasseList:list}    
    return this.http.post<ApiResponse<any>>(
      environment.url +
        Defines.LOOKUPS.LOOKUP_PATH+serviceType+Defines.CRUD.UPDATE_BATCH,
      body
    );
  }

}
