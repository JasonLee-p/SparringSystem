package musicPlayer;

import java.util.ArrayList;
import java.util.List;

public class SongList {
    private List<Song> songs;
    private int currentIndex;

    public SongList() {
        songs = new ArrayList<>();
        currentIndex = 0;
    }

    public Song getCurrentSong() {
        if (songs.isEmpty()) {
            return null;
        }
        return songs.get(currentIndex);
    }

    public Song getNextSong() {
        if (songs.isEmpty()) {
            return null;
        }
        currentIndex = (currentIndex + 1) % songs.size();
        return songs.get(currentIndex);
    }

    public Song getPreviousSong() {
        if (songs.isEmpty()) {
            return null;
        }
        currentIndex = (currentIndex - 1 + songs.size()) % songs.size();
        return songs.get(currentIndex);
    }

    public Song getSong(int index) {
        if (index < 0 || index >= songs.size()) {
            return null;
        }
        currentIndex = index;
        return songs.get(currentIndex);
    }

    public void addSong(Song song) {
        songs.add(song);
    }

    public void insertSong(Song song, int index) {
        if (index < 0 || index > songs.size()) {
            return;
        }
        songs.add(index, song);
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int index) {
        if (index < 0 || index >= songs.size()) {
            return;
        }
        currentIndex = index;
    }
}
