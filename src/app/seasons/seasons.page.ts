import { Component, OnInit } from '@angular/core';
import { Seasons } from '../classes/seasons';
import { AnimeService } from '../services/anime.service';
import * as moment from 'moment';
import { Storage } from '@ionic/storage';
import { CurrentSeason, Anime } from '../classes/anime';
import { LoadingController, ModalController, Platform } from '@ionic/angular';
import { Subscription } from 'rxjs';
import { DetailsModalComponent } from '../schedule/details-modal/details-modal.component';

@Component({
  selector: 'app-seasons',
  templateUrl: './seasons.page.html',
  styleUrls: ['./seasons.page.scss'],
  providers: [Seasons]
})
export class SeasonsPage implements OnInit {

  anime_list: Anime[];
  year_show: number;
  season_show: string;
  backbutton_sub: Subscription;
  constructor(private seasons: Seasons, private anime_service: AnimeService,
    private storage: Storage, private loading_ctrl: LoadingController,
    private modal_ctrl: ModalController, private platform: Platform) { }

  async ngOnInit() {
    this._initBackButton();
    this._initSeasonalAnime();
  }

  ngOnDestroy(): void {
    this.backbutton_sub.unsubscribe();
  }

  async viewAnimeDetails(mal_id: number) {
    const loader = await this._initLoading();
    let anime: Anime;
    let anime_filtered: Anime[];
    this.storage.get('anime_details_history').then(async (value: Anime[]) => {
      value = value == null ? [] : value;
      anime_filtered = value == null ? [] : value.filter((f) => f.mal_id == mal_id);
      if(anime_filtered.length == 0) {
        const result = await this.anime_service.getAnimeDetails(mal_id);
        value.push(result);
        this.storage.set('anime_details_history', value);
        anime = result;
        console.log('adding');
      } else {
        anime = anime_filtered[0];
        console.log('exist');
      }

      await loader.dismiss();

      this.backbutton_sub.unsubscribe();

      const modal = await this._createModal(anime);

      if(await modal.onDidDismiss()) {
        this._initBackButton();
      }
      
    });
  }

  private async _initSeasonalAnime(): Promise<void> {
    const loader = await this._initLoading();
    const year = moment().year();
    const season = this.seasons.getCurrentSeason();
    this.year_show = year;
    this.season_show = season;

    this.storage.get('current_season').then(async (value: CurrentSeason) => {
      console.log(value);
      const is_season_valid: boolean = 
        value == null ? false : value.season == season ? true : false;
        console.log(is_season_valid);
      if(!is_season_valid) {
        const anime_list_seasonal = await this.anime_service.getSeasonalAnime(year, season);
        const current_season: CurrentSeason = {
          year: year,
          season: season,
          anime_list: anime_list_seasonal
        } 
        await this.storage.set('current_season', current_season);
        this.anime_list = current_season.anime_list;
        console.log('added');
        console.log(current_season.anime_list);
      } else {
        this.anime_list = value.anime_list;
        console.log(value.anime_list);
        console.log('exist');
      }
      await loader.dismiss();
    });
  }

  private async _initLoading() {
    const loader = await this.loading_ctrl.create({
      message: 'Please wait...'
    });
    await loader.present();
    return loader;
  }

  private async _createModal(anime: Anime) {
    const modal = await this.modal_ctrl.create({
      component: DetailsModalComponent,
      componentProps: {anime: anime}
    });
    await modal.present();
    return modal;
  }

  private _initBackButton(): void {
    console.log('backbutton init');
    this.backbutton_sub = this.platform.backButton.subscribe(() => {
      navigator['app'].exitApp();
    });
  }
}
