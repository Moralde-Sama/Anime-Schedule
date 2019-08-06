import { Component, OnInit } from '@angular/core';
import { NavParams, ModalController } from '@ionic/angular';
import { Anime } from 'src/app/classes/anime';

@Component({
  selector: 'app-details-modal',
  templateUrl: './details-modal.component.html',
  styleUrls: ['./details-modal.component.scss'],
})
export class DetailsModalComponent implements OnInit {

  anime_details: Anime;
  constructor(private nav_params: NavParams, private modal_ctrl: ModalController) { }

  ngOnInit() {
    this.anime_details = this.nav_params.get('anime');
    console.log(this.anime_details);
  }

  viewOnMal(link: string): void {
    window.open(link, '_system');
  }

  closeModal(): void {
    this.modal_ctrl.dismiss();
  }

  segmentChanged(ev: any) {
    console.log('Segment changed', ev);
  }

  getRatingColor(rating_text: string): string {
    return (rating_text == 'PG-13 - Teens 13 or older' || rating_text == 'PG - Children') 
        ? 'success': 'warning';
  }

}
