import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
   public static void main(String[] args) {
      Result result = JUnitCore.runClasses(CoordinateTests.class);
      int fails = printResult("COORDINATE TESTS", result);

      result = JUnitCore.runClasses(MyLineTests.class);
      fails = printResult("MyLine TESTS", result);

      System.out.println();
      System.out.println();
      if(fails == 0){
         System.out.println("All tests ended successful.");
      }else{
         System.out.println("Number of failed tests: " + fails);
      }    
   }

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
