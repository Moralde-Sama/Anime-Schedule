import { Component, OnInit, Input } from '@angular/core';
import { Anime } from 'src/app/classes/anime';

@Component({
  selector: 'app-related-anime',
  templateUrl: './related-anime.component.html',
  styleUrls: ['./related-anime.component.scss'],
})
export class RelatedAnimeComponent implements OnInit {
  @Input() animedetails: Anime;
  related_anime_keys: string[];
  constructor() { }

  ngOnInit() {
    console.log(this.animedetails.related);
    this.related_anime_keys = Object.keys(this.animedetails.related);
  }

}
