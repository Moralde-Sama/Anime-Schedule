import * as moment from 'moment';

export class Seasons {
    private seasons: string[] = ['Winter', 'Spring', 'Summer', 'Fall'];

    getCurrentSeason(): string {
        const month = moment().month();
        if(month > 11 && month < 3)
            return this.seasons[0];
        else if(month > 3 && month < 5)
            return this.seasons[1];
        else if(month > 5 && month < 8)
            return this.seasons[2]
        else 
            return this.seasons[3];

    }

    getAllSeasons(): string[] {
        return this.seasons;
    }
}