package studio.papercube.criticaltime.application;

import javafx.application.Application;
import javafx.stage.Stage;
import studio.papercube.criticaltime.application.service.TimerHangMonitor;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

import static java.lang.Integer.parseInt;
import static java.lang.Integer.parseUnsignedInt;

public class CriticalTime {
    public static Stage primaryStage;
    /**
     * 你对它做的更改会被mainWindowSupplier.get()覆盖。
     */
    protected static CriticalTimeMainWindow criticalTimeMainWindow;
    public static LocalDate targetDate;
    public static String targetName;
    public static Arguments args;
    private static FxAppLaunch fxEnvironment = new FxAppLaunch();
    /**
     * 如果你通过继承主窗口的方式对它进行更改了，请更改这个变量。
     * 它返回一个CriticalTimeMainWindow的子类实例
     * 如果你不更改，那么你的更改将不会生效。
     * 如果你的类名是A，那么你可以这样
     * <code>
     *     CriticalTime.mainWindowSupplier = A::new;
     * </code>
     *
     * 请一定在调用开始方法前对它进行更改！
     */
    public static Supplier<? extends CriticalTimeMainWindow> mainWindowSupplier = CriticalTimeMainWindow::new;

    public static void setArgs(String[] args) {
        CriticalTime.args = new Arguments(args);
    }
    /**
     * 启动整个程序
     *
     * @param passingName   目标的名称。
     *               如果名称是AAA,那么效果将是 距离20xx年AAA还有……
     * @param passingTarget 目标时间（中考或者会考——高考也可以）;
     */
    public static void startWithFinalDateOf(final LocalDate passingTarget,final String passingName) {
        CriticalTime.targetDate = parseDate(args.getArg("targetDate")).orElse(passingTarget);
        CriticalTime.targetName = Arguments.parseArgVariables(args.getOptionalArg("targetName").orElse(passingName));
        TimerHangMonitor.invokeWhenExceptionRaised(() -> System.exit(0));
        fxEnvironment.launchApp();
    }

    /**
     * @return 今年
     */
    public static int currentYear() {
        return LocalDate.now().getYear();
    }

    private static Optional<LocalDate> parseDate(String value) {
        if(value==null) return Optional.empty();
        String[] values = Arguments.parseArgVariables(value).split("-");
        return Optional.of(
                LocalDate.of(parseInt(values[0]), parseInt(values[1]), parseInt(values[2]))
        );

    }


    /**
     * 封装了JAVA FX的启动相关
     *
     * @author PaperCube
     */
    protected static final class FxAppLaunch extends Application {
        public FxAppLaunch() {
        }

        /**
         * 从父类继承。是JAVAFX应用程序的入口类
         *
         * @param primaryStage 要显示stage
         * @throws Exception 继承父类
         */
        @Override
        public void start(Stage primaryStage) throws Exception {
            CriticalTime.primaryStage = primaryStage;
            Objects.requireNonNull(targetDate);
            criticalTimeMainWindow = mainWindowSupplier.get();
            primaryStage.show();
        }

        void launchApp() {
            launch();
        }
    }


}
