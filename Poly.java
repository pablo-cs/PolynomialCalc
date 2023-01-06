import java.util.ArrayList;
import java.util.*;

/**
 * Class to represent a polynomial, e.g. 3.5x^4 + 3x^2 - 4.
 * 
 * Polynomials can be added, subtracted, multiplied, and divided.
 * 
 * This class is a skeleton. You need to provide implementations
 * for the methods here defined. Feel free however, to add additional
 * methods as you see fit.
 *
 * @author Pablo Crisostomo Suarez
 */
public class Poly{

    
    List<Term> polyTerms = new ArrayList();
    public class Term implements Comparable<Term>{
        double coef;
        int exp;
        public Term(double coef, int exp){
            this.coef = coef;
            this.exp = exp;
        }

        /**
         * Compares the Term on which the method is called (the "this" Term), 
         * to the object passed in as argument, o. o is to be considered equal to the "this"
         * Term if they contain the same coefficient and exponent fields
         * @param o the object to be compared against the Term the method is called on.
         * @return true if o is a Term equivalent to the one the method was called on,
         * and false otherwise.
         */

        public boolean equals(Object o){
            if(o instanceof Term){
                Term comp = (Term) o;
                return comp.coef == this.coef && comp.exp == this.exp;
            }
            return false;
        }

        /** 
         * Returns a textual representation of the Term the method is called on.
         */
        public String toString(){
            String s = "";
            boolean isNegative = coef < 0;
            boolean isOneCoef = coef == 1 ||coef == -1;
            boolean isOneExp = exp == 1;
            boolean isZeroExp = exp == 0;
            boolean isZeroCoef = coef == 0;
            if(isZeroCoef){
                return "0.0";
            }
            if(!isOneCoef || (isOneCoef && isZeroExp)){
                s+=coef;
            }else{
                if(isNegative){
                    s+="-";
                }
            }
            if(!isZeroExp){
                s+= "x";
                if(!isOneExp){
                    s+="^" + exp;
                }
            }

            return s;
        }

        public Term copy(){
            return new Term(coef,exp);
        }

        public int compareTo(Term t){
            return exp - t.exp;
        }
    }
    /**
     * Creates a new polynomial containing a single term with the coefficient
     * and exponent passed in as arguments. E.g. when called with coefficient
     * 3.5 and exponent 2, it should create a polynomial 3.5x^2.
     * 
     * You can create additional constructors if you'd like, but it's 
     * imperative that this one exists.
     * 
     * @param coef the single term's coefficient.
     * @param exp the single term's exponent.
     * @return the polynomial created.
     */
    public Poly(double coef, int exp){
        this.add(new Term(coef,exp));
    }

    public Poly(){

    }

    /**
     * Takes a Term object and returns where it should be placed in the list of p's terms, in order to maintain
     * a highest exponent to lowest exponent order
     * @param p A Poly object containing a list of Terms
     * @param t A Term object to be added to p's list of Terms
     * @return an int representing the appropriate index that the Term t should be added in the polyTerms list
     */
    public int insertPosition(Poly p, Term t){
        int expComp = t.exp;
        for(int i = 0; i < p.polyTerms.size();i++){
            if(expComp > p.polyTerms.get(i).exp){
                return i;
            }
        }
        return -1;
    }
    
    /**
     * Creates and returns a Poly object with the same items in it's polyTerms list
     * @return a polynomial containing the same elements as the one this method was called upon
     */
    private Poly copy(){
        Poly p = new Poly();
        for(int i = 0; i < this.polyTerms.size(); i++){
            double tCoef = this.polyTerms.get(i).coef;
            int tExp = this.polyTerms.get(i).exp;
            p.polyTerms.add(new Term(tCoef, tExp));
        }
        return p;
    }
    
    /**
     * Adds the polynomial passed in as an argument, p, to the polynomial on which the 
     * method is called on (the "this" polynomial), and returns a new polynomial
     * with the result. I.e., it returns "this + p".
     * 
     * @param p the polynomial to add onto the polynomial on which the method is called on.
     * @return a polynomial representing the result of the addition.
     */
    public Poly add(Poly p){
        Poly o = this.copy();

        for(int i = 0; i < p.polyTerms.size(); i++){
            o.add(p.polyTerms.get(i));
        }
        return o;
    }

    /**
     * Adds the Term passed in as an argument, t, to the polyTerms list
     * If Term t has the same exponent as any Term in polyTerms, it adds the coefficient of t to
     * the coefficient of that Term in the list. If resulting coefficient is 0, it removes that
     * Term from the list. If it has no equivalent exponent, and the coefficient is not 0, it
     * adds the Term to the polyTerms
     * @param p the Term to add onto the polynomial on which the method is called on.
     */
    private void add(Term t){
        double termCoef = t.coef;
        
        for(int i = 0; i < this.polyTerms.size(); i++){
            
            if(t.exp == this.polyTerms.get(i).exp){
                polyTerms.get(i).coef += termCoef;

                if(polyTerms.get(i).coef == 0){
                    polyTerms.remove(i);
                }
                
                return;
            }
        }

        if(t.coef != 0 ){
            int pos = insertPosition(this, t);
            if(pos != -1){
                polyTerms.add(pos, t);
            }else{
                polyTerms.add(t);
            }
        }
    }

    /**
     * Subtracts the polynomial passed in as an argument, p, from the polynomial on which the 
     * method is called on (the "this" polynomial), and returns a new polynomial
     * with the result. I.e., it returns "this - p".
     * 
     * @param p the polynomial to be subtracted from the polynomial on which the method is called on.
     * @return a polynomial representing the result of the subtraction.
     */
    public Poly subtract(Poly p){
        //compares every element of the polynomials to each other
        Poly o = this.copy();
        for(int i = 0; i < p.polyTerms.size(); i++){
            Term t = p.polyTerms.get(i);
            t.coef = t.coef * - 1;
            o.add(t);
        }
        return o;
    }

    /**
     * Multiplies the polynomial passed in as an argument, p, by the polynomial on which the 
     * method is called on (the "this" polynomial), and returns a new polynomial
     * with the result. I.e., it returns "this * p".
     * 
     * @param p the polynomial to be multiplied by the polynomial on which the method is called on.
     * @return a polynomial representing the result of the multiplication.
     */
    public Poly multiply(Poly p){
        Poly prod = new Poly();
        Poly o = this.copy();

        for(int i = 0; i < o.polyTerms.size(); i++){
            Term l = o.polyTerms.get(i);
            for(int j = 0; j < p.polyTerms.size();j++){
                Term m = p.polyTerms.get(j);
                Term t = new Term(l.coef * m.coef,l.exp + m.exp);
                prod.add(t);
            }
        }
        return prod;
    }

    /**
     * Divides the polynomial on which the method is called on (the "this" polynomial), by
     * the polynomial passed in as an argument, p, and returns a new polynomial
     * with the resulting quotient. I.e., it returns "this / p".
     * 
     * The division should be performed according to the polynomial long division algorithm
     * ( https://en.wikipedia.org/wiki/Polynomial_long_division ).
     * 
     * Polynomial division may end with a non-zero remainder, which means the polynomials are
     * indivisible. In this case the method should return null. A division by zero should also
     * yield a null return value.
     * 
     * @param p the polynomial to be multiplied by the polynomial on which the method is called on.
     * @return a polynomial representing the quotient of the division, or null if they're indivisible.
     */    
    public Poly divide(Poly p){

        //Return nul if trying to divide by an empty/zero Polynomial
        if(p.polyTerms.size() == 0){
            return null;
        }

        //Return a zero polynomial if trying to divide zero by anything
        if(polyTerms.size() == 0){
            return new Poly(0,0);
        }
        
        Poly quot = new Poly();
        Poly r = this.copy();
        Poly n = this.copy();

        while (r.polyTerms.size() != 0 && r.polyTerms.get(0).exp >= p.polyTerms.get(0).exp){
            double tCoef = r.polyTerms.get(0).coef / p.polyTerms.get(0).coef;
            int tExp = r.polyTerms.get(0).exp - p.polyTerms.get(0).exp;
            Poly t = new Poly(tCoef, tExp);
            quot = quot.add(t);
            r = r.subtract(t.multiply(p));
            n = (p.multiply(quot)).add(r);
        }

        if(r.polyTerms.size() == 0){
            return quot;
        }

        return null;
    }


    /**
     * Compares the polynomial on which the method is called (the "this" polynomial), 
     * to the object passed in as argument, o. o is to be considered equal to the "this"
     * polynomial if they both represent equivalent polynomials.
     * 
     * E.g., "3.0x^4 + 0.0x^2 + 5.0" and "3.0x^4 + 5.0" should be considered equivalent.
     * "3.0x^4 + 5.0" and "3.0x^4 + 3.0" should not.
     * 
     * @param o the object to be compared against the polynomial the method is called on.
     * @return true if o is a polynomial equivalent to the one the method was called on,
     * and false otherwise.
     */
    public boolean equals(Object o){

        if(o instanceof Poly){
            Poly comp = (Poly) o;
            List<Term> t1 = this.polyTerms;
            List<Term> t2 = comp.polyTerms;

            if(t2.size()!= t1.size()){
                return false;
            }
            for(int i = 0; i < t1.size(); i++){
                Term te1 = t1.get(i);
                Term te2 = t2.get(i);
                if(!(te1.equals(te2))){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Returns a textual representation of the polynomial the method is called on.
     * The textual representation should be a sum of monomials, with each monomial 
     * being defined by a double coefficient, the letters "x^", and an integer exponent.
     * Exceptions to this rule: coefficients of 1.0 should be omitted, as should "^1",
     * and "x^0".
     * 
     * Terms should be listed in decreasing-exponent order. Terms with zero-coefficient
     * should be omitted. Each exponent can only appear once. 
     * 
     * Rules for separating terms, applicable to all terms other that the largest exponent one:
     *   - Terms with positive coefficients should be preceeded by " + ".
     *   - Terms with negative coefficients should be preceeded by " - ".
     * 
     * Rules for the highest exponent term (i.e., the first):
     *   - If the coefficient is negative it should be preceeded by "-". E.g. "-3.0x^5".
     *   - If the coefficient is positive it should not preceeded by anything. E.g. "3.0x^5".
     * 
     * The zero/empty polynomial should be represented as "0.0".
     * 
     * Examples of valid representations: 
     *   - "2.0x^2 + 3.0"
     *   - "3.5x + 3.0"
     *   - "4.0"
     *   - "-2.0x"
     *   - "4.0x - 3.0"
     *   - "0.0"
     *   
     * Examples of invalid representations: 
     *   - "+2.0x^2+3.0x^0"
     *   - "3.5x -3.0"
     *   - "- 4.0 + x"
     *   - "-4.0 + x^7"
     *   - ""
     * 
     * @return a textual representation of the polynomial the method was called on.
     */
    public String toString(){
        String s = "";
        String tStr = "";
        boolean isEmptyOrZero = polyTerms.size() == 0;
        if(isEmptyOrZero){
            return "0.0";    
        }

        for(int i = 0; i < polyTerms.size();i++){
            Term t = polyTerms.get(i);
            tStr = t.toString();
            if(i != 0){
                if(t.coef < 0){
                    s+= tStr.substring(0,1) + " " + tStr.substring(1);
                }else if (t.coef !=0){
                    s+= "+ " + tStr;
                } 
            }else{
                s+= tStr;
            }
            if(i!= polyTerms.size()-1){
                s+= " ";
            }
        }

        return s;
    }

    /**
     * Creates a Poly object based on user input
     * Runs until the user provides a valid input which does not contain any letters and follows the appropriate double | int format
     * Returns null if the user inputs "quit"
     * @returns a polynomial comprised of user-input coefficients and exponents
     */
    public static Poly userCreatePoly(String n){
        Poly o = new Poly();
        boolean valid = false;
        //Runs until the user inputs a valid String
        while(!valid){
            try{
                System.out.print("Polynomial " + n +": ");
                Scanner keybScanner = new Scanner(System.in);
                String line = keybScanner.nextLine();

                if(line.equals("")){
                    throw new NoSuchElementException();
                }
                //Returns a null object if user quits
                if(line.toLowerCase().equals("quit")){
                    return null;
                }

                Scanner lineScanner = new Scanner(line);
                //Iterates over line, adding coefficient and exponent pairs as Poly objects to o
                while(lineScanner.hasNext()){
                    double coefU = lineScanner.nextDouble();
                    int expU = lineScanner.nextInt();
                    o = o.add(new Poly(coefU, expU));
                }

                //Sets valid to true and ends loop
                valid = true;
            }catch (NoSuchElementException e){
                System.out.println("Invalid polynomial input! Please refer to the instructions.");
                o = new Poly();
            }
        }
        return o;
    }

    /**
     * Prompts user to enter an appropriate mathematical operator or to quit the program
     * Returns the operator once given the user has input +, -, *, /, or quit
     * @returns a String representing the operator input by the user
     */
    public static String userOperation(){
        ArrayList<String> validOperators = new ArrayList<>(Arrays.asList("+", "-", "*", "/", "quit"));
        Scanner keybScanner = new Scanner(System.in);
        String op = "";
        while(!validOperators.contains(op)){
            System.out.print("Operation (+, -, *, /): ");
            op = keybScanner.nextLine();
            if(!validOperators.contains(op)){
                System.out.println("Invalid operation. Only listed ones supported.");
            }
        }
        return op;
    }

    /**
     * Returns a String representing the calculation of performing an operation
     * on two polynomials.
     * @param op A String representing a mathematical operator
     * @param a A Polynomial object
     * @param b A Polynomial object
     * @return A String with the text representation of both polynomials, the operator
     * and the result
     */
    public static String userCalculation(String op, Poly a, Poly b){
        switch(op){
            case "+":
                return a + " + " + b + " = " + a.add(b);
            case "-":
                return a + " - " + b + " = " + a.subtract(b);
            case "*":
                return a + " * " + b + " = " + a.multiply(b);
            case "/":
                return a + " / " + b + " = " + a.divide(b);
            default:
                return "";
        }
    }

    /**
     * Provides the user interface for a user to be able to create two polynomials(Poly objects),
     * and perform a given operation on them, doing so until the user decides to quit the program
     */
    public static void main(String[] args){
        String op = "";
        Poly a = new Poly();
        Poly b = new Poly();
        int computationCount = 0;
        boolean quit = false;
        
        System.out.println("/*** Welcome to Pablo Crisostomo Suarez's CMPU-102 polynomial calculator! ***/");
        System.out.println("Instructions:");
        System.out.println("- Polynomials are specified using space-separated pairs of coefficient and exponent.");
        System.out.println("E.g., \"2.5x^2 - 1\" should be input as \"2.5 2 -1 0\".");
        System.out.println("- \"quit\" can be used at any time to exit the program.");

        
        while(!quit){
            //Adds 1 to the computation count
            computationCount++;

            System.out.println("--- Computation #" + computationCount +" ---");
            
            //Creates first polynomial
            a = userCreatePoly("a");

            //Quits program
            if(a == null){
                quit = true;
            }else{

                System.out.println("Read a: " + a);
                op = userOperation();

                //Quits program
                if(op.toLowerCase().equals("quit")){
                    quit = true;
                }else{

                    //Creates second polynomial
                    b = userCreatePoly("b");

                    //Quits program
                    if(b == null){
                        quit = true;
                    }else{
                        System.out.println("Read b: " + b);

                        System.out.println(userCalculation(op, a, b));

                        //Resets a, b, and op
                        a = new Poly();
                        b = new Poly();
                        op = "";
                        System.out.println();
                    }
                }
            }

        }
        System.out.println();
        System.out.println("Thank you and goodbye!");
    }
}

