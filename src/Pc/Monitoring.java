package Pc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;


/**
 * Class monitoring and control of the washing machine serial messages
 */

public class Monitoring extends Thread {
    SerialCommChannel channel;
    Gui view;

    public Monitoring(SerialCommChannel channel, Gui view) {
        this.channel = channel;
        this.view = view;
        view.setLavaggi(Integer.toString(readFile("lavaggi.txt")));

    }

    public void run() {

        while (true) {
            try {
                String msg = channel.receiveMsg();
                System.out.println("received: "+msg);

                String[] tokens = msg.split(" ");
                if (tokens.length == 2) {
                    if (tokens[0].equals("T:")) {
                        view.setTemperature(tokens[1]);
                    }

                    if (tokens[0].equals("AT:")) {
                        this.maintenance();
                    }

                    if (tokens[0].equals("A:")) {
                        int numWash = readFile("lavaggi.txt");
                        numWash++;
                        view.setLavaggi(Integer.toString(numWash));
                        rewriteFile("lavaggi.txt", Integer.toString(numWash));
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private int readFile(String path) {

        String data = "-1";
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return -1;
        }

        // return data as integer
        return Integer.parseInt(data);

    }

    private void rewriteFile(String path, String data) {
        try {
            File myObj = new File(path);
            myObj.createNewFile();
            FileWriter myWriter = new FileWriter(path);
            myWriter.write(data);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }



    private void maintenance() {
        
        // Il terzo parametro rappresenta i pulsanti visualizzati nel dialogo
        int result = JOptionPane.showOptionDialog(
                null,
                "Fix the issue and then click the button",
                "WARNING",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new Object[] { "OK", "Cancel" }, // Array di pulsanti
                "OK" // Pulsante predefinito
        );
        //System.out.println(result);
        if (result == 0) {
            channel.sendMsg("MAINTENANCE: OK");  
            //System.out.println("MAINTENANCE: OK");
        }

    }
}
