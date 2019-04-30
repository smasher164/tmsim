import org.json.*;
import java.util.*;

public class State {
    int ip;
    int inp;
    char[] tape;
    Map<String, Character> decls;
    public State(int ip, int inp, char[] tape, Map<String, Character> decls) {
        this.ip = ip;
        this.inp = inp;
        this.tape = tape;
        this.decls = decls;
    }
    public JSONObject toJSONObject() {
        JSONObject o = new JSONObject();
        o.put("ip", ip);
        o.put("inp", inp);
        o.put("tape", new String(tape));
        o.put("decls", decls);
        return o;
    }
    public static JSONArray fromStateList(List<State> states) {
        JSONArray arr = new JSONArray();
        for(State s : states) {
            arr.put(s.toJSONObject());
        }
        return arr;
    }
}