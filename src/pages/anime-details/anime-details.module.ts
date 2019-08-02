import { NgModule } from '@angular/core';
import { IonicPageModule } from 'ionic-angular';
import { AnimeDetailsPage } from './anime-details';

@NgModule({
  declarations: [
    AnimeDetailsPage,
  ],
  imports: [
    IonicPageModule.forChild(AnimeDetailsPage),
  ],
})
export class AnimeDetailsPageModule {}
