import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';

import { IonicModule } from '@ionic/angular';

import { SeasonsPage } from './seasons.page';
import { SchedulePageModule } from '../schedule/schedule.module';
import { AnimeService } from '../services/anime.service';
import { HttpClientModule } from '@angular/common/http';
import { IonicStorageModule } from '@ionic/storage';
import { DetailsModalModule } from '../schedule/details-modal/details-modal.module';

const routes: Routes = [
  {
    path: '',
    component: SeasonsPage
  }
];

@NgModule({
  imports: [
    CommonModule,
    DetailsModalModule,
    RouterModule.forChild(routes)
  ],
  declarations: [
    SeasonsPage
  ]
})
export class SeasonsPageModule {}
