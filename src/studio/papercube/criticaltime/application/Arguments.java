package studio.papercube.criticaltime.application;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Created by imzhy on 2016/6/13.
 */
public class Arguments {
    public final Map<String,String> value;
    protected static final String SEPARATOR = ":";

    public Arguments(String[] args) {
        value = new HashMap<>();
        parseArgs(args);
    }

    private void parseArgs(String[] args) {
        for (String s : args) {
            String[] split = s.split(SEPARATOR);
            value.putIfAbsent(split[0].toUpperCase(), split[1]);
        }
    }

    public Optional<String> getOptionalArg(String s) {
        return Optional.ofNullable(getArg(s));
    }

    public String getArg(String s) {
        return value.get(s.toUpperCase());
    }

    /**
     * 替换某些变量为特定的值。
     * 这些值可能通过CriticalTime#startWithFinalDateOf或者命令行指定。
     */
    public static String parseArgVariables(String s) {
        return s.replace("$YEAR", String.valueOf(LocalDate.now().getYear()));
    }
}
