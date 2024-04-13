/**
 * Javier Cunat Honors Project COP2800 Currency Converter
 * Documentation: https://exchangeratesapi.io/documentation/
 *
 * ReadME Steps:
 *    Download JSON in Java Jar File: https://github.com/stleary/JSON-java
 *    Add Jar File to Project Structure -> Modules in IDE (This is to be able to Parse and create JSONObject)
 *    Make sure you can import org.json.JSONObject and all other classes
 *    Make sure you are using exhangeratesapi.io access key: 3f1c60a4bf4f3f7b623600349838d197 (you can use mine)
 *
 */

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;

public class CurrencyConverter {
    public static void main(String[] args) throws IOException {

        //HashMap with key:value pairs to combine
        HashMap<Integer, String> currencyCodes = new HashMap<Integer, String>();

        //add currency codes in order of most traded
        currencyCodes.put(1, "USD"); //US dollar
        currencyCodes.put(2, "EUR"); //Euro
        currencyCodes.put(3, "JPY"); //Japanese Yen
        currencyCodes.put(4, "GBP"); //Pound sterling
        currencyCodes.put(5, "AUD"); //Australian dollar
        currencyCodes.put(6, "CNY"); //China Yuan Renminbi
        currencyCodes.put(7, "CAD"); //Canadian Dollar
        currencyCodes.put(8, "CHF"); //Switzerland Franc
        currencyCodes.put(9, "HKD"); //Hong Kong Dollar
        currencyCodes.put(10, "INR"); //Indian rupee

        //boolean to start and continue currency converter unless user specifies to quit
        boolean userContinue = true;
        do {

            // Show the message dialog with the loaded image, title, and message
            ImageIcon icon = new ImageIcon(CurrencyConverter.class.getResource("/imageIcon.jpg"));
            JOptionPane.showMessageDialog(
                    null,
                    "Welcome to Javi's Currency Converter",
                    "Currency Converter",
                    JOptionPane.INFORMATION_MESSAGE,
                    icon
            );


            //declare fromCurrencyInput (the Input that the user enters) and fromCurrency (Input that user enters parsed as Int)
            String fromCurrencyInput;
            int fromCurrency;

            //do loop that always ask the user to enter the fromCurrency at least once, it will loop until the user selects a valid currency
            do {
                //Use JOptionPane to create an input dialog pox for the user to choose their fromCurrency
                fromCurrencyInput = JOptionPane.showInputDialog(
                        """
                                        Currency converting from:
                                        1:USD, 2:EUR, 3:JPY, 4:GBP, 5:AUD, 6:CNY, 7:CAD, 8:CHF, 9:HKD, 10:INR
                                """
                );
                //parse user input to store as an Int
                fromCurrency = Integer.parseInt(fromCurrencyInput);

                //if the user entered an invalid currency, prompt them to enter a correct one
                if (fromCurrency < 1 || fromCurrency > 10) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Please select a valid currency converting from (1-10).",
                            "Invalid Input",
                            JOptionPane.ERROR_MESSAGE
                    );
                }

            } while (fromCurrency < 1 || fromCurrency > 10); //run as long as user doesn't enter valid currency

            //Get the correct currency from the ArrayList according to the number the user entered
            String fromCurrencyString = currencyCodes.get(fromCurrency);


            //declare toCurrencyInput (the input that the user enters) and toCurrency (Input that user enters parsed as Int)
            String toCurrencyInput;
            int toCurrency;

            //do loop that always ask the user to enter the toCurrency at least once, it will loop until the user selects a valid currency
            do {
                //Use JOptionPane to create an input dialog pox for the user to choose their toCurrency
                toCurrencyInput = JOptionPane.showInputDialog(
                        """
                                        Currency converting to:
                                        1:USD, 2:EUR, 3:JPY, 4:GBP, 5:AUD, 6:CNY, 7:CAD, 8:CHF, 9:HKD, 10:INR
                                """
                );
                //parse user input to store as an Int
                toCurrency = Integer.parseInt(toCurrencyInput);

                //if the user enters an invalid currency (1-10) show message that they should enter a correct one
                if (toCurrency < 1 || toCurrency > 10) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Please select a valid currency converting to (1-10).",
                            "Invalid Input",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            } while (toCurrency < 1 || toCurrency > 10); //run in loop until user enters valid currency

            //Get the correct currency from the ArrayList according to the number the user entered
            String toCurrencyString = currencyCodes.get(toCurrency);


            //Use JOptionPane to create an input dialog pox for the user to choose the amount they want to convert
            String amountInput = JOptionPane.showInputDialog(
                    """
                                    Currency amount
                            """
            );
            //parse the user input amount from a String to a double
            double amount = Double.parseDouble(amountInput);


            System.out.println(fromCurrencyInput + toCurrencyInput + amount);

            //call function that will handle the httpGetRequest passing the three arguments collected from the user
            sendHttpGETRequest(fromCurrencyString, toCurrencyString, amount, icon);


            //prompt the user if they wish to continue using the currency converted
            String continueProgram = JOptionPane.showInputDialog(
                    """
                                   Thank you for using Javi's Currency converter, do you wish to continue enter "Yes" or "No"
                            """
            );

            //if the answer is not equal to "yes" make bool userContinue to false and exit program
            if(!"yes".equalsIgnoreCase(continueProgram)){
                userContinue = false;
            }

        } while(userContinue);
    }





    /**
     * Sends an HTTP GET request to retrieve exchange rates for converting currency.
     *
     * @param fromCode The currency code to convert from.
     * @param toCode   The currency code to convert to.
     * @param amount   The amount of currency to convert.
     * @throws MalformedURLException if an error occurs while sending the HTTP request.
     */
    private static void sendHttpGETRequest(String fromCode, String toCode, double amount, ImageIcon icon) throws IOException {


        //Store access key for API
        String accessKey = "3f1c60a4bf4f3f7b623600349838d197";

        //URL to use calling the ExchangeRatesAPi.io API latest form
        final String GET_URL = "http://api.exchangeratesapi.io/v1/latest" +
                "?access_key=" + accessKey + "&base=" + fromCode + "&symbols=" + toCode;

        System.out.print(GET_URL);


        //create a URL instance using the GET_URL string
        URL url = new URL(GET_URL);

        //Create a HttpURL Connection instance and setup HTTP connection to make a GET request
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");

        //to check if our get request works we examine the response code
        int responseCode = httpURLConnection.getResponseCode();

        //if the response is successful (200 or OK) we can read the requested info
        if(responseCode == HttpURLConnection.HTTP_OK) { //success
            //use bufferReader instance to read this response
            BufferedReader in =
                    new BufferedReader(
                            new InputStreamReader(httpURLConnection.getInputStream()
                            )
                    );
            String inputLine;
            StringBuffer response = new StringBuffer();

            //while loop to check that if there is still lines to read in response continue reading until there's no more
            while( (inputLine = in.readLine()) != null ) {

                //add content to the String buffer
                response.append(inputLine);
            }in.close(); //close the bufferReader (similar to scanner or file object)


            //Parse our response to make it to be a JSON object
            JSONObject obj = new JSONObject(response.toString());


            //Grab only the information we need from this response, the rates from the key of currency we want
            double exchangeRate = obj.getJSONObject("rates").getDouble(toCode);

            //format the output message
            DecimalFormat toTwoDecimals = new DecimalFormat("00.00");
            String outputMessage = amount + " in currency type " + fromCode + " is = to " +
                   toTwoDecimals.format(amount/exchangeRate) + " of the " + toCode + " currency";


            //display the response in a message dialog
            JOptionPane.showMessageDialog(
                    null,
                    outputMessage,
                    "Currency Converter",
                    JOptionPane.INFORMATION_MESSAGE,
                    icon
            );
        }


        //otherwise if we don't get a 200 or ok return a dialog box with an error
        else{
            JOptionPane.showMessageDialog(
                    null,
                    "Get Request Failed",
                    "Error",
                    JOptionPane.INFORMATION_MESSAGE,
                    icon
            );
        }

    }
}