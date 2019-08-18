import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';

import { IonicModule } from '@ionic/angular';

import { SchedulePage } from './schedule.page';
import { IonicStorageModule } from '@ionic/storage';
import { DetailsModalModule } from './details-modal/details-modal.module';

const routes: Routes = [
  {
    path: '',
    component: SchedulePage
  }
];

@NgModule({
  imports: [
    CommonModule,
    DetailsModalModule,
    RouterModule.forChild(routes)
  ],
  declarations: [
    SchedulePage,
  ]
})
export class SchedulePageModule {}
