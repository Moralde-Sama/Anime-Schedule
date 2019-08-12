import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { AnimeService } from 'src/app/services/anime.service';
import { Anime, AnimeVideos } from 'src/app/classes/anime';
import { Storage } from '@ionic/storage';

@Component({
  selector: 'app-videos',
  templateUrl: './videos.component.html',
  styleUrls: ['./videos.component.scss'],
  providers: [AnimeService]
})
export class VideosComponent implements OnInit {

  @Input() animedetails: string;
  @Output() addedVideos = new EventEmitter<AnimeVideos[]>(true);
  anime_details: Anime;
  constructor(public anime_service: AnimeService, public storage: Storage) { 
  }

  async ngOnInit() {
    this.anime_details = JSON.parse(this.animedetails);
    if(this.anime_details.videos == null) {
      this.anime_details.videos = await this.anime_service.getAnimeVideos(this.anime_details.mal_id);
      await this._updateAnimeDetails(this.anime_details);
      await this.addedVideos.emit(this.anime_details.videos);
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
