import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreatePgGroupsComponent } from './create-pg-groups.component';

describe('CreatePgGroupsComponent', () => {
  let component: CreatePgGroupsComponent;
  let fixture: ComponentFixture<CreatePgGroupsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CreatePgGroupsComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreatePgGroupsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
