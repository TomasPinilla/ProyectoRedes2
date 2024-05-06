import org.json.JSONArray;
import org.json.JSONObject;


import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        String url = "https://air-quality-api.open-meteo.com/v1/air-quality?latitude=4.6097&longitude=-74.0817&current=european_aqi,pm10,pm2_5&domains=cams_global";
        JSONObject infoAireActualBogota =  obtenerInfoAPI(url);


    }

    public static JSONObject obtenerInfoAPI(String urlAPI)
    {
        try {

            //Creacion de programa que implemente datos de una API para enviar datos a MQTT Mosquitto
            //Se necesita solicitar una peticion de: https://air-quality-api.open-meteo.com/

            URL url = new URL(urlAPI);
            HttpURLConnection conectar = (HttpURLConnection) url.openConnection();
            conectar.setRequestMethod("GET");
            conectar.connect();

            int responseCode = conectar.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("Ocurrio un error: " + responseCode);
            } else {
                //Abrir un scanner que lea el flujo de datos
                StringBuilder informationSring = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    informationSring.append(scanner.nextLine());
                }

                scanner.close();
                //System.out.println(informationSring);

                JSONObject jsonObject = new JSONObject(informationSring.toString());
                JSONObject current = jsonObject.getJSONObject("current");
                double european_aqi = current.getDouble("european_aqi");
                double pm10 = current.getDouble("pm10");
                double pm2_5 = current.getDouble("pm2_5");
                System.out.println("european_aqi: " + european_aqi);
                System.out.println("pm10: " + pm10);
                System.out.println("pm2_5: " + pm2_5);

                return current;

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    return null;
    }
}