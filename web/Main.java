import org.json.*;
import java.io.*;
import java.util.*;

public class Main {
    public static Map<String, Integer> getlabels(JSONObject o) throws JSONException {
        Map<String, Integer> labels = new HashMap<>();
        for(String key : o.keySet()) {
            labels.put(key, o.getInt(key));
        }
        return labels;
    }
    public static Map<String, Character> getdecls(JSONObject o) throws JSONException {
        Map<String, Character> decls = new HashMap<>();
        for(String key : o.keySet()) {
            decls.put(key, o.getString(key).charAt(0));
        }
        return decls;
    }
    public static void main(String[] args) {
        String err = "";
        String json = "";
        try {
            json = new String(System.in.readAllBytes());
        } catch(IOException e) {
            err = "I/O Error\n";
        } catch(OutOfMemoryError e) {
            err = "Input too large\n";
        }
        JSONObject obj = new JSONObject();
        try {
            obj = new JSONObject(json);
        } catch (JSONException e) {
            err += "Malformed JSON\n";
        }
        String input = "";
        String prog = "";
        int ip = 0;
        int inp = 0;
        Map<String, Integer> labels = new HashMap<>();
        Map<String, Character> decls = new HashMap<>();
        try {
            input = obj.getString("input");
            prog = obj.getString("program");
            ip = obj.getInt("ip");
            inp = obj.getInt("inp");
            labels = getlabels(obj.getJSONObject("labels"));
            decls = getdecls(obj.getJSONObject("decls"));
        } catch (JSONException e) {
            err += "JSON does not contain necessary fields\n";
        }

        Parser p = new Parser();
        List<Instruction> il = p.parse(prog);
        String perr = p.err();
        err += perr;

        List<State> states = new ArrayList<>();
        if (perr.length() == 0) {
            Simulator sim = new Simulator(il, ip, inp, labels, decls);
            states = sim.simulate1001(input.toCharArray());
            err += sim.err();
        }

        JSONObject o = new JSONObject();
        o.put("err", err);
        o.put("states", State.fromStateList(states));

        try {
            OutputStreamWriter osw = new OutputStreamWriter(System.out);
            o.write(osw);
            osw.close();
        } catch(JSONException je) {
            System.exit(1);
        } catch(IOException ie) {
            System.exit(1);
        }
    }
}