import { TestBed } from '@angular/core/testing';

import { ConfigSystemService } from './configSystem.service';

describe('ServicesService', () => {
  let service: ConfigSystemService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ConfigSystemService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
