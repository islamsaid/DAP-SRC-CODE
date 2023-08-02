import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PricesGroupComponent } from './prices-group.component';

describe('PricesGroupComponent', () => {
  let component: PricesGroupComponent;
  let fixture: ComponentFixture<PricesGroupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PricesGroupComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PricesGroupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
