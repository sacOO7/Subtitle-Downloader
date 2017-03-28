package sample;

import Opensubs.OpenSubtitle;
import Opensubs.OpenSubtitleHasher;
import Opensubs.SubtitleInfo;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

//Sub file name
//Language
//Download count
//Sub publish Date
//IMDB rating
//Year

public class Controller implements Initializable{


    public static final Integer DOWNLOAD=1;
    public static final Integer DOWNLOADING=2;
    public static final Integer OPENFOLDER=3;
    public static  int STAGEHEIGHT1 =620 ;
    public static  int STAGEHEIGHT2 =550 ;
    public HashMap <Integer,Integer> status;

    public JFXTreeTableView jfxtable;

    public JFXTextField searchedittext;
    public JFXButton searchbutton;
    public JFXProgressBar progressbar;
    public JFXCheckBox advancedoptions;
    public AnchorPane anchorroot;
    public HashMap <String,String> map;
    public Stage primary_stage;
    public boolean advanced=false;
    public boolean Login=false;
    public ImageView setting;
    public Label jfxtablelabel;

    public List <Thread> threads;
    public ImageView deleteandclose;
    private String tablestyle;


    public Stage getPrimary_stage() {
        return primary_stage;
    }

    public void setPrimary_stage(Stage primary_stage) {
        this.primary_stage = primary_stage;
    }

    TreeItem<Subtitleinfo> root;

    public OpenSubtitle subtitle;
    ObservableList<Subtitleinfo> subtitleinfoObservableList ;
    public List <SubtitleInfo> subtitleInfos;
    private sublayout_control control;



    public boolean CheckAlreadyDownloaded(String filename){
        return new File(filename).exists();
    }

    public boolean converttoanotherarraylist(){
        subtitleinfoObservableList.clear();
        status.clear();

        if (subtitleInfos.size()==0){
            return false;
        }else {
//        Main.path+File.separator+subtitleinfo.getMovieName().replaceAll("[^a-zA-Z\\s]","")+File.separator+subtitleinfo.getSubFileName()
            for (SubtitleInfo subtitleInfo : subtitleInfos) {
                if (CheckAlreadyDownloaded(Main.path + File.separator + subtitleInfo.getMovieName().replaceAll("[^a-zA-Z\\s]", "") + File.separator + subtitleInfo.getSubFileName())) {
                    status.put(subtitleInfos.indexOf(subtitleInfo), OPENFOLDER);
                } else {
                    status.put(subtitleInfos.indexOf(subtitleInfo), DOWNLOAD);
                }
                subtitleinfoObservableList.add(new Subtitleinfo(subtitleInfo));
            }
            return true;
        }
    }

    public void login(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                progressbar.setVisible(true);
                searchbutton.setDisable(true);
                jfxtablelabel=new Label("Logging in ,Please wait ...");
                jfxtablelabel.setTextFill(Color.BLACK);
                jfxtablelabel.setStyle("-fx-font-size: 16");
                jfxtable.setPlaceholder(jfxtablelabel);
            }
        });
                try {
                    subtitle.login();
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            Login=true;
                            ((Label)jfxtable.getPlaceholder()).setText("Searching for subs...");
//                            setdefaulttableview();
                        }
                    });
                } catch (Exception e){
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            setdefaulttableview();
                            ((Label)jfxtable.getPlaceholder()).setText("Error logging in, Check your network connection");
                        }
                    });
            }
    }

//    public void setOpenfolder(){
//        for (int i=0;i<subtitleinfoObservableList.size();i++){
//            if (status.get(i)==OPENFOLDER){
//                System.out.println(jfxtable.getTreeItem(i).nextSibling());
//            }
//        }
//    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        subtitle=new OpenSubtitle();
        control=new sublayout_control();
        map=new HashMap<>();
        threads=new ArrayList<>();
        status=new HashMap<>();

//        try {
//            login();

        setdefaulttableview();

        setting.hoverProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                Tooltip.install(setting, new Tooltip("Set Download folder"));
            }
        });

        deleteandclose.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                if (progressbar.isVisible()) {
                    System.out.println("Stopping threads");
                    stopAllThreads();
                } else {
                    System.out.println("Clearing table data");
                    clearTableData();
                }
            }
        });


        setting.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                ContextMenu contextMenu = new ContextMenu();
                contextMenu.setStyle("-fx-focus-color: rgba(82, 100, 174)");
                MenuItem item1 = new MenuItem("Choose Download Directory");
                item1.setStyle("-fx-focus-color: rgba(82, 100, 174)");
                item1.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        select_directory();
                    }
                });
                MenuItem item2 = new MenuItem("Show Downloads");
                item2.setStyle("-fx-focus-color: rgba(82, 100, 174)");
                item2.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Main.openDesktop(Main.path);
                            }
                        }).start();
                    }
                });

                MenuItem item3 = new MenuItem("About");
                item3.setStyle("-fx-focus-color: rgba(82, 100, 174)");
                item3.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                    }
                });

                contextMenu.getItems().addAll(item2, item1, item3);
                contextMenu.show(setting, event.getScreenX(), event.getScreenY());
//                select_directory();
            }

            private void select_directory() {
                DirectoryChooser directoryChooser = new DirectoryChooser();
                directoryChooser.setInitialDirectory(new File(Main.path));
                directoryChooser.setTitle("Choose Download Directory");
                File file = directoryChooser.showDialog(getPrimary_stage());
                if (file == null) {
//                Alert alert=new Alert(Alert.AlertType.ERROR);
//                    alert.setTitle("Error selecting directory");
//                    alert.setContentText("Please select proper directory");
//                    alert.show();
//                    alert.setOnCloseRequest(new EventHandler<DialogEvent>() {
//                        @Override
//                        public void handle(DialogEvent event) {
//                            select_directory();
//                        }
//                    });

                } else {
                    Main.path = file.getAbsolutePath();
                }
            }
        });


        jfxtable.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                tablestyle = jfxtable.getStyle();
                jfxtable.setStyle("-fx-background-color: lightgray");
            }
        });

        jfxtable.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                jfxtable.setStyle(tablestyle);
            }
        });



        jfxtable.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                if (event.getGestureSource() != jfxtable
                        && event.getDragboard().hasFiles()) {
                    /* allow for both copying and moving, whatever user chooses */
                    event.acceptTransferModes(TransferMode.ANY);
                }
                event.consume();
            }
        });

        jfxtable.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
//                List<File> files = (ArrayList<File>) db.getContent(DataFormat.FILES);
                boolean success = false;
                File file=null;
                if (db.getFiles().get(0)!=null) {
                    file=db.getFiles().get(0);
                    success = true;
                }
                /* let the source know whether the string was successfully
                 * transferred and used */
                event.setDropCompleted(success);
                event.consume();

                getsublist(file);
            }
        });

        map.put("Arabic","ara");
        map.put("English","eng");
        map.put("Chinese","chi");
        map.put("French","fre");

        subtitleinfoObservableList= FXCollections.observableArrayList();

//        progressbar.setVisible(false);


        JFXTreeTableColumn<Subtitleinfo, String> title = new JFXTreeTableColumn<>("Title");
        title.setMinWidth(470);
        title.setCellValueFactory((TreeTableColumn.CellDataFeatures<Subtitleinfo, String> param) ->{
            if(title.validateValue(param)) return param.getValue().getValue().SubFileName;
            else return title.getComputedValue(param);
        });

//        JFXTreeTableColumn<Subtitleinfo, String> language = new JFXTreeTableColumn<>("Language");
//        language.setMinWidth(150);
//        language.setCellValueFactory((TreeTableColumn.CellDataFeatures<Subtitleinfo, String> param) ->{
//            if(language.validateValue(param)) return param.getValue().getValue().LanguageName;
//            else return language.getComputedValue(param);
//        });

        JFXTreeTableColumn<Subtitleinfo, String> downloads= new JFXTreeTableColumn<>("Downloads");
        downloads.setMinWidth(150);
        downloads.setCellValueFactory((TreeTableColumn.CellDataFeatures<Subtitleinfo, String> param) ->{
            if(downloads.validateValue(param)) return param.getValue().getValue().SubDownloadsCnt;
            else return downloads.getComputedValue(param);
        });

        JFXTreeTableColumn<Subtitleinfo, String> date = new JFXTreeTableColumn<>("Uploaded on");
        date.setMinWidth(190);
        date.setCellValueFactory((TreeTableColumn.CellDataFeatures<Subtitleinfo, String> param) ->{
            if(date.validateValue(param)) return param.getValue().getValue().SubAddDate;
            else return date.getComputedValue(param);
        });

        JFXTreeTableColumn Action = new JFXTreeTableColumn<>("Action");
        Action.setMinWidth(190);
        Action.setCellFactory(new Callback<TreeTableColumn, TreeTableCell>() {
            @Override
            public TreeTableCell call(TreeTableColumn param) {
                ButtonCell cell=new ButtonCell();
                cell.setStyle("-fx-alignment: top-center ");
                return new ButtonCell();
            }

        });



//        JFXTreeTableColumn<Subtitleinfo, String> rating = new JFXTreeTableColumn<>("IMDB rating");
//        rating.setMinWidth(150);
//        rating.setCellValueFactory((TreeTableColumn.CellDataFeatures<Subtitleinfo, String> param) ->{
//            if(rating.validateValue(param)) return param.getValue().getValue().MovieImdbRating;
//            else return rating.getComputedValue(param);
//        });
//
//        JFXTreeTableColumn<Subtitleinfo, String> year = new JFXTreeTableColumn<>("Year");
//        year.setMinWidth(150);
//        year.setCellValueFactory((TreeTableColumn.CellDataFeatures<Subtitleinfo, String> param) ->{
//            if(year.validateValue(param)) return param.getValue().getValue().MovieYear;
//            else return year.getComputedValue(param);
//        });


//        ageColumn.setCellFactory((TreeTableColumn<User, String> param) -> new GenericEditableTreeTableCell<User, String>(new TextFieldEditorBuilder()));
//        ageColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<User, String> t)->{
//            ((User) t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue()).age.set(t.getNewValue());
//        });
//
//        empColumn.setCellFactory((TreeTableColumn<User, String> param) -> new GenericEditableTreeTableCell<User, String>(new TextFieldEditorBuilder()));
//        empColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<User, String> t)->{
//            ((User) t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue()).userName.set(t.getNewValue());
//        });
//
//        deptColumn.setCellFactory((TreeTableColumn<User, String> param) -> new GenericEditableTreeTableCell<User, String>(new TextFieldEditorBuilder()));
//        deptColumn.setOnEditCommit((TreeTableColumn.CellEditEvent<User, String> t)->{
//            ((User) t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue()).department.set(t.getNewValue());
//        });




        searchbutton.setOnMouseClicked(event -> {

            if (!advanced) {

                progressbar.setVisible(true);
                deleteandclose.setVisible(true);
                deleteandclose.setImage(new Image("/sample/ic_close_black_24dp_2x.png"));
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {

                            if (!Login)
                                login();
                            subtitleInfos = subtitle.getMovieSubsByName(searchedittext.getText(), "20", "eng");
                            if (converttoanotherarraylist()) {
//                System.out.println(subtitleinfoObservableList.size());

//                root.notifyAll();
                                root.getChildren().removeAll();
                                root = new RecursiveTreeItem<>(subtitleinfoObservableList, RecursiveTreeObject::getChildren);

                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressbar.setVisible(false);
                                        setdefaulttableview();
                                        deleteandclose.setImage(new Image("/sample/ic_delete_black_24dp_2x.png"));
                                        jfxtable.setRoot(root);
//                                    setOpenfolder();

                                    }
                                });
//                        jfxtable.refresh();
                            }else{
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressbar.setVisible(false);
                                        searchbutton.setDisable(false);

                                        //                Alert alert=new Alert(Alert.AlertType.ERROR);
                                        if (!isrootnull())
                                            deleteandclose.setImage(new Image("/sample/ic_delete_black_24dp_2x.png"));
                                        else{
                                            deleteandclose.setVisible(false);
                                        }
                                        ((Label)jfxtable.getPlaceholder()).setText("No subtitles found");
                                    }
                                });
                            }
                        } catch (Exception e) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    progressbar.setVisible(false);
                                    //                Alert alert=new Alert(Alert.AlertType.ERROR);
                                    if (!isrootnull())
                                        deleteandclose.setImage(new Image("/sample/ic_delete_black_24dp_2x.png"));
                                    else{
                                        deleteandclose.setVisible(false);
                                    }
                                    ((Label)jfxtable.getPlaceholder()).setText(" Check your network connection");
                                    Alert alert=new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Error getting data");
                                    alert.setContentText("Please check your network connection");
                                    alert.show();
                                    alert.setOnCloseRequest(new EventHandler<DialogEvent>() {
                                        @Override
                                        public void handle(DialogEvent event) {
//                                            select_directory();
                                        }
                                    });
                                }
                            });
                        }

                    }
                });

                t.start();
                threads.add(t);
            }else{

                progressbar.setVisible(true);
                deleteandclose.setVisible(true);
                deleteandclose.setImage(new Image("/sample/ic_close_black_24dp_2x.png"));

                Thread t1= new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (!Login)
                                login();
                            System.out.println(((Label)control.getLanguag_chooser().getValue()).getText());
                            subtitleInfos = subtitle.getTvSeriesSubs(searchedittext.getText(), control.getSeasontext().getText(), control.getEpisodetext().getText(),"20",map.get(((Label)control.getLanguag_chooser().getValue()).getText()));
                            if(converttoanotherarraylist()) {
                                root.getChildren().removeAll();
                                root = new RecursiveTreeItem<>(subtitleinfoObservableList, RecursiveTreeObject::getChildren);

                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        setdefaulttableview();

                                        progressbar.setVisible(false);
                                        deleteandclose.setImage(new Image("/sample/ic_delete_black_24dp_2x.png"));
                                        jfxtable.setRoot(root);
//                                    setOpenfolder();
                                    }
                                });
//                        jfxtable.refresh();

                            }else{
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressbar.setVisible(false);
                                        searchbutton.setDisable(false);

                                        //                Alert alert=new Alert(Alert.AlertType.ERROR);
                                        if (!isrootnull())
                                            deleteandclose.setImage(new Image("/sample/ic_delete_black_24dp_2x.png"));
                                        else{
                                            deleteandclose.setVisible(false);
                                        }
                                        ((Label)jfxtable.getPlaceholder()).setText("No subtitles found");
                                    }
                                });
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {

                                    if (!isrootnull())
                                        deleteandclose.setImage(new Image("/sample/ic_delete_black_24dp_2x.png"));
                                    else{
                                        deleteandclose.setVisible(false);
                                    }
                                    progressbar.setVisible(false);
                                    ((Label)jfxtable.getPlaceholder()).setText(" Check your network connection");

//                                    deleteandclose.setImage(new Image("/sample/ic_delete_black_24dp_2x.png"));
                                    Alert alert=new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("Error getting data");
                                    alert.setContentText("Please check your network connection");
                                    alert.show();
                                    alert.setOnCloseRequest(new EventHandler<DialogEvent>() {
                                        @Override
                                        public void handle(DialogEvent event) {
//                                            select_directory();
                                        }
                                    });

                                }
                            });

                        }

                    }
                });

                t1.start();
                threads.add(t1);
            }

        });
        // data



//        searchedittext.setOnKeyTyped(new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent event) {
//                if (event.getCharacter()=="\n"){
//                    searchbutton.getOnMouseClicked().handle();
//                }
//            }
//        });




        root= new RecursiveTreeItem<>(subtitleinfoObservableList, RecursiveTreeObject::getChildren);
        jfxtable.setRoot(null);
        jfxtable.setShowRoot(false);
        jfxtable.setEditable(false);
        jfxtable.getColumns().setAll(title, downloads,date,Action);


        advancedoptions.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (advancedoptions.isSelected()){

                    if (!advanced) {
                        System.out.println("Checked");
                        advanced = true;
//                        ExpandStage();
                        getPrimary_stage().setHeight(STAGEHEIGHT1);
                        TranslateTransition tt = new TranslateTransition(Duration.millis(300), jfxtable);
                        tt.setByY(70);
                        tt.play();
                        tt.setOnFinished(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                try {
                                    anchorroot.getChildren().add(control);
                                } catch (IllegalArgumentException e) {
                                }
                            }
                        });
                    }

                }else {
                    if (advanced) {
                        System.out.println("Unchecked");
                        advanced = false;
                        anchorroot.getChildren().remove(control);
                        TranslateTransition tt = new TranslateTransition(Duration.millis(300), jfxtable);
                        tt.setByY(-70);
                        tt.play();
//                        contractStage();
                        tt.setOnFinished(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                getPrimary_stage().setHeight(STAGEHEIGHT2);
                            }
                        });
                    }
                }
            }
        });

//        advancedoptions.setOnTouch(new EventHandler<TouchEvent>() {
//            @Override
//            public void handle(TouchEvent event) {
//                System.out.println("Touched");
//            }
//        });
    }



    private class ButtonCell extends TreeTableCell {

        JFXButton cellButton = new JFXButton("Download");
        Subtitleinfo subtitleinfo;
        FadeTransition ft;
        int index;
        private String filepath;

        ButtonCell(){

            cellButton.setStyle("-fx-alignment: top-center ; -fx-background-color: #e0e0e0 ");
            cellButton.setButtonType(JFXButton.ButtonType.RAISED);
            cellButton.setRipplerFill(Color.SLATEGRAY);
            cellButton.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent t) {
                    // do something when button clicked
                    //...

                    subtitleinfo = (Subtitleinfo) ((TreeTableRow) getParent()).getItem();
                    index=subtitleinfoObservableList.indexOf(subtitleinfo);
                    filepath=Main.path+File.separator+subtitleinfo.getMovieName().replaceAll("[^a-zA-Z\\s]","");

                    if (cellButton.getText().equalsIgnoreCase("Download")) {

                        progressbar.setVisible(true);
                        deleteandclose.setVisible(true);
                        deleteandclose.setImage(new Image("/sample/ic_close_black_24dp_2x.png"));
                        cellButton.setText("Downloading");
                        status.replace(index,DOWNLOAD,DOWNLOADING);

                        ft = new FadeTransition(Duration.millis(500), cellButton);
                        ft.setFromValue(1.0);
                        ft.setToValue(0.0);
                        ft.setAutoReverse(true);
                        ft.setCycleCount(100);
                        ft.play();

                        Thread t1=new Thread(() -> {
                            try {

//                                if (!Login)
//                                    login();
                                Main.MakeIndividualDirectory(filepath);

                                subtitle.downloadSubtitle(new URL(subtitleinfo.getSubDownloadLink()), filepath+File.separator+subtitleinfo.getSubFileName());
                                subtitleinfo.setDownloaded(true);
//                                    updateIndex(subtitleinfoObservableList.indexOf(subtitleinfo));
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressbar.setVisible(false);
                                        setdefaulttableview();

                                        deleteandclose.setImage(new Image("/sample/ic_delete_black_24dp_2x.png"));
                                        ft.stop();
                                        status.replace(index,DOWNLOADING,OPENFOLDER);
                                        cellButton.setText("Open Folder");
//                                        updateItem(null,true);
//                                        updateIndex(index);
//                                        jfxtable.refresh();
                                        cellButton.setOpacity(1);
                                    }
                                });

                            } catch (Exception e) {
                                e.printStackTrace();
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {

                                        ft.stop();
                                        cellButton.setText("Download");
                                        cellButton.setOpacity(1);
                                        progressbar.setVisible(false);
                                        ((Label)jfxtable.getPlaceholder()).setText(" Check your network connection");

                                        if (!isrootnull())
                                            deleteandclose.setImage(new Image("/sample/ic_delete_black_24dp_2x.png"));
                                        else{
                                            deleteandclose.setVisible(false);
                                        }
//                                        deleteandclose.setImage(new Image("/sample/ic_delete_black_24dp_2x.png"));
                                        Alert alert=new Alert(Alert.AlertType.ERROR);
                                        alert.setTitle("Error getting data");
                                        alert.setContentText("Please check your network connection");
                                        alert.show();

                                    }
                                });

                                e.printStackTrace();
                            }
                        });
                        t1.start();
                        threads.add(t1);

                    }else{
                        new Thread(() -> {
                            Main.openDesktop(filepath);
                        }).start();
                    }
                }
            });

        }



        @Override
        protected void updateItem(Object item, boolean empty) {
            super.updateItem(item, empty);
                if (!empty) {
                    if (getParent() != null) {
                        subtitleinfo = (Subtitleinfo) ((TreeTableRow) getParent()).getItem();
                        int index = subtitleinfoObservableList.indexOf(subtitleinfo);
                        if (status.get(index) == DOWNLOAD) {
                            cellButton.setText("Download");
                            setGraphic(cellButton);
                            setStyle("-fx-alignment: top-center;");
                            System.out.println("Download called");
                            if (ft!=null)
                            ft.stop();
                            cellButton.setOpacity(1);
                        } else if (status.get(index) == DOWNLOADING) {
                            cellButton.setText("Downloading");
                            setGraphic(cellButton);
                            setStyle("-fx-alignment: top-center;");
                            System.out.println("Downloading called");
                            if (ft!=null)
                            ft.play();
                        } else {
                            cellButton.setText("Open Folder");
                            setGraphic(cellButton);
                            setStyle("-fx-alignment: top-center;");
                            System.out.println("Open folder called");
                            if (ft!=null)
                            ft.stop();
                            cellButton.setOpacity(1);
                        }
                    }else{
                        setGraphic(cellButton);
                        setStyle("-fx-alignment: top-center;");
                    }
                }
        }
    }

//    public void ExpandStage(){
//        Timer animTimer = new Timer();
//        animTimer.scheduleAtFixedRate(new TimerTask() {
//            int i=0;
//            @Override
//            public void run() {
//                if (i<110){
//
////                    getPrimary_stage().setWidth(getPrimary_stage().getWidth()+3);
//                    getPrimary_stage().setHeight(getPrimary_stage().getHeight()+1);
//                }
//                else {
//                    this.cancel();
//                }
//
//                i++;
//            }
//        }, 0, 6);
//    }

//    public void contractStage(){
//
//        Timer animTimer = new Timer();
//        animTimer.scheduleAtFixedRate(new TimerTask() {
//            int j=0;
//            @Override
//            public void run() {
//                if (j>-55){
//                    getPrimary_stage().setHeight(getPrimary_stage().getHeight()-2);
//                }
//                else {
//                    this.cancel();
//                }
//
//                j--;
//            }
//        }, 0, 12);
//    }

    public void getsublist(File file){
        System.out.println(file);
        try {
//            System.out.println("Hash generated is"+OpenSubtitleHasher.computeHash(file));
            ((Label)jfxtable.getPlaceholder()).setStyle("-fx-text-alignment: center ;-fx-alignment: top-center");
            ((Label)jfxtable.getPlaceholder()).setText("File Name :" + file.getName() +"\n\n Hash generated :"+OpenSubtitleHasher.computeHash(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        progressbar.setVisible(true);
        deleteandclose.setVisible(true);
        deleteandclose.setImage(new Image("/sample/ic_close_black_24dp_2x.png"));

        Thread t = new Thread(() -> {

            try {
//                    subtitleInfos.clear();
                if (!Login)
                    login();
                subtitleInfos = subtitle.Search(file.getAbsolutePath());
                if(converttoanotherarraylist()) {
                    root.getChildren().removeAll();
                    root = new RecursiveTreeItem<>(subtitleinfoObservableList, RecursiveTreeObject::getChildren);

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {

                            progressbar.setVisible(false);
                            deleteandclose.setImage(new Image("/sample/ic_delete_black_24dp_2x.png"));
                            setdefaulttableview();

                            jfxtable.setRoot(root);
                            //                            setOpenfolder();
                        }
                    });
                }else{
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            progressbar.setVisible(false);
                            searchbutton.setDisable(false);
                            //                Alert alert=new Alert(Alert.AlertType.ERROR);
                            if (!isrootnull())
                                deleteandclose.setImage(new Image("/sample/ic_delete_black_24dp_2x.png"));
                            else{
                                deleteandclose.setVisible(false);
                            }
                            ((Label)jfxtable.getPlaceholder()).setText("No subtitles found");
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        ((Label)jfxtable.getPlaceholder()).setText(" Check your network connection");

                        progressbar.setVisible(false);
                        if (!isrootnull())
                        deleteandclose.setImage(new Image("/sample/ic_delete_black_24dp_2x.png"));
                        else{
                            deleteandclose.setVisible(false);
                        }
                        Alert alert=new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error getting data");
                        alert.setContentText("Please check your network connection");
                        alert.show();
                    }
                });
            }

        });

        t.start();
        threads.add(t);
    }

    public void stopAllThreads(){
        for (Thread t:threads){
            t.interrupt();
            t.stop();
        }
        for (Thread t:threads){
            if (t.isDaemon()){
                System.out.println(t+" is alive ");
            }
        }
        threads.clear();
        progressbar.setVisible(false);
//        jfxtable.setRoot(root);
        if (!isrootnull()) {
            deleteandclose.setVisible(true);
            deleteandclose.setImage(new Image("/sample/ic_delete_black_24dp_2x.png"));
        }else{
            deleteandclose.setVisible(false);
            setdefaulttableview();
        }
    }

    public void setdefaulttableview(){
        searchbutton.setDisable(false);
        progressbar.setVisible(false);
        jfxtablelabel=new Label("Drag VIDEO FILE to search for subtitles");
        jfxtablelabel.setTextFill(Color.BLACK);
        jfxtablelabel.setStyle("-fx-font-size: 16");
        jfxtable.setPlaceholder(jfxtablelabel);
    }
    public boolean isrootnull(){
        return jfxtable.getRoot()==null;
    }

    public void clearTableData(){
        jfxtable.setRoot(null);
        progressbar.setVisible(false);
        deleteandclose.setVisible(false);
        setdefaulttableview();
    }

}
