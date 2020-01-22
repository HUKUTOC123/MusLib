package sample.ForProject;

import java.util.ArrayList;
import java.util.List;

public class TrackLST {
    List journal = new ArrayList<Track>();

    public TrackLST(){}

    public TrackLST(List journal) {
        this.journal=journal;
    }

    public TrackLST(ArrayList<Track> tracks) {
        this.journal = tracks;
    }

    public List<Track> getJournal() {
        return journal;
    }

    public void setJournal(ArrayList<Track> journal) {
        this.journal = journal;
    }

    public void add(Track track) {
        journal.add(track);
    }

    public void remove(Track track) {
        journal.remove(track);
    }

    public void removeAll(ArrayList<Track> tracks) {
        journal.addAll(tracks);
    }

}
