import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class LoaderService {
  showLoader = false;
  loaderSubject: BehaviorSubject<boolean>;

  constructor() {
    this.loaderSubject = new BehaviorSubject<boolean>(false);
  }

  get loaderState$(): Observable<boolean> {
    console.log(this.loaderSubject.value);
    return this.loaderSubject.asObservable();
  }

  public show() {
    console.log(true);

    this.loaderSubject.next(true);
  }

  public hide() {
    console.log(false);

    this.loaderSubject.next(false);
  }
}
