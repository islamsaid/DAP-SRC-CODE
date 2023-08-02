import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfilesManagementComponent } from './profiles-management.component';

describe('ProfilesManagementComponent', () => {
  let component: ProfilesManagementComponent;
  let fixture: ComponentFixture<ProfilesManagementComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProfilesManagementComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProfilesManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
