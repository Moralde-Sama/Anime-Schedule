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
    scored_by: number;
    source: string;
    synopsis: string;
    title: string;
    type: string;
    url: string;
    trailer_url: string;
    title_english: string;
    title_japanese: string;
    title_synonyms: string[];
    status: string;
    airing: boolean;
    aired: {from: Date, to: Date, string: string};
    duration: string;
    rating: string;
    rank: number;
    popularity: number;
    favorites: number;
    background: Object;
    premiered: string;
    broadcast: string;
    related: Object[];
    producer: Object[];
    studios: Object[];
    opening_themes: string[];
    ending_themes: string[];
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