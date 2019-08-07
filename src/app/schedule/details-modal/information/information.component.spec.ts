import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InformationComponent } from './information.component';

describe('InformationComponent', () => {
  let component: InformationComponent;
  let fixture: ComponentFixture<InformationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InformationComponent ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InformationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
