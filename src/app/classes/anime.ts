export class Anime {
    airing_start: Date;
    episodes: number;
    genres: Genres[];
    continuing: boolean;
    image_url: string;
    kids: boolean;
    licensors: Object[];
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
    videos: AnimeVideos[];
    characters: AnimeCharacters;
    staff: AnimeStaff;
}

export class AnimeVideos {
    title: string;
    image_url: string;
    video_url: string;
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

export class AnimeCharacters {
    mal_id: number;
    url: string;
    image_url: string;
    name: string;
    role: string;
    voice_actors: AnimeVoiceActors[];
}

export class AnimeVoiceActors {
    mal_id: number;
    name: string;
    url: string;
    image_url: string;
    language: string;
}

export class AnimeStaff {
    mal_id: string;
    url: string;
    name: string;
    image_url: string;
    position: string[];
}

export type TodayRelease = {
    weekday: string,
    anime: Anime[] 
}

export type CharactersAndStaff = {
    characters: AnimeCharacters,
    staff: AnimeStaff
};