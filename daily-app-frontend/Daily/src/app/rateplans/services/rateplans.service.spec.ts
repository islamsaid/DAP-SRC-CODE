import { TestBed } from '@angular/core/testing';

import { RateplansService } from './rateplans.service';

describe('RateplansServiceService', () => {
  let service: RateplansService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RateplansService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
