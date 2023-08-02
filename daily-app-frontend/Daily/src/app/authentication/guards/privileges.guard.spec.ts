import { TestBed } from '@angular/core/testing';

import { PrivilegesGuard } from './privileges.guard';

describe('PrivilegesGuard', () => {
  let guard: PrivilegesGuard;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    guard = TestBed.inject(PrivilegesGuard);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });
});
