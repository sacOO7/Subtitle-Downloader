package sample;

import Opensubs.SubtitleInfo;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by sachin on 28/4/16.
 */

//Sub file name
//Language
//Download count
//Sub publish Date
//IMDB rating
//Year


public class Subtitleinfo extends RecursiveTreeObject<Subtitleinfo> {

    public StringProperty IDSubMovieFile;
    public boolean isDownloaded=false;
    public StringProperty MovieHash,MovieByteSize,MovieTimeMS,MovieFrames,IDSubtitleFile,SubFileName,SubActualCD,SubSize,SubHash;
    public StringProperty IDSubtitle,UserID,SubLanguageID,SubFormat,SubSumCD,SubAddDate,SubDownloadsCnt,SubBad,SubRating;
    public StringProperty IDMovie,IDMovieImdb,MovieName,MovieNameEng,MovieYear,MovieImdbRating,UserNickName,ISO639,LanguageName,SubDownloadLink;

    public boolean isDownloaded() {
        return isDownloaded;
    }

    public void setDownloaded(boolean downloaded) {
        isDownloaded = downloaded;
    }

    Subtitleinfo(SubtitleInfo info){
        this.MovieName= new SimpleStringProperty(info.getMovieName());
        this.IDMovieImdb=new SimpleStringProperty(info.getIDMovieImdb());
        this.SubDownloadLink=new SimpleStringProperty(info.getSubDownloadLink());
        this.SubFileName=new SimpleStringProperty(info.getSubFileName());
        this.LanguageName=new SimpleStringProperty(info.getLanguageName());
        this.SubDownloadsCnt=new SimpleStringProperty(info.getSubDownloadsCnt());
        this.SubAddDate=new SimpleStringProperty(info.getSubAddDate());
        this.MovieImdbRating=new SimpleStringProperty(info.getMovieImdbRating());
        this.MovieYear=new SimpleStringProperty(info.getMovieYear());

    }

    public String getSubActualCD() {
        return SubActualCD.get();
    }

    public StringProperty subActualCDProperty() {
        return SubActualCD;
    }

    public void setSubActualCD(String subActualCD) {
        this.SubActualCD.set(subActualCD);
    }

    public String getIDSubMovieFile() {
        return IDSubMovieFile.get();
    }

    public StringProperty IDSubMovieFileProperty() {
        return IDSubMovieFile;
    }

    public void setIDSubMovieFile(String IDSubMovieFile) {
        this.IDSubMovieFile.set(IDSubMovieFile);
    }

    public String getMovieHash() {
        return MovieHash.get();
    }

    public StringProperty movieHashProperty() {
        return MovieHash;
    }

    public void setMovieHash(String movieHash) {
        this.MovieHash.set(movieHash);
    }

    public String getMovieByteSize() {
        return MovieByteSize.get();
    }

    public StringProperty movieByteSizeProperty() {
        return MovieByteSize;
    }

    public void setMovieByteSize(String movieByteSize) {
        this.MovieByteSize.set(movieByteSize);
    }

    public String getMovieTimeMS() {
        return MovieTimeMS.get();
    }

    public StringProperty movieTimeMSProperty() {
        return MovieTimeMS;
    }

    public void setMovieTimeMS(String movieTimeMS) {
        this.MovieTimeMS.set(movieTimeMS);
    }

    public String getMovieFrames() {
        return MovieFrames.get();
    }

    public StringProperty movieFramesProperty() {
        return MovieFrames;
    }

    public void setMovieFrames(String movieFrames) {
        this.MovieFrames.set(movieFrames);
    }

    public String getIDSubtitleFile() {
        return IDSubtitleFile.get();
    }

    public StringProperty IDSubtitleFileProperty() {
        return IDSubtitleFile;
    }

    public void setIDSubtitleFile(String IDSubtitleFile) {
        this.IDSubtitleFile.set(IDSubtitleFile);
    }

    public String getSubFileName() {
        return SubFileName.get();
    }

    public StringProperty subFileNameProperty() {
        return SubFileName;
    }

    public void setSubFileName(String subFileName) {
        this.SubFileName.set(subFileName);
    }

    public String getSubSize() {
        return SubSize.get();
    }

    public StringProperty subSizeProperty() {
        return SubSize;
    }

    public void setSubSize(String subSize) {
        this.SubSize.set(subSize);
    }

    public String getSubHash() {
        return SubHash.get();
    }

    public StringProperty subHashProperty() {
        return SubHash;
    }

    public void setSubHash(String subHash) {
        this.SubHash.set(subHash);
    }

    public String getIDSubtitle() {
        return IDSubtitle.get();
    }

    public StringProperty IDSubtitleProperty() {
        return IDSubtitle;
    }

    public void setIDSubtitle(String IDSubtitle) {
        this.IDSubtitle.set(IDSubtitle);
    }

    public String getUserID() {
        return UserID.get();
    }

    public StringProperty userIDProperty() {
        return UserID;
    }

    public void setUserID(String userID) {
        this.UserID.set(userID);
    }

    public String getSubLanguageID() {
        return SubLanguageID.get();
    }

    public StringProperty subLanguageIDProperty() {
        return SubLanguageID;
    }

    public void setSubLanguageID(String subLanguageID) {
        this.SubLanguageID.set(subLanguageID);
    }

    public String getSubFormat() {
        return SubFormat.get();
    }

    public StringProperty subFormatProperty() {
        return SubFormat;
    }

    public void setSubFormat(String subFormat) {
        this.SubFormat.set(subFormat);
    }

    public String getSubSumCD() {
        return SubSumCD.get();
    }

    public StringProperty subSumCDProperty() {
        return SubSumCD;
    }

    public void setSubSumCD(String subSumCD) {
        this.SubSumCD.set(subSumCD);
    }

    public String getSubAddDate() {
        return SubAddDate.get();
    }

    public StringProperty subAddDateProperty() {
        return SubAddDate;
    }

    public void setSubAddDate(String subAddDate) {
        this.SubAddDate.set(subAddDate);
    }

    public String getSubDownloadsCnt() {
        return SubDownloadsCnt.get();
    }

    public StringProperty subDownloadsCntProperty() {
        return SubDownloadsCnt;
    }

    public void setSubDownloadsCnt(String subDownloadsCnt) {
        this.SubDownloadsCnt.set(subDownloadsCnt);
    }

    public String getSubBad() {
        return SubBad.get();
    }

    public StringProperty subBadProperty() {
        return SubBad;
    }

    public void setSubBad(String subBad) {
        this.SubBad.set(subBad);
    }

    public String getSubRating() {
        return SubRating.get();
    }

    public StringProperty subRatingProperty() {
        return SubRating;
    }

    public void setSubRating(String subRating) {
        this.SubRating.set(subRating);
    }

    public String getIDMovie() {
        return IDMovie.get();
    }

    public StringProperty IDMovieProperty() {
        return IDMovie;
    }

    public void setIDMovie(String IDMovie) {
        this.IDMovie.set(IDMovie);
    }

    public String getIDMovieImdb() {
        return IDMovieImdb.get();
    }

    public StringProperty IDMovieImdbProperty() {
        return IDMovieImdb;
    }

    public void setIDMovieImdb(String IDMovieImdb) {
        this.IDMovieImdb.set(IDMovieImdb);
    }

    public String getMovieName() {
        return MovieName.get();
    }

    public StringProperty movieNameProperty() {
        return MovieName;
    }

    public void setMovieName(String movieName) {
        this.MovieName.set(movieName);
    }

    public String getMovieNameEng() {
        return MovieNameEng.get();
    }

    public StringProperty movieNameEngProperty() {
        return MovieNameEng;
    }

    public void setMovieNameEng(String movieNameEng) {
        this.MovieNameEng.set(movieNameEng);
    }

    public String getMovieYear() {
        return MovieYear.get();
    }

    public StringProperty movieYearProperty() {
        return MovieYear;
    }

    public void setMovieYear(String movieYear) {
        this.MovieYear.set(movieYear);
    }

    public String getMovieImdbRating() {
        return MovieImdbRating.get();
    }

    public StringProperty movieImdbRatingProperty() {
        return MovieImdbRating;
    }

    public void setMovieImdbRating(String movieImdbRating) {
        this.MovieImdbRating.set(movieImdbRating);
    }

    public String getUserNickName() {
        return UserNickName.get();
    }

    public StringProperty userNickNameProperty() {
        return UserNickName;
    }

    public void setUserNickName(String userNickName) {
        this.UserNickName.set(userNickName);
    }

    public String getISO639() {
        return ISO639.get();
    }

    public StringProperty ISO639Property() {
        return ISO639;
    }

    public void setISO639(String ISO639) {
        this.ISO639.set(ISO639);
    }

    public String getLanguageName() {
        return LanguageName.get();
    }

    public StringProperty languageNameProperty() {
        return LanguageName;
    }

    public void setLanguageName(String languageName) {
        this.LanguageName.set(languageName);
    }

    public String getSubDownloadLink() {
        return SubDownloadLink.get();
    }

    public StringProperty subDownloadLinkProperty() {
        return SubDownloadLink;
    }

    public void setSubDownloadLink(String subDownloadLink) {
        this.SubDownloadLink.set(subDownloadLink);
    }
}
