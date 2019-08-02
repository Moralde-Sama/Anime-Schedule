import { NgModule } from '@angular/core';
import { HomePopoverComponent } from './home-popover/home-popover';
import { IonicModule } from 'ionic-angular';
@NgModule({
	declarations: [HomePopoverComponent],
	imports: [
		IonicModule.forRoot(HomePopoverComponent)
	],
	exports: [HomePopoverComponent],
	entryComponents: [HomePopoverComponent]
})
export class ComponentsModule {}
