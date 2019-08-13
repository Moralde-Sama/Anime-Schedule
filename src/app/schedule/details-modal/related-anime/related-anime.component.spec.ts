import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RelatedAnimeComponent } from './related-anime.component';

describe('RelatedAnimeComponent', () => {
  let component: RelatedAnimeComponent;
  let fixture: ComponentFixture<RelatedAnimeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RelatedAnimeComponent ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RelatedAnimeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
