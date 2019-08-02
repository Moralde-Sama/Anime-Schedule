import { Component, OnInit } from '@angular/core';
import * as moment from 'moment';
import { DayTypeNum } from '../../enum/date';
import { Storage } from '@ionic/storage';
import { AnimeServiceProvider } from '../../providers/anime-service';
import { Anime } from '../../class/anime';
import { PopoverController, LoadingController, Loading, NavController } from 'ionic-angular';
import { HomePopoverComponent } from '../../components/home-popover/home-popover';
import { AnimeDetailsPage } from '../anime-details/anime-details';

@Component({
  selector: 'page-home',
  templateUrl: 'home.html',
  providers: [AnimeServiceProvider]
})
export class HomePage implements OnInit {
  
  private _weekday: string;
  public weekday_show: string;
  public anime_list: Anime[];
  public test: string = 'test';
  private _loader: Loading;
  constructor(public _storage: Storage, public loadingCtrl: LoadingController,
    public anime_service: AnimeServiceProvider, public popover: PopoverController,
    public navCtrl: NavController) {
  }

  ngOnInit(): void {
    this._loader = this.initLoader();
    setTimeout(() => {
      const isoweekday: number = moment().isoWeekday();
      const weekday: number = isoweekday -2 === -1 ? 7 : isoweekday -2 === -2 ? 6 : isoweekday - 2;
      this._weekday = DayTypeNum(weekday);
      this.weekday_show = DayTypeNum(isoweekday - 1);
      this._todaysRelease();
    }, 500);
  }

  presentPopover(): void {
    const popover = this.popover.create(HomePopoverComponent, {weekday: this.weekday_show});
    popover.present();
    popover.onDidDismiss((data) => {
      if(data != null && this.weekday_show !== data['weekday_show']) {
        this.weekday_show = data['weekday_show'];
        this._getAnimeRelease(data['weekday']);
      }
    });
  }

  showAnimeDetails(anime: Anime): void {
    console.log(anime);
    this.navCtrl.push(AnimeDetailsPage, {
      anime: anime
    });
  }

  private _todaysRelease(): void {
    type TodayRelease = {weekday: string, anime: Anime[] };

    this._storage.get('today_release').then((value) => {
      const parse_value: TodayRelease = JSON.parse(value);
      const is_weekday_not_same: boolean = 
        value == null ? true : parse_value.weekday != this._weekday ? true : false;

      if(value == null || value == undefined || is_weekday_not_same) {
        this._getAnimeRelease(this._weekday);
      } else {
        this.anime_list = parse_value.anime.filter((f) => f.score != null);
      }
      this._loader.dismiss();
    });
  }

  private _getAnimeRelease(weekday: string): void {
    this._loader = this.initLoader();
    type TodayRelease = {weekday: string, anime: Anime[] };

    const release_service = this.anime_service.getTodaytRelease(weekday.toLowerCase()).subscribe(res => {
      const today_release: TodayRelease = {
        weekday: weekday,
        anime: res['body'][weekday.toLowerCase()]
      }
      this._storage.set('today_release', JSON.stringify(today_release));
      this._loader.dismiss();
      this.anime_list = today_release.anime.filter((f) => f.score != null);
      release_service.unsubscribe();
    });
  }

  private initLoader(): Loading {
    const loader = this.loadingCtrl.create({
       content: 'Please wait...'
    });
    loader.present();
    return loader;
  }

}
