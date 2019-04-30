import java.util.*;
import java.io.*;

public class Simulator {
    int ip;
    int inp;
    Map<String, Integer> labels;
    Map<String, Character> decls;
    List<Instruction> prog;
    ByteArrayOutputStream ebuf;
    PrintStream out;

    public Simulator(List<Instruction> prog) {
        ip = 0;
        inp = 0;
        labels = new HashMap<>();
        decls = new HashMap<>();
        this.prog = prog;
        ebuf = new ByteArrayOutputStream();
        out = new PrintStream(ebuf);
    }
    public Simulator(List<Instruction> prog, int ip, int inp, Map<String, Integer> labels, Map<String, Character> decls) {
        this.prog = prog;
        this.ip = ip;
        this.inp = inp;
        this.labels = labels;
        this.decls = decls;
        ebuf = new ByteArrayOutputStream();
        out = new PrintStream(ebuf);
    }
    public void errorf(String format, Object... args) {
        out.printf(format, args);
    }
    public String err() {
        return ebuf.toString();
    }
    public List<State> simulate1001(char[] input) {
        List<State> states = new ArrayList<>();
        for (int i = 0; i < prog.size(); i++) {
            Instruction ins = prog.get(i);
            if (ins.type == Instruction.LABEL) {
                Instruction.Label l = (Instruction.Label)ins;
                labels.put(l.id, i);
            }
        }
        int steps = 0;
        L:
        while (ip < prog.size()) {
            states.add(new State(ip, inp, input.clone(), new HashMap<>(decls)));
            if (steps == 1000) {
                errorf("Execution timed out\n");
                break;
            }
            Instruction ins = prog.get(ip);
            switch (ins.type) {
            case Instruction.VAR:
                Instruction.Var v = (Instruction.Var)ins;
                decls.put(v.id, (char)0);
                ip++;
                break;
            case Instruction.SKIPIF:
                Instruction.SkipIf s = (Instruction.SkipIf)ins;
                char a = s.a.c;
                char b = s.b.c;
                if (s.a.type == Instruction.Operand.IDENT)
                    try {
                        a = decls.get(s.a.id);    
                    } catch (NullPointerException e) {
                        errorf("variable '%s' not declared\n", s.a.id);
                        break L;
                    }
                if (s.b.type == Instruction.Operand.IDENT)
                    try {
                        b = decls.get(s.b.id);
                    } catch (NullPointerException e) {
                        errorf("variable '%s' not declared\n", s.b.id);
                        break L;
                    }
                if (s.equals == (a == b))
                    ip++;
                ip++;
                break;
            case Instruction.MOVE:
                Instruction.Move m = (Instruction.Move)ins;
                int ninp = inp += m.v;
                if (ninp < 0){
                    errorf("cannot move past beginning of tape: %d\n", inp);
                    break L;
                } else if (ninp >= input.length) {
                    char[] narr = new char[ninp + 1];
                    System.arraycopy(input, 0, narr, 0, input.length);
                    for(int i = input.length; i < narr.length; i++) {
                        narr[i] = ' ';
                    }
                    input = narr;
                }
                inp = ninp;
                ip++;
                break;
            case Instruction.JUMP:
                Instruction.Jump j = (Instruction.Jump)ins;
                ip = labels.get(j.id);
                break;
            case Instruction.READ:
                Instruction.Read r = (Instruction.Read)ins;
                if (input.length == 0) {
                    input = new char[]{' '};
                }
                if (decls.containsKey(r.id))
                    decls.put(r.id, input[inp]);
                ip++;
                break;
            case Instruction.WRITE:
                if (input.length == 0) {
                    input = new char[]{' '};
                }
                Instruction.Write w = (Instruction.Write)ins;
                if (w.x.type == Instruction.Operand.IDENT)
                    try {
                        input[inp] = decls.get(w.x.id);
                    } catch (NullPointerException e) {
                        errorf("variable '%s' not declared\n", w.x.id);
                        break L;
                    }
                else if (w.x.type == Instruction.Operand.CHAR)
                    input[inp] = w.x.c;
                ip++;
                break;
            default:
                ip++;
                break;
            }
            steps++;
        }
        states.add(new State(ip, inp, input.clone(), new HashMap<>(decls)));
        return states;
    }
    public char[] simulate(char[] input) {
        for (int i = 0; i < prog.size(); i++) {
            Instruction ins = prog.get(i);
            if (ins.type == Instruction.LABEL) {
                Instruction.Label l = (Instruction.Label)ins;
                labels.put(l.id, i);
            }
        }
        while (ip < prog.size()) {
            Instruction ins = prog.get(ip);
            switch (ins.type) {
            case Instruction.VAR:
                Instruction.Var v = (Instruction.Var)ins;
                decls.put(v.id, (char)0);
                ip++;
                break;
            case Instruction.SKIPIF:
                Instruction.SkipIf s = (Instruction.SkipIf)ins;
                char a = s.a.c;
                char b = s.b.c;
                if (s.a.type == Instruction.Operand.IDENT)
                    a = decls.get(s.a.id);
                if (s.b.type == Instruction.Operand.IDENT)
                    b = decls.get(s.b.id);
                if (s.equals == (a == b))
                    ip++;
                ip++;
                break;
            case Instruction.MOVE:
                Instruction.Move m = (Instruction.Move)ins;
                int ninp = inp += m.v;
                if (ninp >= input.length) {
                    char[] narr = new char[ninp + 1];
                    System.arraycopy(input, 0, narr, 0, input.length);
                    for(int i = input.length; i < narr.length; i++) {
                        narr[i] = ' ';
                    }
                    input = narr;
                }
                inp = ninp;
                ip++;
                break;
            case Instruction.JUMP:
                Instruction.Jump j = (Instruction.Jump)ins;
                ip = labels.get(j.id);
                break;
            case Instruction.READ:
                Instruction.Read r = (Instruction.Read)ins;
                if (input.length == 0) {
                    input = new char[]{' '};
                }
                if (decls.containsKey(r.id))
                    decls.put(r.id, input[inp]);
                ip++;
                break;
            case Instruction.WRITE:
                if (input.length == 0) {
                    input = new char[]{' '};
                }
                Instruction.Write w = (Instruction.Write)ins;
                if (w.x.type == Instruction.Operand.IDENT)
                    input[inp] = decls.get(w.x.id);
                else if (w.x.type == Instruction.Operand.CHAR)
                    input[inp] = w.x.c;
                ip++;
                break;
            default:
                ip++;
                break;
            }
        }
        return input;
    }
}