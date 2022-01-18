package Server.Model.Network;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class JSONReader {

    private NetworkModel networkModel;

    public void read() {

        Gson gson = new Gson();
        JsonReader reader;

        try {
            reader = new JsonReader(new FileReader("src/Shared/data/config"));
            networkModel = gson.fromJson(reader, NetworkModel.class);


        } catch (FileNotFoundException e) {
            System.out.println("error, no s'ha pogut llegir correctament el fitxer");

        }
    }

    public NetworkModel getNetworkModel() {
        return networkModel;
    }
}
