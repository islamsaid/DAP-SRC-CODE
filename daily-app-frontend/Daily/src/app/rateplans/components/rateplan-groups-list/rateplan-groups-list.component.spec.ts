import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RateplanGroupsListComponent } from './rateplan-groups-list.component';

describe('RateplanGroupsListComponent', () => {
  let component: RateplanGroupsListComponent;
  let fixture: ComponentFixture<RateplanGroupsListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RateplanGroupsListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RateplanGroupsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
