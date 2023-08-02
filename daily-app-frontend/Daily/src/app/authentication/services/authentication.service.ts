import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Defines } from 'src/app/shared/statics/defines';
import { environment } from 'src/environments/environment';
import { User } from '../../users/models/user';

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  headers = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(private router: Router, private http: HttpClient) {}

  login(loginInfo: User) {
    return this.http.post(
      environment.url +
        Defines.USERS_ENDPOINTS.USERS_MANAGEMENT_PATH +
        Defines.AUTH_ENDPOINTS.LOGIN_PATH,
      loginInfo
    );
  }

  storeToken(userName: string, token: string) {
    return this.http.post(
      environment.url +
        Defines.AUTH_ENDPOINTS.HANDLER_PATH +
        Defines.AUTH_ENDPOINTS.TOKEN_PATH,
      {
        username: userName,
        token: token,
      }
    );
  }

  logout(userName: string) {
    return this.http.post(
      environment.url +
        Defines.USERS_ENDPOINTS.USERS_MANAGEMENT_PATH +
        Defines.AUTH_ENDPOINTS.LOGOUT_PATH,
      { username: userName }
    );
  }
}
