import {
  animate,
  state,
  style,
  transition,
  trigger,
} from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PrivilegesService } from 'src/app/shared/services/privileges-service/privileges.service';
import { PRIVILEGES } from 'src/app/shared/statics/privileges';
import { Sidebar } from '../../models/sidebar.model';
import { SidebarService } from '../../services/sidebar-service/sidebar.service';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss'],
  animations: [
    // Each unique animation requires its own trigger. The first argument of the trigger function is the name
    trigger('rotatedState', [
      state('close', style({ transform: 'rotate(0)' })),
      state('open', style({ transform: 'rotate(-180deg)' })),
      transition('open => close', animate('200ms ease-out')),
      transition('close => open', animate('200ms ease-in')),
    ]),
  ],
})
export class SidebarComponent implements OnInit {
  state: string = 'close';
  stateName: string = 'Expand';
  sidebarItems: Sidebar[] = [
    {
      icon: 'icon-home',
      title: 'Home Page',
      name: 'Home',
      active: true,
      route: 'home',
    },
    {
      icon: 'icon-services',
      title: 'Organize Service Classes/Tariffs',
      name: 'Service',
      active: false,
      route: 'services',
    },
    {
      icon: 'icon-mark',
      title: 'Rateplans',
      name: 'Rateplans',
      active: false,
      route: 'rateplans',
    },
    {
      icon: 'icon-rateplan',
      title: 'Manage Price Group',
      name: 'Prices',
      active: false,
      route: 'prices',
    },
    {
      icon: 'icon-chart',
      title: 'Manage Validation',
      name: 'Validation',
      active: false,
      route: 'validation',
    },
    {
      icon: 'icon-manual',
      title: 'Manual Adjustment',
      name: 'Manual',
      active: false,
      route: 'manual',
    },
    {
      icon: 'icon-transfer',
      title: 'Transfer Adjustment',
      name: 'Transfer',
      active: false,
      route: 'transfer',
    },
    {
      icon: 'icon-setting',
      title: 'Manage Users',
      name: 'Users',
      active: false,
      route: 'users',
    },
    {
      icon: 'icon-profiles',
      title: 'Profiles Management',
      name: 'Profiles',
      active: false,
      route: 'profiles',
    },
    {
      icon: 'icon-footprint',
      title: 'Footprint Logs',
      name: 'Footprint',
      active: false,
      route: 'footprint',
    },
    {
      icon: 'pi pi-cog',
      title: 'System Configuration',
      name: 'Configuration',
      active: false,
      route: 'systemConfig',
    },
  ];
  viewClasses!: boolean;
  viewPrices!: boolean;
  viewRateplans!: boolean;
  constructor(
    private sidebarService: SidebarService,
    private router: Router,
    private privilegesService: PrivilegesService
  ) {}

  ngOnInit(): void {
    this.viewClasses = this.privilegesService.checkPrivileges(
      PRIVILEGES.SERVICES_CLASSES.GET_ALL
    );
    this.viewPrices = this.privilegesService.checkPrivileges(
      PRIVILEGES.PRICE_GROUP.GET_ALL
    );
    this.viewRateplans = this.privilegesService.checkPrivileges(
      PRIVILEGES.RATE_PLANS.GET_ALL
    );

    if (localStorage.getItem('activeItem')) {
      this.sidebarItems.map((item) => {
        item.active =
          localStorage.getItem('activeItem') === item.name ? true : false;
      });
    }
  }

  toggle() {
    this.state = this.state === 'close' ? 'open' : 'close';
    this.stateName = this.state === 'close' ? 'Expand' : 'Minimize';
    this.sidebarService.stateSubject.next(this.state);
  }

  onSelectItem(activeItem: Sidebar) {
    console.log('Selected item', activeItem);
    localStorage.setItem('activeItem', activeItem.name);
    activeItem.active = true;
    this.sidebarItems.map((item) => {
      item.active = activeItem.name === item.name ? true : false;
    });
    let url = 'daily/' + activeItem.route;
    if (!this.viewClasses && activeItem.route == 'services') {
      url = 'daily/services/tariffs';
    } else if (!this.viewPrices && activeItem.route == 'prices') {
      url = 'daily/prices/pgGroups';
    } else if (!this.viewRateplans && activeItem.route == 'rateplans') {
      url = 'daily/rateplans/rateplansGroups';
    }
    this.router.navigateByUrl(url);
  }
}
