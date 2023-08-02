import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Defines } from 'src/app/shared/statics/defines';
import { AuthenticationService } from '../../services/authentication.service';
import { User } from '../../models/user.model';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { ConfirmationService } from 'primeng/api';
@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.scss'],
})
export class LoginFormComponent implements OnInit, OnDestroy {
  loginForm = new FormGroup({
    username: new FormControl('', [
      Validators.required,
      Validators.minLength(5),
    ]),
    password: new FormControl('', [
      Validators.required,
      Validators.minLength(5),
    ]),
  });
  showPassword!: boolean;
  urlRoute: string = 'daily/home/dashboard';
  loginSubscription!: Subscription;
  constructor(
    private authenticationService: AuthenticationService,
    private router: Router,
    private confirmationService: ConfirmationService
  ) {}

  ngOnInit(): void {}
  onSubmit() {
    let user: User = this.loginForm.value;
    this.loginSubscription = this.authenticationService
      .login(user)
      .subscribe((response: any) => {
        console.log('userData', response);
        if (response.statusCode == 0) {
          let privileges = response.payload.user.profileModel.privileges;
          let userId = response.payload.user.userId;
          let userName = response.payload.user.username;
          localStorage.setItem('userId', JSON.stringify(userId));
          localStorage.setItem('userName', JSON.stringify(userName));
          localStorage.setItem('privileges', JSON.stringify(privileges));
          this.authenticationService
            .storeToken(
              response.payload.user.username,
              'Bearer ' + response.payload.token
            )
            .subscribe((response: any) => {
              console.log('Token Stored :', response.statusCode);
              console.log('urlRoute :', this.urlRoute);
              response.statusCode == 0
                ? this.router.navigateByUrl(this.urlRoute)
                : '';
            });
        } else if (response.statusCode == -422) {
          this.logout();
        }
      });
  }
  logout() {
    this.confirmationService.confirm({
      // target: event.target,
      message: 'Would you like to end the active session of this user?',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        //confirm action
        this.authenticationService
          .logout(this.loginForm.value.username)
          .subscribe();
      },
      reject: () => {
        //reject action
      },
    });
  }
  ngOnDestroy(): void {
    this.loginSubscription?.unsubscribe();
  }
}
