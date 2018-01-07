package use.newer.java;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.regex.Pattern;

public class Util {

    public static final String TARGET_CLASS_ATTRIBUTE_NAME = "Main-Class-After-UseNewerJava-Check";

    public static void checkJavaVersionAndExecuteMainProgram(int majorJavaVersion, String[] args) {
        checkJavaVersionIsNewEnough(majorJavaVersion);
        executeMainProgram(args);
    }

    private static void executeMainProgram(String[] args) {
        String targetMainClass = readTargetClassNameFromManifest();
        if (targetMainClass == null) {
            System.err.println("\n\nCouldn't find the target main class to run after checking the Java version.\n" +
                    "\nThe attribute should be in the Jar manifest and called " + TARGET_CLASS_ATTRIBUTE_NAME);
            System.exit(-1);
        } else {
            Util.invokeMainMethod(args, targetMainClass);
        }
    }

    private static void checkJavaVersionIsNewEnough(int majorJavaVersion) {
        String specVersionString = specVersion();
        if (specVersionString == null) {
            dieAndAskFor(majorJavaVersion, "Looks like your version of Java is too old to run this program.");
        } else {
            int versionOfCurrentJvm = majorJavaVersionFrom(specVersionString);
            if (versionOfCurrentJvm < majorJavaVersion) {
                dieAndAskFor(majorJavaVersion, "Looks like your version of Java (" + specVersionString + ") is too old to run this program.");
            }
        }
    }

    private static void dieAndAskFor(int requiredMajorJavaVersion, String warningForCurrentJvm) {
        System.err.println("\n\n" + warningForCurrentJvm + "\n" +
                "\nYou'll need to get Java " + requiredMajorJavaVersion + " or later.");
        System.exit(-1);
    }

    private static String readTargetClassNameFromManifest() {
        try {
            Enumeration<URL> resources = Util.class.getClassLoader().getResources("META-INF/MANIFEST.MF");
            while (resources.hasMoreElements()) {
                Attributes attributes = new Manifest(resources.nextElement().openStream()).getMainAttributes();
                String targetClass = attributes.getValue(TARGET_CLASS_ATTRIBUTE_NAME);
                if (targetClass != null) {
                    return targetClass;
                }
            }
        } catch (IOException e) {

        }
        return null;
    }

    static int majorJavaVersionFrom(String specVersionString) {
        String[] split = specVersionString.split("\\.");

        if (split.length > 1 && split[0].equals("1")) {
            return Integer.parseInt(split[1]);
        }

        try {
            return Integer.parseInt(split[0]);
        } catch (Exception e) {
            return 0;
        }
    }

    static String specVersion() {
        return System.getProperty("java.specification.version");
    }

    static void invokeMainMethod(String[] args, String targetMainClass) {
        try {
            Method targetMainMethod =
                    Class.forName(targetMainClass).getMethod("main", String[].class);

            targetMainMethod.invoke(null, new Object[]{args});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
