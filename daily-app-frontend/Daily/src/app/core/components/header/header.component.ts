import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/authentication/services/authentication.service';
import { PrivilegesService } from 'src/app/shared/services/privileges-service/privileges.service';
import { Defines } from 'src/app/shared/statics/defines';
import { PRIVILEGES } from 'src/app/shared/statics/privileges';
import { HeaderService } from '../../services/header-service/header.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent implements OnInit, OnDestroy {
  dateValue!: any;
  notifications!: string[];
  interval!: any;
  viewNotification!: boolean;
  userName!: any;
  audio = new Audio('./assets/sound/pull-out.wav');

  constructor(
    private headerService: HeaderService,
    private router: Router,
    private privilegesService: PrivilegesService,
    private authenticationService: AuthenticationService
  ) {}

  ngOnInit(): void {
    this.userName = localStorage.getItem('activeUser');
    this.viewNotification = this.privilegesService.checkPrivileges(
      PRIVILEGES.NOTIFICATIONS.GET_ALL
    );
    this.viewNotification ? this.getNotfic() : '';
    this.viewNotification
      ? (this.interval = setInterval(() => this.getNotfic(), 30000))
      : '';
    this.dateValue = localStorage.getItem('date')
      ? new Date(+this.headerService.calendarDate)
      : new Date();
    localStorage.setItem('date', String(Date.parse(this.dateValue)));
    console.log(this.dateValue);
    this.headerService.DateSubject.next(Date.parse(this.dateValue));
  }
  change() {
    console.log(this.dateValue);

    localStorage.setItem('date', Date.parse(this.dateValue) + '');
    this.headerService.DateSubject.next(Date.parse(this.dateValue));
  }
  alert() {
    var resp = this.audio.play();
    if (resp !== undefined) {
      resp.then((_) => {}).catch((error) => {});
    }
  }
  getNotfic() {
    this.headerService.getNotifications().subscribe((resp) => {
      if (this.notifications) {
        if (resp?.payload?.length > this.notifications?.length) {
          this.alert();
          console.log('new notification');
        }
      }
      this.notifications = resp.payload;
      //   .map((item: any) => {
      //   return item.notificationHeader;
      // });
      this.headerService.notificationsSubject.next(resp.payload);
    });
  }
  ngOnDestroy(): void {
    this.viewNotification ? clearInterval(this.interval) : '';
  }
  getPage(notic: any, op: any, event: any) {
    console.log(notic);

    notic.notificationType == Defines.NOTIFICATION_TYPE.PRICE_GROUP
      ? this.router.navigateByUrl('/daily/prices/groups')
      : '';
    notic.notificationType == Defines.NOTIFICATION_TYPE.RATE_PALEN
      ? this.router.navigateByUrl('/daily/rateplans/rateplans')
      : '';
    notic.notificationType == Defines.NOTIFICATION_TYPE.SERVICE_CLASS
      ? this.router.navigateByUrl('/daily/services/classes')
      : '';
    notic.notificationType == Defines.NOTIFICATION_TYPE.TARRIF_MODEL
      ? this.router.navigateByUrl('/daily/services/tariffs')
      : '';
    op.hide(event);
  }
  logout() {
    this.authenticationService
      .logout(this.userName)
      .subscribe((response: any) => console.log('logout resp', response));
    localStorage.clear();
    this.userName = '';
    this.router.navigateByUrl('/login');
  }
}
