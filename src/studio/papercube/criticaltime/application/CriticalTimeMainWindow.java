package studio.papercube.criticaltime.application;

import javafx.animation.*;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import studio.papercube.criticaltime.application.localizations.ChronoUnitLocalization;
import studio.papercube.criticaltime.application.service.DateUpdateService;
import studio.papercube.criticaltime.application.service.FunctionalTimerTask;
import studio.papercube.criticaltime.application.service.FxUpdateService;
import studio.papercube.criticaltime.res.Resources;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Timer;


/**
 * Created by imzhy on 2016/2/28.
 *
 * @author PaperCube
 */
public class CriticalTimeMainWindow {

    private final Text label = new Text();
    private final Text labelTimeUnit = new Text();
    private final Text number = new Text();
    //    private final Button exitButton = new Button("退出");
    private final LocalDate targetDate;
    private final String targetName;
    private final Stage thisStage;
    private ChronoUnit targetUnit = ChronoUnit.DAYS;
    /**
     * 数字所使用的字体
     */
    private Font fontF2U;
    private Font fontTeaPro;
    private Text timeInMenuBar;
    private Text dateInMenuBar;
    private HBox timedateBoxInMenuBar;
    private StackPane rootStackPane;
    private Background colorBackGround;

    /**
     * 窗口管理器的标准构造函数
     * 你不需要传入任何参数，因为它会自动从CriticalTime中读取
     * @see CriticalTime
     */
    public CriticalTimeMainWindow() {

        thisStage = CriticalTime.primaryStage;
        targetDate = CriticalTime.targetDate;
        this.targetName = CriticalTime.targetName;

        thisStage.setTitle(
                String.format("目标日: %s (%s) - Critical Time",targetDate.toString(),targetName)
        );

        autoBackground();
        initializeComponents(thisStage);
        initTimers();
    }

    /**
     * 初始化窗口
     *
     * @param stage 所显示的窗口。
     */
    protected void initializeComponents(Stage stage) {

        Circle circle = new Circle(shortestSideOfTheScreen()/2+10, Color.rgb(0, 0, 0, 0.2));
        //LAYOUTS AND CONTAINERS
        fontF2U = Font.loadFont(Resources.class.getResourceAsStream("F2U_Scratch.ttf"), 220);
        fontTeaPro = Font.loadFont(Resources.class.getResourceAsStream("AfternoonTeaPro.ttf"), 50);

        colorBackGround = autoBackground();

        stage.setResizable(true);

        BorderPane rootBorder = new BorderPane();


        GridPane upperGrid = new GridPane();
//        Group rootGroup = new Group(rootBorder);

        rootStackPane = new StackPane();
        rootStackPane.getChildren().addAll(circle, rootBorder);
        rootStackPane.setBackground(colorBackGround); //Override background

        Scene sc = new Scene(rootStackPane);//FIXME SCENE
        sc.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());

        /*
         * MENU BAR 里的时间和日期
         */
        timeInMenuBar = new Text("00:00");
        timeInMenuBar.setFill(Color.WHITE);
        timeInMenuBar.setFont(Font.font(25));

        dateInMenuBar = new Text("--月--日 星期---");
        dateInMenuBar.setFill(Color.WHITE);
        dateInMenuBar.setFont(Font.font(17));

        timedateBoxInMenuBar = new HBox();
        timedateBoxInMenuBar.setAlignment(Pos.BOTTOM_LEFT);
        timedateBoxInMenuBar.getChildren().addAll(timeInMenuBar, dateInMenuBar);
        timedateBoxInMenuBar.setMargin(timeInMenuBar, new Insets(0, 5, 0, 0));

        MenuBar menubar = new MenuBar();
        Menu menu = new Menu("", timedateBoxInMenuBar);


        menubar.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0.2), null, null)));
        menubar.getMenus().addAll(menu);

        MenuItem exit = new MenuItem("关闭应用程序");

        MenuItem descriptionOfProgram = new MenuItem("CriticalTime-中考倒计时 v1.3.2");
        descriptionOfProgram.setDisable(true);

        MenuItem authorOfProgram = new MenuItem("by PaperCube");
        authorOfProgram.setDisable(true);

        MenuItem tgFullscreen = new MenuItem("切换全屏");
        tgFullscreen.setOnAction(e->toggleFullScreen());

        menu.getItems().addAll(descriptionOfProgram, authorOfProgram, tgFullscreen,exit);


        exit.setOnAction(e -> System.exit(0));

       /* menubar.setBackground(new Background(new BackgroundFill(Color.rgb(126,87,179),null,null)));
        DropShadow dropShadowForMenuBar = new DropShadow();
        dropShadowForMenuBar.setOffsetX(3);
        dropShadowForMenuBar.setOffsetY(3);
        dropShadowForMenuBar.setColor(Color.BLACK);
        dropShadowForMenuBar.setRadius(15);

        menubar.setEffect(dropShadowForMenuBar);*/

        rootBorder.setTop(menubar);
        upperGrid.setPadding(new Insets(60, 100, 60, 100));
//        Circle
        //TEXTS
        label.setText(String.format("距离%s还有",  targetName));
        label.setFont(fontTeaPro);
        label.setFill(Color.WHITE);

//        exitButton.
        labelTimeUnit.setText(nameOfTimeUnit());
        labelTimeUnit.setFont(fontTeaPro);
        labelTimeUnit.setFill(Color.WHITE);

        number.setFill(Color.WHITE);
        number.setText("--?");
        number.setFont(fontF2U);


        GaussianBlur blur = new GaussianBlur();
        blur.setRadius(1);
//        label.setEffect(blur);

        //ANIMATIONS
        FadeTransition fadeTransOnStart = new FadeTransition(Duration.millis(750), label);
        fadeTransOnStart.setFromValue(0);
        fadeTransOnStart.setToValue(1);

        FadeTransition slightFadeTranstition = new FadeTransition(Duration.millis(1000), circle);
        slightFadeTranstition.setFromValue(1.0);
        slightFadeTranstition.setToValue(0.8);
        slightFadeTranstition.setCycleCount(Animation.INDEFINITE);
        slightFadeTranstition.setAutoReverse(true);

        double INITIAL_SIZE = 1;
        double CHANGING_SIZE = 0.25;
        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(500));
        scaleOut.setFromX(INITIAL_SIZE);
        scaleOut.setFromY(INITIAL_SIZE);
        scaleOut.setByX(CHANGING_SIZE);
        scaleOut.setByY(CHANGING_SIZE);

        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(2000));
        scaleIn.setFromX(INITIAL_SIZE + CHANGING_SIZE);
        scaleIn.setFromY(INITIAL_SIZE + CHANGING_SIZE);
        scaleIn.setByX(-CHANGING_SIZE);
        scaleIn.setByY(-CHANGING_SIZE);

        SequentialTransition scaleInAndOut = new SequentialTransition();
        scaleInAndOut.getChildren().addAll(scaleOut, scaleIn);
        scaleInAndOut.setCycleCount(Animation.INDEFINITE);

        ParallelTransition transparencyAndScaling = new ParallelTransition(circle);
        transparencyAndScaling.getChildren().addAll(slightFadeTranstition, scaleInAndOut);
        transparencyAndScaling.setCycleCount(Animation.INDEFINITE);

        SequentialTransition animationFromStartToEnd = new SequentialTransition();
        animationFromStartToEnd.getChildren().addAll(fadeTransOnStart, transparencyAndScaling);
        animationFromStartToEnd.play();

//        transparencyAndScaling.play();

        //EFFECTS
//        Rectangle rec = new Rectangle(sc.getWidth(),sc.getHeight(),);

        //ALIGNMENTS
        rootBorder.setCenter(upperGrid); //TODO
        upperGrid.setAlignment(Pos.CENTER);
//        upperGrid.setGridLinesVisible(true);

        HBox numberAndDay = new HBox();
        numberAndDay.setAlignment(Pos.BOTTOM_CENTER);
        numberAndDay.getChildren().addAll(number, labelTimeUnit);

        upperGrid.addColumn(0, label, numberAndDay);



        //WINDOW FEATURES
        stage.setMaximized(true);
        stage.setIconified(true);//Enable this feature comes a fancy window-show-in animation
        stage.setOnCloseRequest(Event::consume);//Prevent this program from exiting
        stage.setScene(sc);
//        stage.initStyle(StageStyle.TRANSPARENT);

    }

    /**
     * 初始化用于更新时间的Timer.
     * 如果不调用这个方法，那么程序将不能自动更新时间。
     */
    protected void initTimers() {
//        new DateUpdateService(targetDate, number::setText, labelTimeUnit::setText); //TODO 处理他的后事
        Timer t = new Timer();
        t.schedule(new FunctionalTimerTask(this::timerImpulse), 0, 1000);
    }

    /**
     * 每秒脉冲执行的东西
     *
     * @see #initTimers()
     * @since app 1.1.8 beta
     */
    protected void timerImpulse() {
        updateUnit();

        final long period = DateUpdateService.durationBetweenNowAndCertainTimeUsingCertainUnit(targetUnit, targetDate);

        colorBackGround = autoBackground();
        rootStackPane.setBackground(colorBackGround);

        LocalDateTime now = LocalDateTime.now();

        FxUpdateService updateTitle = new FxUpdateService(() -> this.updateTitle(period));
        updateTitle.run();

        timeInMenuBar.setText(timeOf(now));
        dateInMenuBar.setText(dateOf(now));

        number.setText(String.valueOf(period));
        labelTimeUnit.setText(nameOfTimeUnit());


    }

    /**
     * @return 根据时间返回合适的背景
     */
    protected Background autoBackground() {
        return new Background(new BackgroundFill(properColorForNow(), null, null));
    }

    /**
     * @return 根据倒计时决定的合适的背景颜色
     */
    protected Color properColorForNow() {
        long period = DateUpdateService.durationBetweenNowAndCertainTimeUsingCertainUnit(ChronoUnit.DAYS, targetDate);
        if (period > 0 && period <= 10) return Color.rgb(243, 66, 53); //RED
        else if (period > 10 && period <= 20) return Color.rgb(254, 86, 33); //DEEP ORANGE
        else if (period > 20 && period <= 35) return Color.rgb(254, 151, 0); //AMBER
        else if (period > 35 && period <= 50) return Color.rgb(62, 80, 180); //INDIGO BLUE
        else if (period == 0) return Color.rgb(69,90,100);//BLUE GREY
        else if (period < 0) return Color.rgb(75, 174, 79);//GREEN
        else return Color.rgb(155, 38, 175);//PURPLE

    }

    private String nameOfTimeUnit() {

        return ChronoUnitLocalization.get(targetUnit);
    }

    private String timeOf(LocalDateTime now) {
        return String.format("%02d:%02d", now.getHour(), now.getMinute());
    }

    private String dateOf(LocalDateTime now) {
        return String.format(
                "%d月%d日 %s",
                now.getMonth().getValue(),
                now.getDayOfMonth(),
                now.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.getDefault())
        );
    }

    /**
     * 或许在这个功能能够实现之前都不要用它了
     * @param period 还有多少天到目标日期呢？
     */
    private void updateTitle(long period) {
//        thisStage.setTitle(
//                String.format(
//                        "还有%d%s%s",
//                        period,
//                        nameOfTimeUnit(),
//                        targetName
//                )
//        );
    }

    /**
     * 获得当前的小时剩余，如果小于96，那么目标时间将会变成小时
     */
    private void updateUnit() {
        long period = DateUpdateService.durationBetweenNowAndCertainTimeUsingCertainUnit(ChronoUnit.HOURS, targetDate);
        if (period <= 96 && period >= 0) targetUnit = ChronoUnit.HOURS;
        else targetUnit = ChronoUnit.DAYS;
    }

    private int shortestSideOfTheScreen(){
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        return dim.height<dim.width?dim.height:dim.width;
    }

    public void toggleFullScreen(){
        thisStage.setFullScreen(!thisStage.isFullScreen());
    }
}
