import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { AnimeService } from 'src/app/services/anime.service';
import { Anime, AnimeCharacters, AnimeStaff, CharactersAndStaff } from 'src/app/classes/anime';
import { Storage } from '@ionic/storage';

@Component({
  selector: 'app-characters',
  templateUrl: './characters.component.html',
  styleUrls: ['./characters.component.scss'],
  providers: [AnimeService]
})
export class CharactersComponent implements OnInit {

  @Input() animedetails: string;
  @Output() animecharacters = new EventEmitter<CharactersAndStaff>(true);
  anime_details: Anime;
  selected_segment: string;
  constructor(public anime_service: AnimeService, public storage: Storage) { }

  async ngOnInit() {
    this.anime_details = JSON.parse(this.animedetails);
    if(this.anime_details.characters == null) {
      const characters_and_staff: CharactersAndStaff =
       await this.anime_service.getAnimeCharacters(this.anime_details.mal_id);
      this.anime_details.characters = characters_and_staff.characters;
      this.anime_details.staff = characters_and_staff.staff;
      await this._updateAnimeDetails(this.anime_details);
      await this.animecharacters.emit(characters_and_staff);
    }
  }

  ngOnDestroy(): void {
    this.anime_service.cancelPendingRequest();
  }

  segmentChanged(ev: any): void {
    console.log(ev['detail']['value']);
    this.selected_segment = ev['detail']['value'];
  }

  isSegmentSelected(segment: string): boolean {
    return this.selected_segment == segment;
  }

  private async _updateAnimeDetails(animedetails: Anime): Promise<void> {
    await this.storage.get('anime_details_history').then(async (anime_list: Anime[]) => {
      const anime_list_filtered: Anime = anime_list.filter(f => f.mal_id == animedetails.mal_id)[0];
      anime_list[anime_list.indexOf(anime_list_filtered)] = animedetails;
      await this.storage.set('anime_details_history', anime_list);
    });
  }

}
