import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/delay';

/*
  Generated class for the AnimeServiceProvider provider.

  See https://angular.io/guide/dependency-injection for more info on providers
  and Angular DI.
*/
@Injectable()
export class AnimeServiceProvider {

  constructor(private http: HttpClient) {
  }

  getTodaytRelease(weekday: string): Observable<Object> {
    return this.http.get(`https://api.jikan.moe/v3/schedule/${weekday}`,
     { observe: 'response' }).delay(4000);
  }

  getSeasonalAnime(year: number, season: string): Observable<Object> {
    return this.http.get(`https://api.jikan.moe/v3/season/${year}/${season.toLowerCase()}`,
     { observe: 'response' }).delay(4000);
  }

  getAnimeMoreInfo(mal_id: number): Observable<Object> {
    return this.http.get(`https://api.jikan.moe/v3/anime/${mal_id}/moreinfo`).delay(4000);
  }
}
