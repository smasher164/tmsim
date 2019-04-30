import java.util.*;
import java.util.regex.*;
import java.io.*;

public class Parser {
    ByteArrayOutputStream ebuf;
    PrintStream out;
    public Parser() {
        ebuf = new ByteArrayOutputStream();
        out = new PrintStream(ebuf);
    }
    public void errorf(String format, Object... args) {
        out.printf(format, args);
    }
    public String err() {
        return ebuf.toString();
    }
    public static boolean isIdent(String s) {
        return s.matches("[a-zA-Z]\\w*");
    }
    Instruction.Operand parseOperand(String s) {
        if (isIdent(s)) {
            return new Instruction.Operand(s);
        }
        if (s.length() == 3 && s.charAt(0) == '\'' && s.charAt(2) == '\'') {
            return new Instruction.Operand(s.charAt(1));
        }
        try {
            int v = Integer.parseInt(s);
            return new Instruction.Operand((char)v);
        } catch (NumberFormatException e) {
            errorf("operand \"%s\" is not a valid integer\n", s);
        }
        return new Instruction.Operand();
    }
    public List<String> matches(String pattern, String s) {
        List<String> allMatches = new ArrayList<String>();
        Matcher m = Pattern.compile(pattern)
             .matcher(s);
        int i = 0;
        while (m.find()) {
           for (int j = 0; j <= m.groupCount(); j++) {
              allMatches.add(m.group(j));
              i++;
           }
        }
        if (allMatches.size() == 4) {
            allMatches.remove(0);
        }
        return allMatches;
    }
    public Instruction parseInstruction(String line) {
        if (line.length() == 0 || line.startsWith("//")) {
            return new Instruction();
        } else if (line.startsWith("var ")) {
            line = line.substring(4,line.length()).trim();
            if (isIdent(line)) {
                return new Instruction.Var(line);
            }
        } else if (line.startsWith("skipif ")) {
            line = line.substring(7,line.length()).trim();
            List<String> tok = matches("(.+) (==|!=) (.+)", line);
            if (tok.size() == 3) {
                Instruction.Operand a = parseOperand(tok.get(0));
                Instruction.Operand b = parseOperand(tok.get(2));
                if (tok.get(1).equals("==")) {
                    return new Instruction.SkipIf(a, b, true);
                } else if (tok.get(1).equals("!=")) {
                    return new Instruction.SkipIf(a, b, false);
                }
            }
        } else if (line.startsWith("move ")) {
            line = line.substring(5,line.length()).trim();
            try {
                int v = Integer.parseInt(line);
                return new Instruction.Move(v);
            } catch (NumberFormatException e) {
                errorf("\"%s\" is not a valid offset\n", line);
            }
        } else if (line.startsWith("jump ")) {
            line = line.substring(5,line.length()).trim();
            if (isIdent(line)) {
                return new Instruction.Jump(line);
            }
        } else if (line.startsWith("read ")) {
            line = line.substring(5,line.length()).trim();
            if (isIdent(line)) {
                return new Instruction.Read(line);
            }
        } else if (line.startsWith("write ")) {
            line = line.substring(6,line.length()).trim();
            Instruction.Operand x = parseOperand(line);
            if (x.type != Instruction.Operand.ILLEGAL) {
                return new Instruction.Write(x);
            }
        } else if (
            line.length() > 1 &&
            isIdent(line.substring(0,line.length()-1)) && 
            line.charAt(line.length()-1) == ':') {
            return new Instruction.Label(line.substring(0,line.length()-1));
        }
        errorf("\"%s\" is not a valid instruction\n", line);
        return new Instruction();
    }
    public List<Instruction> parse(String prog) {
        List<Instruction> list = new ArrayList<>();
        BufferedReader r = new BufferedReader(new StringReader(prog));
        try {
            String line = r.readLine();
            while(line != null) {
                list.add(parseInstruction(line.trim()));
                line = r.readLine();
            }
        } catch (IOException e) {}
        return list;
    }
}