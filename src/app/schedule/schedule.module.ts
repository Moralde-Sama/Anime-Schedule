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
    InformationComponent
  ],
  declarations: [
    SchedulePage,
    DetailsModalComponent,
    InformationComponent
  ],
  providers: [
    AnimeService
  ]
})
export class SchedulePageModule {}
