import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfigSystemLayoutComponent } from './config-system-layout.component';

describe('ConfigSystemLayoutComponent', () => {
  let component: ConfigSystemLayoutComponent;
  let fixture: ComponentFixture<ConfigSystemLayoutComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ConfigSystemLayoutComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ConfigSystemLayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
