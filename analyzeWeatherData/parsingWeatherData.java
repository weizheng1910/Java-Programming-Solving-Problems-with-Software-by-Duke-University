
/**
 * Parsing weather data. 
 * Using the CSV parser library (org.apache.commons.csv.*), we are able to analyse data in CSV files with Java.
 * We access each field using the .get method.
 * 
 * @author (weizheng) 
 * @version (a version number or a date)
 */
import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Part1 {
    public String HottestHourInFile(CSVParser parser) {
        String MaxTemp = null;
        for (CSVRecord record : parser) {
            String CurrentTemp = record.get("TemperatureF");
            if (MaxTemp == null) {
                MaxTemp = CurrentTemp;
            } else {
                MaxTemp = HotterOfTheTwo(CurrentTemp,MaxTemp);
            }
        }
        return MaxTemp;  
    }
    
    public void FileWithHottestTemp() {
        String MaxTemp = null;
        DirectoryResource dr = new DirectoryResource();
        for (File f: dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            CSVParser q1 = fr.getCSVParser();
            String CurrentTemp = HottestHourInFile(q1);
            System.out.println(MaxTemp);
            if (MaxTemp == null) {
                MaxTemp = CurrentTemp;
            } 
            else {
                MaxTemp = HotterOfTheTwo(CurrentTemp,MaxTemp);  
            }
        }
    }
    
    
    public String HotterOfTheTwo(String candidate, String current) {
        Double DblCandidate = Double.parseDouble(candidate);
        Double DblCurrent = Double.parseDouble(current);
        if ( DblCandidate > DblCurrent) {
            return candidate;
        } else {
            return current;
        }
    }
    
    public String ColderOfTheTwo(String candidate, String current) {
        Double DblCandidate = Double.parseDouble(candidate);
        Double DblCurrent = Double.parseDouble(current);
        if ( DblCandidate < DblCurrent) {
            return candidate;
        } else {
            return current;
        }
    }
    
    
    public String ColdestHourInFile(CSVParser parser) {
        String MinTemp = null;
        for (CSVRecord record : parser) {
            String CurrentTemp = record.get("TemperatureF");
            if (MinTemp == null) {
                MinTemp = CurrentTemp;
            } else {
                MinTemp = ColderOfTheTwo(CurrentTemp,MinTemp);  
            }
        }
        return MinTemp;
    }
    
    
    public void FileWithColdestTemp() {
        String MinTemp = null;
        String FileName = null;
        DirectoryResource dr = new DirectoryResource();
        for (File f: dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            CSVParser q1 = fr.getCSVParser();
            String CurrentTemp = ColdestHourInFile(q1);

            if (MinTemp == null) {
                MinTemp = CurrentTemp;
                FileName = f.getName();
            }  else {
                MinTemp = ColderOfTheTwo(CurrentTemp,MinTemp);
                if (MinTemp == CurrentTemp) {
                    FileName = f.getName();
                }
            } 
        }
        System.out.println("Coldest day was in file:" + FileName);
        System.out.println("Coldest temperature on that day was:" + MinTemp);
        PrintAllTemp(FileName);
    }
    
    public void PrintAllTemp(String FName) {   
        FileResource fr = new FileResource(FName);
        CSVParser q2 = fr.getCSVParser();
        for (CSVRecord record : q2) {
            String DateUtC = record.get("DateUTC");
            String Temp = record.get("TemperatureF");
            System.out.println(DateUtC +" =>  "+ Temp + "K");
     
        }
    }
    
    public void lowestHumidityInFile(CSVParser parser) {
        String MinHum = null; 
        String xx = null;
        for (CSVRecord record : parser) {
            String CurrHum = record.get("Humidity");
            
            if (CurrHum.equals("N/A") ) {
                MinHum = MinHum;
            } else if (MinHum == null) {
                MinHum = CurrHum;
                xx = record.get("DateUTC");
            } else {
                MinHum = ColderOfTheTwo(CurrHum,MinHum);
                if (CurrHum == MinHum) {
                    xx = record.get("DateUTC");
                }
            } 
        }
        System.out.println("Lowest Humidity was "+MinHum+" at "+ xx);
    }
    
    
    
    public void averageTemperatureInFile(CSVParser parser) {
        double count = 0.0;
        double accumulatedSum = 0.0;
        for (CSVRecord record: parser) {
            double TempDbl = Double.parseDouble(record.get("TemperatureF"));
            count = count + 1;
            accumulatedSum = accumulatedSum + TempDbl;
        }
        double avg = accumulatedSum/count;
        System.out.println("Average temperature in file is "+avg);
    }
    
    public void averageTemperatureWithHighHumidityInFile(CSVParser parser,int value) {
        double count = 0.0;
        double accumulatedSum = 0.0;
        
        for (CSVRecord record: parser) {
            String Humidity = record.get("Humidity");
            int IntHumidity = Integer.parseInt(Humidity);
            double TempDbl = Double.parseDouble(record.get("TemperatureF"));
            if (IntHumidity>value) {
                count = count + 1;
                accumulatedSum = accumulatedSum + TempDbl;
            }
        }
        
        if (count == 0) {
            System.out.println("No temperature of that humidity!");
        }
        
        else {
            double avg = accumulatedSum/count;
            System.out.println("Average Temp =" + avg);
        }
    }
    
    

    
    public void test() {
       //FileResource fr = new FileResource();
       //CSVParser q1 = fr.getCSVParser();
       //averageTemperatureWithHighHumidityInFile(q1,79);
       //lowestHumidityInManyFiles();
    }
    
  }
