package org.jpsil;

import java.io.File;

public class Main
{
    public static void main( String[] args )
    {
        // Path to database given as argument.
      /*  if(args.length != 1) {
            System.out.println("Arguments: <Path to sqlite .db file>");
            System.exit(0);
        }

        // Checking if file in given path exists. Terminate if not.
        File file = new File(args[0]);
        if(file.isFile()) {
            String URL = "jdbc:sqlite:" + args[0];
            AppLogic appLogic = new AppLogic(URL);
            appLogic.runApp();
        }
        else {
            System.out.println("File not found: Invalid path");
            System.exit(0);
        }

       */

        AppLogic appLogic = new AppLogic("jdbc:sqlite:/home/jpsil/Bookshelf/bookshelf.db");
        appLogic.runApp();

    }
}
