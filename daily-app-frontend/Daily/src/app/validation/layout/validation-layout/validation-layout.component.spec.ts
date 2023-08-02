import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ValidationLayoutComponent } from './validation-layout.component';

describe('ValidationLayoutComponent', () => {
  let component: ValidationLayoutComponent;
  let fixture: ComponentFixture<ValidationLayoutComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ValidationLayoutComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ValidationLayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
