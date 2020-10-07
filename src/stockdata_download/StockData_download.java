/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stockdata_download;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.time.LocalDate;
/**
 *
 * @author histo
 */
public class StockData_download {
        
	private final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.142 Safari/537.36";
        private final static String KEY = "SPN9ACIFUB9NAPEH"; // perhaps change in future?
        
        //private final int day = LocalDate.getDayOfMonth();
        
	public static void main(String[] args) throws Exception {

		StockData_download stockdatadownload = new StockData_download();

		//System.out.println("Stock data writer -- gets intraday data and write to specific folder in doc.");
                //System.out.println("Enter stock symbol: ");
                
                //Scanner sc = new Scanner(System.in);
                String input; //= sc.nextLine();
                
                input = "MSFT";
                
                for(int i = 0; i < input.length(); i++) {
                    if(input.charAt(i) == ' ') {
                        if(i == 0) {
                            input = input.substring(1, input.length());
                            i--;
                        }
                        else {
                            input = input.substring(0, i) + '_' + input.substring(i+1, input.length());
                        }
                    }
                }
                
		stockdatadownload.sendGet(input, KEY);
	}
        
	// HTTP GET request
	private void sendGet(String input, String key) throws Exception {
                // https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=MSFT&interval=5min&outputsize=full&apikey=SPN9ACIFUB9NAPEH
		String url = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&symbol=" + input
                        + "&interval=5min&outputsize=full&apikey=" + key;
		
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode;
                responseCode = con.getResponseCode();
                
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuilder response = new StringBuilder();
                
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
                
		//print result to file
                String file_location = "C:\\Users\\histo\\OneDrive\\Documents\\StockMarket\\Intraday_Data";

                LocalDate date = LocalDate.now();
                String year = Integer.toString(date.getYear());
                
                int month_val = date.getMonthValue();
                String month_num = Integer.toString(month_val);
                String month = (month_val < 10) 
                        ? ("0" + month_num) 
                        : (month_num);
                
                int day = date.getDayOfMonth();
                String date_num = Integer.toString(day);
                String date_string = (day < 10) 
                        ? ("0" + date_num) 
                        : (date_num);
                
                String filedate = year + "_" + month + "_" + date_string;
                String filename = file_location + "\\" + filedate + ".txt";
                
                FileWriter file = new FileWriter(filename);
                
                String data = response.toString();
                
                try{
                    file.write(data);
                    file.close();
                } catch(Exception e){}
	}
}
