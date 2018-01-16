package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Main {

    public static void main(String[] args) {

        System.out.println("eine Rechnung eingeben:");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String term = null;

        try {
            term = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(term == null || term.equals("")){
            System.out.println("es trat ein fehler auf!");
            return;
        }

        //term behandeln

        term = term.replaceAll(" ", "");
        term = term.replaceAll("\t", "");


        try {
            System.out.println(parse(term));
        }catch(NumberFormatException e){
            System.out.println("invalid term");
        }
    }

    public static double parse(String _term) throws NumberFormatException{

        System.out.println(_term);

        Map<Character, Number> signs = new HashMap<>();
        signs.put('/', 3); signs.put('*', 2); signs.put('-', 1); signs.put('+', 0);

        Map<Character, Number> brackets = new HashMap<>();
        brackets.put(')', -1); brackets.put('(',1);

        int b = 0;
        int split_index = 0;
        int pref_sign = 4;
        char sign = ' ';

        for(int i = 0; i < _term.length(); i++){
            if(b < 0){
                throw new NumberFormatException();
            }
            char c = _term.charAt(i);
            if(brackets.containsKey(c)){
                b = b + brackets.get(c).intValue();
            }
            if(signs.containsKey(c) && b == 0){
                if(signs.get(c).intValue() < pref_sign){
                    pref_sign = signs.get(c).intValue();
                    split_index = i;
                    sign = c;
                }
            }
        }

        if(split_index == 0 && !_term.contains("(")){
            return Double.parseDouble(_term);
        }

        if(split_index == 0){
            _term = _term.replaceFirst("\\(", "");
            _term = new StringBuilder(new StringBuilder(_term).reverse().toString().replaceFirst("\\)", "")).reverse().toString();
            return parse(_term);
        }

        double left = parse(_term.substring(0,split_index));
        double right = parse(_term.substring(split_index+1,_term.length()));

        switch (sign){
            case '+' : return left + right;
            case '-' : return left - right;
            case '*' : return left * right;
            case '/' : return left / right;
        }

        return 0.;
    }
}
