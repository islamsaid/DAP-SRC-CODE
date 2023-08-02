import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-services-layout',
  templateUrl: './services-layout.component.html',
  styleUrls: ['./services-layout.component.scss'],
})
export class ServicesLayoutComponent implements OnInit {
  items!: MenuItem[];
  constructor() {}

  ngOnInit(): void {
    this.items = [
      {
        label: 'Services Classes',
        routerLink: ["classes"],
        routerLinkActiveOptions: '{ exact: true }',
      },
      {
        label: 'Tariffs',
        routerLink: ["tariffs"],
        routerLinkActiveOptions: '{ exact: true }',
      },
    ];
  }
}
