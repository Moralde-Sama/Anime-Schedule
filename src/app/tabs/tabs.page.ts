import { Component, Renderer2 } from '@angular/core';

@Component({
  selector: 'app-tabs',
  templateUrl: 'tabs.page.html',
  styleUrls: ['tabs.page.scss']
})
export class TabsPage {
  current_selected: number = 0;
  constructor(private renderer: Renderer2) {}

  changeTab(index: number): void {
    this.current_selected = index;
    const tabs = document.querySelectorAll('ion-tab-button');
    this.renderer.addClass(tabs[this.current_selected], 'tab-selected');
    console.log(tabs);
  }

}
