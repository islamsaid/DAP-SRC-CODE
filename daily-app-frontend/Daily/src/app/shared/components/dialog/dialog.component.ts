import {
  Component,
  Input,
  OnChanges,
  OnDestroy,
  OnInit,
  SimpleChanges,
} from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Subscription } from 'rxjs';
import { Profile } from 'src/app/profiles/models/profile.model';
import { ProfilesService } from 'src/app/profiles/services/profiles.service';
import { ProfileModel } from 'src/app/users/models/profile.model';
import { User } from 'src/app/users/models/user';
import { UsersService } from 'src/app/users/services/users.service';
// import { DynamicDialogConfig } from 'primeng/dynamicdialog';
import { DialogData } from '../../models/dialog.model';
import { FootprintService } from '../../services/footprint-service/footprint.service';

@Component({
  selector: 'app-dialog',
  templateUrl: './dialog.component.html',
  styleUrls: ['./dialog.component.scss'],
})
export class DialogComponent implements OnInit, OnChanges, OnDestroy {
  @Input() dialogConfig!: DialogData;
  @Input() openDialog!: boolean;
  @Input() closeDialog!: boolean;
  open!: boolean;
  profileList!: ProfileModel[];
  user!: User;
  profileListSubscriber = new Subscription();
  userManagementSubscriber = new Subscription();

  dialogForm = new FormGroup({
    name: new FormControl('', [Validators.required]),
    username: new FormControl('', [Validators.required]),
    profileId: new FormControl(),
  });

  constructor(
    private profilesService: ProfilesService,
    private usersService: UsersService,
    private footprintService: FootprintService
  ) {}

  ngOnInit(): void {}
  ngOnChanges(changes: SimpleChanges): void {
    this.open = this.openDialog;
    if (this.dialogConfig) {
      if (this.dialogConfig.name == 'user-management') {
        this.profileListSubscriber = this.profilesService
          .getProfilesList()
          .subscribe((resp) => {
            this.profileList = resp.payload.profilesList.filter(
              (item: Profile) => item.isActive == 1
            );
            console.log(this.profileList);
          });
      }
      if (this.dialogConfig.name == 'user-management' && this.dialogConfig.id) {
        console.log(this.dialogConfig.id);
        this.userManagementSubscriber = this.usersService
          .getUser(this.dialogConfig.id as number)
          .subscribe((resp) => {
            this.user = resp['payload'];
            this.footprintService.objectIdentifier = this.dialogConfig.id;
            let oldUser: User = {
              name: this.user.name,
              username: this.user.username,
              profileId: this.user.profileId,
            };
            this.footprintService.handleOldValue(oldUser);
            this.dialogForm.patchValue({
              name: this.user.name,
              username: this.user.username,
              profileId: this.user.profileId,
            });
          });
      }

      if (this.dialogConfig.view) this.dialogForm.disable();
    }
  }
  onSubmit() {
    this.open = false;
    console.log(this.dialogForm.value);
    if (this.dialogConfig.name == 'user-management' && !this.dialogConfig.id) {
      let user = this.dialogForm.value;
      this.footprintService.handleNewValue(user);
      this.usersService.addUser(user);
    } else if (
      this.dialogConfig.name == 'user-management' &&
      this.dialogConfig.id
    ) {
      console.log(this.user);
      let newUser: User = {
        name: this.dialogForm.value.name,
        username: this.dialogForm.value.username,
        profileId: this.dialogForm.value.profileId,
      };
      this.footprintService.handleNewValue(newUser);
      let user: User = {
        name: this.dialogForm.value.name,
        lockFlag: this.user.lockFlag,
        username: this.dialogForm.value.username,
        profileId: this.dialogForm.value.profileId,
        userId: this.dialogConfig.id,
      };
      this.usersService.updateUser(user);
    }
    this.dialogForm.reset();
  }
  cancel() {
    this.open = false;
    this.dialogForm.reset();
  }
  ngOnDestroy(): void {
    this.profileListSubscriber.unsubscribe();
    this.userManagementSubscriber.unsubscribe();
  }
}
