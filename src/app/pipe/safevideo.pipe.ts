import { Pipe, PipeTransform } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';

@Pipe({
  name: 'safevideo'
})
export class SafeVideoPipe implements PipeTransform {

  constructor(private sanitizer: DomSanitizer) {}
  transform(value: string): any {
    return this.sanitizer.bypassSecurityTrustResourceUrl(value);
  }

}
