import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OpEdThemeComponent } from './op-ed-theme.component';

describe('OpEdThemeComponent', () => {
  let component: OpEdThemeComponent;
  let fixture: ComponentFixture<OpEdThemeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OpEdThemeComponent ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OpEdThemeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
