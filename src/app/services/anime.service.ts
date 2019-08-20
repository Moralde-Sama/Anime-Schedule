import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subscription } from 'rxjs';
import { delay } from 'rxjs/operators';

@Injectable()
export class AnimeService {

  private _pending_request: Subscription;
  constructor(private http: HttpClient) { }

  async getAnimeBySchedule(weekday: string): Promise<any> {
    return new Promise((resolved) => {
      setTimeout(() => {
        this.http.get(`https://api.jikan.moe/v3/schedule/${weekday}`)
        .subscribe(
          result => resolved(result[weekday]),
          () => resolved('Error'));
      }, 4000);
    });
  }

  async getAnimeDetails(mal_id: number): Promise<any> {
    return new Promise((resolved) => {
      setTimeout(() => {
        this.http.get(`https://api.jikan.moe/v3/anime/${mal_id}/`)
        .subscribe(result => {
          resolved(result);
        },
        () => resolved('Error'));
      }, 4000);
    });
  }

  async getSeasonalAnime(year: number, season: string): Promise<any> {
    return new Promise((resolve) => {
      this._pending_request =
       this.http.get(`https://api.jikan.moe/v3/season/${year}/${season.toLowerCase()}`)
       .pipe(delay(4000))
       .subscribe(
         value => {
          resolve(value['anime']);
          this._pending_request.unsubscribe();
         },
         () => resolve('Error'));
    });
  }

  getAnimeMoreInfo(mal_id: number): Observable<Object> {
    return this.http.get(`https://api.jikan.moe/v3/anime/${mal_id}/moreinfo`);
  }

  getAnimeVideos(mal_id: number): Promise<any> {
    return new Promise((resolve) => {
      this._pending_request = this.http.get(`https://api.jikan.moe/v3/anime/${mal_id}/videos/`)
      .pipe(delay(4000))
      .subscribe(value => {
          resolve(value['promo']);
          this._pending_request.unsubscribe();
        });
    });
  }

  getAnimeCharacters(mal_id: number): Promise<any> {
    return new Promise((resolve) => {
      this._pending_request = this.http.get(`https://api.jikan.moe/v3/anime/${mal_id}/characters_staff/`)
        .pipe(delay(4000))
        .subscribe(value => {
          resolve({
            characters: value['characters'],
            staff: value['staff']
          });
          this._pending_request.unsubscribe();
        });
    });
  }

  cancelPendingRequest(): void {
    if(this._pending_request != undefined)
      this._pending_request.unsubscribe();
  }
}
