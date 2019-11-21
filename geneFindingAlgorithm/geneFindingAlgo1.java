/*
 * @author (weizheng) 
 * @version (1)
 */

/*A DNA Sequence is made up a many codons i.e. 3 character strings.
Within a DNA Sequence, we can find genes. They too, are made up of codons, and they can be identified by a start codon and a end codon.
There are many genes within a DNA sequence.
For the below code, we will be finding the genes which has a start codon of ATG, and a end codon of either TAA,TAG, or TGA.
With a given DNA sequence, we will be able to print all the genes within, and also count the number of them.*/

/*edu.duke.* is a library created by Duke university. http://www.dukelearntoprogram.com/course2/doc/javadoc/edu/duke/package-summary.html
*Essential classes include:
*DirectoryResource, which allows the user to choose one or more files from a directory.                                                        
*FileResource, which allows access to its content one word or one line at a time. 
*StorageResource, which allows String objects to be stored one at a time.
*URLResource, which allows the content of a webpage to be accessed one line or one word at a time.
*/


public class Part3 {
    // Finds the index of a specific Codon
    public int findStopCodon(String dna, int startAt, String stopCodon) {
        //ATG is the start codon.
        int nearestATG = dna.indexOf("ATG",startAt);
        int currIndex = dna.indexOf(stopCodon,nearestATG);
        // currIndex f
        while (currIndex != -1) {
            int checkLength = currIndex - (nearestATG + 3);
            if ((checkLength) % 3 == 0) {
                //As a gene is made up of codons, the sequence has to be divisible by 3. 
                return currIndex;
            } else {
                //If the currIndex is not a valid endCodon, we proceed to find the next one,until we reach the end of the dna sequence.
               currIndex = dna.indexOf(stopCodon,currIndex+3);
            }
        }
        //When we can't find a the specified end codon, we give it the max value for the min function we are going to run next to find the shortest valid gene among the end codons.
        return dna.length();
    }
    
    //Among the 3 end codons, the end codon which forms the shortest gene sequence is the valid gene.
    //The below function prints the valid gene.
    public String findGene(String dna, int s) {
        int startATG = dna.indexOf("ATG",s);
        if (startATG == -1) {
            return "";
        }
        
        int taaIndex = findStopCodon(dna,startATG,"TAA");
        int tagIndex = findStopCodon(dna,startATG,"TAG");
        int tgaIndex = findStopCodon(dna,startATG,"TGA");
        int minIndex = Math.min(taaIndex,Math.min(tagIndex,tgaIndex));
        
        if (minIndex > dna.length()) {
            return "No end codon found";
        } else {
            return dna.substring(startATG,minIndex+3);
        }
        
    }
    
    public void printAllGenes(String dna) {
        int start = 0;
        while (!findGene(dna,start).isEmpty()) {
            System.out.println(findGene(dna,start));
            start = dna.indexOf(findGene(dna,start),start) + findGene(dna,start).length();  
        }  
    }
    
    public int countGenes(String dna) {
        int start = 0;
        int count = 0;
        while (!findGene(dna,start).isEmpty()) {
            count = count + 1;
            start = dna.indexOf(findGene(dna,start),start) + findGene(dna,start).length();  
        }
        return count;
    }
    
    //To test the above code
    public void testcase() {
        int test1 = countGenes("ATGXXXTAAXATGXXXTAGXATGXXXTGAXATGXXXTAAX"); 
        System.out.println(test1);
        
    }
}
