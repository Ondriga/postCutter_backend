/*
 * Tests for the backend of Bachelor thesis.
 * TestRunner class
 * 
 * (C) Patrik Ondriga (xondri08)
 */

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Start all tests for backhand of postCutter and print result to the standard output.
 */
public class TestRunner {
   public static void main(String[] args) {
      Result result = JUnitCore.runClasses(CoordinateTests.class);
      int fails = printResult("COORDINATE TESTS", result);

      result = JUnitCore.runClasses(MyLineTests.class);
      fails += printResult("MyLine TESTS", result);

      result = JUnitCore.runClasses(LineHandlerTests.class);
      fails += printResult("LineHandler TESTS", result);

      result = JUnitCore.runClasses(MyRectangleTests.class);
      fails += printResult("MyRectangle TESTS", result);

      result = JUnitCore.runClasses(RectangleHandlerTests.class);
      fails += printResult("RectangleHandler TESTS", result);

      result = JUnitCore.runClasses(CutterTests.class);
      fails += printResult("Cutter TESTS", result);

      System.out.println();
      System.out.println();
      System.out.println("----------------------------");
      if(fails == 0){
         System.out.println("All tests ended successful.");
      }else{
         System.out.println("Number of failed tests: " + fails);
      }
      System.out.println("----------------------------");
   }

   /**
    * Print result to the standard output.
    * @param testName name of tests.
    * @param result of tests.
    * @return number of failed tests.
    */
   private static int printResult(String testName, Result result){
      System.out.println();
      System.out.println(testName);  

      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }

      System.out.println("Result: " + result.getFailureCount() + " fails");
      return result.getFailureCount();
   }
}
