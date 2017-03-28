package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class Main extends Application {

    public static String path=null;
    public static String osname=System.getProperty ("os.name").toLowerCase();
    public static String username=System.getProperty("user.name");

//    int counter = 0 ;

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader=new FXMLLoader(getClass().getResource("original_layout.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("DownSub");
        Scene scene=new Scene(root, 1038, 525);

//        primaryStage.initModality();
        ((Controller)loader.getController()).setPrimary_stage(primaryStage);



        scene.getStylesheets().add(Main.class.getResource("jfoenix-main.css").toExternalForm());
        primaryStage.getIcons().add(new Image("/sample/subtitle.png"));
        primaryStage.setScene(scene);

        primaryStage.show();
    }


    public static void main(String[] args) throws MalformedURLException {

        MakeDownloadDir();
        launch(args);



    }

    public static void openDesktop(String filepath){
        Desktop desktop= Desktop.getDesktop();
        try {
            desktop.open(new File(filepath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void MakeDownloadDir(){
        if (osname.contains("linux")) {
            path="/home/"+username+"/subDownloads";
            System.out.println("This is linux");
            File file = new File(path);
            if (!file.exists())
                file.mkdir();
        }else{
            Controller.STAGEHEIGHT1=630;
            Controller.STAGEHEIGHT2=560;
            System.out.println("This is windows");
            path="C:\\Users\\"+username+"\\Documents\\subDownloads";
            File file = new File(path);
            if (!file.exists())
                file.mkdir();
        }
    }

    public static void MakeIndividualDirectory(String filepath){
        System.out.println("Directory "+filepath+" created");
        File file=new File(filepath);
        if (!file.exists())
        file.mkdir();
    }

}
