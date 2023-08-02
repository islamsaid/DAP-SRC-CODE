import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { TableModel } from 'src/app/shared/models/table.model';
import { PrivilegesService } from 'src/app/shared/services/privileges-service/privileges.service';
import { PRIVILEGES } from 'src/app/shared/statics/privileges';
import { User } from '../../models/user';
import { UsersService } from '../../services/users.service';

@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html',
  styleUrls: ['./users-list.component.scss'],
})
export class UsersListComponent implements OnInit {
  usersList: Observable<any[]> = this.usersService.usersList$;
  usersTable = new TableModel();
  headers = [
    { fieldId: 'name', title: 'Display Name' },
    { fieldId: 'username', title: 'Username' },
    { fieldId: 'profileName', title: 'Profile' },
  ];
  extraCols = [
    { fieldId: 'lockFlag', title: 'Actions', show: true },
    { fieldId: 'search', title: 'search', show: true },
  ];
  enableUpdate!: boolean;
  enableDelete!: boolean;
  viewDetails!: boolean;

  constructor(
    private usersService: UsersService,
    private privilegesService: PrivilegesService
  ) {}

  ngOnInit(): void {
    this.enableUpdate = this.privilegesService.checkPrivileges(
      PRIVILEGES.USERS_MANAGMENT.UPDATE
    );
    this.enableDelete = this.privilegesService.checkPrivileges(
      PRIVILEGES.USERS_MANAGMENT.DELETE
    );
    this.viewDetails = this.privilegesService.checkPrivileges(
      PRIVILEGES.USERS_MANAGMENT.GET_BY_ID
    );
    this.usersService.getUsersList();
  }
  fillTable(users: User[]) {
    if (this.enableDelete && this.enableUpdate) {
    }
    this.usersTable.cols = this.headers;
    this.usersTable.extracols = this.extraCols;
    this.usersTable.pagination = true;
    this.usersTable.name = 'users';
    this.usersTable.data = users;
    this.usersTable.lockFlag = true;
    this.usersTable.editFlag = true;
    this.usersTable.deleteFlag = true;
    this.usersTable.globalFilterFields = [
      'name',
      'username',
      'profileName',
    ];
  }
}
