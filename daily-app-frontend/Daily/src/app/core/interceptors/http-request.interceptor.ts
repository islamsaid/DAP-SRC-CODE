import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpHeaders,
  HttpResponse,
} from '@angular/common/http';
import { Observable, tap } from 'rxjs';
import { ToastMessageService } from '../services/toast-message-service/toast-message.service';
import { Router } from '@angular/router';
import { Defines } from 'src/app/shared/statics/defines';
import { Footprint } from 'src/app/shared/models/footprint';
import { ACTIONS } from 'src/app/shared/statics/actions';
import { FootprintService } from 'src/app/shared/services/footprint-service/footprint.service';
import { LoaderService } from '../services/loader-service/loader.service';
import { AuthenticationService } from 'src/app/authentication/services/authentication.service';

@Injectable()
export class HttpRequestInterceptor implements HttpInterceptor {
  headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Access-Control-Allow-Origin': '*',
    'Access-Control-Allow-Methods': '*',
    'Access-Control-Allow-Headers':
      'Content-Type, Authorization, X-Requested-With',
  });
  requestBody: any;
  responseBody: any;
  constructor(
    private toastMessageService: ToastMessageService,
    private router: Router,
    private footprintService: FootprintService,
    private loaderService: LoaderService
  ) {}

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    request.url?.includes('notifications')
      ? this.loaderService.hide()
      : this.loaderService.show();
    let req = this.prework(request);
    return next.handle(req).pipe(
      tap((response) => {
        if (response instanceof HttpResponse) {
          this.loaderService.hide();
          this.postwork(response);
        }
      })
    );
  }

  prework(request: HttpRequest<any>) {
    this.requestBody = request;
    if (request.url?.includes('login')) {
      const req = request.clone({
        headers: this.headers,
        body: { ...request.body },
      });
      console.log('%c prework request', 'color: green', req);
      return req;
    } else {
      let token = localStorage.getItem('token') as string;
      let fullHeaders = this.headers.set('Authorization', 'Bearer ' + token);
      const req = request.clone({
        headers: fullHeaders,
        body: { ...request.body },
      });
      console.log('%c prework request', 'color: green', req);
      return req;
    }
  }

  postwork(response: HttpResponse<any>) {
    this.responseBody = response;

    if (response.url?.includes('login')) {
      if (response?.body?.payload) {
        localStorage.setItem('token', response?.body?.payload['token']);
        localStorage.setItem(
          'activeUser',
          response?.body?.payload['user'].username
        );
      }
    }
    console.log('%c postwork response', 'color: red', response);
    this.handleError(response);
    if(response.body.statusCode !== -422) this.handleFootPrintRequest();
  }

  public handleError(response: HttpResponse<any>) {
    let crud = [Defines.CRUD.ADD, Defines.CRUD.DELETE, Defines.CRUD.UPDATE];
    if (response.body.statusCode == Defines.RESPONSE_STATUS.NON_AUTH) {
      localStorage.clear();
      this.router.navigateByUrl('/');
    }
    Object.values(crud).map((crud) => {
      if (response.url?.includes(crud)) {
        if (
          response.body.statusCode == Defines.RESPONSE_STATUS.SUCCESS &&
          !response.url?.includes(Defines.FOOTPRINT.ADD_LOG)
        ) {
          this.toastMessageService.showSuccess(
            'Success',
            response.body.statusMessage,
            false
          );
        }
      }
    });

    if (
      response.body.statusCode !== Defines.RESPONSE_STATUS.SUCCESS &&
      !response.url?.includes(Defines.FOOTPRINT.ADD_LOG)
    ) {
      if (response.body.statusCode == Defines.RESPONSE_STATUS.NO_DATA) {
        if (!this.footprintService.refreshFlag) {
          this.toastMessageService.showWarn(
            'No Data',
            response.body.statusMessage
              ? response.body.statusMessage
              : response.body.message,
            false
          );
        }
        this.footprintService.refreshFlag = false;
      } else {
        if(response.body.statusCode !== Defines.RESPONSE_STATUS.NOT_ALLOWED) {
        this.toastMessageService.showError(
          'Error',
          response.body.statusMessage
            ? response.body.statusMessage
            : response.body.message,
          false
        );
        }
      }
    }
  }

  handleFootPrintRequest() {
    let newValue: any;
    let oldValue: any;
    let allValues: any;
    let objectIdentifier: any;
    let checkAdd = this.requestBody.url?.includes(Defines.CRUD.ADD);
    let checkUpdate = this.requestBody.url?.includes(Defines.CRUD.UPDATE);
    let checkUpdateBatch = this.requestBody.url?.includes(
      Defines.CRUD.UPDATE_BATCH
    );
    let checkDelete = this.requestBody.url?.includes(Defines.CRUD.DELETE);
    let checkAddMutiple =
      this.requestBody.url?.includes('validation-engine/update') ||
      this.requestBody.url?.includes('manual-adjustment/update') ||
      this.requestBody.url?.includes('transfer-adjustment/agg-data/add');
    if (checkUpdateBatch) {
      allValues = this.footprintService.filterOldValues();
      this.footPrintMutipe(allValues, this.requestBody, this.responseBody);
    } else if (checkUpdate && !checkAddMutiple) {
      newValue = this.footprintService.newValue;
      oldValue = this.footprintService.oldValue;
      objectIdentifier = this.footprintService.objectIdentifier;
      this.addFootprint(
        oldValue,
        newValue,
        checkUpdate,
        checkAdd,
        objectIdentifier
      );
    } else if (
      checkAdd &&
      !this.requestBody.url?.includes('add-logs') &&
      !checkAddMutiple
    ) {
      objectIdentifier = this.responseBody.body.payload.id; // get id from add request response
      newValue = this.footprintService.newValue;
      this.addFootprint(
        oldValue,
        newValue,
        checkUpdate,
        checkAdd,
        objectIdentifier
      );
    } else if (checkDelete) {
      objectIdentifier = this.footprintService.objectIdentifier;
      this.addFootprint(
        oldValue,
        newValue,
        checkUpdate,
        checkAdd,
        objectIdentifier
      );
    } else if (checkAddMutiple) {
      allValues = this.footprintService.addMultipeNewVal();
      this.footPrintMutipe(allValues, this.requestBody, this.responseBody);
    } else {
      this.addFootprint(oldValue, newValue, checkUpdate, checkAdd);
    }
  }

  addFootprint(
    oldValue: any,
    newValue: any,
    checkUpdate: boolean,
    checkAdd: boolean,
    objectIdentifier?: any
  ) {
    let footprint: Footprint;
    ACTIONS.ACTIONS_DETAILS.map((action) => {
      if (this.requestBody.url === action.url) {
        if (action.id === 1 ) { //login
          delete this.requestBody.body["token"];    
        }
        footprint = {
          pageName: action.pageName,
          requestId: this.responseBody.body.traceId, //traceId
          objectIdentifier: objectIdentifier ? objectIdentifier : 'all',
          actionId: action.id,
          requestBody: JSON.stringify(this.requestBody),
          responseBody: JSON.stringify(this.responseBody),
          oldValue: checkUpdate ? oldValue : null,
          newValue: checkUpdate || checkAdd ? newValue : null,
        };
        this.footprintService.footprintRequest(footprint).subscribe();
        return;
      }
    });
  }
  footPrintMutipe(allValues: any[], reqBody: any, resBody: any) {
    let footprint: Footprint;
    let actionID: number;
    let pageName: string;
    ACTIONS.ACTIONS_DETAILS.map((action) => {
      if (this.requestBody.url === action.url) {
        actionID = action.id;
        pageName = action.pageName;
        return;
      }
    });
    allValues = allValues.map((val: any) => {
      footprint = {
        pageName: pageName,
        requestId: this.responseBody.body.traceId, //traceId
        objectIdentifier: val.identifier,
        actionId: actionID,
        requestBody: JSON.stringify(reqBody),
        responseBody: JSON.stringify(resBody),
        oldValue: val.oldValues,
        newValue: val.newValues,
      };
      this.footprintService.footprintRequest(footprint).subscribe();
    });
  }
}
