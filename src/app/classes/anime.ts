export class Anime {
    airing_start: Date;
    episodes: number;
    genres: Genres[];
    continuing: boolean;
    image_url: string;
    kids: boolean;
    licensor: Object[];
    mal_id: number;
    members: number;
    producers: Producers[];
    r18: boolean;
    score: number;
    source: string;
    synopsis: string;
    title: string;
    type: string;
    url: string;
}

export class Producers {
    mal_id: number;
    name: string;
    type: string;
    url: string;
}

export class Genres {
    mal_id: number;
    name: string;
    type: string;
    url: string;
}

export type TodayRelease = {
    weekday: string,
    anime: Anime[] 
}