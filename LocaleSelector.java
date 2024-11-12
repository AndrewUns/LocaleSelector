/**
 *
 * @author Andrew Unsworth
 */

// LocaleSelector Version 5
   
import java.io.*;
import java.util.*;
import java.text.*;
import java.time.*;
import java.time.format.*;

   
class LocaleSelector {
	
	// Declarations
	static String[] tableHeader = {"Product Name", "Price", "Discount%", "Date Available"};
	static Object[][] productData = { 
									{"Comfy Mattress", 2010.45d, 0.07d, "23/02/2026"},
									{"Bonzer Bed", 1450.75d, 0.1d, "04/11/2025"},
									{"Top-Brand TV", 2300.00d, 0.4d, "16/05/2025"},
									{"My Smart-Fridge", 1899.00d, 0.22d, "25/02/2026"}
									};
    	static Object[][] localData = new Object[4][4];
        
	static Locale loca = Locale.getDefault();
	static NumberFormat discountFormatter;
	static NumberFormat priceFormatter;
	static Currency localCurrency;
	static DateTimeFormatter dateFormatter;
    	static Date tempDate;
	
	// Set up a means of taking input from the keyboard
	static InputStreamReader input = new InputStreamReader(System.in);
	static BufferedReader stdIn = new BufferedReader(input);
	
	// Set up a means of writing to standard output
	static PrintWriter stdOut = new PrintWriter(System.out, true);
	
	public static void main(String[] args) throws IOException {
		
		boolean loopIterator = true;
		while(loopIterator) {
		stdOut.println("\nWelome to LocaleSelector! Please choose the locale you'd like to use for this program.");
		stdOut.println("Enter 1 for UK English\nEnter 2 for US English\nEnter 3 for French\nEnter 4 for Spanish\n");
		stdOut.println("Enter 0 to terminate the program.\n");
		int menuChoice = 0;
		try {
			menuChoice = Integer.valueOf(stdIn.readLine());
			} catch (IOException ioe) {
				stdOut.println("IOException thrown: " + ioe.toString());
				}
				catch (NumberFormatException nfe) {
					stdOut.println("Please enter a number only.");
					stdOut.println("NumberFormatException thrown: " + nfe.toString());
					}
		
		
		
		// Set the locale using a Switch statement
		switch(menuChoice) {
			case 0: stdOut.println("You chose to terminate the program\n"); System.exit(0);
			case 1: stdOut.println("You chose UK English.\n"); loca = new Locale("en", "GB"); break;
            case 2: stdOut.println("You chose US English\n"); loca = new Locale("en", "US"); break;
			case 3: stdOut.println("You chose French.\n"); loca = new Locale("fr", "FR"); break;
			case 4: stdOut.println("You chose Spanish.\n"); loca = new Locale("es", "ES"); break;
			}
		
                try{
                localizeData();
                } catch (ParseException pe) {stdOut.println(pe);}
		outputData();
		}
	}
	
        private static void localizeData() throws ParseException {
                        
            // Set up the means of formatting a currency value
            localCurrency = Currency.getInstance(loca);
            priceFormatter = NumberFormat.getCurrencyInstance(loca);
            
            // Set up the means of formatting a currency value
            discountFormatter = NumberFormat.getPercentInstance(loca);
            
			// Use two FOR loops to access the product array data, localize the data and then write it
			// to a second array
			
            for(int outerIndex = 0; outerIndex < 4; outerIndex++){
            
                for(int innerIndex = 0; innerIndex <4; innerIndex++){
                    
					// A series of IF statements identify elements to localize, localize the data and then write
					// the freshly localized elements to the localData array
					
                    // Add product names to the localData array
                    if(innerIndex == 0){
                        localData[outerIndex][innerIndex] = productData[outerIndex][innerIndex];
                    }
                    
                    // Format prices and add them to the localData array
                    if(innerIndex == 1) {
                        priceFormatter = NumberFormat.getCurrencyInstance(loca);
                        localData[outerIndex][innerIndex] = priceFormatter.format(productData[outerIndex][innerIndex]);
                    }
                    
                    // Format the discount percentages and add them to the localData array
                    if(innerIndex == 2) {
                        localData[outerIndex][innerIndex] = discountFormatter.format(productData[outerIndex][innerIndex]);
                    }
                    
                    // Format the dates and add them to the localData array
                    if(innerIndex == 3) {
                                                
                        String dater = (String) productData[outerIndex][innerIndex];
						
						dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy").withLocale(loca);
						LocalDate tempDate = LocalDate.parse(dater, dateFormatter);
						dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(loca);
						String localizedDate = tempDate.format(dateFormatter);
                                                                        
                        localData[outerIndex][innerIndex] = tempDate.format(dateFormatter);
                        
                    }
                }
            }
            
            
        }
        
	private static void outputData() {
		// This method uses FOR loops to output the contents of the localData array
		// to standard output
		
		
		for(int index = 0; index < 4; index++) {
			
			// Output the table headers
			stdOut.print(tableHeader[index] + "\t"); stdOut.flush();
			
			// Add a conditional statement to neaten up output
			if(index == 1) {
				stdOut.print("\t"); stdOut.flush();
			}
		}
		
		// Output the  data
		for(int outerIndex = 0; outerIndex < 4; outerIndex++) {
			stdOut.print("\n"); stdOut.flush();
			for(int innerIndex = 0; innerIndex < 4; innerIndex++) {
				stdOut.print(localData[outerIndex][innerIndex] + "\t"); stdOut.flush();
				
				// Add a conditional statement to neaten up output
				if(innerIndex == 2) {
					stdOut.print("\t"); stdOut.flush();
					}
				}		
			}
			stdOut.println("\n");
		}
}
