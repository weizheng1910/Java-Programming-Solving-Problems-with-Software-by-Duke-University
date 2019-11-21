
/**
 * Analysing the popularity of baby names in the US through the years, using the org.apache.commons.csv. library
 * CSV files contain the fields: Baby name, Gender, and Number of births; and are sorted according to popularity. 
 */

/*edu.duke.* is a library created by the university. http://www.dukelearntoprogram.com/course2/doc/javadoc/edu/duke/package-summary.html
*Essential classes include:
*DirectoryResource, which allows the user to choose one or more files from a directory.                                                        
*FileResource, which allows access to its content one word or one line at a time. 
*StorageResource, which allows String objects to be stored one at a time.
*URLResource, which allows the content of a webpage to be accessed one line or one word at a time.
*/
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Part1 {
    
    //Count how many boys name and girls name there are in a list. 
    public void NumberOfNames(CSVParser parser) {
        double GirlsNames = 0;
        double BoysNames = 0;
        for(CSVRecord record: parser) {
            String CurrentGen = record.get(1);
            if (CurrentGen.equals("M")) {
                BoysNames = BoysNames + 1;
            }   
            if (CurrentGen.equals("F")) {
                GirlsNames = GirlsNames + 1;
            }
        }
        System.out.println(BoysNames);
        System.out.println(GirlsNames);
    }
    
    //Total number of boys and girls born in a particular year. 
    //CSVParser is a .csv file which contains birth data of a particular year.
    public void totalBirths(CSVParser parser) {
        int totalBoys = 0;
        int totalGirls = 0;
        for(CSVRecord record: parser) {
            String NumberOfBirths = record.get(2);
            int NumberOfBirthsInt = Integer.parseInt(record.get(2));
            if (record.get(1).equals("M")) {
                totalBoys += NumberOfBirthsInt;
            } else {
                totalGirls += NumberOfBirthsInt;
            }
        }  
        System.out.println("Total number of boys= "+totalBoys);
        System.out.println("Total number of girls= "+totalGirls);
    }
    
    // Find how popular(rank) the name was in a given year and gender)
    public double getRank(int year, String name, String gender) {
        //This method assumes that the baby names are already sorted according to their popularity.
        //This method takes in CSV file named by the year from which the data contains.
        FileResource fr = new FileResource("yob"+year+".csv");
        CSVParser q1 = fr.getCSVParser(false);
        double rank = 1;

        for (CSVRecord record: q1) {
            String genderInRc = record.get(1);
            String nameInRc = record.get(0);
            if (genderInRc.equals(gender)) {
                if (nameInRc.equals(name)) {
                    return rank;
                } else {
                    rank = rank + 1;
                }
            }
        }
        return -1;
    }
    
    //This methods gets the rank, but it uses CSV data which is already parsed.
    public int getRank2(CSVParser parser, String name, String gender) {
        int rank = 1;
        for (CSVRecord record: parser) {
            String genderInRc = record.get(1);
            String nameInRc = record.get(0);
            if (genderInRc.equals(gender)) {
                if (nameInRc.equals(name)) {
                    return rank;
                } else {
                    rank = rank + 1;
                }
            }
        }
        return 99999;
    }
    
    //Find the baby name of the requested rank, gender and year.
    public String getName(int year,double rank,String gender) {
        
        FileResource fr = new FileResource("yob"+year+".csv");
        CSVParser q1 = fr.getCSVParser(false);
        
        double findRank = 1;
        
        for (CSVRecord record: q1) {
            String genderInRc = record.get(1);
            String nameInRc = record.get(0);
            
            if (genderInRc.equals(gender)) {
                if (findRank == rank) {
                    return nameInRc;
                } else {
                    findRank = findRank + 1;
                }
            }
        }
        return("No such person in rank");
    }

    //Based on the rank, <name> born in <year> would be <newName> in <newYear> 
    public void whatIsNameInYear(String name, int year, int newYear, String gender) {
        String newName = getName(newYear,getRank(year,name,gender),gender);
        System.out.println(name+" born in "+year+" would be "+newName+" if born in "+newYear);
    }
    
    //Find out the year in which the requested name (and gender) achieve the best rank
    public void yearOfHighestRank(String name, String gender) {
        DirectoryResource dr = new DirectoryResource();
        int BestRank = 9999;
        int YearofBestRank = 0;
        
        for (File f: dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            CSVParser q1 = fr.getCSVParser(false);
            int CurrRank = getRank2(q1,name,gender);
            //CurrYear is derived from the way the file is named - yob1984csv
            int CurrYear = Integer.parseInt(f.getName().substring(3,7));
            if (CurrRank<BestRank) {
                BestRank = CurrRank;
                YearofBestRank = CurrYear;
            }   
        }
        System.out.println(BestRank+" , "+YearofBestRank);
    }
    
    //Out of a selected number of files, get the average rank of a particular baby name. 
    public void getAverageRank(String name,String gender) {
        DirectoryResource dr = new DirectoryResource();
        
        double accRank = 0.00;
        double count = 0.00;
        
        for (File f: dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            CSVParser q1 = fr.getCSVParser(false);
            double CurrRank = getRank2(q1,name,gender);
            if (CurrRank != -1) {
                accRank = accRank + CurrRank;
                count = count + 1;
            } 
        }  
        double avrank = accRank/count;
        System.out.println("Average Rank is: "+avrank);
    }
    
    // The total number of babies which are born, whose names are ranked higher than the specified name and gender in a particular year.
    public void getTotalBirthsRankedHigher(int year, String name, String gender) {
    
        FileResource fr = new FileResource("yob"+year+".csv");
        CSVParser q1 = fr.getCSVParser(false);
        int untilThisRank = getRank2(q1, name, gender);
        int counter = 1;
        int AccuBirths = 0;
        CSVParser q2 = fr.getCSVParser(false);
        
        for (CSVRecord record: q2) {
            int NumberOfBirths = Integer.parseInt(record.get(2));
            String currGender = record.get(1);
            if (currGender.equals(gender)) {
                    if (counter<untilThisRank) {
                        AccuBirths = AccuBirths + NumberOfBirths;
                        counter = counter + 1;   
                    }   
            }
        }
        System.out.println(AccuBirths);
    }
    
    
    
    public void test() {
    }
    
    
}
