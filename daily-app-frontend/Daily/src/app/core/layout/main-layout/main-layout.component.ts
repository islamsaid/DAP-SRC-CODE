import {
  ChangeDetectorRef,
  Component,
  OnDestroy,
  OnInit,
  ChangeDetectionStrategy,
} from '@angular/core';
import { MessageService } from 'primeng/api';
import { Subscription } from 'rxjs';
import { LoaderService } from '../../services/loader-service/loader.service';
import { SidebarService } from '../../services/sidebar-service/sidebar.service';

@Component({
  selector: 'app-main-layout',
  templateUrl: './main-layout.component.html',
  styleUrls: ['./main-layout.component.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  providers: [MessageService],
})
export class MainLayoutComponent implements OnInit, OnDestroy {
  state: string = 'close';
  sidebarSubscription!: Subscription;
  loaderState = this.loaderService.loaderState$;
  constructor(
    private loaderService: LoaderService,
    private sidebarService: SidebarService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    this.sidebarSubscription = this.sidebarService.stateSubject.subscribe(
      (state) => {
        this.state = state;
      }
    );
  }
  ngAfterViewInit() {
    this.cdr.detectChanges();
  }

  ngOnDestroy(): void {
    this.sidebarSubscription?.unsubscribe();
  }
}
