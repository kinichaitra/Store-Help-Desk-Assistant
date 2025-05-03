import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateStoreComponent } from './updatestore.component';

describe('UpdatestoreComponent', () => {
  let component: UpdateStoreComponent;
  let fixture: ComponentFixture<UpdateStoreComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdateStoreComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UpdateStoreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
