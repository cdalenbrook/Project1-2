package Setup;

import java.util.ArrayList;

public class Calculator {
    public static double findSecondDerivative(ArrayList<char[]> function, double X, double Y, Respect respect){
        String s = new String();
        for(char[] data: function){
            s = s + new String(data);
        }
        ArrayList<ArrayList<MathematicalExpression>> array = splitForDerivation(s.toCharArray());
        array = removeConstant(array, respect);
        array = splitForDerivation(solve(array, respect));
        array = removeConstant(array, respect);
        ArrayList<char[]> ff = new ArrayList<>();
        ff.add(solve(array, respect));
        
        return solve(ff, 0, X, Y).value;
    }
    public static double findFirstDerivative(ArrayList<char[]> function, double X, double Y, Respect respect){
        String s = new String();
        for(char[] data: function){
            s = s + new String(data);
        }
        ArrayList<ArrayList<MathematicalExpression>> array = splitForDerivation(s.toCharArray());
        array = removeConstant(array, respect);
        ArrayList<char[]> ff = new ArrayList<>();
        ff.add(solve(array, respect));
        
        return solve(ff, 0, X, Y).value;
    }
    public static double findZ(ArrayList<char[]> function, double X, double Y){
        return solve(function, 0, X, Y).value;
    }
    private static char[] solve(ArrayList<ArrayList<MathematicalExpression>> function, Respect respect){
        String newFunction = new String();
        for(int x = 0; x < function.size(); x++){
            function.set(x, derivate(function.get(x), respect));
            for(int i = 0; i < function.get(x).size(); i++){
                if(i == 0){
                    newFunction = newFunction + "(";
                }
                newFunction = newFunction + new String(function.get(x).get(i).Value);
                if(i == function.get(x).size() - 1){
                    newFunction = newFunction + ")";
                }
                newFunction = newFunction  + new String(new char[]{function.get(x).get(i).operator});
            }
        }
        return newFunction.toCharArray();
    }
    private static boolean checkForConstant(char[] function, Respect respect){
        for(int i = 0; i < function.length; i++){
            if(function[i] == Character.toUpperCase(respect.toString().toCharArray()[0]) || function[i] == Character.toLowerCase(respect.toString().toCharArray()[0])){
                return false;
            }
        }
        return true;
    }
    
    private static ArrayList<ArrayList<MathematicalExpression>> removeConstant(ArrayList<ArrayList<MathematicalExpression>> function, Respect respect){
        for(int i = 0; i < function.size(); i++){
            ArrayList<MathematicalExpression> m = function.get(i);
            boolean constant = true;
            for(int x = 0; x < m.size(); x++){
                MathematicalExpression math = m.get(x);
                if(!checkForConstant(math.Value, respect)){
                    constant = false;
                }
            }
            if(constant){
                if(i == 0){
                    function.remove(m);
                }
                else if(i == function.size() - 1){
                    function.remove(m);
                }
                else{
                    function.get(i - 1).get(function.get(i - 1).size() - 1).operator = function.get(i).get(function.get(i).size() - 1).operator;
                    function.remove(m);
                }
            }
        }    
        return function;
    }
    private static ArrayList<MathematicalExpression> derivate(ArrayList<MathematicalExpression> list, Respect respect){
        ArrayList<MathematicalExpression> newList = new ArrayList<>();
        int size = 0;
        if(list.size() == 1){
            size = 1;
        }
        else{
            size = list.size() - 1;
        }
        for(int i = 0; i < size; i++){
            MathematicalExpression math = list.get(i);
            if(!checkForConstant(math.Value, respect)){
                if(list.size() == 1 && math.Value.length == 1){
                    newList.add(new MathematicalExpression(new char[]{'1'}, math.operator));
                }
                else{
                    switch(math.operator){
                        case '^': 
                                char[] derivated = solve(splitForDerivation(math.Value), respect);
                                char[] powerOld = list.get(i + 1).Value;
                                String s = "(" + new String(powerOld) + "-1)";
                                char[] powerNew = s.toCharArray();
                                s = "(" + new String(powerOld) + "*" + new String(derivated) + ")";
                                newList.add(new MathematicalExpression(s.toCharArray(), '*'));
                                char[] newExpression = new char[math.Value.length + powerNew.length + 3];
                                newExpression[0] = '(';
                                newExpression[newExpression.length - 1] = ')';
                                newExpression[math.Value.length + 1] = '^';
                                for(int x = 0; x < math.Value.length; x++){
                                    newExpression[x + 1] = math.Value[x];
                                }
                                for(int x = 0; x < powerNew.length; x++){
                                    newExpression[x + math.Value.length + 2] = powerNew[x];
                                }
                                newList.add(new MathematicalExpression(newExpression, list.get(i + 1).operator));
                            break;
                        case '*': 
                                char[] next = list.get(i + 1).Value;
                                String s1 = "(" + new String(solve(splitForDerivation(math.Value), respect)) + "*" + new String(next) + ")" ;
                                newList.add(new MathematicalExpression(s1.toCharArray(), '+'));
                                String s2 = "(" + new String(math.Value) + "*" + new String(solve(splitForDerivation(next), respect)) + ")" ;
                                newList.add(new MathematicalExpression(s2.toCharArray(), list.get(i + 1).operator));
                            break;
                        case '/':
                                char[] next1 = list.get(i + 1).Value;
                                String s3 = "(" + new String(solve(splitForDerivation(math.Value), respect)) + "*" + new String(next1) + ")" ;
                                String s4 = "(" + new String(math.Value) + "*" + new String(solve(splitForDerivation(next1), respect)) + ")" ;
                                String s5 = "(" + s3 + "-" + s4 + ")";
                                newList.add(new MathematicalExpression(s5.toCharArray(), '/'));
                                String s6 = "((" + new String(next1) + ")^2)";
                                newList.add(new MathematicalExpression(s6.toCharArray(), list.get(i + 1).operator));
                            break;
                        case '\u0000':
                            switch(math.Value[0]){
                                case 'c': 
                                    if(math.Value[2] == 's'){
                                        char[] inside = new char[math.Value.length - 5];
                                        for(int b = 0; b < inside.length; b++){
                                            inside[b] = math.Value[b + 4];
                                        }
                                        String s8 = "(-sin(" + new String(inside) + ")";
                                        newList.add(new MathematicalExpression(s8.toCharArray(), '*'));
                                        String s7 = "(" + new String(solve(splitForDerivation(inside), respect)) + ")";
                                        newList.add(new MathematicalExpression(s7.toCharArray(), math.operator));
                                    }
                                    else{
                                        char[] inside1 = new char[math.Value.length - 6];
                                        for(int b = 0; b < inside1.length; b++){
                                            inside1[b] = math.Value[b + 5];
                                        }
                                        String s8 = "(csc(" + new String(inside1) + ")^(2)"; 
                                        newList.add(new MathematicalExpression(s8.toCharArray(), '*'));
                                        String s9 = "(-" + new String(solve(splitForDerivation(inside1), respect)) + ")";
                                        newList.add(new MathematicalExpression(s9.toCharArray(), math.operator));
                                    }
                                    break;
                                case 's': 
                                    math.Value[0] = 'c';
                                    math.Value[1] = 'o';
                                    math.Value[2] = 's';
                                    newList.add(new MathematicalExpression(math.Value, '*'));
                                    char[] inside = new char[math.Value.length - 5];
                                    for(int b = 0; b < inside.length; b++){
                                        inside[b] = math.Value[b + 4];
                                    }
                                    String s7 = "(" + new String(solve(splitForDerivation(inside), respect)) + ")";
                                    newList.add(new MathematicalExpression(s7.toCharArray(), math.operator));
                                    break;
                                case 't': 
                                    char[] inside1 = new char[math.Value.length - 4];
                                    for(int b = 0; b < inside1.length; b++){
                                        inside1[b] = math.Value[b + 3];
                                    }
                                    String s8 = "(sec(" + new String(inside1) + ")^(2)"; 
                                    newList.add(new MathematicalExpression(s8.toCharArray(), '*'));
                                    String s9 = "(" + new String(solve(splitForDerivation(inside1), respect)) + ")";
                                    newList.add(new MathematicalExpression(s9.toCharArray(), math.operator));
                                    break;
                            }
                            break;
                    }
                }    
            }
            else{
                switch(math.operator){
                    case '\u0000':
                        newList.add(new MathematicalExpression(new char[]{'0'}));
                        break;
                    case '^':
                        char[] next = list.get(i + 1).Value;
                        String s1 = "((" + new String(math.Value) + ")^(" + new String(next) + "))" ;
                        String s2 = "(" + "ln(" + new String(math.Value) + "))";
                        newList.add(new MathematicalExpression(s1.toCharArray(), '*'));
                        newList.add(new MathematicalExpression(s2.toCharArray(), list.get(i + 1).operator));
                        break;
                    case '*': 
                            char[] next1 = list.get(i + 1).Value;
                            String s3 = "(" + new String(solve(splitForDerivation(math.Value), respect)) + "*" + new String(next1) + ")" ;
                            newList.add(new MathematicalExpression(s3.toCharArray(), '+'));
                            String s4 = "(" + new String(math.Value) + "*" + new String(solve(splitForDerivation(next1), respect)) + ")" ;
                            newList.add(new MathematicalExpression(s4.toCharArray(), list.get(i + 1).operator));
                        break;
                    case '/':
                            char[] next2 = list.get(i + 1).Value;
                            String s5 = "(" + new String(solve(splitForDerivation(math.Value), respect)) + "*" + new String(next2) + ")" ;
                            String s6 = "(" + new String(math.Value) + "*" + new String(solve(splitForDerivation(next2), respect)) + ")" ;
                            String s7 = "(" + s5 + "-" + s6 + ")";
                            newList.add(new MathematicalExpression(s7.toCharArray(), '/'));
                            String s8 = "((" + new String(next2) + ")^2)";
                            newList.add(new MathematicalExpression(s8.toCharArray(), list.get(i + 1).operator));
                        break;
                }
            }
        }
        return newList;
    }
    private static boolean checkForDerivationSplit(char[] function){
        for(int i = 0; i < function.length; i++){
            switch(function[i]){
                case '*':
                case '/':
                case '^':
                //case '+':
                //case '-':
                case ')':
                case '(':
                    return true;
            }
        }
        return false;
    }
    
    private static ArrayList<ArrayList<MathematicalExpression>> splitForDerivation(char[] function){
        ArrayList<ArrayList<MathematicalExpression>> split = new ArrayList<>();
        ArrayList<MathematicalExpression> part = new ArrayList<>();
        int skip_Bracket = 0;
        int start_Symbol = 0;
        int end_Symbol = 0;
        boolean left = false;
        boolean right = false;
        for(int i = 0; i < function.length; i++){
            switch(function[i]){
                case '(' :
                    if(skip_Bracket == 0){
                        if(i == 0){
                            left = false;
                        }
                        else{
                            int x = i - 1;
                            while(function[x] == ' ' && x - 1 != -1){
                                x--;
                            }
                            if(x - 1 != -1){
                                switch(function[x]){
                                    case '+':
                                    case '-':
                                    case '(':
                                       left = false;
                                       break;
                                    case '*':
                                    case '/':
                                    case '^':
                                    default :
                                        left = true;
                                        break;
                                }
                            }
                            else{
                                left = false;
                            }
                        }
                    }
                    skip_Bracket++;
                    break;
                case ')' :
                    skip_Bracket--;
                    if(skip_Bracket == 0){
                        if(i == function.length - 1 && left == true){
                            end_Symbol = i;
                            char[] Part = new char[end_Symbol + 1 - start_Symbol];
                            for(int x = 0; x < Part.length; x++){
                                Part[x] = function[x + start_Symbol];
                            }
                            start_Symbol = i + 1;
                            part.add(new MathematicalExpression(Part));
                            if(!part.isEmpty()){
                                split.add(part);
                            }
                            right = false;
                        }
                        else if(i + 1 == function.length){
                            right = false;
                        }
                        else{
                            int x = i + 1;
                            while(x + 1 != function.length && function[x] == ' '){
                                x++;
                            }
                            if(x + 1 != function.length){
                                switch(function[x]){
                                    case '+':
                                    case '-':
                                    case ')':
                                       right = false;
                                       break;
                                    case '*':
                                    case '/':
                                    case '^':
                                    default : 
                                        right = true;
                                        break;
                                }
                            }
                            else{
                                right = false;
                            }
                        }
                    }
                    if(skip_Bracket == 0 && left == false && right == false){
                        end_Symbol = i;
                        char[] Part = new char[end_Symbol + 1 - start_Symbol];
                        for(int x = 0; x < Part.length; x++){
                            Part[x] = function[x + start_Symbol];
                        }
                        start_Symbol = i + 1;
                        ArrayList<char[]> newList = splitByBrackets(Part);
                        for(char[] c: newList){
                            ArrayList<ArrayList<MathematicalExpression>> m = splitForDerivation(c);
                            for(ArrayList<MathematicalExpression> math: m){
                                ArrayList<MathematicalExpression> clone = new ArrayList<>();
                                for(MathematicalExpression e: math){
                                    clone.add(e);
                                }
                                if(!clone.isEmpty()){
                                    split.add(clone);
                                }
                            }
                        }
                    }
                    break;
                case '+' :
                case '-' :
                    if(skip_Bracket == 0){
                        end_Symbol = i;
                        char[] Part = new char[end_Symbol - start_Symbol];
                        for(int x = 0; x < Part.length; x++){
                            Part[x] = function[x + start_Symbol];
                        }
                        start_Symbol = i + 1;
                        Part = new String(Part).trim().replaceAll(" ", "").toCharArray();
                        if(Part.length != 0){
                            part.add(new MathematicalExpression(Part, function[i]));
                            ArrayList<MathematicalExpression> clone = new ArrayList<>();
                            for(MathematicalExpression m: part){
                                clone.add(m);
                            }
                            if(!clone.isEmpty()){
                                split.add(clone);
                            }
                            part.clear();
                        }
                        else{
                            split.get(split.size() - 1).get(split.get(split.size() - 1).size() - 1).operator = function[i];
                        }
                    }
                    break;
                case '*':
                case '/':
                case '^':
                    if(skip_Bracket == 0){
                        end_Symbol = i;
                        char[] Part = new char[end_Symbol - start_Symbol];
                        for(int x = 0; x < Part.length; x++){
                            Part[x] = function[x + start_Symbol];
                        }
                        start_Symbol = i + 1;
                        Part = new String(Part).trim().replaceAll(" ", "").toCharArray();
                        if(Part.length != 0){
                            part.add(new MathematicalExpression(Part, function[i]));
                        }
                    }
                    break;
                case ' ': break;
                default:
                    if(i + 1 == function.length){
                        end_Symbol = i + 1;
                        char[] Part = new char[end_Symbol - start_Symbol];
                        for(int x = 0; x < Part.length; x++){
                            Part[x] = function[x + start_Symbol];
                        }
                        start_Symbol = i + 1;
                        Part = new String(Part).trim().replaceAll(" ", "").toCharArray();
                        if(Part.length != 0){
                            part.add(new MathematicalExpression(Part));
                        }
                        if(!part.isEmpty()){
                            split.add(part);
                        }
                    }
                    break;
            }
        }
        if(split.isEmpty()){
            part.add(new MathematicalExpression(function));
            split.add(part);
        }
        return split;
    }
    //function calculator methods
    private static MathematicalExpression solve(ArrayList<char[]> function, double z, double X, double Y){
        ArrayList<MathematicalExpression> list = new ArrayList<>();
        for(char[] part: function){
            if(checkForBrackets(part)){
                list.add(solve(splitByBrackets(part), z, X, Y));
            }
            else{
                switch(new String(part).trim().replaceAll(" ", "")){
                    case "*":
                    case "-":
                    case "+":
                    case "/": 
                    case "^":
                        list.get(list.size() - 1).operator = new String(part).trim().replaceAll(" ", "").toCharArray()[0];
                        break;
                    case " ":
                    case "":
                        break;
                    default: 
                        list.add(calculate(part, X, Y));
                        break;
                }
            }
        }
        for(int i = 0; i < list.size(); i++){
            if(i == 0){
                z = list.get(i).value;
            }
            else{
                switch(list.get(i - 1).operator){
                    case '*': z = z*list.get(i).value; break;
                    case '/': z = z/list.get(i).value; break;
                    case '+': z = z+list.get(i).value; break;
                    case '-': z = z-list.get(i).value; break;
                    case '^': z = Math.pow(z,list.get(i).value); break;
                }
            }
        }
        return new MathematicalExpression(z, list.get(list.size() - 1).operator);
    }
    
    private static boolean checkForBrackets(char[] function){
        for(int i = 0; i < function.length; i++){
            if(function[i] == '('){
                if(i > 0 && function[i - 1] != 'n' && function[i - 1] != 's' && function[i - 1] != 'g'){
                    return true;
                }
                else if(i == 0){
                    return true;
                }
            }
        }
        return false;
    }
    
    private static ArrayList<char[]> splitByBrackets(char[] function){
        ArrayList<char[]> split = new ArrayList<>();
        int skip_Bracket = 0;
        int start_Symbol = -1;
        int end_Symbol = 0;
        for(int i = 0; i < function.length; i++){
            switch(function[i]){
                case '(' :
                    if(i > 0 && function[i - 1] != 'n' && function[i - 1] != 's' && function[i - 1] != 'g'){
                        if(start_Symbol == -1){
                            start_Symbol = i;
                        }
                        else{
                            skip_Bracket++;
                        }
                    }
                    else if(i == 0){
                        if(start_Symbol == -1){
                            start_Symbol = i;
                        }
                        else{
                            skip_Bracket++;
                        }
                    }
                    else{
                        skip_Bracket++;
                    }
                    break;
                case ')' :
                    if(skip_Bracket == 0){
                        end_Symbol = i;
                        char[] part = new char[end_Symbol - start_Symbol - 1];
                        for(int x = start_Symbol + 1; x < end_Symbol; x++){
                            part[x - start_Symbol - 1] = function[x];
                        }
                        split.add(part);
                        skip_Bracket = 0;
                        start_Symbol = -1;
                    }
                    else{
                        skip_Bracket--;
                    }
                    break;
                default :
                    if(start_Symbol == -1){
                        start_Symbol = i;
                        boolean breakLoop = false;
                        for(end_Symbol = i; end_Symbol < function.length; end_Symbol++){
                            switch(function[end_Symbol]){
                                case '(':
                                    if(end_Symbol > 0 && function[end_Symbol - 1] != 'n' && function[end_Symbol - 1] != 's' && function[end_Symbol - 1] != 'g'){
                                        if(skip_Bracket == 0){
                                            breakLoop = true;
                                        }
                                        else{
                                            skip_Bracket++;
                                        }
                                    }
                                    else{
                                        skip_Bracket++;
                                    }
                                    break;
                                case ')':
                                    if(skip_Bracket != 0){
                                        skip_Bracket--;
                                    }
                                    break;
                            }
                            if(breakLoop){
                                break;
                            }
                        }
                        i = end_Symbol - 1;
                        char[] part = new char[end_Symbol - start_Symbol];
                        for(int x = start_Symbol; x < end_Symbol; x++){
                            part[x - start_Symbol] = function[x];
                        }
                        String s = new String(part).trim().replaceAll(" ", "");
                        if(s.toCharArray().length != 0){
                            split.add(part);
                        }
                        skip_Bracket = 0;
                        start_Symbol = -1;
                    }
                    break;
            }
        }
        return split;
    }
    private static MathematicalExpression calculate(char[] function, double X, double Y){
        ArrayList<MathematicalExpression> Function = new ArrayList<>();
        for(int i = 0; i < function.length; i++){
            if((i == 0 && function[i] == '-') || Character.isDigit(function[i]) || function[i] == '.' || (function[i] == '/' && i < function.length - 1 && Character.isDigit(function[i + 1]))){
                int end = i;
                while((end == 0 && function[i] == '-') || (end < function.length) && (Character.isDigit(function[end]) || function[end] == '.' || (function[end] == '/' && end < function.length - 1 && Character.isDigit(function[end + 1])))){
                    end++;
                }
                Function.add(getMultiplier(function, i, end));
                i = end - 1;
            }
            else if(!Character.isLetter(function[i]) && i != 0){
                switch(function[i]){
                    case ' ': break;
                    case '*':
                    case '/':
                    case '+':
                    case '-':
                    case '^':
                        Function.get(Function.size() - 1).operator = function[i];
                }
            }
            else if(Character.isLetter(function[i])){
                Function.add(calculateVariable(function, i, X, Y));
                switch(function[i]){
                    case 'x':
                    case 'X':
                    case 'y':
                    case 'Y':
                        break;
                    default:
                        int end = i;
                        while(function[end] != ')'){
                            end++;
                        }
                        i = end;
                        break;
                }
            }
        }
        double z = 0;
        for(int i = 0; i < Function.size(); i++){
            if(i == 0){
                z = Function.get(i).value;
            }
            else{
                switch(Function.get(i - 1).operator){
                    case '*': z = z*Function.get(i).value; break;
                    case '/': z = z/Function.get(i).value; break;
                    case '+': z = z+Function.get(i).value; break;
                    case '-': z = z-Function.get(i).value; break;
                    case '^': z = Math.pow(z,Function.get(i).value); break;
                }
            }
        }
        return new MathematicalExpression(z, Function.get(Function.size() - 1).operator);
    }
    private static MathematicalExpression calculateVariable(char[] data, int Start, double X, double Y){
        switch(data[Start]){
            case 'x': 
            case 'X':
                return new MathematicalExpression(X);
            case 'y': 
            case 'Y':
                return new MathematicalExpression(Y);
            case 's':
                int end = Start;
                while(data[end] != ')'){
                    end++;
                }
                char[] newData = new char[end - Start - 4];
                for(int i = 0; i < newData.length; i++){
                    newData[i] = data[i + Start + 4];
                }
                MathematicalExpression math = calculate(newData, X, Y);
                return new MathematicalExpression(Math.sin(math.value), math.operator);
            case 'c':
                if(data[Start + 2] == 's'){
                    int End = Start;
                    while(data[End] != ')'){
                        End++;
                    }
                    char[] NewData = new char[End - Start - 4];
                    for(int i = 0; i < NewData.length; i++){
                        NewData[i] = data[i + Start + 4];
                    }
                    MathematicalExpression math2 = calculate(NewData, X, Y);
                    return new MathematicalExpression(Math.cos(math2.value), math2.operator);
                }
                else if(data[Start + 2] == 't'){
                    int End = Start;
                    while(data[End] != ')'){
                        End++;
                    }
                    char[] NewData = new char[End - Start - 5];
                    for(int i = 0; i < NewData.length; i++){
                        NewData[i] = data[i + Start + 5];
                    }
                    MathematicalExpression math2 = calculate(NewData, X, Y);
                    return new MathematicalExpression(1.0/(Math.tan(math2.value)), math2.operator);
                }
            case 't':
                int End = Start;
                while(data[End] != ')'){
                    End++;
                }
                char[] NewData = new char[End - Start - 3];
                for(int i = 0; i < NewData.length; i++){
                    NewData[i] = data[i + Start + 3];
                }
                MathematicalExpression math2 = calculate(NewData, X, Y);
                return new MathematicalExpression(Math.tan(math2.value), math2.operator);
            default:
                return new MathematicalExpression(1.0);
        }
    }
    /**
     * Reads the multiplier (number in front of x or y) from a function.
     * @param data char array to read from
     * @return double as value
     */
    private static MathematicalExpression getMultiplier(char[] data, int Start, int End){
        ArrayList<Character> list = new ArrayList<>();
        for(int i = Start; i < End; i++){
            list.add(data[i]);
        }
        if(list.isEmpty()){
            return new MathematicalExpression(1.0);
        }
        else{
            char[] a = new char[list.size()];
            char[] b = new char[list.size()];
            b[0] = '1';
            boolean division = false;
            int count = 0;
            for(int i = 0; i < list.size(); i++){
                if(list.get(i) == '/'){
                    division = true;
                    count = i + 1;
                }
                else if(!division){
                    a[i] = list.get(i);
                }
                else{
                    b[i - count] = list.get(i);
                }
            }
            double A = Double.parseDouble(new String(a).trim().replaceAll(" ", ""));
            double B = Double.parseDouble(new String(b).trim().replaceAll(" ", ""));
            return new MathematicalExpression(A/B);
        }
    }
    private static class MathematicalExpression{
        private double value;
        private char operator;
        private char[] Value;
        private MathematicalExpression(double value, char operator){
           this.value = value;
           this.operator = operator;
        }
        private MathematicalExpression(double value){
            this.value = value;
        }
        private MathematicalExpression(char[] Value, char operator){
            this.Value = Value;
            this.operator = operator;
        }
        private MathematicalExpression(char[] Value){
            this.Value = Value;
        }
    }
}
