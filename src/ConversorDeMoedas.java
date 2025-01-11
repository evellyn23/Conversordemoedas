import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

public class ConversorDeMoedas {
    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/"; // Exemplo de URL

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Escolha uma opção de conversão:");
        System.out.println("1. Real para Dólar");
        System.out.println("2. Dólar para Euro");
        System.out.println("3. Euro para Real");
        System.out.println("4. Dólar para Libra");
        System.out.println("5. Libra para Dólar");
        System.out.println("6. Real para Euro");

        int opcao = scanner.nextInt();
        double valorConvertido = 0;

        System.out.print("Digite o valor que deseja converter: ");
        double valor = scanner.nextDouble();

        switch (opcao) {
            case 1:
                valorConvertido = converterMoeda("BRL", "USD", valor);
                System.out.println("Valor em Dólares: " + valorConvertido);
                break;
            case 2:
                valorConvertido = converterMoeda("USD", "EUR", valor);
                System.out.println("Valor em Euros: " + valorConvertido);
                break;
            case 3:
                valorConvertido = converterMoeda("EUR", "BRL", valor);
                System.out.println("Valor em Reais: " + valorConvertido);
                break;
            case 4:
                valorConvertido = converterMoeda("USD", "GBP", valor);
                System.out.println("Valor em Libras: " + valorConvertido);
                break;
            case 5:
                valorConvertido = converterMoeda("GBP", "USD", valor);
                System.out.println("Valor em Dólares: " + valorConvertido);
                break;
            case 6:
                valorConvertido = converterMoeda("BRL", "EUR", valor);
                System.out.println("Valor em Euros: " + valorConvertido);
                break;
            default:
                System.out.println("Opção inválida!");
                break;
        }

        scanner.close();
    }

    private static double converterMoeda(String deMoeda, String paraMoeda, double valor) {
        try {
            String urlString = API_URL + deMoeda; // URL da API
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject jsonResponse = new JSONObject(response.toString());
            double taxa = jsonResponse.getJSONObject("rates").getDouble(paraMoeda);
            return valor * taxa;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}