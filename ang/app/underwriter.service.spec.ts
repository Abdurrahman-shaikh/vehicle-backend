import { TestBed } from '@angular/core/testing';

import { UnderwriterService } from './underwriter.service';

describe('UnderwriterService', () => {
  let service: UnderwriterService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(UnderwriterService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
