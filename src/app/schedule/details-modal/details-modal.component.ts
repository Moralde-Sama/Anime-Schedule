import { Component } from '@angular/core';
import { NavParams, ModalController, LoadingController } from '@ionic/angular';
import { Anime } from 'src/app/classes/anime';

@Component({
  selector: 'app-details-modal',
  templateUrl: './details-modal.component.html',
  styleUrls: ['./details-modal.component.scss'],
})
export class DetailsModalComponent {

  anime_details: Anime;
  loading: HTMLIonLoadingElement
  constructor(private nav_params: NavParams, private modal_ctrl: ModalController,
    private loader: LoadingController) { }
    
  async ionViewWillEnter() {
    this.loading = await this._presentLoading();
  }
  async ionViewDidEnter() {
    this.anime_details = this.nav_params.get('anime');
    this.loading.dismiss();
  }

  viewOnMal(link: string): void {
    window.open(link, '_system');
  }

  closeModal(): void {
    this.modal_ctrl.dismiss();
  }

  segmentChanged(ev: any) {
    console.log('Segment changed');
  }

  getRatingColor(rating_text: string): string {
    return (rating_text == 'PG-13 - Teens 13 or older' || rating_text == 'PG - Children') 
        ? 'success': 'warning';
  }

  private async _presentLoading() {
    const loader = await this.loader.create({
      message: 'Please wait...'
    });
    await loader.present();
    return loader;
  }

}
