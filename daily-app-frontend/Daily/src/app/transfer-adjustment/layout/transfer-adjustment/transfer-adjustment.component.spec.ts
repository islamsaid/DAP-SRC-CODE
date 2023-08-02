import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransferAdjustmentComponent } from './transfer-adjustment.component';

describe('TransferAdjustmentComponent', () => {
  let component: TransferAdjustmentComponent;
  let fixture: ComponentFixture<TransferAdjustmentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TransferAdjustmentComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TransferAdjustmentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
