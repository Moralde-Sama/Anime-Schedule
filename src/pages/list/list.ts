import { Component, OnInit } from '@angular/core';
import { Anime } from '../../class/anime';
import { Storage } from '@ionic/storage';
import { Loading, LoadingController } from 'ionic-angular';
import * as moment from 'moment';
import { AnimeServiceProvider } from '../../providers/anime-service';

@Component({
  selector: 'page-list',
  templateUrl: 'list.html',
  providers: [AnimeServiceProvider]
})
export class ListPage implements OnInit {
  
  public anime_list: Anime[];
  public year: number;
  public loader: Loading;
  items: Array<{title: string, note: string, icon: string}>;

  constructor(private _storage: Storage, private loadingCtrl: LoadingController,
    private anime_service: AnimeServiceProvider) {
  }

  ngOnInit(): void {
    this.loader = this._loader();
    setTimeout(() => {
      type SeasonalAnime = {year: number, season: string, anime: Anime[] };

      this.year = moment().year();

      this._storage.get('seasonal_anime').then((value) => {
        const parse_value: SeasonalAnime = JSON.parse(value);
        const is_seasonal_not_same: boolean = 
          value == null ? true : parse_value.year != this.year
          && parse_value.season != 'Summer' ? true : false;

        if(value == null || value == undefined || is_seasonal_not_same) {
          this._getSeasonalAnime(this.year, 'Summer');
        } else {
          this.anime_list = parse_value.anime.filter((f) => f.score != null);
        }
        this.loader.dismiss();
      });
    }, 500);
  }

  private _getSeasonalAnime(year: number, season: string): void {
    this.loader = this._loader();
    type SeasonalAnime = {year: number, season: string, anime: Anime[] };

    this.anime_service.getSeasonalAnime(year, season.toLowerCase()).subscribe(res => {
      const seasonal_release: SeasonalAnime = {
        year: year,
        season: season,
        anime: res['body']['anime']
      }
      this._storage.set('seasonal_anime', JSON.stringify(seasonal_release));
      this.loader.dismiss();
      this.anime_list = seasonal_release.anime.filter((f) => f.score != null);
    });
  }

  private _loader(): Loading {
    const loader = this.loadingCtrl.create({
       content: 'Please wait...'
    });
    loader.present();
    return loader;
  }
}
