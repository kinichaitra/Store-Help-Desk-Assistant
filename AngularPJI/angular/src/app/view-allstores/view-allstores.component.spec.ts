import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewAllstoresComponent } from './view-allstores.component';

describe('ViewAllstoresComponent', () => {
  let component: ViewAllstoresComponent;
  let fixture: ComponentFixture<ViewAllstoresComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewAllstoresComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewAllstoresComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
