import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { Inputs } from 'src/app/shared/models/inputs.model';
import { FootprintService } from 'src/app/shared/services/footprint-service/footprint.service';


@Component({
  selector: 'app-footprint',
  templateUrl: './footprint.component.html',
  styleUrls: ['./footprint.component.scss'],
})
export class FootprintComponent implements OnInit, OnDestroy {
  display:boolean = false;
  inputs: Inputs[] = [
    {
      label: 'User ID',
      controlName: 'userId',
      type: 'number',
    },
    {
      label: 'Date',
      controlName: 'date',
      type: 'date',
    },
  ];
  logslist: Inputs[] = [
    {
      label: 'User name',
      controlName: 'userName',
      type: 'text',
    },
    {
      label: 'User ID',
      controlName: 'userId',
      type: 'text',
    },
    {
      label: 'Date',
      controlName: 'date',
      type: 'text',
    },
    {
      label: 'Page name',
      controlName: 'pageName',
      type: 'text',
    },
    {
      label: 'Identifier',
      controlName: 'objectIdentifier',
      type: 'text',
    },
    {
      label: 'Action name',
      controlName: 'actionName',
      type: 'text',
    },
  ];

  dialogSubscription!: Subscription;
  empty: boolean = false;
  data: any;
  width!: string;

  constructor(private footprintService:FootprintService) {}

  ngOnInit(): void {
    this.dialogSubscription = this.footprintService.openDialog.subscribe(dialog=>{
      this.display = dialog.show;
      this.empty = dialog.empty;
      this.data = dialog.data;
      this.width = this.empty?'40vw':'95%';
    });
  }
  ngOnDestroy(): void {
    this.dialogSubscription?.unsubscribe();
  }
}
