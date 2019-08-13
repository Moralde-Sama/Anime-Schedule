import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DetailsModalComponent } from '../schedule/details-modal/details-modal.component';
import { InformationComponent } from '../schedule/details-modal/information/information.component';
import { StatisticsComponent } from '../schedule/details-modal/statistics/statistics.component';
import { VideosComponent } from '../schedule/details-modal/videos/videos.component';
import { OpEdThemeComponent } from '../schedule/details-modal/op-ed-theme/op-ed-theme.component';
import { CharactersComponent } from '../schedule/details-modal/characters/characters.component';
import { CharacterListComponent } from '../schedule/details-modal/characters/character-list/character-list.component';
import { VoiceActorComponent } from '../schedule/details-modal/characters/voice-actor/voice-actor.component';
import { StaffComponent } from '../schedule/details-modal/staff/staff.component';
import { RelatedAnimeComponent } from '../schedule/details-modal/related-anime/related-anime.component';
import { AlternativeTitlesComponent } from '../schedule/details-modal/alternative-titles/alternative-titles.component';
import { ViewVideoComponent } from '../schedule/details-modal/videos/view-video/view-video.component';
import { SafeVideoPipe } from '../pipe/safevideo.pipe';
import { IonicModule } from '@ionic/angular';
import { HttpClientModule } from '@angular/common/http';
import { IonicStorageModule } from '@ionic/storage';
import { AnimeService } from '../services/anime.service';



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
  entryComponents: [
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
    ViewVideoComponent
  ],
  imports: [
    CommonModule,
    IonicModule,
    HttpClientModule,
    IonicStorageModule.forRoot()
  ],
  providers: [
    AnimeService
  ]
})
export class SharedModuleModule { }
