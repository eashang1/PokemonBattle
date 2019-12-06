import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.nio.file.Paths;

/**
 * PokeBattle class
 *
 * @author Eashan Gandotra
 * @version 1.0
 */
public class PokeBattle extends Application {

    /*Additional features:
     * -Implemented background
     * -Implemented AudioClip (please turn on audio)
     * -Damage is calculated with type advantage and Pokemon attack stat in addition to the attacker
     * power (atk/20 + (typemultiplier*power))
     * -"RUN" button gives "Got away safely!" message
     * -"BAG" method provides a potion option (if declined it returns to the base menu and if accepted
     * it heals 10 HP
     * (but counts for a turn, like in an actual Pokemon game)
     * -"POKEMON" button switches between Snorlax and Psyduck, each of which has its own stats/moveset
     * -All text is in Courier New, which is similar to that of Pokemon games
     * -Control panel for text added to bottom to mimic real Pokemon game rather than just putting it
     * into the scene
     * */

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Makes full pane (with root and control sections), sets title, and creates a stage & scene
        primaryStage.setTitle("Pokemon Battle!");
        GridPane root = new GridPane();
        root.setMaxWidth(500);
        root.setMaxHeight(300);
        GridPane full = new GridPane();
        GridPane control = new GridPane();
        full.add(root, 0, 0);
        full.add(control, 0, 1);
        full.setMaxWidth(500);
        full.setMaxHeight(400);
        primaryStage.setScene(new Scene(full, 500, 400));
        primaryStage.show();

        //Plays trainer battle theme song
        AudioClip song = new AudioClip(Paths.get("trainerbattle.mp3").toUri().toString());
        song.play();

        //Sets background
        BackgroundSize backgroundsize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO,
            false, false, true, false);
        Image background = new Image("battlebackground.png");
        root.setBackground(new Background(new BackgroundImage(background,
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            backgroundsize)));


        //Sets root pane constraints
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(0, 10, 0, 10));

        //Creates children panes
        GridPane enemyPokemon = new GridPane();
        GridPane myPokemon = new GridPane();
        GridPane mystats = new GridPane();
        GridPane enemystats = new GridPane();

        //Lists all panes and sets grid line visibility
        //enemyPokemon.setGridLinesVisible(true);
        //myPokemon.setGridLinesVisible(true);
        //root.setGridLinesVisible(true);
        //control.setGridLinesVisible(true);
        //full.setGridLinesVisible(true);
        //mystats.setGridLinesVisible(true);
        //enemystats.setGridLinesVisible(true);
        //TilePane for intial menu below
        //TilePane for fight menu below

        //Initialize Pokemon and moves
        Move scratch = new Move("Scratch", 10, "NORMAL");
        Move watergun = new Move("Watergun", 12, "WATER");
        Move confusion = new Move("Confusion", 8, "PSYCHIC");
        Move psybeam = new Move("Psybeam", 10, "PSYCHIC");
        Move splash = new Move("Spalsh", 6, "WATER");
        Move tackle = new Move("Tackle", 8, "NORMAL");
        Move flail = new Move("Flail", 8, "NORMAL");
        Move struggle = new Move("Struggle", 4, "NORMAL");
        Move yawn = new Move("Yawn", 4, "NORMAL");
        Move lick = new Move("Lick", 8, "NORMAL");
        Move bite = new Move("Bite", 6, "DARK");
        Pokemon psyduck =
            new Pokemon("Psyduck", 42, 18, 42, "WATER", scratch, watergun, confusion, psybeam);
        Pokemon magikarp =
            new Pokemon("Magikarp", 21, 60, 21, "WATER", splash, tackle, flail, struggle);
        Pokemon snorlax = new Pokemon("Snorlax", 18, 42, 14, "NORMAL", tackle, yawn, lick, bite);
        Pokemon mycriter = new Pokemon();
        mycriter.setStats(psyduck);
        Pokemon enemycriter = new Pokemon();
        enemycriter.setStats(magikarp);

        //Adds images to children panes
        Image poke1 = new Image("file:" + enemycriter.getName() + ".png");
        Image poke2 = new Image("file:" + mycriter.getName() + ".png");
        Image poke3 = new Image("file:Snorlax.png", 120, 120, false, false);
        ImageView show1 = new ImageView(poke1);
        ImageView show2 = new ImageView(poke2);
        ImageView show3 = new ImageView(poke3);
        show1.setFitWidth(105);
        show1.setFitHeight(107.5);
        show2.setFitWidth(100);
        show2.setFitHeight(100);
        enemyPokemon.add(show1, 1, 1);
        myPokemon.add(show2, 1, 1);
        root.add(enemyPokemon, 2, 1);
        root.add(myPokemon, 1, 2);

        //Creates stats windows for both pokemon
        ProgressBar myHP = new ProgressBar(mycriter.getCurrentHP());
        myHP.setStyle("-fx-accent: lawngreen");
        ProgressBar enemyHP = new ProgressBar(enemycriter.getCurrentHP());
        enemyHP.setStyle("-fx-accent: lawngreen");
        Text stats1 = new Text(mycriter.getName() + " Lv" + mycriter.getLevel());
        stats1.setFont(Font.font("Courier New"));
        stats1.setStyle("-fx-font-weight: bold");
        Text hp1 = new Text(mycriter.getCurrentHP() + "/" + mycriter.getMaxHP());
        hp1.setFont(Font.font("Courier New"));
        hp1.setStyle("-fx-font-weight: bold");
        Text hp2 = new Text(enemycriter.getCurrentHP() + "/" + enemycriter.getMaxHP());
        hp2.setFont(Font.font("Courier New"));
        hp2.setStyle("-fx-font-weight: bold");
        Text stats2 = new Text(enemycriter.getName() + " Lv" + enemycriter.getLevel());
        stats2.setFont(Font.font("Courier New"));
        stats2.setStyle("-fx-font-weight: bold");
        mystats.add(stats1, 1, 1);
        mystats.add(myHP, 1, 2);
        mystats.add(hp1, 1, 3);
        enemystats.add(stats2, 1, 1);
        enemystats.add(enemyHP, 1, 2);
        enemystats.add(hp2, 1, 3);
        root.add(mystats, 2, 2);
        root.add(enemystats, 1, 1);

        //Column constraints for children panes
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(55);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(40);
        ColumnConstraints maxwidth = new ColumnConstraints();
        maxwidth.setPercentWidth(100);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(5);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPercentWidth(60);
        enemyPokemon.getColumnConstraints().addAll(col1);
        myPokemon.getColumnConstraints().addAll(col2);
        full.getColumnConstraints().addAll(maxwidth);
        control.getColumnConstraints().addAll(col1, col3, col2, col3);
        mystats.getColumnConstraints().add(col4);
        enemystats.getColumnConstraints().add(col2);

        //Row constraints for children panes
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(35);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(80);
        RowConstraints row3 = new RowConstraints();
        row3.setPercentHeight(43.5);
        enemyPokemon.getRowConstraints().addAll(row1);
        full.getRowConstraints().addAll(row2);
        mystats.getRowConstraints().add(row1);
        enemystats.getRowConstraints().add(row3);

        //Creates the button panel
        Button fight = new Button("FIGHT");
        fight.setMinWidth(80);
        Button bag = new Button("BAG");
        bag.setMinWidth(80);
        Button pokemon = new Button("POKEMON");
        pokemon.setMinWidth(80);
        Button run = new Button("RUN");
        run.setMinWidth(80);
        TilePane tile = new TilePane();
        tile.setPadding(new Insets(1, 1, 1, 1));
        tile.setPrefColumns(2);
        tile.setPrefRows(2);
        tile.getChildren().addAll(fight, bag, pokemon, run);
        control.add(tile, 2, 0);

        //Simulates control during a battle
        Text command = new Text("  What should " + mycriter.getName() + " do?");
        Text space = new Text(" ");
        Text endspace = new Text(" ");
        control.add(space, 1, 0);
        control.add(endspace, 3, 0);
        command.setFont(Font.font("Courier New", 20));
        control.add(command, 0, 0);
        mystats.add(space, 0, 0);

        //Creates fight button panel
        Button move1 = new Button(mycriter.getMoves()[0].getName());
        move1.setMinWidth(80);
        Button move2 = new Button(mycriter.getMoves()[1].getName());
        move2.setMinWidth(80);
        Button move3 = new Button(mycriter.getMoves()[2].getName());
        move3.setMinWidth(80);
        Button move4 = new Button(mycriter.getMoves()[3].getName());
        move4.setMinWidth(80);
        TilePane attacks = new TilePane();
        attacks.setPadding(new Insets(1, 1, 1, 1));
        attacks.setPrefColumns(2);
        attacks.setPrefRows(2);
        attacks.getChildren().addAll(move1, move2, move3, move4);

        //Creates potion menu button panel
        Button yes = new Button("Yes");
        yes.setMinWidth(60);
        Button no = new Button("No");
        no.setMinWidth(60);
        TilePane potion = new TilePane();
        potion.setPrefColumns(2);
        potion.setPrefRows(1);
        potion.setPadding(new Insets(1, 1, 1, 1));
        potion.getChildren().addAll(yes, no);

        //Potion options (handles clicked potion choices)
        yes.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                double newHP = Math.min(mycriter.getCurrentHP() + 10, mycriter.getMaxHP());
                mycriter.setCurrentHP(newHP);

                int enemymove = (int) (Math.random() * 4);
                Text second = new Text("  " + enemycriter.getName()
                    + " used " + mycriter.getMoves()[enemymove].getName() + "!");
                second.setFont(Font.font("Courier New", 20));
                control.add(second, 0, 0);
                mycriter.setCurrentHP(mycriter.getCurrentHP() - (int) (enemycriter.getAtk() / 20.0)
                    - (enemycriter.getMoves()[enemymove].getPower()
                    * mycriter.compareType(enemycriter.getMoves()[enemymove])));
                double enemydisplay = Math.max(0.0, mycriter.getCurrentHP());
                myHP.setProgress(enemydisplay / mycriter.getMaxHP());
                hp1.setText(enemydisplay + "/" + mycriter.getMaxHP());
                control.getChildren().clear();

                if (mycriter.isFainted()) {
                    Text fainted = new Text(mycriter.getName() + " has fainted!");
                    fainted.setFont(Font.font("Courier New", 20));
                    control.add(fainted, 0, 0);
                    Timeline timeline = new Timeline(new KeyFrame(
                        Duration.millis(750),
                        ae -> System.exit(0)));
                    timeline.play();
                } else {
                    Text next = new Text("  What will " + mycriter.getName() + " do?");
                    next.setFont(Font.font("Courier New", 20));
                    control.add(next, 0, 0);
                    control.add(tile, 2, 0);
                }
            }
        });

        no.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                control.getChildren().clear();
                Text next = new Text("  What will " + mycriter.getName() + " do?");
                next.setFont(Font.font("Courier New", 20));
                control.add(next, 0, 0);
                control.add(tile, 2, 0);
            }
        });

        //Deals damage (handles clicked moves)
        move1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                control.getChildren().clear();
                Text first = new Text("  " + mycriter.getName() + " used "
                    + mycriter.getMoves()[0].getName() + "!");
                first.setFont(Font.font("Courier New", 20));
                control.add(first, 0, 0);
                enemycriter.setCurrentHP(enemycriter.getCurrentHP()
                    - (int) (mycriter.getAtk() / 20.0) - (mycriter.getMoves()[0].getPower()
                    * enemycriter.compareType(mycriter.getMoves()[0])));
                double display = Math.max(0.0, enemycriter.getCurrentHP());
                enemyHP.setProgress(display / enemycriter.getMaxHP());
                hp2.setText(display + "/" + enemycriter.getMaxHP());
                control.getChildren().clear();

                if (!enemycriter.isFainted()) {
                    int enemymove = (int) (Math.random() * 4);
                    Text second = new Text("  " + enemycriter.getName() + " used "
                        + mycriter.getMoves()[enemymove].getName() + "!");
                    second.setFont(Font.font("Courier New", 20));
                    control.add(second, 0, 0);
                    mycriter.setCurrentHP(mycriter.getCurrentHP() - (int) (enemycriter.getAtk() / 20.0)
                        - (enemycriter.getMoves()[enemymove].getPower()
                        * mycriter.compareType(enemycriter.getMoves()[enemymove])));
                    double enemydisplay = Math.max(0.0, mycriter.getCurrentHP());
                    myHP.setProgress(enemydisplay / mycriter.getMaxHP());
                    hp1.setText(enemydisplay + "/" + mycriter.getMaxHP());
                    control.getChildren().clear();
                }

                if (enemycriter.isFainted()) {
                    Text fainted = new Text("  Enemy " + enemycriter.getName() + " has fainted!");
                    fainted.setFont(Font.font("Courier New", 20));
                    control.add(fainted, 0, 0);
                    Timeline timeline = new Timeline(new KeyFrame(
                        Duration.millis(750),
                        ae -> System.exit(0)));
                    timeline.play();
                } else if (mycriter.isFainted()) {
                    Text fainted = new Text(mycriter.getName() + " has fainted!");
                    fainted.setFont(Font.font("Courier New", 20));
                    control.add(fainted, 0, 0);
                    Timeline timeline = new Timeline(new KeyFrame(
                        Duration.millis(750),
                        ae -> System.exit(0)));
                    timeline.play();
                } else {
                    Text next = new Text("  What will " + mycriter.getName() + " do?");
                    next.setFont(Font.font("Courier New", 20));
                    control.add(next, 0, 0);
                    control.add(tile, 2, 0);
                }
            }
        });

        move2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                control.getChildren().clear();
                Text first = new Text("  " + mycriter.getName() + " used "
                    + mycriter.getMoves()[1].getName() + "!");
                first.setFont(Font.font("Courier New", 20));
                enemycriter.setCurrentHP(enemycriter.getCurrentHP() - (int) (mycriter.getAtk() / 20.0)
                    - (mycriter.getMoves()[1].getPower() * enemycriter.compareType(mycriter.getMoves()[1])));
                double display = Math.max(0.0, enemycriter.getCurrentHP());
                enemyHP.setProgress(display / enemycriter.getMaxHP());
                hp2.setText(display + "/" + enemycriter.getMaxHP());
                control.getChildren().clear();

                if (!enemycriter.isFainted()) {
                    int enemymove = (int) (Math.random() * 4);
                    Text second = new Text("  " + enemycriter.getName() + " used "
                        + mycriter.getMoves()[enemymove].getName() + "!");
                    second.setFont(Font.font("Courier New", 20));
                    control.add(second, 0, 0);
                    mycriter.setCurrentHP(mycriter.getCurrentHP() - (int) (enemycriter.getAtk() / 20.0)
                        - (enemycriter.getMoves()[enemymove].getPower()
                        * mycriter.compareType(enemycriter.getMoves()[enemymove])));
                    double enemydisplay = Math.max(0.0, mycriter.getCurrentHP());
                    myHP.setProgress(enemydisplay / mycriter.getMaxHP());
                    hp1.setText(enemydisplay + "/" + mycriter.getMaxHP());
                    control.getChildren().clear();
                }

                if (enemycriter.isFainted()) {
                    Text fainted = new Text("  Enemy " + enemycriter.getName() + " has fainted!");
                    fainted.setFont(Font.font("Courier New", 20));
                    control.add(fainted, 0, 0);
                    Timeline timeline = new Timeline(new KeyFrame(
                        Duration.millis(750),
                        ae -> System.exit(0)));
                    timeline.play();
                } else if (mycriter.isFainted()) {
                    Text fainted = new Text(mycriter.getName() + " has fainted!");
                    fainted.setFont(Font.font("Courier New", 20));
                    control.add(fainted, 0, 0);
                    Timeline timeline = new Timeline(new KeyFrame(
                        Duration.millis(750),
                        ae -> System.exit(0)));
                    timeline.play();
                } else {
                    Text next = new Text("  What will " + mycriter.getName() + " do?");
                    next.setFont(Font.font("Courier New", 20));
                    control.add(next, 0, 0);
                    control.add(tile, 2, 0);
                }
            }
        });

        move3.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                control.getChildren().clear();
                Text first = new Text("  " + mycriter.getName() + " used "
                    + mycriter.getMoves()[2].getName() + "!");
                first.setFont(Font.font("Courier New", 20));
                enemycriter.setCurrentHP(enemycriter.getCurrentHP() - (int) (mycriter.getAtk() / 20.0)
                    - (mycriter.getMoves()[2].getPower() * enemycriter.compareType(mycriter.getMoves()[2])));
                double display = Math.max(0.0, enemycriter.getCurrentHP());
                enemyHP.setProgress(display / enemycriter.getMaxHP());
                hp2.setText(display + "/" + enemycriter.getMaxHP());
                control.getChildren().clear();

                if (!enemycriter.isFainted()) {
                    int enemymove = (int) (Math.random() * 4);
                    Text second = new Text("  " + enemycriter.getName() + " used "
                        + mycriter.getMoves()[enemymove].getName() + "!");
                    second.setFont(Font.font("Courier New", 20));
                    control.add(second, 0, 0);
                    mycriter.setCurrentHP(mycriter.getCurrentHP() - (int) (enemycriter.getAtk() / 20.0)
                        - (enemycriter.getMoves()[enemymove].getPower()
                        * mycriter.compareType(enemycriter.getMoves()[enemymove])));
                    double enemydisplay = Math.max(0.0, mycriter.getCurrentHP());
                    myHP.setProgress(enemydisplay / mycriter.getMaxHP());
                    hp1.setText(enemydisplay + "/" + mycriter.getMaxHP());
                    control.getChildren().clear();
                }

                if (enemycriter.isFainted()) {
                    Text fainted = new Text("  Enemy " + enemycriter.getName() + " has fainted!");
                    fainted.setFont(Font.font("Courier New", 20));
                    control.add(fainted, 0, 0);
                    Timeline timeline = new Timeline(new KeyFrame(
                        Duration.millis(750),
                        ae -> System.exit(0)));
                    timeline.play();
                } else if (mycriter.isFainted()) {
                    Text fainted = new Text(mycriter.getName() + " has fainted!");
                    fainted.setFont(Font.font("Courier New", 20));
                    control.add(fainted, 0, 0);
                    Timeline timeline = new Timeline(new KeyFrame(
                        Duration.millis(750),
                        ae -> System.exit(0)));
                    timeline.play();
                } else {
                    Text next = new Text("  What will " + mycriter.getName() + " do?");
                    next.setFont(Font.font("Courier New", 20));
                    control.add(next, 0, 0);
                    control.add(tile, 2, 0);
                }
            }
        });

        move4.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                control.getChildren().clear();
                Text first = new Text("  " + mycriter.getName() + " used "
                    + mycriter.getMoves()[3].getName() + "!");
                first.setFont(Font.font("Courier New", 20));
                enemycriter.setCurrentHP(enemycriter.getCurrentHP() - (int) (mycriter.getAtk() / 20.0)
                    - (mycriter.getMoves()[3].getPower() * enemycriter.compareType(mycriter.getMoves()[3])));
                double display = Math.max(0.0, enemycriter.getCurrentHP());
                enemyHP.setProgress(display / enemycriter.getMaxHP());
                hp2.setText(display + "/" + enemycriter.getMaxHP());
                control.getChildren().clear();

                if (!enemycriter.isFainted()) {
                    int enemymove = (int) (Math.random() * 4);
                    Text second = new Text("  " + enemycriter.getName() + " used "
                        + mycriter.getMoves()[enemymove].getName() + "!");
                    second.setFont(Font.font("Courier New", 20));
                    control.add(second, 0, 0);
                    mycriter.setCurrentHP(mycriter.getCurrentHP()
                        - (int) (enemycriter.getAtk() / 20.0) - (enemycriter.getMoves()[enemymove].getPower()
                        * mycriter.compareType(enemycriter.getMoves()[enemymove])));
                    double enemydisplay = Math.max(0.0, mycriter.getCurrentHP());
                    myHP.setProgress(display / mycriter.getMaxHP());
                    hp1.setText(display + "/" + mycriter.getMaxHP());
                    control.getChildren().clear();
                }

                if (enemycriter.isFainted()) {
                    Text fainted = new Text("  Enemy " + enemycriter.getName() + " has fainted!");
                    fainted.setFont(Font.font("Courier New", 20));
                    control.add(fainted, 0, 0);
                    Timeline timeline = new Timeline(new KeyFrame(
                        Duration.millis(750),
                        ae -> System.exit(0)));
                    timeline.play();
                } else if (mycriter.isFainted()) {
                    Text fainted = new Text(mycriter.getName() + " has fainted!");
                    fainted.setFont(Font.font("Courier New", 20));
                    control.add(fainted, 0, 0);
                    Timeline timeline = new Timeline(new KeyFrame(
                        Duration.millis(750),
                        ae -> System.exit(0)));
                    timeline.play();
                } else {
                    Text next = new Text("  What will " + mycriter.getName() + " do?");
                    next.setFont(Font.font("Courier New", 20));
                    control.add(next, 0, 0);
                    control.add(tile, 2, 0);
                }
            }
        });

        //Handles clicked buttons
        fight.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                control.getChildren().clear();
                Text pick = new Text("  Pick a move.");
                pick.setFont(Font.font("Courier New", 20));
                control.add(pick, 0, 0);
                control.add(attacks, 2, 0);
            }
        });

        run.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                control.getChildren().clear();
                Text pick = new Text("  Got away safely!");
                pick.setFont(Font.font("Courier New", 20));
                control.add(pick, 0, 0);
                Timeline timeline = new Timeline(new KeyFrame(
                    Duration.millis(750),
                    ae -> System.exit(0)));
                timeline.play();
            }
        });

        bag.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                control.getChildren().clear();
                Text pickpotion = new Text("  Would you like to \n  use a potion?");
                pickpotion.setFont(Font.font("Courier New", 20));
                control.add(pickpotion, 0, 0);
                control.add(potion, 2, 0);
            }
        });

        pokemon.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mycriter.getName().equals("Psyduck")) {
                    psyduck.setStats(mycriter);
                    mycriter.setStats(snorlax);
                    myPokemon.getChildren().remove(show2);
                    myPokemon.add(show3, 1, 1);
                    myHP.setProgress(mycriter.getCurrentHP() / mycriter.getMaxHP());
                    hp1.setText(mycriter.getCurrentHP() + "/" + mycriter.getMaxHP());
                    stats1.setText(mycriter.getName() + " Lv" + mycriter.getLevel());
                    mystats.getChildren().clear();
                    mystats.add(myHP, 1, 2);
                    mystats.add(hp1, 1, 3);
                    mystats.add(stats1, 1, 1);

                    move1.setText(mycriter.getMoves()[0].getName());
                    move2.setText(mycriter.getMoves()[1].getName());
                    move3.setText(mycriter.getMoves()[2].getName());
                    move4.setText(mycriter.getMoves()[3].getName());

                    control.getChildren().clear();
                    Text next = new Text("  What will " + mycriter.getName() + " do?");
                    next.setFont(Font.font("Courier New", 20));
                    control.add(next, 0, 0);
                    control.add(tile, 2, 0);
                } else {
                    snorlax.setStats(mycriter);
                    mycriter.setStats(psyduck);
                    myPokemon.getChildren().remove(show3);
                    myPokemon.add(show2, 1, 1);
                    myHP.setProgress(mycriter.getCurrentHP() / mycriter.getMaxHP());
                    hp1.setText(mycriter.getCurrentHP() + "/" + mycriter.getMaxHP());
                    stats1.setText(mycriter.getName() + " Lv" + mycriter.getLevel());
                    mystats.getChildren().clear();
                    mystats.add(myHP, 1, 2);
                    mystats.add(hp1, 1, 3);
                    mystats.add(stats1, 1, 1);

                    move1.setText(mycriter.getMoves()[0].getName());
                    move2.setText(mycriter.getMoves()[1].getName());
                    move3.setText(mycriter.getMoves()[2].getName());
                    move4.setText(mycriter.getMoves()[3].getName());

                    control.getChildren().clear();
                    Text next = new Text("  What will " + mycriter.getName() + " do?");
                    next.setFont(Font.font("Courier New", 20));
                    control.add(next, 0, 0);
                    control.add(tile, 2, 0);
                }
            }
        });
    }

    /**
     * Main method
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}