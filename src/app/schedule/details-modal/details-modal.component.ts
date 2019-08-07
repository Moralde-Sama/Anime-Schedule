import { Component, OnInit } from '@angular/core';
import { NavParams, ModalController, LoadingController } from '@ionic/angular';
import { Anime } from 'src/app/classes/anime';

@Component({
  selector: 'app-details-modal',
  templateUrl: './details-modal.component.html',
  styleUrls: ['./details-modal.component.scss'],
})
export class DetailsModalComponent implements OnInit {

  anime_details: Anime;
  anime_details_input: string;
  loading: HTMLIonLoadingElement
  constructor(private nav_params: NavParams, private modal_ctrl: ModalController,
    private loader: LoadingController) { 
      this.anime_details = this.nav_params.get('anime');
      this.anime_details_input = JSON.stringify(this.nav_params.get('anime'));
      console.log(this.anime_details);
    }

  async ngOnInit() {
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

  private async _presentLoading() {
    const loader = await this.loader.create({
      message: 'Please wait...'
    });
    await loader.present();
    return loader;
  }

}
