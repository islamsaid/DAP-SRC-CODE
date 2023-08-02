import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, tap } from 'rxjs';
import { DialogData } from 'src/app/shared/models/dialog.model';
import { ApiResponse } from 'src/app/shared/statics/apiResponse';
import { Defines } from 'src/app/shared/statics/defines';
import { environment } from 'src/environments/environment';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root',
})
export class UsersService {
  pathUrl: string =
    environment.url +
    Defines.USERS_ENDPOINTS.USERS_MANAGEMENT_PATH +
    Defines.USERS_ENDPOINTS.USER;
  openDialog: boolean = false;
  dialogConfig!: DialogData;
  private usersList = new BehaviorSubject<User[]>([]);
  constructor(private http: HttpClient) {}

  get usersList$() {
    return this.usersList.asObservable();
  }

  openUserDialog(id?: number, viewMode?: boolean) {
    this.openDialog = true;
    let header = viewMode ? 'View Details' : 'Edit User';
    this.dialogConfig = {
      header: id ? header : 'New User',
      name: 'user-management',
      icon: 'icon icon-user',
      view:viewMode,
      inputs: [
        {
          label: 'Display Name',
          element: 'input',
          type: 'text',
          fieldId: 'name',
          required: id ? false : true,
        },
        {
          label: 'Username',
          element: 'input',
          type: 'text',
          fieldId: 'username',
          required: id ? false : true,
        },
        {
          label: 'User Profile',
          element: 'dropdown',
          type: 'singleSelect',
          fieldId: 'profileId',
          required: id ? false : true,
        },
      ],
      buttons: [
        {
          label: id ? 'Update' : 'Create',
          type: 'submit',
        },
        {
          label: 'Cancel',
          type: 'reset',
        },
      ],
      id: id,
    };
  }

  public getUsersList() {
    let pathUrl = this.pathUrl + Defines.CRUD.GET_ALL;
    return this.http
      .post<ApiResponse<User[]>>(pathUrl, '')
      .pipe(
        tap((resp) => {
          resp.payload?.users.map((user:any)=>{
            user.profileName = user.profileModel.name;
          })
          this.usersList.next(resp.payload?.users);
        })
      )
      .subscribe();
  }

  public addUser(user: User) {
    let pathUrl = this.pathUrl + Defines.CRUD.ADD;
    return this.http
      .post<ApiResponse<User[]>>(pathUrl, { ...user, lockFlag: 0 })
      .pipe(
        tap((resp) => {
          this.getUsersList();
        })
      )
      .subscribe();
  }

  public updateUser(user: User) {
    let pathUrl = this.pathUrl + Defines.CRUD.UPDATE;
    return this.http
      .post<ApiResponse<any>>(pathUrl, user)
      .pipe(
        tap((resp) => {
          console.log(resp);
          this.getUsersList();
        })
      )
      .subscribe();
  }
  public getUser(id: number) {
    console.log(id);
    let pathUrl = this.pathUrl + Defines.CRUD.GET;
    return this.http.post<ApiResponse<any>>(pathUrl, { userId: id });
  }
  public deleteUser(id: number) {
    let pathUrl = this.pathUrl + Defines.CRUD.DELETE;
    return this.http
      .post<ApiResponse<any>>(pathUrl, { userId: id })
      .pipe(
        tap((resp) => {
          console.log(resp);
          this.getUsersList();
        })
      )
      .subscribe();
  }
}
