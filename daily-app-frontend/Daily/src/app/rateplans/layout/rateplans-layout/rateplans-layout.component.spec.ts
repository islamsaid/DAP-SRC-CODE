import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RateplansLayoutComponent } from './rateplans-layout.component';

describe('rateplansLayoutComponent', () => {
  let component: RateplansLayoutComponent;
  let fixture: ComponentFixture<RateplansLayoutComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RateplansLayoutComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RateplansLayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
