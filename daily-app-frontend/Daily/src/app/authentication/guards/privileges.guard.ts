import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';
import { Observable } from 'rxjs';
import { PrivilegesService } from 'src/app/shared/services/privileges-service/privileges.service';

@Injectable({
  providedIn: 'root',
})
export class PrivilegesGuard implements CanActivate {
  constructor(
    private privilegesServices: PrivilegesService,
    private router: Router
  ) {}
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ):
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree>
    | boolean
    | UrlTree {
    if (this.privilegesServices.checkPrivileges(route.data['privilege'])) {
      return true;
    } else {
      this.router.navigateByUrl('daily/accessDenied');
      return false;
    }
  }
}
