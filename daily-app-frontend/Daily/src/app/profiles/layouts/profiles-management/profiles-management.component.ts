import { ChangeDetectorRef, Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { PrivilegesService } from 'src/app/shared/services/privileges-service/privileges.service';
import { PRIVILEGES } from 'src/app/shared/statics/privileges';
import { ProfilesService } from '../../services/profiles.service';

@Component({
  selector: 'app-profiles-management',
  templateUrl: './profiles-management.component.html',
  styleUrls: ['./profiles-management.component.scss'],
})
export class ProfilesManagementComponent implements OnInit,OnDestroy {
  showSubscription!: Subscription;
  enableAdd!: boolean;
  constructor(
    private router: Router,
    private profilesService: ProfilesService,
    private cdr: ChangeDetectorRef,
    private privilegesService:PrivilegesService
  ) {}
  showBtn!: boolean;
  ngOnInit(): void {
    this.enableAdd = this.privilegesService.checkPrivileges(
      PRIVILEGES.PROFILES_MANAGMENT.ADD
    );
   this.showSubscription = this.profilesService.showSubject.subscribe((flag: boolean) => {
      this.showBtn = flag;
      this.cdr.detectChanges();
    });
  }

  addProfile() {
    this.router.navigateByUrl('daily/profiles/add');
    this.showBtn = false;
  }

  ngOnDestroy(): void {
    this.showSubscription?.unsubscribe();
  }
}
