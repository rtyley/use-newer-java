package use.newer.java.demo;

public class Example {
    public static void main(String[] args) {
        System.out.println("Example target main class for use-newer-java running... "+args.length+" arguments found");
        for (int i=0; i<args.length; ++i) {
            System.out.println("arg "+i+" : '"+args[i]+"'");
        }
    }
}
