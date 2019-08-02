import { Component, OnInit, OnDestroy } from '@angular/core';
import { DayTypeNum } from '../../enum/date';
import { ViewController, NavParams } from 'ionic-angular';

@Component({
  selector: 'home-popover',
  templateUrl: 'home-popover.html'
})
export class HomePopoverComponent implements OnInit, OnDestroy {

  weekdays: string[] =  [
    'Monday',
    'Tuesday',
    'Wednesday',
    'Thursday',
    'Friday',
    'Saturday',
    'Sunday'
];
  private weekday: string;

  constructor(public viewCtrl: ViewController, public navParams: NavParams) {
  }

  ngOnInit(): void {
    this.weekday = this.navParams.get('weekday');
  }

  ngOnDestroy(): void {
    this.weekday = null;
    this.weekdays = null;
  }

  isWeekdaySame(weekday: string): boolean {
    return weekday === this.weekday ? true : false;
  }

  setSelectedItem(index: number): void {
    this.weekday = DayTypeNum(index);
    const data = {
      weekday: DayTypeNum(
        (index - 1) === -1 ? 6 : index -1
      ),
      weekday_show: this.weekday
    }
    this.viewCtrl.dismiss(data);
  }

}
