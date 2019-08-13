import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';

import { IonicModule } from '@ionic/angular';

import { SchedulePage } from './schedule.page';
import { HttpClientModule } from '@angular/common/http';
import { AnimeService } from '../services/anime.service';
import { IonicStorageModule } from '@ionic/storage';
import { DetailsModalComponent } from './details-modal/details-modal.component';
import { InformationComponent } from './details-modal/information/information.component';
import { StatisticsComponent } from './details-modal/statistics/statistics.component';
import { VideosComponent } from './details-modal/videos/videos.component';
import { SafeVideoPipe } from '../pipe/safevideo.pipe';
import { OpEdThemeComponent } from './details-modal/op-ed-theme/op-ed-theme.component';
import { CharactersComponent } from './details-modal/characters/characters.component';
import { CharacterListComponent } from './details-modal/characters/character-list/character-list.component';
import { VoiceActorComponent } from './details-modal/characters/voice-actor/voice-actor.component';
import { StaffComponent } from './details-modal/staff/staff.component';
import { RelatedAnimeComponent } from './details-modal/related-anime/related-anime.component';
import { AlternativeTitlesComponent } from './details-modal/alternative-titles/alternative-titles.component';
import { ViewVideoComponent } from './details-modal/videos/view-video/view-video.component';

const routes: Routes = [
  {
    path: '',
    component: SchedulePage
  }
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    HttpClientModule,
    RouterModule.forChild(routes),
    IonicStorageModule.forRoot()
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
  declarations: [
    SchedulePage,
    DetailsModalComponent,
    InformationComponent,
    StatisticsComponent,
    VideosComponent,
    SafeVideoPipe,
    OpEdThemeComponent,
    CharactersComponent,
    CharacterListComponent,
    VoiceActorComponent,
    StaffComponent,
    RelatedAnimeComponent,
    AlternativeTitlesComponent,
    ViewVideoComponent
  ],
  providers: [
    AnimeService
  ]
})
export class SchedulePageModule {}
