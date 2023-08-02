import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewPriceGroupComponent } from './new-price-group.component';

describe('NewPriceGroupComponent', () => {
  let component: NewPriceGroupComponent;
  let fixture: ComponentFixture<NewPriceGroupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewPriceGroupComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NewPriceGroupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
