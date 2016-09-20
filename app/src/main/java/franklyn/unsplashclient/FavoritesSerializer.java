package franklyn.unsplashclient;

import android.content.Context;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Franklyn on 11/6/2015.
 */
public class FavoritesSerializer {


    public void SerializeFile(Context context,  Object object){

        File f = new File(context.getFilesDir()+ "/favorites");


        ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(f)));

            try {
                oos.writeObject(object);

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            oos.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public Object DeSerializeFile(Context context){

        Object o = null;
        ObjectInputStream ois;
        try {
            ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(context.getFilesDir()+ "/favorites")));

            try {
                o = ois.readObject();
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            ois.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return o;

    }
}
