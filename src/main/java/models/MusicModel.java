package models;

public class MusicModel {
    private String title;
    private String artist;
    private String path;

    public MusicModel(String title, String artist, String path) {
        this.title = title;
        this.artist = artist;
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getPath() {
        return path;
    }
}
