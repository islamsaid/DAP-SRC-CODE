import { HttpClient, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { ApiResponse } from 'src/app/shared/statics/apiResponse';
import { Defines } from 'src/app/shared/statics/defines';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class HeaderService {
  DateSubject = new Subject<number>();
  notificationsSubject = new Subject<any[]>();
  calendarDate: any = localStorage.getItem('date');
  pathUrl: string =
    environment.url +
    Defines.NOTIFICTION_ENDPOINTS.NOTIFICATION_SERVICE +
    Defines.NOTIFICTION_ENDPOINTS.NOTIFICATIONS;
  constructor(private http: HttpClient) {}
  public getNotifications() {
    let pathUrl = this.pathUrl + Defines.CRUD.GET_ALL;
    return this.http.post<ApiResponse<Notification[]>>(pathUrl, {});
  }
}
