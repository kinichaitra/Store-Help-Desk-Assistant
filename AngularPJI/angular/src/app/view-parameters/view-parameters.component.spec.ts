import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewParametersComponent } from './view-parameters.component';

describe('ViewParametersComponent', () => {
  let component: ViewParametersComponent;
  let fixture: ComponentFixture<ViewParametersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewParametersComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewParametersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
