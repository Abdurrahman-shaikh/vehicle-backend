import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UnderwriterListComponent } from './underwriter-list.component';

describe('UnderwriterListComponent', () => {
  let component: UnderwriterListComponent;
  let fixture: ComponentFixture<UnderwriterListComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [UnderwriterListComponent]
    });
    fixture = TestBed.createComponent(UnderwriterListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
