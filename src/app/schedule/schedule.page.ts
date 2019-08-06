import { Component, OnInit } from '@angular/core';
import { Storage } from '@ionic/storage';
import { LoadingController, PickerController, ModalController } from '@ionic/angular';
import { AnimeService } from '../services/anime.service'
import { Anime, TodayRelease } from '../classes/anime';
import * as moment from 'moment';
import { DayTypeNum, WeekDays } from '../classes/enum/date';
import { DetailsModalComponent } from './details-modal/details-modal.component';

@Component({
  selector: 'app-schedule',
  templateUrl: './schedule.page.html',
  styleUrls: ['./schedule.page.scss'],
})
export class SchedulePage implements OnInit {

  anime_list: Anime[];
  weekday: string;
  weekday_show: string;
  current_weekday: string;
  _isoweekday: number;
  parsed_value_trelease: TodayRelease;
  constructor(private anime_service: AnimeService, private storage: Storage,
    private loader: LoadingController, private picker_ctrl: PickerController,
    private modal_ctrl: ModalController) { }

  async ngOnInit() {
    this._isoweekday = moment().isoWeekday();
    const weekday: number = this._isoweekday -2 === -1 ? 6 : this._isoweekday -2 === -2 ? 6 :
       this._isoweekday - 2;
    this.weekday = DayTypeNum(weekday);
    this.weekday_show = DayTypeNum(this._isoweekday - 1);
    this.current_weekday = this.weekday_show;

    const loader = await this.presentLoading();
    await this.initTodayRelease(async () => {
      loader.dismiss();
    });
  }

  async initTodayRelease(callback): Promise<void> {
    this.storage.get('today_release').then(async (value) => {
    this.parsed_value_trelease = value;
    const is_weekday_not_same: boolean = 
    value == null ? true : this.parsed_value_trelease.weekday != this.weekday ? true : false;

      if(is_weekday_not_same) {
        const anime_list = await this.anime_service.getAnimeBySchedule(this.weekday.toLowerCase());
        const today_release: TodayRelease = {
          weekday: this.weekday,
          anime: anime_list
        }
        await this.storage.remove('anime_details_history');
        await this.storage.set('today_release', today_release);
        this.anime_list = today_release.anime.filter((f) => f.score !== null);
      } else {
        this.anime_list = this.parsed_value_trelease.anime.filter((f) => f.score !== null);
      }
      callback();
    });
  }

  async browseReleaseByWeekDay(weekday: string): Promise<void> {
    this.weekday_show = weekday;
    const weekday_index = WeekDays().indexOf(weekday) - 1;
    weekday = DayTypeNum(weekday_index == -1 ? 6 : weekday_index);
    const loader = await this.presentLoading();
    if(this.current_weekday === this.weekday_show) {
      this.anime_list = this.parsed_value_trelease.anime;
    } else {
      const browse_result: Anime[] = await this.anime_service.getAnimeBySchedule(weekday.toLowerCase());
      this.anime_list = browse_result.filter((f) => f.score != null);
    }
    await loader.dismiss();
  }

  async viewAnimeDetails(mal_id: number) {
    const loader = await this.presentLoading();
    let anime: Anime;
    let anime_filtered: Anime[];
    this.storage.get('anime_details_history').then(async (value: Anime[]) => {
      anime_filtered = value == null ? [] : value.filter((f) => f.mal_id == mal_id);
      if(anime_filtered.length == 0) {
        const result = await this.anime_service.getAnimeDetails(mal_id);
        anime_filtered.push(result);
        await this.storage.set('anime_details_history', anime_filtered);
        anime = result;
        console.log('adding');
      } else {
        anime = anime_filtered[0];
        console.log('exist');
      }

      await loader.dismiss();

      const modal = await this.modal_ctrl.create({
        component: DetailsModalComponent,
        componentProps: {anime: anime}
      });
      await modal.present();
    });
  }

  async presentLoading() {
    const loading = await this.loader.create({
      message: 'Please wait...'
    });
    await loading.present()
    return loading;
  }

  async showPicker() {
    const picker = await this.picker_ctrl.create({
      columns: [{
        name: 'Weekdays',
        selectedIndex: WeekDays().indexOf(this.weekday_show),
        options: this._getPickerColumns()
      }],
      buttons: [
        {
          text: 'Cancel',
          role: 'cancel'
        },
        {
          text: 'Confirm',
          handler: (value) => {
            this.browseReleaseByWeekDay(value['Weekdays']['value']);
          }
        }
      ]
    });
    await picker.present();
  }

  private _getPickerColumns() {
    const column = [];
    for(let weekday of WeekDays()) {
      column.push({
        text: weekday,
        value: weekday
      });
    }
    return column;
  }

}
