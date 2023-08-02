import { Injectable, OnInit } from '@angular/core';
import { MessageService, PrimeNGConfig } from 'primeng/api';

@Injectable({
  providedIn: 'root',
})
export class ToastMessageService implements OnInit {
  constructor(
    private messageService: MessageService,
    private primengConfig: PrimeNGConfig
  ) {}
  ngOnInit(): void {
    this.primengConfig.ripple = true;
  }
  showSuccess(summary: string, detail: string, sticky: boolean) {
    this.messageService.add({
      severity: 'success',
      summary: summary,
      detail: detail,
      sticky: sticky,
    });
  }
  showInfo(summary: string, detail: string, sticky: boolean) {
    this.messageService.add({
      severity: 'info',
      summary: summary,
      detail: detail,
      sticky: sticky,
    });
  }
  showWarn(summary: string, detail: string, sticky: boolean) {
    this.messageService.add({
      severity: 'warn',
      summary: summary,
      detail: detail,
      sticky: sticky,
    });
  }

  showError(summary: string, detail: string, sticky: boolean) {
    this.messageService.add({
      severity: 'error',
      summary: summary,
      detail: detail,
      sticky: sticky,
    });
  }
}
