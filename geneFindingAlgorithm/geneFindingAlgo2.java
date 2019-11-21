/**
 * @author (weizheng) 
 * @version (findGene)
 */

/*edu.duke.* is a library created by Duke university. http://www.dukelearntoprogram.com/course2/doc/javadoc/edu/duke/package-summary.html
*Essential classes include:
*DirectoryResource, which allows the user to choose one or more files from a directory.                                                        
*FileResource, which allows access to its content one word or one line at a time. 
*StorageResource, which allows String objects to be stored one at a time.
*URLResource, which allows the content of a webpage to be accessed one line or one word at a time.
*/

import edu.duke.*;
import java.io.File;

public class FindGeneV2 {
    public int findStopCodon(String dna, int startIndex, String stopCodon) {
        int currIndex = dna.indexOf(stopCodon,startIndex+3);
        while (currIndex != -1) {
            int diff = currIndex - startIndex; 
            if (diff % 3 == 0) {
                return currIndex;
            } else {
               currIndex = dna.indexOf(stopCodon,currIndex+1);
            }
        } 
        return -1;
    }
    
    public String findGene(String dna,int s) {
        int startIndex = dna.indexOf("ATG",s);
        if (startIndex == -1) {
            return "";
        }
        int taaIndex = findStopCodon(dna,startIndex,"TAA");
        int tagIndex = findStopCodon(dna,startIndex,"TAG");
        int tgaIndex = findStopCodon(dna,startIndex,"TGA");
        int minIndex=0;

        // Below, we compare two codon at a time, using if else statements to find the shortest sequence which ends with TAA,TGA,or TAG.
        // We first compare TAA and TGA. The output, minIndex will return the valid index or return -1 if both codons can't be found.
        // Using the minIndex, we then compare it with TAG. If TAA is also -1, then the final minIndex will return -1.
        // If final minIndex is -1, we return empty String. 
        // Otherwise we would have found a valid gene, which we return as output. 
        
        if (taaIndex == -1 || (tgaIndex != -1 && tgaIndex < taaIndex)){
            minIndex = tgaIndex;
        } else {
            minIndex = taaIndex;
        }
        if(minIndex == -1 || (tagIndex != -1 && tagIndex < minIndex)) {
            minIndex = tagIndex;
        }
        if (minIndex == -1) {
            return "";
        } else {
            return dna.substring(startIndex,minIndex+3);
        }   
    }
    
    
    // Genes are added string by string into StorageResources.
    public StorageResource getAllGenes(String dna) {
        StorageResource store = new StorageResource();
        int start = 0;
        while (true) {
            if (findGene(dna,start).isEmpty()) {
                    break;
            }
            store.add(findGene(dna,start));
            start = dna.indexOf(findGene(dna,start),start) + findGene(dna,start).length(); 
        }
        return store;
    }
    
    // With the genes in StorageResources, we run processGene which does a basic summmary statistics of the genes.
    public void processGene(StorageResource storage) {
        int countSixty = 0;
        int countCG = 0;
        int countTT = 0;
        String storeMax = "";
        
        for(String s: storage.data()) {
            countTT = countTT + 1;
            if (s.length()>60) {
                countSixty = countSixty + 1;   
            }
            if (cgRatio(s)> 0.35 ) {
                countCG = countCG + 1;   
            }
            //The below code will find the longest genetic sequence.
            if (s.length()> storeMax.length()) {
                storeMax = s;
            }
        }
        System.out.println(countCG);
        System.out.println(storeMax.length()); 
    }

    //Ratio of Cs and Gs in a DNA string.
    public double cgRatio(String dna) {
        double output = (float)(howMany("C",dna) + howMany("G",dna))/ dna.length();
        return output;
    }
    
    //How many codon CTG are there
    public int countCTG(String dna) {
        return howMany("CTG",dna);
    }

    public void printAllStore(StorageResource store) {
        for(String s: store.data()) {
            System.out.println(s);
        }
    }
    
    //Final test
    public void testProcessGenes() {   
        URLResource urllink = new URLResource("https://users.cs.duke.edu/~rodger/GRch38dnapart.fa");
        String linktoString = urllink.asString();
        StorageResource genesInStorage = getAllGenes(linktoString.toUpperCase()) ; 
        processGene(genesInStorage);
        System.out.println("ctgctg =" + countCTG(linktoString));
    }
    
    //Count the number of stringa in stringb.
    public int howMany(String stringa, String stringb) {
      int currIndex = 0;
      int count = 0; 
      while (stringb.indexOf(stringa,currIndex) != -1) {
          count = count+1;
          currIndex = stringb.indexOf(stringa,currIndex) + stringa.length();
     }
     return count;
    }
    
    
}
