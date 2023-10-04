package uz.job.javainterview.util;

public class Util {
    public static String getMethodName() {
        return Thread.currentThread().getStackTrace()[2].getClassName() + "." + Thread.currentThread().getStackTrace()[2].getMethodName();
    }
}
