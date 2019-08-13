import { Component, OnInit, Input } from '@angular/core';
import { Anime } from 'src/app/classes/anime';

@Component({
  selector: 'app-alternative-titles',
  templateUrl: './alternative-titles.component.html',
  styleUrls: ['./alternative-titles.component.scss'],
})
export class AlternativeTitlesComponent implements OnInit {

  @Input() animedetails: Anime;
  constructor() { }

  ngOnInit() {}

}
