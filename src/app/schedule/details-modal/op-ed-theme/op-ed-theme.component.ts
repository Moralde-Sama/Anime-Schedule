import { Component, OnInit, Input } from '@angular/core';
import { Anime } from 'src/app/classes/anime';

@Component({
  selector: 'app-op-ed-theme',
  templateUrl: './op-ed-theme.component.html',
  styleUrls: ['./op-ed-theme.component.scss'],
})
export class OpEdThemeComponent implements OnInit {

  @Input() animedetails: string;
  anime_details: Anime;
  constructor() { }

  ngOnInit() {
    this.anime_details = JSON.parse(this.animedetails);
  }

}
