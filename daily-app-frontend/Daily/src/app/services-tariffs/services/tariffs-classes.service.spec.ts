import { TestBed } from '@angular/core/testing';

import { TariffsClassesService } from './tariffs-classes.service';

describe('TariffsClassesService', () => {
  let service: TariffsClassesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TariffsClassesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
