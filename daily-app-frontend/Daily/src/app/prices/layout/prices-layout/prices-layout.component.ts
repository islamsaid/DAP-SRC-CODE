import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-prices-layout',
  templateUrl: './prices-layout.component.html',
  styleUrls: ['./prices-layout.component.scss'],
})
export class PricesLayoutComponent implements OnInit {
  items!: {
    label: string;
    routerLink: string[];
    routerLinkActiveOptions: string;
  }[];

  constructor() {}

  ngOnInit(): void {
    this.items = [
      {
        label: 'Prices Groups',
        routerLink: ['groups'],
        routerLinkActiveOptions: '{ exact: true }',
      },
      {
        label: 'PG Groups',
        routerLink: ['pgGroups'],
        routerLinkActiveOptions: '{ exact: true }',
      },
    ];
  }
}
