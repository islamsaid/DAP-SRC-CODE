import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PricesLayoutComponent } from './prices-layout.component';

describe('PricesLayoutComponent', () => {
  let component: PricesLayoutComponent;
  let fixture: ComponentFixture<PricesLayoutComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PricesLayoutComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PricesLayoutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
