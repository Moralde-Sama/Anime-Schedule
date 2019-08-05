import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AnimeService {

  constructor(private http: HttpClient) { }

  async getAnimeBySchedule(weekday: string): Promise<any> {
    return new Promise((resolved) => {
      setTimeout(() => {
        this.http.get(`https://api.jikan.moe/v3/schedule/${weekday}`).subscribe(result => {
        resolved(result[weekday]);
      });
      }, 4000);
    });
  }

  getSeasonalAnime(year: number, season: string): Observable<Object> {
    return this.http.get(`https://api.jikan.moe/v3/season/${year}/${season.toLowerCase()}`,
     { observe: 'response' });
  }

  getAnimeMoreInfo(mal_id: number): Observable<Object> {
    return this.http.get(`https://api.jikan.moe/v3/anime/${mal_id}/moreinfo`);
  }
}
