package musicPlayer;

public class Song {
    private String url = null;
    private String localPath = null;
    private int resourceId = 0;
    private String title;
    private String artist;
    private int imageResourceId;

    public Song(String url, String title, String artist) {
        this.url = url;
        this.title = title;
        this.artist = artist;
    }

    public Song(int resourceId, String title, String artist) {
        this.resourceId = resourceId;
        this.title = title;
        this.artist = artist;
    }


    public Song(String localPath, String title, String artist, int imageResourceId, boolean isLocal) {
        this.localPath = localPath;
        this.title = title;
        this.artist = artist;
        this.imageResourceId = imageResourceId;
    }

    public String getUrl() {
        if (url == null) return "";
        return url;
    }

    public String getLocalPath() {
        if (localPath == null) return "";
        return localPath;
    }

    public int getResourceId() {
        return resourceId;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public boolean isUrl() {
        return url != null;
    }

    public boolean isLocalPath() {
        return localPath != null;
    }

    public boolean isResourceId() {
        return resourceId != 0;
    }
}
