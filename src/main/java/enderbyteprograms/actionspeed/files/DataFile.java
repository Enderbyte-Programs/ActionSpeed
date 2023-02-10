package enderbyteprograms.actionspeed.files;

import enderbyteprograms.actionspeed.ActionSpeedMain;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class DataFile {
    public String filename;
    public File folderroot = ActionSpeedMain.INSTANCE.getDataFolder();
    public DataFile (String name) {
        filename = name;
    }
    public boolean Exists() {
        return new File(folderroot,filename).exists();
    }
    public void Write(String text) throws IOException {
        Files.write(new File(folderroot,filename).toPath(), List.of(text.split("\n")));
    }
    public String Read() throws IOException {
        if (!this.Exists()) {
            return null;
        }
        else {
            return Files.readString(new File(folderroot,filename).toPath());
        }
    }
}
