package cmsc256;  

/**
 * CMSC 256
 * Health Data Analyzer 
 * Mohammad Garada 
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
public class HealthDataAnalyzer {
    Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
       
       Project1 p1 = new Project1();

        String inputFileName = p1.checkArgs(args);

        String[][] data = null;

        try {
            File inputFile = p1.getFile(inputFileName);       // validate input file

            data = p1.readFile(inputFile, 500);     // read data file into matrix
        } catch (FileNotFoundException noFile) {
            System.out.println("Error reading from file; Program could not continue.");

        } catch (IOException dataError) {
            System.out.println(dataError.getMessage());

        }
        System.out.println(Arrays.deepToString(data));

        // Display appropriately labeled information for the following:

        // What is tallest height?
        System.out.println("The tallest height is " + p1.findTallest(data));

        // Which row has the lowest weight?
        System.out.println("The row with the lowest weight is " + Arrays.toString(p1.findLightestRecord(data)));


        // Calculate average height of 20-30 year age range in the data.
        System.out.println("Average height for ages 20-30 is " + p1.findAvgHeightByAgeRange(data, 25, 35));

    }






    /**
     * Gets the file name from command line argument;
     * If parameter is empty, call promptForFileName() method
     *
     * @param argv String array from command line argument
     * @return the name of the data file
     */
    public String checkArgs(String[] argv) {

        //if the parameter is empty, the it will call the prompForFileName method
        if (argv.length == 0) {
            promptForFileName();
        }
        return argv[0];
    }

    /**
     * Prompt user to enter a file name
     *
     * @return user entered file name
     */
    public String promptForFileName() {

        //Prompt the user to enter a file name
        System.out.println("Enter a file name: ");
        //create scanner object
        Scanner scan = new Scanner(System.in);
       //Create string for the file and prompt user to enter
        String nameOfFile = scan.nextLine();

        return nameOfFile;
    }


    /**
     * Retrieve file with the given file name.
     * Prompts user if file cannot be opened or does not exist.
     *
     * @param fileName The name of the data file
     * @return File object
     * @throwsjava.io.FileNotFoundException
     */
    public File getFile(String fileName) throws FileNotFoundException {


        //create file using the already created String for the fileName
        File retrievedFile = new File(fileName);
        return retrievedFile;

    }

    /**
     * Reads the comma delimited file to extract the number data elements
     * provided in the second argument.
     *
     * @param file       The File object
     * @param numRecords The number of values to read from the input file
     * @return 2D array of data from the File
     * @throws IOException if any lines are missing data
     */
    public String[][] readFile(File file, int numRecords) throws IOException {
        //creating a scanner object to input the file
        Scanner scan = new Scanner(file);
        //creating a 2D array with rows being the number of values the user wants to read and 3 being the columns of data (age, height, weight)
        String[][] output = new String[numRecords][3];
        //Create string object
        String data = scan.nextLine();
        //Integer object for the rows
        int integer = 0;
        //while loop to order to go through each line in the 2D array
        while (scan.hasNextLine() && integer < numRecords) {
            data = scan.nextLine().trim();

            //New scanner variable for the string of data
            Scanner input = new Scanner(data);

            //Delimiter in order to remove the commas in the string
            input.useDelimiter(",");

            //for loop to loop through the 2D array and assign the data to its respective row and column
            for (int i = 0; i < 3; i++) {
                if (input.hasNext()) {
                    output[integer][i] = input.next().trim();
                } else {
                    System.out.println("Error");
                    input.close();
                    throw new IOException();
                }
            }
            integer++;
            //closing the input Scanner method
            input.close();
        }
        //closing the scan Scanner method
       scan.close();
        return output;
    }


    /**
     * Determines the tallest height in the data set
     * Height is the second field in each row
     *
     * @param db 2D array of data containing [age] [height] [weight]
     * @return Maximum height value
     */
    public int findTallest(String[][] db) {

        //variable for parsing
        int parse = 0;
        //tallest height variable set to 0
        int tallestHeight = 0;

        //nested for loops to go through the rows and the columns
        for (int i = 0; i < db.length; i++) {
            for (int j = 0; j < db[i].length; j++) {

                //parse the 2D array into an integer
                parse = Integer.parseInt(db[i][1]);

                //if then statement to declare the tallest height
                if (parse > tallestHeight) {
                    tallestHeight = parse;
                }
            }
        }
        //return the tallest height
        return tallestHeight;

    }


    /**
     * Returns the values in the record that have the lowest weight
     *
     * @param db 2D array of data containing [age] [height] [weight]
     * @return Smallest weight value
     */
    public String[] findLightestRecord(String[][] db) {
        int parse = 0;
        int number1 = 0;
        int rowOutput = 0;
        //for some reason, inputing the number 1 didn't work, so I made a variable set to zero and incremented it.
        int lowestWeight = Integer.parseInt(db[number1++][2]);


        //nested for loops to find the row that contained the lowest weight
        for (int i = 0; i < db.length; i++) {
            parse = Integer.parseInt(db[i][2]);
            if (parse < lowestWeight) {
                lowestWeight = parse;
              //storing the nth number of the row into a variable called "row"
                rowOutput = i;

            }
        }

        //return row
        return db[rowOutput];
    }

    /**
     * Calculates the average height for all records with the given age range.
     *
     * @param db         2D array of data containing [age] [height] [weight]
     * @param lowerBound youngest age to include in the average
     * @param upperBound oldest age to include in the average
     * @return The average height for the given range or 0 if no
     * records match the filter criteria
     */
    public double findAvgHeightByAgeRange(String[][] db, int lowerBound, int
            upperBound) {
        //double total in order to divide from
        double total = 0;
       //to count how many times the loop is went through
        int count = 0;



        for (int i = 0; i < db.length; i++) {

            //parsing the 2D array into an int
            int age = Integer.parseInt(db[i][0]);
            if (age >= lowerBound && age <= upperBound) {
              //count variable
                count++;
                total += Double.parseDouble(db[i][1]);
            }
        }
        if (count > 0) {
        //calculate average
            return total / count;
        }
      //return average
        return 0;
    }
}
