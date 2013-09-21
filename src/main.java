import java.text.DecimalFormat;

/**
 * Created with IntelliJ IDEA.
 * User: Bill
 * Date: 21/09/13
 * Time: 1:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class main
{

    public static void main(String[] args)
    {
        Program pro = new Program();
        pro.begin();
    }


}

class Program
{
    public void begin()
    {
        mainMenu();
    }

    public void mainMenu()
    {
        int menuChoice = 0;
        System.out.println("Welcome to Bill's calculator of dreams");
        System.out.println("We support negative numbers, exponents and spaces");

        while(menuChoice != 3 )
        {
            System.out.println("Menu options\n" +
                    "1) Enter equation\n" +
                    "2) Tutorial\n" +
                    "3) Exit\n" +
                    "Enter a choice>");
            menuChoice = ConsoleInput.readInt();
            switch (menuChoice)
            {
                case 1:
                    System.out.println("Enter the equation");
                    String equation = ConsoleInput.readLine();
                    DecimalFormat format = new DecimalFormat("##.0000");
                    System.out.println(format.format(EquationSolver.solve(equation)));
                    break;
                case 2:
                    System.out.println("Sorry, no tutorial");
                    break;
            }
        }

    }
}