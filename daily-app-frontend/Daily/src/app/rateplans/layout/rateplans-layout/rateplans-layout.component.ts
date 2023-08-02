import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-rateplans-layout',
  templateUrl: './rateplans-layout.component.html',
  styleUrls: ['./rateplans-layout.component.scss'],
})
export class RateplansLayoutComponent implements OnInit {
  items!: MenuItem[];
  constructor() {}

  ngOnInit(): void {this.items = [
    {
      label: 'Rateplans',
      routerLink: ['rateplans'],
      routerLinkActiveOptions: '{ exact: true }',
    },
    {
      label: 'Rateplans Groups',
      routerLink: ['rateplansGroups'],
      routerLinkActiveOptions: '{ exact: true }',
    },
  ];
  }
}
