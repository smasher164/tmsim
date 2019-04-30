import java.util.List;

public class Instruction {
    public int type;
    public final static int ILLEGAL = 0;
    public final static int LABEL = 1;
    public final static int VAR = 2;
    public final static int SKIPIF = 3;
    public final static int MOVE = 4;
    public final static int JUMP = 5;
    public final static int READ = 6;
    public final static int WRITE = 7;

    public Instruction() { type = ILLEGAL; }

    public static class Label extends Instruction {
        public String id;

        public Label() {
            type = LABEL;
            id = "";
        }
        public Label(String id) {
            this();
            this.id = id;
        }
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof Label))
                return false;
            Label l = (Label)o;
            return id.equals(l.id);
        }
        public String toString() { return id + ":"; }
    }

    public static class Var extends Instruction {
        public String id;

        public Var() {
            type = VAR;
            id = "";
        }
        public Var(String id) {
            this();
            this.id = id;
        }
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof Var))
                return false;
            Var l = (Var)o;
            return id.equals(l.id);
        }
        public String toString() { return "var " + id; }
    }

    public static class SkipIf extends Instruction {
        public Operand a, b;
        public boolean equals;

        public SkipIf() {
            type = SKIPIF;
            a = new Operand();
            b = new Operand();
            equals = false;
        }
        public SkipIf(Operand a, Operand b, boolean equals) {
            this();
            this.a = a;
            this.b = b;
            this.equals = equals;
        }
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof SkipIf))
                return false;
            SkipIf l = (SkipIf)o;
            return a.equals(l.a) && b.equals(l.b) && equals == l.equals;
        }
        public String toString() {
            String eq = " != ";
            if (equals) eq = " == ";
            return a.toString() + eq + b.toString();
        }
    }

    public static class Move extends Instruction {
        public int v;
        public Move() {
            type = MOVE;
            v = 0;
        }
        public Move(int v) {
            this();
            this.v = v;
        }
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof Move))
                return false;
            Move l = (Move)o;
            return v == l.v;
        }
        public String toString() { return "move " + String.valueOf(v); }
    }

    public static class Jump extends Instruction {
        public String id;

        public Jump() {
            type = JUMP;
            id = "";
        }
        public Jump(String id) {
            this();
            this.id = id;
        }
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof Jump))
                return false;
            Jump l = (Jump)o;
            return id.equals(l.id);
        }
        public String toString() { return "jump " + id; }
    }
    public static class Read extends Instruction {
        public String id;

        public Read() {
            type = READ;
            id = "";
        }
        public Read(String id) {
            this();
            this.id = id;
        }
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof Read))
                return false;
            Read l = (Read)o;
            return id.equals(l.id);
        }
        public String toString() { return "read " + id; }
    }
    public static class Write extends Instruction {
        public Operand x;

        public Write() {
            type = WRITE;
            x = new Operand();
        }
        public Write(Operand x) {
            this();
            this.x = x;
        }
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof Write))
                return false;
            Write l = (Write)o;
            return x.equals(l.x);
        }
        public String toString() { return "write " + x.toString(); }
    }

    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Instruction))
            return false;
        Instruction ins = (Instruction)o;
        switch (type) {
        case ILLEGAL:
            return this == ins;
        case LABEL:
            return (Label)this == (Label)ins;
        case VAR:
            return (Var)this == (Var)ins;
        case SKIPIF:
            return (SkipIf)this == (SkipIf)ins;
        case MOVE:
            return (Move)this == (Move)ins;
        case JUMP:
            return (Jump)this == (Jump)ins;
        case READ:
            return (Read)this == (Read)ins;
        case WRITE:
            return (Write)this == (Write)ins;
        default:
            return false;
        }
    }

    public static class Operand {
        public String id;
        public char c;
        public int type;
        public final static int ILLEGAL = 0;
        public final static int IDENT = 1;
        public final static int CHAR = 2;

        public Operand() { type = ILLEGAL; }
        public Operand(String id) {
            type = IDENT;
            this.id = id;
        }
        public Operand(char c) {
            type = CHAR;
            this.c = c;
        }
        public boolean equals(Object o) {
            if (o == this)
                return true;
            if (!(o instanceof Operand))
                return false;
            Operand l = (Operand)o;
            switch (type) {
            case IDENT:
                return id.equals(l.id);
            case CHAR:
                return c == l.c;
            }
            return true;
        }
        public String toString() {
            switch (type){
            case IDENT: return id;
            case CHAR: return "'" + c + "'";
            }
            return "";
        }
    }

    public String toString() {
        switch (type) {
        case LABEL:
            return ((Label)this).toString();
        case VAR:
            return ((Var)this).toString();
        case SKIPIF:
            return ((SkipIf)this).toString();
        case MOVE:
            return ((Move)this).toString();
        case JUMP:
            return ((Jump)this).toString();
        case READ:
            return ((Read)this).toString();
        case WRITE:
            return ((Write)this).toString();
        default:
            return "";
        }
    }

    public static String printProg(List<Instruction> prog) {
        String s = "";
        if (prog.size() > 0)
            s += prog.get(0).toString();
        for(int i = 1; i < prog.size(); i++) {
            s += "\n" + prog.get(i).toString();
        }
        return s;
    }
}