package edu.armstrong.robotics;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Compiler extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public static ArrayList<String> compile(String text) {
        String[] tokens = text.split("( )|(,)|[(]|[)]|(;)");

        // REMOVE ALL WHITESPACE
        ArrayList<String> temp = new ArrayList<>();
        for (int i = 0; i < tokens.length; i++) {
            if (!tokens[i].isEmpty()) {
                temp.add(tokens[i]);
                System.out.println(tokens[i]);
            }
        }
        tokens = new String[temp.size()];
        for (int i = 0; i < temp.size(); i++) {
            tokens[i] = temp.get(i);
        }

        ArrayList<String> result = new ArrayList<>();

        for (int i = 0; i < tokens.length; i++) {
            try {

                if (tokens[i].toLowerCase().contains("point")) {
                    result.add(new String("gotoPoint("
                            + Integer.parseInt(tokens[i + 1]) + ", "
                            + Integer.parseInt(tokens[i + 2]) + ");"));
                } else if (tokens[i].toLowerCase().contains("edge")) {
                    result.add(new String("gotoEdge("
                            + Integer.parseInt(tokens[i + 1]) + ");"));
                } else if (tokens[i].toLowerCase().contains("corner")) {
                    result.add(new String("gotoCorner("
                            + Integer.parseInt(tokens[i + 1]) + ");"));
                } else if (tokens[i].toLowerCase().contains("move")) {
                    if (tokens[i + 1].toLowerCase().startsWith("n")) {
                        result.add(new String("move(NORTH,"
                                + Integer.parseInt(tokens[i + 2]) + ");"));
                    } else if (tokens[i + 1].toLowerCase().startsWith("s")) {
                        result.add(new String("move(SOUTH,"
                                + Integer.parseInt(tokens[i + 2]) + ");"));
                    } else if (tokens[i + 1].toLowerCase().startsWith("e")) {
                        result.add(new String("move(EAST,"
                                + Integer.parseInt(tokens[i + 2]) + ");"));
                    } else if (tokens[i + 1].toLowerCase().startsWith("w")) {
                        result.add(new String("move(WEST,"
                                + Integer.parseInt(tokens[i + 2]) + ");"));
                    } else {
                        result.add(new String("move(NORTH,"
                                + Integer.parseInt(tokens[i + 2]) + ");"));
                    }
                } else if (tokens[i].toLowerCase().contains("face")) {
                    if (tokens[i + 1].toLowerCase().startsWith("n")) {
                        result.add(new String("changeDir(NORTH);"));
                    } else if (tokens[i + 1].toLowerCase().startsWith("s")) {
                        result.add(new String("changeDir(SOUTH);"));
                    } else if (tokens[i + 1].toLowerCase().startsWith("e")) {
                        result.add(new String("changeDir(EAST);"));
                    } else if (tokens[i + 1].toLowerCase().startsWith("w")) {
                        result.add(new String("changeDir(WEST);"));
                    } else {
                        result.add(new String("changeDir(NORTH);"));
                    }
                } else {
                    // skip
                }
            } catch (NumberFormatException e) {
            }
        }
        return result;
    }

    public void start(Stage primary) {
        primary.setTitle("Robotics Compiler");

        TextArea script = new TextArea();

        Button compile = new Button();
        compile.setText("Compile");
        compile.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                System.out.println(compile(script.getText()));
            }
        });

        BorderPane root = new BorderPane();
        root.setCenter(script);
        root.setBottom(compile);
        primary.setScene(new Scene(root, 720, 480));
        primary.show();

    }
}

