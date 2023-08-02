import { Component, OnDestroy, OnInit } from '@angular/core';
import { ProfilesService } from '../../services/profiles.service';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Profile } from '../../models/profile.model';
import { TableModel } from 'src/app/shared/models/table.model';
import { PrivilegesService } from 'src/app/shared/services/privileges-service/privileges.service';
import { PRIVILEGES } from 'src/app/shared/statics/privileges';

@Component({
  selector: 'app-profiles-list',
  templateUrl: './profiles-list.component.html',
  styleUrls: ['./profiles-list.component.scss'],
})
export class ProfilesListComponent implements OnInit, OnDestroy {
  profilesTable = new TableModel();
  profilesList: Profile[] = [];
  headers = [{ fieldId: 'name', title: 'Profile' }];

  extraCols = [
    { fieldId: 'lockFlag', title: 'Actions', show: true },
    { fieldId: 'search', title: 'search', show: true },
  ];
  listSubscription!: Subscription;
  enableDelete!: boolean;
  enableUpdate!: boolean;
  viewDetails!: boolean;
  constructor(
    private profilesService: ProfilesService,
    private router: Router,
    private privilegesService:PrivilegesService
  ) {}

  ngOnInit(): void {
    this.enableUpdate = this.privilegesService.checkPrivileges(
      PRIVILEGES.PROFILES_MANAGMENT.UPDATE
    );
    this.enableDelete = this.privilegesService.checkPrivileges(
      PRIVILEGES.PROFILES_MANAGMENT.DELETE
    );
    this.viewDetails = this.privilegesService.checkPrivileges(
      PRIVILEGES.PROFILES_MANAGMENT.GET_BY_ID
    );
    this.profilesService.showSubject.next(this.router.url.includes('list'));
    this.listSubscription = this.profilesService
      .getProfilesList()
      .subscribe((response: any) => {
        console.log('ProfilesList', response['payload']['profilesList']);
        this.profilesList = response['payload']['profilesList'];
        this.fillTable();
      });
  }

  fillTable() {
    this.profilesTable.cols = this.headers;
    this.profilesTable.extracols = this.extraCols;
    this.profilesTable.pagination = true;
    this.profilesTable.lockFlag = true;
    this.profilesTable.editFlag = true;
    this.profilesTable.deleteFlag = true;
    this.profilesTable.name = 'profiles';
    this.profilesTable.globalFilterFields = ['name'];
    this.profilesTable.data = this.profilesList;
  }

  ngOnDestroy(): void {
    this.listSubscription?.unsubscribe();
  }
}
