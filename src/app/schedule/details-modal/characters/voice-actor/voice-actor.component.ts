import { Component, OnInit, Input } from '@angular/core';
import { AnimeVoiceActors, AnimeCharacters } from 'src/app/classes/anime';

@Component({
  selector: 'app-voice-actor',
  templateUrl: './voice-actor.component.html',
  styleUrls: ['./voice-actor.component.scss'],
})
export class VoiceActorComponent implements OnInit {
  @Input() animecharacters: AnimeCharacters[]
  constructor() { }

  ngOnInit() {
    console.log(this.animecharacters);
  }

  isVoiceActorExist(voice_actor: AnimeVoiceActors[]): boolean {
    return voice_actor.length > 0 ? true : false;
  }

}
