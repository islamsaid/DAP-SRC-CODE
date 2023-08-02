import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { Notification } from 'src/app/core/models/notification.model';
import { HeaderService } from 'src/app/core/services/header-service/header.service';
import { PrivilegesService } from 'src/app/shared/services/privileges-service/privileges.service';
import { Defines } from 'src/app/shared/statics/defines';
import { PRIVILEGES } from 'src/app/shared/statics/privileges';

@Component({
  selector: 'app-home-layout',
  templateUrl: './home-layout.component.html',
  styleUrls: ['./home-layout.component.scss'],
})
export class HomeLayoutComponent implements OnInit, OnDestroy {
  notificationsSubsc!: Subscription;
  priceGroups: any[] = [];
  serviceClasses: any[] = [];
  tarrifsModels: any[] = [];
  viewNotification!: boolean;
  viewBalnace!: boolean;
  viewMaxMin!: boolean;
  viewpriceGroup!: boolean;
  constructor(
    private headerService: HeaderService,
    private privilegesService: PrivilegesService
  ) {}

  ngOnInit(): void {
    this.headerService.getNotifications().subscribe((resp) => {
      this.headerService.notificationsSubject.next(resp.payload);
    });

    this.viewNotification = this.privilegesService.checkPrivileges(
      PRIVILEGES.NOTIFICATIONS.GET_ALL
    );
    this.viewBalnace = this.privilegesService.checkPrivileges(
      PRIVILEGES.VALIDATION.BALANCE
    );
    this.viewMaxMin = this.privilegesService.checkPrivileges(
      PRIVILEGES.DASHBOARD.MAX_MIN_AGG_DATE
    );
    this.viewpriceGroup = this.privilegesService.checkPrivileges(
      PRIVILEGES.PRICE_GROUP.GET_ALL
    );
    this.notificationsSubsc = this.headerService.notificationsSubject.subscribe(
      (resp) => {
        resp?.map((item) => {
          if (item.notificationType == Defines.NOTIFICATION_TYPE.PRICE_GROUP) {
            this.checkList(this.priceGroups, item);
          }
          if (
            item.notificationType == Defines.NOTIFICATION_TYPE.SERVICE_CLASS
          ) {
            this.checkList(this.serviceClasses, item);
          }
          if (item.notificationType == Defines.NOTIFICATION_TYPE.TARRIF_MODEL) {
            this.checkList(this.tarrifsModels, item);
          }
        });
      }
    );
  }

  fillList(list: any, item: any) {
    let timeArr = item.creationDate.split('-');
    let date = new Date(timeArr[0], timeArr[1] - 1, timeArr[2]);
    let days = new Date().getTime() - new Date(date).getTime();
    days = days / (1000 * 3600 * 24);

    list.push({
      name: item.dataName,
      date: ~~days,
      dataId: item.dataId,
    });
  }
  checkList(list: any[], item: any) {
    let check;
    check = list.find((elem) => elem.dataId == item.dataId);
    check ? '' : this.fillList(list, item);
  }
  ngOnDestroy(): void {
    this.notificationsSubsc.unsubscribe();
  }
}
