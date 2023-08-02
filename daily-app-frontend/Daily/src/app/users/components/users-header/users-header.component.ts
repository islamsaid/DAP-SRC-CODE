import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { DialogData } from 'src/app/shared/models/dialog.model';
import { PrivilegesService } from 'src/app/shared/services/privileges-service/privileges.service';
import { PRIVILEGES } from 'src/app/shared/statics/privileges';
import { UsersService } from '../../services/users.service';

@Component({
  selector: 'app-users-header',
  templateUrl: './users-header.component.html',
  styleUrls: ['./users-header.component.scss'],

})
export class UsersHeaderComponent implements OnInit {
  openDialog!: boolean;
  dialogConfig!: DialogData;
  enableAdd!: boolean;
  profilesAllow!: boolean;
  constructor(private router: Router, private usersService: UsersService,
    private privilegesService:PrivilegesService) { }

  ngOnInit(): void {
    this.enableAdd = this.privilegesService.checkPrivileges(
      PRIVILEGES.USERS_MANAGMENT.ADD
    );
    this.profilesAllow = this.privilegesService.checkPrivileges(
      PRIVILEGES.PROFILES_MANAGMENT.GET_ALL
    );
  }
  openAddUserDialog() {
    this.usersService.openUserDialog();
    this.openDialog = this.usersService.openDialog;
    this.dialogConfig = this.usersService.dialogConfig;
  }

  openProfilesManagement() {
    this.router.navigateByUrl('daily/profiles/list')
  }
}
