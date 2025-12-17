import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Forcelogoutdialogue } from './forcelogoutdialogue';

describe('Forcelogoutdialogue', () => {
  let component: Forcelogoutdialogue;
  let fixture: ComponentFixture<Forcelogoutdialogue>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Forcelogoutdialogue]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Forcelogoutdialogue);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
