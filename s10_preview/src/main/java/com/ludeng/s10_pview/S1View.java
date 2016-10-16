package com.ludeng.s10_pview;

/**
 * Created by chen on 16-10-15.
 */
public class S1View {
    private static S1View ourInstance = new S1View();

    public static S1View getInstance() {
        return ourInstance;
    }

    private S1View() {
    }
}
