import { Component, OnInit } from '@angular/core';
import { Inputs } from 'src/app/shared/models/inputs.model';
import { PrivilegesService } from 'src/app/shared/services/privileges-service/privileges.service';
import { PRIVILEGES } from 'src/app/shared/statics/privileges';

@Component({
  selector: 'app-tariffs',
  templateUrl: './tariffs.component.html',
  styleUrls: ['./tariffs.component.scss'],
})
export class TariffsComponent implements OnInit {
  searchInputs: Inputs[] = [
    {
      label: 'Tariff Name',
      controlName: 'tariffModelName',
      type: 'text',
    },
    {
      label: 'Select Type',
      controlName: 'tariffModelType',
      type: 'dropDown',
      options: [
        { id: 'Prepaid', value: 1 },
        { id: 'Postpaid', value: 2 },
      ],
    },
    {
      label: 'Select Contract Type',
      controlName: 'contractType',
      type: 'dropDown',
      options: [
        { id: 'Consumer', value: 1 },
        { id: 'Enterprise', value: 2 },
      ],
    },
    {
      label: 'Select Bundle Type',
      controlName: 'bundleType',
      type: 'dropDown',
      options: [
        { id: 'Voice & Messaging', value: '1' },
        { id: 'Voice & Data', value: '2' },
        { id: 'Messaging & Data', value: '3' },
        { id: 'Voice and access only', value: '4' },
        { id: 'Messaging only', value: '5' },
        { id: 'Data only', value: '6' },
        { id: 'Mobile broadband', value: '7' },
        { id: 'Integrated', value: '8' },
        { id: 'Out of bundle', value: '9' },
      ],
    },
    {
      label: 'Hybird',
      controlName: 'hybird',
      type: 'checkbox',
    },
    {
      label: 'Activation Source',
      controlName: 'activationSource',
      type: 'checkbox',
    },
    {
      label: 'Deactivation Source',
      controlName: 'deactivationSourceFlag',
      type: 'checkbox',
    },
  ];
  inputs: Inputs[] = [
    {
      label: 'Tariff Name',
      controlName: 'tariffModelName',
      type: 'text',
    },
    {
      label: 'Select Type',
      controlName: 'tariffModelType',
      type: 'dropDown',
      options: [
        { id: 'Prepaid', value: 1 },
        { id: 'Postpaid', value: 2 },
      ],
    },
    {
      label: 'Hybird',
      controlName: 'hybird',
      type: 'checkbox',
    },
    {
      label: 'Activation Source',
      controlName: 'activationSource',
      type: 'checkbox',
      disabled:true
    },
    {
      label: 'Deactivation Source',
      controlName: 'deactivationSourceFlag',
      type: 'checkbox',
      disabled:true
    },
    {
      label: 'Select Contract Type',
      controlName: 'contractType',
      type: 'dropDown',
      options: [
        { id: 'Consumer', value: 1 },
        { id: 'Enterprise', value: 2 },
      ],
    },
    {
      label: 'Select Bundle Type',
      controlName: 'bundleType',
      type: 'dropDown',
      options: [
        { id: 'Voice & Messaging', value: '1' },
        { id: 'Voice & Data', value: '2' },
        { id: 'Messaging & Data', value: '3' },
        { id: 'Voice and access only', value: '4' },
        { id: 'Messaging only', value: '5' },
        { id: 'Data only', value: '6' },
        { id: 'Mobile broadband', value: '7' },
        { id: 'Integrated', value: '8' },
        { id: 'Out of bundle', value: '9' },
      ],
    },
  ];
  enableUpdate!: boolean;
  constructor(private privilegesService: PrivilegesService) { }

  ngOnInit(): void {
    this.enableUpdate = this.privilegesService.checkPrivileges(
      PRIVILEGES.SERVICES_TARIFFS.UPDATE
    );
  }
}
