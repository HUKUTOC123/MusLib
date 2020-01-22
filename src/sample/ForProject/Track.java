package sample.ForProject;

import java.io.Serializable;

import java.util.Date;

public class Track implements Comparable<Track> {

    String nameTrack;
    String nameGenre;
    int numberTrack;
    Date recordLength;
    String titleAlbum;
    String performerName;

    public Track() {
        numberTrack = 0;
        nameTrack = "";
        nameGenre = "";
        recordLength = new Date(1000L);
        titleAlbum = "";
        performerName = "";
    }

    public Track(int numberTrack,String performerName, String nameTrack, String nameGenre, String titleAlbum, Date recordLength) {
        this.numberTrack = numberTrack;
        this.nameTrack = nameTrack;
        this.nameGenre = nameGenre;
        this.recordLength = recordLength;
        this.titleAlbum = titleAlbum;
        this.performerName = performerName;
    }

    public Date getRecordLength() {
        return recordLength;
    }

    public void setRecordLength(Date recordLength) {
        this.recordLength = recordLength;
    }

    public String getNameTrack() {
        return nameTrack;
    }

    public void setNameTrack(String Track_name) {
        this.nameTrack = Track_name;
    }

    public String getNameGenre() {
        return nameGenre;
    }

    public void setNameGenre(String Genre_Name) {
        this.nameGenre = Genre_Name;
    }


    public String getTitleAlbum() {
        return titleAlbum;
    }

    public void setAlbumTitle(String s) {
        this.titleAlbum = s;
    }

    public String getPerformerName() {
        return performerName;
    }

    public void setPerformerName(String Performer_Name) {
        this.performerName = Performer_Name;
    }

    public int getNumberTrack() {
        return numberTrack;
    }

    public void setNumberTrack(int numberTrack) {
        this.numberTrack = numberTrack;
    }

    @Override
    public int compareTo(Track element) {
        return recordLength.compareTo(element.recordLength);
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(numberTrack).append(" | ").append(performerName).append(" | ").append(nameTrack).append(" | ").append(nameGenre).append(" | ").append(titleAlbum).append(" | ").append(recordLength).append(" | ").append("\n");
        return str.toString();
    }
}



