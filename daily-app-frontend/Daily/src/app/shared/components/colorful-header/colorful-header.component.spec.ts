import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ColorfulHeaderComponent } from './colorful-header.component';

describe('ColorfulHeaderComponent', () => {
  let component: ColorfulHeaderComponent;
  let fixture: ComponentFixture<ColorfulHeaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ColorfulHeaderComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ColorfulHeaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
