import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DetailsModalComponent } from './details-modal.component';
import { InformationComponent } from './information/information.component';
import { StatisticsComponent } from './statistics/statistics.component';
import { VideosComponent } from './videos/videos.component';
import { OpEdThemeComponent } from './op-ed-theme/op-ed-theme.component';
import { CharactersComponent } from './characters/characters.component';
import { CharacterListComponent } from './characters/character-list/character-list.component';
import { VoiceActorComponent } from './characters/voice-actor/voice-actor.component';
import { StaffComponent } from './staff/staff.component';
import { RelatedAnimeComponent } from './related-anime/related-anime.component';
import { AlternativeTitlesComponent } from './alternative-titles/alternative-titles.component';
import { ViewVideoComponent } from './videos/view-video/view-video.component';
import { SafeVideoPipe } from 'src/app/pipe/safevideo.pipe';
import { AnimeService } from 'src/app/services/anime.service';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { IonicModule } from '@ionic/angular';
import { IonicStorageModule } from '@ionic/storage';



@NgModule({
  declarations: [
    DetailsModalComponent,
    InformationComponent,
    StatisticsComponent,
    VideosComponent,
    OpEdThemeComponent,
    CharactersComponent,
    CharacterListComponent,
    VoiceActorComponent,
    StaffComponent,
    RelatedAnimeComponent,
    AlternativeTitlesComponent,
    ViewVideoComponent,
    SafeVideoPipe
  ],
  imports: [
    CommonModule,
    HttpClientModule,
    FormsModule,
    IonicModule,
    IonicStorageModule.forRoot()
  ],
  exports: [
    FormsModule,
    IonicModule,
    IonicStorageModule
  ],
  entryComponents: [
    DetailsModalComponent,
    ViewVideoComponent
  ],
  providers: [
    AnimeService
  ]
})
export class DetailsModalModule { }
