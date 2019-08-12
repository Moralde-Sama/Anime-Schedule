import { Component, OnInit, Input } from '@angular/core';
import { Anime } from 'src/app/classes/anime';

@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.scss'],
})
export class StatisticsComponent implements OnInit {
  @Input() animedetails: string;
  anime_details: Anime;
  constructor() { 
  }

  ngOnInit() {
    this.anime_details = JSON.parse(this.animedetails);
  }

}
