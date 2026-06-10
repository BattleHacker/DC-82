package dc82.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.SerializationException;
import dc82.model.Race;
import dc82.model.SaveSlot;
import dc82.model.Settings;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SaveManager {

    private static final String SAVES_DIR = "saves";
    private static final String INDEX_FILE = "index.json";
    private static final String SLOT_PREFIX = "slot_";
    private static final String SLOT_SUFFIX = ".json";
    private static final String SETTINGS_FILE = "settings.json";

    private final Json json;

    public SaveManager() {
        this.json = new Json();
        json.setOutputType(com.badlogic.gdx.utils.JsonWriter.OutputType.json);
        json.setSerializer(Race.class, new Json.Serializer<Race>() {
            @Override
            public void write(Json json, Race race, Class knownType) {
                json.writeValue(race.getName());
            }
            @Override
            public Race read(Json json, JsonValue jsonData, Class type) {
                return Race.getRace(jsonData.asString());
            }
        });
        Gdx.files.local(SAVES_DIR).mkdirs();
    }

    public List<SaveSlot> getSaveSlots() {
        List<SaveSlot> slots = readIndex();
        slots.sort(Comparator.comparingLong((SaveSlot s) -> s.updatedAt).reversed());
        return slots;
    }

    public void saveSlot(SaveSlot slot) {
        List<SaveSlot> slots = readIndex();

        boolean exists = false;
        for (int i = 0; i < slots.size(); i++) {
            if (slots.get(i).id.equals(slot.id)) {
                slot.createdAt = slots.get(i).createdAt;
                slots.set(i, slot);
                exists = true;
                break;
            }
        }
        if (!exists) {
            slots.add(slot);
        }
        writeIndex(slots);

        FileHandle slotFile = slotFile(slot.id);
        slotFile.writeString(json.prettyPrint(slot), false);
    }

    public void deleteSlot(String slotId) {
        List<SaveSlot> slots = readIndex();
        slots.removeIf(s -> s.id.equals(slotId));
        writeIndex(slots);

        FileHandle slotFile = slotFile(slotId);
        if (slotFile.exists()) {
            slotFile.delete();
        }
    }

    public SaveSlot getLatestSlot() {
        List<SaveSlot> slots = getSaveSlots();
        return slots.isEmpty() ? null : slots.get(0);
    }

    public boolean hasSaves() {
        return !readIndex().isEmpty();
    }

    public Settings loadSettings() {
        FileHandle file = Gdx.files.local(SETTINGS_FILE);
        if (!file.exists()) {
            return new Settings();
        }
        try {
            return json.fromJson(Settings.class, file);
        } catch (SerializationException e) {
            return new Settings();
        }
    }

    public void saveSettings(Settings settings) {
        Gdx.files.local(SETTINGS_FILE).writeString(json.prettyPrint(settings), false);
    }

    private List<SaveSlot> readIndex() {
        FileHandle indexFile = Gdx.files.local(SAVES_DIR + "/" + INDEX_FILE);
        if (!indexFile.exists()) {
            return new ArrayList<>();
        }
        try {
            String text = indexFile.readString();
            JsonValue root = new JsonReader().parse(text);
            List<SaveSlot> slots = new ArrayList<>();
            for (JsonValue entry : root) {
                slots.add(json.readValue(SaveSlot.class, entry));
            }
            return slots;
        } catch (SerializationException e) {
            return new ArrayList<>();
        }
    }

    private void writeIndex(List<SaveSlot> slots) {
        FileHandle indexFile = Gdx.files.local(SAVES_DIR + "/" + INDEX_FILE);
        indexFile.writeString(json.prettyPrint(slots), false);
    }

    private FileHandle slotFile(String slotId) {
        return Gdx.files.local(SAVES_DIR + "/" + SLOT_PREFIX + slotId + SLOT_SUFFIX);
    }
}
