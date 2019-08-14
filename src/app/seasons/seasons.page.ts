import { Component, OnInit } from '@angular/core';
import { Seasons } from '../classes/seasons';
import { AnimeService } from '../services/anime.service';
import * as moment from 'moment';
import { Storage } from '@ionic/storage';
import { CurrentSeason, Anime } from '../classes/anime';
import { LoadingController, ModalController, Platform, PickerController } from '@ionic/angular';
import { Subscription } from 'rxjs';
import { filter } from 'rxjs/operators';
import { DetailsModalComponent } from '../schedule/details-modal/details-modal.component';
import { Router, NavigationEnd } from '@angular/router';

@Component({
  selector: 'app-seasons',
  templateUrl: './seasons.page.html',
  styleUrls: ['./seasons.page.scss'],
  providers: [Seasons]
})
export class SeasonsPage implements OnInit {

  anime_list: Anime[];
  year_show: number;
  years: Object[];
  season_show: string;
  backbutton_sub: Subscription;
  router_sub: Subscription;
  constructor(private seasons: Seasons, private anime_service: AnimeService,
    private storage: Storage, private loading_ctrl: LoadingController,
    private modal_ctrl: ModalController, private platform: Platform,
    private router: Router, private picker_ctrl: PickerController) { }

  async ngOnInit() {
    this._observeRouterChanges();
    await this._initSeasonalAnime();
    this.years = await this._getPickerYears();
  }

  ngOnDestroy(): void {
    this.backbutton_sub.unsubscribe();
    this.router_sub.unsubscribe();
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

  async showPicker(): Promise<void> {
    const seasons = this.seasons.getAllSeasons();
    const picker = await this.picker_ctrl.create({
      columns: [{
        name: 'Seasons',
        selectedIndex: seasons.indexOf(this.season_show),
        options: await this._getPickerSeasons()
      },
      {
        name: 'Year',
        selectedIndex: (moment().year() + 1) - this.year_show,
        options: this.years
      }],
      buttons: [
        {
          text: 'Cancel',
          role: 'cancel'
        },
        {
          text: 'Confirm',
          handler: (value) => {
            this.year_show = value['Year']['value'];
            this.season_show = value['Seasons']['value'];
            this._browseSeasonalAnime(this.year_show, this.season_show);
          }
        }
      ]
    });
    picker.columns[1].options.forEach(element => {
      delete element.selected;
      delete element.duration;
      delete element.transform;
    });

    await picker.present();
  }

  private async _browseSeasonalAnime(year: number, season: string): Promise<void> {
    const loader = await this._initLoading();
    this.storage.get('current_season').then(async (value: CurrentSeason) => {
      const is_season_valid: boolean = 
        value == null ? false : value.season == season ? true : false;
        if(is_season_valid) {
          this.anime_list = value.anime_list;
        } else {
          this.anime_list = await this.anime_service.getSeasonalAnime(year, season);
        }
        loader.dismiss();
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
      if(!is_season_valid) {
        const anime_list_seasonal = await this.anime_service.getSeasonalAnime(year, season);
        const current_season: CurrentSeason = {
          year: year,
          season: season,
          anime_list: anime_list_seasonal
        } 
        await this.storage.set('current_season', current_season);
        this.anime_list = current_season.anime_list;
      } else {
        this.anime_list = value.anime_list;
      }
      await loader.dismiss();
    });
  }

  private async _getPickerSeasons(): Promise<any[]> {
    return new Promise((resolve) => {
      const seasons_array = [];
      for(let season of this.seasons.getAllSeasons()) {
        seasons_array.push({
          text: season,
          value: season
        });
      }
      resolve(seasons_array);
    });
  }

  private async _getPickerYears(): Promise<Object[]> {
    return new Promise((resolve) => {
      const year = moment().year() + 1;
      const year_sub = year - 80;
      let year_array = [];
      for(let i = year; i > year_sub; i--) {
        year_array.push({
          text: i,
          value: i
        });
        resolve(year_array);
      }
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

  private _observeRouterChanges(): void {
    this.router_sub = this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe((event: NavigationEnd) => {
        switch (event.url) {
          case '/tabs/tab1':
            this.backbutton_sub.unsubscribe();
            break;
          case '/tabs/tab2':
            this._initBackButton();
            break;
          default:
            this.backbutton_sub.unsubscribe();
            break;
        }
    });
  }
}
