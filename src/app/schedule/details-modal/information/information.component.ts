import { Component, OnInit, Input } from '@angular/core';
import { Anime } from 'src/app/classes/anime';

@Component({
  selector: 'app-information',
  templateUrl: './information.component.html',
  styleUrls: ['./information.component.scss'],
})
export class InformationComponent implements OnInit {
  @Input() animedetails: string;
  @Input('test') tests: string;
  anime_details: Anime;
  constructor() {
  }

  ngOnInit() {
    this.anime_details = JSON.parse(this.animedetails);
  }

  getRatingColor(rating_text: string): string {
    return (rating_text == 'PG-13 - Teens 13 or older' || rating_text == 'PG - Children') 
        ? 'success': 'warning';
  }

}
