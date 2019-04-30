import java.util.*;

public class TestSimulator {
    private static class Case {
        String name;
        List<Instruction> prog;
        String[] inputs;
        String[] outputs;
        Case(String name, Instruction[] prog, String[] inputs, String[] outputs) {
            this.name = name;
            this.prog = Arrays.asList(prog);
            this.inputs = inputs;
            this.outputs = outputs;
        }
    }
    public static void main(String[] args) {
        Case[] cases = new Case[]{
            new Case(
                "testfiles/dollarending.tm",
                new Instruction[]{
                    new Instruction.Var("x"),
                    new Instruction.Label("loop"),
                    new Instruction.Read("x"),
                    new Instruction.SkipIf(new Instruction.Operand("x"), new Instruction.Operand(' '), false),
                    new Instruction.Jump("exit"),
                    new Instruction.Move(1),
                    new Instruction.Jump("loop"),
                    new Instruction.Label("exit"),
                    new Instruction.Write(new Instruction.Operand('$')),
                },
                new String[]{"","abc", "hello world", "042"},
                new String[]{"$", "abc$", "hello$world", "042$"}
            ),
            new Case(
                "testfiles/branch.tm",
                new Instruction[]{
                    new Instruction.Var("x"),
                    new Instruction.Read("x"),
                    new Instruction.SkipIf(new Instruction.Operand("x"), new Instruction.Operand('0'), false),
                    new Instruction.Write(new Instruction.Operand('a')),
                    new Instruction.SkipIf(new Instruction.Operand("x"), new Instruction.Operand('1'), false),
                    new Instruction.Write(new Instruction.Operand('b')),
                    new Instruction.SkipIf(new Instruction.Operand("x"), new Instruction.Operand('2'), false),
                    new Instruction.Write(new Instruction.Operand('c')),
                    new Instruction.SkipIf(new Instruction.Operand("x"), new Instruction.Operand('3'), false),
                    new Instruction.Write(new Instruction.Operand('d')),
                    new Instruction.SkipIf(new Instruction.Operand("x"), new Instruction.Operand('4'), false),
                    new Instruction.Write(new Instruction.Operand('e')),
                    new Instruction.SkipIf(new Instruction.Operand("x"), new Instruction.Operand('5'), false),
                    new Instruction.Write(new Instruction.Operand('f')),
                    new Instruction.SkipIf(new Instruction.Operand("x"), new Instruction.Operand('6'), false),
                    new Instruction.Write(new Instruction.Operand('g')),
                    new Instruction.SkipIf(new Instruction.Operand("x"), new Instruction.Operand('7'), false),
                    new Instruction.Write(new Instruction.Operand('h')),
                    new Instruction.SkipIf(new Instruction.Operand("x"), new Instruction.Operand('8'), false),
                    new Instruction.Write(new Instruction.Operand('i')),
                    new Instruction.SkipIf(new Instruction.Operand("x"), new Instruction.Operand('9'), false),
                    new Instruction.Write(new Instruction.Operand('j')),
                },
                new String[]{"","a", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"},
                new String[]{"","a", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "b0"}
            ),
            new Case(
                "testfiles/rot13.tm",
                new Instruction[]{
                    new Instruction.Var("x"),
                    new Instruction.Label("loop"),
                    new Instruction.Read("x"),
                    new Instruction.SkipIf(new Instruction.Operand("x"), new Instruction.Operand(' '), false),
                    new Instruction.Jump("halt"),
                    new Instruction.SkipIf(new Instruction.Operand("x"), new Instruction.Operand('A'), false),
                    new Instruction.Write(new Instruction.Operand('N')),
                    new Instruction.SkipIf(new Instruction.Operand("x"), new Instruction.Operand('B'), false),
                    new Instruction.Write(new Instruction.Operand('O')),
                    new Instruction.SkipIf(new Instruction.Operand("x"), new Instruction.Operand('C'), false),
                    new Instruction.Write(new Instruction.Operand('P')),
                    new Instruction.SkipIf(new Instruction.Operand("x"), new Instruction.Operand('D'), false),
                    new Instruction.Write(new Instruction.Operand('Q')),
                    new Instruction.SkipIf(new Instruction.Operand("x"), new Instruction.Operand('E'), false),
                    new Instruction.Write(new Instruction.Operand('R')),
                    new Instruction.SkipIf(new Instruction.Operand("x"), new Instruction.Operand('F'), false),
                    new Instruction.Write(new Instruction.Operand('S')),
                    new Instruction.SkipIf(new Instruction.Operand("x"), new Instruction.Operand('G'), false),
                    new Instruction.Write(new Instruction.Operand('T')),
                    new Instruction.SkipIf(new Instruction.Operand("x"), new Instruction.Operand('H'), false),
                    new Instruction.Write(new Instruction.Operand('U')),
                    new Instruction.SkipIf(new Instruction.Operand("x"), new Instruction.Operand('I'), false),
                    new Instruction.Write(new Instruction.Operand('V')),
                    new Instruction.SkipIf(new Instruction.Operand("x"), new Instruction.Operand('J'), false),
                    new Instruction.Write(new Instruction.Operand('W')),
                    new Instruction.SkipIf(new Instruction.Operand("x"), new Instruction.Operand('K'), false),
                    new Instruction.Write(new Instruction.Operand('X')),
                    new Instruction.SkipIf(new Instruction.Operand("x"), new Instruction.Operand('L'), false),
                    new Instruction.Write(new Instruction.Operand('Y')),
                    new Instruction.SkipIf(new Instruction.Operand("x"), new Instruction.Operand('M'), false),
                    new Instruction.Write(new Instruction.Operand('Z')),
                    new Instruction.SkipIf(new Instruction.Operand("x"), new Instruction.Operand('N'), false),
                    new Instruction.Write(new Instruction.Operand('A')),
                    new Instruction.SkipIf(new Instruction.Operand("x"), new Instruction.Operand('O'), false),
                    new Instruction.Write(new Instruction.Operand('B')),
                    new Instruction.SkipIf(new Instruction.Operand("x"), new Instruction.Operand('P'), false),
                    new Instruction.Write(new Instruction.Operand('C')),
                    new Instruction.SkipIf(new Instruction.Operand("x"), new Instruction.Operand('Q'), false),
                    new Instruction.Write(new Instruction.Operand('D')),
                    new Instruction.SkipIf(new Instruction.Operand("x"), new Instruction.Operand('R'), false),
                    new Instruction.Write(new Instruction.Operand('E')),
                    new Instruction.SkipIf(new Instruction.Operand("x"), new Instruction.Operand('S'), false),
                    new Instruction.Write(new Instruction.Operand('F')),
                    new Instruction.SkipIf(new Instruction.Operand("x"), new Instruction.Operand('T'), false),
                    new Instruction.Write(new Instruction.Operand('G')),
                    new Instruction.SkipIf(new Instruction.Operand("x"), new Instruction.Operand('U'), false),
                    new Instruction.Write(new Instruction.Operand('H')),
                    new Instruction.SkipIf(new Instruction.Operand("x"), new Instruction.Operand('V'), false),
                    new Instruction.Write(new Instruction.Operand('I')),
                    new Instruction.SkipIf(new Instruction.Operand("x"), new Instruction.Operand('W'), false),
                    new Instruction.Write(new Instruction.Operand('J')),
                    new Instruction.SkipIf(new Instruction.Operand("x"), new Instruction.Operand('X'), false),
                    new Instruction.Write(new Instruction.Operand('K')),
                    new Instruction.SkipIf(new Instruction.Operand("x"), new Instruction.Operand('Y'), false),
                    new Instruction.Write(new Instruction.Operand('L')),
                    new Instruction.SkipIf(new Instruction.Operand("x"), new Instruction.Operand('Z'), false),
                    new Instruction.Write(new Instruction.Operand('M')),
                    new Instruction.Move(1),
                    new Instruction.Jump("loop"),
                    new Instruction.Label("halt"),
                },
                new String[]{"", "THE", "QUICK", "BROWN", "FOX", "JUMPS", "OVER", "THE", "LAZY", "DOG"},
                new String[]{"", "GUR", "DHVPX", "OEBJA", "SBK", "WHZCF", "BIRE", "GUR", "YNML", "QBT"}
            ),
            new Case(
                "testfiles/astarbstar.tm",
                new Instruction[]{
                    new Instruction.Var("x"),
                    new Instruction.Var("res"),
                    new Instruction.Read("x"),
                    new Instruction.Write(new Instruction.Operand('0')),
                    new Instruction.Read("res"),
                    new Instruction.Write(new Instruction.Operand("x")),
                    new Instruction.SkipIf(new Instruction.Operand("x"), new Instruction.Operand(' '), false),
                    new Instruction.Jump("beg"),
                    new Instruction.SkipIf(new Instruction.Operand("x"), new Instruction.Operand('a'), false),
                    new Instruction.Jump("beg"),
                    new Instruction.SkipIf(new Instruction.Operand("x"), new Instruction.Operand('b'), false),
                    new Instruction.Jump("beg"),
                    new Instruction.Jump("halt"),
                    new Instruction.Label("beg"),
                    new Instruction.Write(new Instruction.Operand('$')),
                    new Instruction.Move(1),
                    new Instruction.SkipIf(new Instruction.Operand("x"), new Instruction.Operand('b'), false),
                    new Instruction.Jump("L2"),
                    new Instruction.Label("L1"),
                    new Instruction.Read("x"),
                    new Instruction.SkipIf(new Instruction.Operand("x"), new Instruction.Operand('a'), true),
                    new Instruction.Jump("L2"),
                    new Instruction.Write(new Instruction.Operand(' ')),
                    new Instruction.Move(1),
                    new Instruction.Jump("L1"),
                    new Instruction.Label("L2"),
                    new Instruction.Read("x"),
                    new Instruction.SkipIf(new Instruction.Operand("x"), new Instruction.Operand('b'), true),
                    new Instruction.Jump("L3"),
                    new Instruction.Write(new Instruction.Operand(' ')),
                    new Instruction.Move(1),
                    new Instruction.Jump("L2"),
                    new Instruction.Label("L3"),
                    new Instruction.SkipIf(new Instruction.Operand("x"), new Instruction.Operand(' '), true),
                    new Instruction.Jump("reject"),
                    new Instruction.Jump("accept"),
                    new Instruction.Label("reject"),
                    new Instruction.Write(new Instruction.Operand(' ')),
                    new Instruction.Move(1),
                    new Instruction.Read("x"),
                    new Instruction.SkipIf(new Instruction.Operand("x"), new Instruction.Operand(' '), true),
                    new Instruction.Jump("reject"),
                    new Instruction.Jump("end"),
                    new Instruction.Label("accept"),
                    new Instruction.Write(new Instruction.Operand('1')),
                    new Instruction.Read("res"),
                    new Instruction.Write(new Instruction.Operand(' ')),
                    new Instruction.Jump("end"),
                    new Instruction.Label("end"),
                    new Instruction.Read("x"),
                    new Instruction.SkipIf(new Instruction.Operand("x"), new Instruction.Operand('$'), false),
                    new Instruction.Jump("halt"),
                    new Instruction.Move(-1),
                    new Instruction.Jump("end"),
                    new Instruction.Label("halt"),
                    new Instruction.Write(new Instruction.Operand("res")),
                },
                new String[]{"", "a", "b", "c", "ab", "ba", "abc", "aab", "aabbb"},
                new String[]{"1", "1", "1", "0", "1", "0", "0", "1", "1"}
            ),
        };
        int nerr = 0;
        for(Case c : cases) {
            for(int i = 0; i < c.inputs.length; i++) {
                Simulator sim = new Simulator(c.prog);
                char[] in = c.inputs[i].toCharArray();
                char[] out = sim.simulate(in);
                String output = String.valueOf(out).trim();
                if(!output.equals(c.outputs[i])) {
                    System.err.printf("==CASE:%s, INPUT:%s, WANT:%s, GOT:%s\n", c.name, c.inputs[i], c.outputs[i], output);
                    nerr++;
                }
            }
        }
        if (nerr == 0) System.err.printf("ALL PASSED\n");
    }
}