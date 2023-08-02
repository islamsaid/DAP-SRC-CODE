import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RateplansNumbersComponent } from './rateplans-numbers.component';

describe('RateplansNumbersComponent', () => {
  let component: RateplansNumbersComponent;
  let fixture: ComponentFixture<RateplansNumbersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RateplansNumbersComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RateplansNumbersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
