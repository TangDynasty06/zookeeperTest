package com.zookeeper.test;

import java.io.IOException;
import java.util.Scanner;

public class ListNetStatus1 {

    public static String executeCommand(String cmd) throws IOException {
        Process ps = Runtime.getRuntime().exec(cmd);
        Scanner scanner = new Scanner(ps.getInputStream());
        StringBuilder result = new StringBuilder();
        while (scanner.hasNextLine()) {
            result.append(scanner.nextLine());
            result.append(System.getProperty("line.separator"));
        }
    
        scanner.close();
        return result.toString();
    }

    // �г���������ǰ����״̬
    public static void main(String[] args) throws InterruptedException, IOException {
          System.out.println(executeCommand("ipconfig"));

    }

}