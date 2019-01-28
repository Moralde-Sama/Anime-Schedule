package epm.animeschedule.MainClasses;

public class Anime {
    String AnimeID;
    String Day;
    String Description;
    String Duration;
    String Episode;
    String ImageLink;
    String Score;
    String ImageLinkHeader;
    String Month;
    String Rating;
    String Season;
    String Source;
    String Start;
    String End;
    String Status;
    String Studio;
    String Title;
    String Type;
    String Year;
    String Time;


    public String getScore() {
        return Score;
    }

    public String getDuration() {
        return Duration;
    }

    public String getRating() {
        return Rating;
    }

    public String getSource() {
        return Source;
    }

    public String getImageLinkHeader() {
        return ImageLinkHeader;
    }

    public String getTime() {
        return Time;
    }

    public String getAnimeID() {
        return AnimeID;
    }

    public String getDescription() {
        return Description;
    }

    public String getImageLink() {
        return ImageLink;
    }

    public String getSeason() {
        return Season;
    }

    public String getStart() {
        return Start;
    }

    public String getDay() {
        return Day;
    }

    public String getMonth() {
        return Month;
    }

    public String getYear() {
        return Year;
    }

    public String getStatus() {
        return Status;
    }

    public String getStudio() {
        return Studio;
    }

    public String getTitle() {
        return Title;
    }

    public String getType() {
        return Type;
    }

    public String getEpisode() { return Episode; }

    public String getEnd() {
        return End;
    }

    public Anime(String animeID, String Day, String description, String duration, String episode, String imageLink, String ImageLinkHeader,
                 String Month, String rating, String Score, String season, String source, String start, String end, String status, String studio, String title,
                 String type, String Year, String Time) {

        AnimeID = animeID;
        Description = description;
        Episode = episode;
        ImageLink = imageLink;
        this.Score = Score;
        Season = season;
        Start = start;
        Duration = duration;
        Rating = rating;
        Source = source;
        End = end;
        Status = status;
        Studio = studio;
        Title = title;
        Type = type;
        this.Month = Month;
        this.Day = Day;
        this.Year = Year;
        this.Time = Time;
        this.ImageLinkHeader = ImageLinkHeader;
    }
}
