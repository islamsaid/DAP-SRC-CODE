import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RateplansListComponent } from './rateplans-list.component';

describe('rateplansListComponent', () => {
  let component: RateplansListComponent;
  let fixture: ComponentFixture<RateplansListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [RateplansListComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RateplansListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
