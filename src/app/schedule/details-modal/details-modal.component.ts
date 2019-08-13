import { Component, OnInit } from '@angular/core';
import { NavParams, ModalController, LoadingController } from '@ionic/angular';
import { Anime, AnimeVideos, CharactersAndStaff } from 'src/app/classes/anime';

@Component({
  selector: 'app-details-modal',
  templateUrl: './details-modal.component.html',
  styleUrls: ['./details-modal.component.scss'],
})
export class DetailsModalComponent implements OnInit {

  anime_details: Anime;
  anime_details_input: string;
  loading: HTMLIonLoadingElement;
  selected_segment: string;
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

  segmentChanged(ev: any): void {
    this.selected_segment = ev['detail']['value'];
  }

  isSegmentSelected(segment_name: string): boolean {
    return segment_name == this.selected_segment;
  }

  onAddedVideos(videos: AnimeVideos[]): void {
    this.anime_details.videos = videos;
    this.anime_details_input = JSON.stringify(this.anime_details);
  }

  onAddedAnimeChar(char_and_staff: CharactersAndStaff): void {
    this.anime_details.characters = char_and_staff.characters;
    this.anime_details.staff = char_and_staff.staff;
    console.log(this.anime_details);
  }

  private async _presentLoading() {
    const loader = await this.loader.create({
      message: 'Please wait...'
    });
    await loader.present();
    return loader;
  }

}
