import { Component, OnInit, Input } from '@angular/core';
import { CharactersAndStaff, Anime } from 'src/app/classes/anime';
import { AnimeService } from 'src/app/services/anime.service';
import { Storage } from '@ionic/storage';

@Component({
  selector: 'app-staff',
  templateUrl: './staff.component.html',
  styleUrls: ['./staff.component.scss'],
  providers: [AnimeService]
})
export class StaffComponent implements OnInit {
  @Input() animedetails: Anime;
  constructor(public anime_service: AnimeService, public storage: Storage) { }

  async ngOnInit() {
    if(this.animedetails.staff == null) {
      const char_and_staff: CharactersAndStaff = 
        await this.anime_service.getAnimeCharacters(this.animedetails.mal_id);
      this.animedetails.staff = char_and_staff.staff;
      this.animedetails.characters = char_and_staff.characters;
      await this._updateAnimeDetails(this.animedetails);
    }
  }

  ngOnDestroy(): void {
    this.anime_service.cancelPendingRequest();
  }

  private async _updateAnimeDetails(animedetails: Anime): Promise<void> {
    await this.storage.get('anime_details_history').then(async (anime_list: Anime[]) => {
      const anime_list_filtered: Anime = anime_list.filter(f => f.mal_id == animedetails.mal_id)[0];
      anime_list[anime_list.indexOf(anime_list_filtered)] = animedetails;
      await this.storage.set('anime_details_history', anime_list);
    });
  }
}
