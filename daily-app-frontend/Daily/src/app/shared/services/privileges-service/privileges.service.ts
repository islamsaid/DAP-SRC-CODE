import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class PrivilegesService {
  constructor() {}
  checkPrivileges(privilege: number) {
    let profilePrivileges = JSON.parse('' + localStorage.getItem('privileges'));
    let exist = profilePrivileges?.find((item: any) => item.id === privilege);
    if (exist) return true;
    else return false;
  }
}
