import { Component, OnInit } from '@angular/core';
import { ModalController, NavParams } from '@ionic/angular';
import { ScreenOrientation } from '@ionic-native/screen-orientation/ngx';

@Component({
  selector: 'app-view-video',
  templateUrl: './view-video.component.html',
  styleUrls: ['./view-video.component.scss'],
  providers: [ScreenOrientation]
})
export class ViewVideoComponent implements OnInit {

  video_url: string;
  video_title: string;
  is_video_loaded: boolean = false;
  count_load: number = 0;
  constructor(private modal_ctrl: ModalController, private nav_param: NavParams,
    private screen_orientation: ScreenOrientation) {
    this.screen_orientation.lock(this.screen_orientation.ORIENTATIONS.LANDSCAPE)
  }

  ngOnInit() {
    this.video_url = this.nav_param.get('video_url');
    this.video_title = this.nav_param.get('video_title');
  }

  closeModal(): void {
    this.modal_ctrl.dismiss();
  }

  onLoad(): void {
    if(this.count_load > 0) {
      this.is_video_loaded = true;
    } else {
      this.count_load++;
    }
  }

}
