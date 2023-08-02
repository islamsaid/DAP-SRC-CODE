import { TestBed } from '@angular/core/testing';

import { TransferAdjustmentService } from './transfer-adjustment.service';

describe('TransferAdjustmentService', () => {
  let service: TransferAdjustmentService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TransferAdjustmentService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
