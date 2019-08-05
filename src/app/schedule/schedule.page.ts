import { Component, OnInit } from '@angular/core';
import { Storage } from '@ionic/storage';
import { LoadingController, PickerController } from '@ionic/angular';
import { AnimeService } from '../services/anime.service'
import { Anime, TodayRelease } from '../classes/anime';
import * as moment from 'moment';
import { DayTypeNum, WeekDays } from '../classes/enum/date';

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
    private loader: LoadingController, private picker_ctrl: PickerController) { }

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
      if(value == null) {
        const anime_list = await this.anime_service.getAnimeBySchedule(this.weekday.toLowerCase());
        const today_release: TodayRelease = {
          weekday: this.weekday,
          anime: anime_list
        }
        this.storage.set('today_release', today_release);
        this.anime_list = today_release.anime.filter((f) => f.type == 'TV' && f.score !== null);
      } else {
        this.anime_list = this.parsed_value_trelease.anime.filter((f) => f.type == 'TV' && f.score !== null);
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
      const browse_result = await this.anime_service.getAnimeBySchedule(weekday.toLowerCase());
      this.anime_list = browse_result;
    }
    await loader.dismiss();
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
