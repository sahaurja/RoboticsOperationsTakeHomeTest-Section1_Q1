package com.section1;

/*
 * Author: Urja Saha
 * J.P.S.H.S Edison NJ Junior
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 
 * This program is the solution for Section#1 option#1
 * 
 * How to run as CLI e.g.: 
 *     java HacktonBrowser --challenge_type online --length days --page 1
 * The ouput is is saved in result.json file
 * 
 * Available Command line arguments:
 *   challenge_type
 *   length
 *   open_to
 *   status
 *   themes
 *   page 
 */
public class HacktonBrowser {
	
	/** 
	 * the filer name and values are passed as parameter in the CLI
	 * e.g. '--challenge_type online'
	 * @param args
	 * @return
	 */
	static String buildRestUrl(String[] args) {
		String restStr= "https://devpost.com/api/hackathons?";
		Map<String, String> argMap = new LinkedHashMap<>();
		for (int ind = 0; ind < args.length; ind += 2) {
			argMap.put(args[ind], args[ind + 1]);
		}
		for (Map.Entry<String, String> entrySet : argMap.entrySet()) {
			String property = entrySet.getKey().substring(2);
			String value = entrySet.getValue();
			//System.out.println("key = " + property + ", value = " + value);
			if(property.equalsIgnoreCase("page"))
				restStr+=property+"="+value+"&";	
			else
			    restStr+=property+"[]="+value+"&";		
		}
		// remove last '&'
		restStr=restStr.substring(0, restStr.length()-1);
		return restStr;
	}

    public static void main(String[] args) throws Exception {
    	
    	// Here I build the Rest url using the filter from command line arguments
    	
    	String restUrl =buildRestUrl(args);
        //System.out.println(restUrl);   	
    	
        /**
         * Here I make the  REST Call (webservice call) to retrieve data.
         *
         */
        HttpRequest request = HttpRequest.newBuilder()
        		.GET() // uses GET
                .uri(URI.create(restUrl))               
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        // Rest response
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());  // Here I got the response
        
        
        
        // I get the .json string. Now I save the .json in 'result.json' file
        
        String jsonResponse = response.body();        
        //System.out.println(jsonResponse);        
        File file = new File("result.json");
        try (PrintStream out = new PrintStream(new FileOutputStream(file))) {
            out.print(jsonResponse);
            out.flush();
        } 
        System.out.println("response json is saved in result.json");
    }
}
