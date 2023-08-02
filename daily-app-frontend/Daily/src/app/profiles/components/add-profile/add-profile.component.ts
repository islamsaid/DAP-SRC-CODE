import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { FootprintService } from 'src/app/shared/services/footprint-service/footprint.service';
import { Defines } from 'src/app/shared/statics/defines';
import { Privilege } from '../../models/privileges.model';
import { Profile } from '../../models/profile.model';
import { ProfilesService } from '../../services/profiles.service';

@Component({
  selector: 'app-add-profile',
  templateUrl: './add-profile.component.html',
  styleUrls: ['./add-profile.component.scss'],
})
export class AddProfileComponent implements OnInit, OnDestroy {
  sourceList: Privilege[] = [];
  targetList: Privilege[] = [];
  profileForm!: FormGroup;
  pageMode: string = 'Add';
  profileId: any;
  getSubscription!: Subscription;
  urlRoute: string =
    Defines.ROUTES.DAILY +
    '/' +
    Defines.ROUTES.PROIFLES +
    '/' +
    Defines.ROUTES.LIST;
  isActive: number = 1;
  viewMode!: boolean;

  constructor(
    private profilesService: ProfilesService,
    private route: ActivatedRoute,
    private router: Router,
    private footprintService:FootprintService
  ) {}

  ngOnInit(): void {
    if (this.route.snapshot.params['id']) {
      this.profileId = this.route.snapshot.params['id'];
      this.viewMode = this.route.snapshot.url[0].path === 'view';
      this.pageMode = this.viewMode ? 'View ' : 'edit';
    }
    this.initForm();
    this.pageMode == 'edit' ? this.getProfileByID() : this.getPrivilieges();
  }

  unassignedPrivileges(privileges: any[]) {
    this.targetList.forEach((element) => {
      privileges = privileges.filter((priv) => {
        return priv.id !== element.id;
      });
    });
    return privileges;
  }

  getPrivilieges() {
    this.profilesService.getPrivilegesList().subscribe((resp) => {
      this.sourceList = this.unassignedPrivileges(
        resp['payload']['privileges']
      );
    });
  }
  getProfileByID() {
    this.getSubscription = this.profilesService
      .getProfileById(this.profileId)
      .subscribe((response: any) => {
        let profile = response['payload']['profile'];
        this.footprintService.objectIdentifier = profile.id;
        let profileClone:Profile = {
          name: profile.name,
          isActive: profile.isActive,
          privileges: profile.privileges,
        };
        profileClone = JSON.parse(JSON.stringify(profileClone));
        console.log("profileClone",profileClone);
        this.footprintService.handleOldValue(profileClone);
        this.targetList = profile.privileges;
        this.isActive = profile.isActive;
        this.getPrivilieges();
        this.profileForm.patchValue({
          name: profile.name,
        });
        if(this.viewMode) this.profileForm.disable();
      });
  }

  initForm() {
    this.profileForm = new FormGroup({
      name: new FormControl(null, Validators.required),
    });
  }

  onSubmit() {
    let profile: Profile = {
      name: this.profileForm.value.name,
      isActive: this.isActive,
      privileges: this.targetList,
    };
    if (this.pageMode == 'edit') {
      this.footprintService.handleNewValue(profile);
      profile.id = this.profileId;
      this.profilesService
        .manageProfile(profile,this.pageMode)
        .subscribe((response) => {
          console.log('add profile response', response);
          if (response.statusCode == 0) {
            this.router.navigateByUrl(this.urlRoute);
          }
        });
    } else {
      this.footprintService.handleNewValue(profile);
      this.profilesService.manageProfile(profile).subscribe((response) => {
        console.log('add profile response', response);
        if (response.statusCode == 0) {
          this.router.navigateByUrl(this.urlRoute);
        }
      });
    }

    console.log('Profile', profile);
    console.log('Source list', this.sourceList);
    console.log('Target list', this.targetList);
  }

  ngOnDestroy(): void {
    this.getSubscription?.unsubscribe();
  }
}
