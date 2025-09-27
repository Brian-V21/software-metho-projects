import software.metho.project1.Frontend;
/* Project 1
Main class, only library classes we can have are Scanner,
StringTokenizer, Calendar, DecimalFormat
 Handle invalid cmds, Cant use ArrayList lib class,
 Must write java.util.<whatever_we_specifically_need>, Cant be General
 System.in/system.out only in user interface, testbed main(), & print()
 @Override when needed (ToString, equals, compareTo)
 */

public class RunProject1 {
    public static void main(String[] args) {
        new Frontend().run();
    }
}