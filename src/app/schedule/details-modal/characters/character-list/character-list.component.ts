import { Component, OnInit, Input } from '@angular/core';
import { AnimeCharacters } from 'src/app/classes/anime';

@Component({
  selector: 'app-character-list',
  templateUrl: './character-list.component.html',
  styleUrls: ['./character-list.component.scss'],
})
export class CharacterListComponent implements OnInit {
  
  @Input() animecharacters: AnimeCharacters;
  constructor() { }

  ngOnInit() {
    this.animecharacters;
  }

}
