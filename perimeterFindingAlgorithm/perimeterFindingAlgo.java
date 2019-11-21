import edu.duke.*;
import java.io.File;

public class PerimeterAssignmentRunner {
    public double getPerimeter (Shape s) {
        // Start with totalPerim = 0
        double totalPerim = 0.0;
        // Start wth prevPt = the last point 
        Point prevPt = s.getLastPoint();
        // For each point currPt in the shape,
        for (Point currPt : s.getPoints()) {
            // Find distance from prevPt point to currPt 
            double currDist = prevPt.distance(currPt);
            // Update totalPerim by currDist
            totalPerim = totalPerim + currDist;
            // Update prevPt to be currPt
            prevPt = currPt;
        }
        // totalPerim is the answer
        return totalPerim;
    }

    public int getNumPoints (Shape s) {
        int count = 0;
        for (Point currPt : s.getPoints()) {
            count = count + 1;
        }
        return count;
    }

    public double getAverageLength(Shape s) {
        double avg = getPerimeter(s) / getNumPoints(s);
        return avg;
    }

    public double getLargestSide(Shape s) {
        double max = 0.0;
        Point prevPt = s.getLastPoint();
        for (Point currPt : s.getPoints()) {
            double currDist = prevPt.distance(currPt);
            if (currDist > max) { 
                max = currDist;
            }
        prevPt = currPt;
        }
        return max;
    }
    
    public double getLargestX(Shape s) {
        double maxX=0.0;
        Point prevPt = s.getLastPoint();
        for (Point currPt : s.getPoints()) {
            if (currPt.getX() > prevPt.getX()) { 
                maxX = currPt.getX();
            }
        prevPt = currPt; 
       } 
       return maxX;
    }

    public double getLargestPerimeterMultipleFiles() {
        DirectoryResource dr = new DirectoryResource();
        double maxy = 0.0;
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            Shape s = new Shape(fr);
            double p = getPerimeter(s);
                if (p>maxy) {
                    maxy = p;
                }
        }
        return maxy;
    }

    public String getFileWithLargestPerimeter() {
        DirectoryResource dr = new DirectoryResource();
        File temp = null; 
        double largestp = getLargestPerimeterMultipleFiles();
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            Shape s = new Shape(fr);
            double p = getPerimeter(s);
                if (p == largestp) {
                       temp = f;
                }
        }
        return temp.getName();
    }

    public void testPerimeter () {
        FileResource fr = new FileResource();
        Shape s = new Shape(fr);
        double length = getPerimeter(s);
        System.out.println("perimeter = " + length);
        int points = getNumPoints(s);
        System.out.println("NumPoints = " + points);
        double average = getAverageLength(s);
        System.out.println("Average = " + average);
        double max = getLargestSide(s);
        System.out.println("Largest Side = " + max);
        double maxX = getLargestX(s);
        System.out.println("Largest X = " + maxX);
    }
    
    public void testPerimeterMultipleFiles() {
        double maxyy = getLargestPerimeterMultipleFiles();
        System.out.println("Largest Perimeter Multiple Files= " + maxyy);
    }

    public void testFileWithLargestPerimeter() {
        String nameFile = getFileWithLargestPerimeter();
        System.out.println("Name of file= " + nameFile);
    }

    // This method creates a triangle that you can use to test your other methods
    public void triangle(){
        Shape triangle = new Shape();
        triangle.addPoint(new Point(0,0));
        triangle.addPoint(new Point(6,0));
        triangle.addPoint(new Point(3,6));
        for (Point p : triangle.getPoints()){
            System.out.println(p);
        }
        double peri = getPerimeter(triangle);
        System.out.println("perimeter = "+peri);
    }

    // This method prints names of all files in a chosen folder that you can use to test your other methods
    public void printFileNames() {
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            System.out.println(f);
        }
    }

    public static void main (String[] args) {
        PerimeterAssignmentRunner pr = new PerimeterAssignmentRunner();
        pr.testPerimeterMultipleFiles();
        pr.testFileWithLargestPerimeter();
    }
}