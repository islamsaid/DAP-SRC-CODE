import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageRateplanGroupComponent } from './manage-rateplan-group.component';

describe('ManageRateplanGroupComponent', () => {
  let component: ManageRateplanGroupComponent;
  let fixture: ComponentFixture<ManageRateplanGroupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ManageRateplanGroupComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ManageRateplanGroupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
