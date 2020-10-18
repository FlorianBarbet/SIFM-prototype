package com.shoppo.infrastructure;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
public class ProcessHandler extends Thread {
    InputStream inputStream;
    String streamType;

    public ProcessHandler(InputStream inputStream, String streamType) {
        this.inputStream = inputStream;
        this.streamType = streamType;
    }

    public void run() {
        try {
            InputStreamReader inpStrd = new InputStreamReader(inputStream);
            BufferedReader buffRd = new BufferedReader(inpStrd);
            String line;
            while ((line = buffRd.readLine()) != null) {
                System.out.println(streamType+ "::" + line);
            }
            buffRd.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}