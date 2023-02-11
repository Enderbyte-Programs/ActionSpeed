package enderbyteprograms.actionspeed.files;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConfigFile extends DataFile {

    public ConfigFile(String name) {
        super(name);
    }
    public HashMap<String,Object> Load() throws IOException {
        HashMap hm = new HashMap<String,String>();
        String raw = this.Read();
        for (String s:raw.split("\n")) {
            String head = s.split("=")[0];
            String foot = s.split("=")[1];
            hm.put(head,foot);
        }
        return hm;
    }
    public void Dump(HashMap<String,Object> indata) throws IOException {
        String master = "";
        for (Map.Entry<String, Object> entry : indata.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            // ...
            master += key;
            master += "=";
            master += value.toString();
            master += "\n";
        }
        Write(master);

    }
}
