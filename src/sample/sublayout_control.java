package sample;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Created by sachin on 29/4/16.
 */


public class sublayout_control extends Pane {

    Label season,episode,language;
    JFXTextField seasontext,episodetext;
    JFXComboBox <Label>languag_chooser;

    private  void initialize(){
        language=new Label();
        season=new Label();
        episode=new Label();
        seasontext=new JFXTextField();
        episodetext=new JFXTextField();
        languag_chooser=new JFXComboBox();
    }

    private void setcombobox(){
        languag_chooser.getItems().add(new Label("Arabic"));//ara
        languag_chooser.getItems().add(new Label("English"));//eng
        languag_chooser.getItems().add(new Label("Chinese"));//chi
        languag_chooser.getItems().add(new Label("French")); //fre

        Label label=new Label("English");
        label.setTextFill(Color.BLACK);
//        label.setStyle("-fx-background-color: black");
        languag_chooser.setValue(label);
    }
    sublayout_control(){
        initialize();
        setcombobox();
        setPrefHeight(94);
        setPrefWidth(1000);
        setLayoutX(4);
        setLayoutY(40);
        language.setLayoutX(750);
        language.setLayoutY(42);
        language.setText("Language");
        season.setLayoutX(16);
        season.setLayoutY(42);
        season.setText("Season (series)");
        episode.setLayoutX(378);
        episode.setLayoutY(42);
        episode.setText("Episode (series)");

        seasontext.setLayoutX(128);
        seasontext.setLayoutY(37);
        seasontext.prefWidth(221);
        seasontext.prefHeight(34);
        seasontext.setText("1");
        seasontext.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.length()>0) {
                    if (Character.isDigit(newValue.charAt(newValue.length() - 1))) {
//                        System.out.println("This is a digit");

                    } else {
                        seasontext.setText(oldValue);
                    }
                }
            }
        });


        episodetext.setLayoutX(496);
        episodetext.setLayoutY(37);
        episodetext.prefWidth(221);
        episodetext.prefHeight(34);
        episodetext.setText("1");
        episodetext.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                if (newValue.length()>0) {
                    if (Character.isDigit(newValue.charAt(newValue.length() - 1))) {
//                        System.out.   println("This is a digit");
                    } else {

                        episodetext.setText(oldValue);

                    }
                }
            }
        });

//        languag_chooser.setEditable(true);
        languag_chooser.setLayoutX(855);
        languag_chooser.setLayoutY(33);
        languag_chooser.prefWidth(221);
        languag_chooser.prefHeight(31);

        getChildren().addAll(language,languag_chooser,season,episode,seasontext,episodetext);
    }

    public Label getLanguage() {
        return language;
    }

    public Label getSeason() {
        return season;
    }

    public Label getEpisode() {
        return episode;
    }

    public JFXTextField getSeasontext() {
        return seasontext;
    }

    public JFXTextField getEpisodetext() {
        return episodetext;
    }

    public JFXComboBox getLanguag_chooser() {
        return languag_chooser;
    }
}
