import java.util.HashMap;
import java.util.ArrayList;

public class Room {

    private String name;
    private String description;
    private HashMap<String, Room> exits;
    private ArrayList<Item> items;
    private boolean dark;
    private boolean locked;

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
        this.exits = new HashMap<String, Room>();
        this.items = new ArrayList<Item>();
        this.dark = false;
        this.locked = false;
    }

    public String getName() {
        return this.name;
    }

    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    public Room getExit(String direction) {
        return exits.get(direction);
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public Item removeItem(String itemName) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getName().equalsIgnoreCase(itemName)) {
                return items.remove(i);
            }
        }
        return null;
    }

    public Item getItem(String itemName) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    public void setDark(boolean dark) {
        this.dark = dark;
    }

    public boolean isDark() {
        return this.dark;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isLocked() {
        return this.locked;
    }

    public String getLongDescription() {
        String message = "\n" + this.name + "\n" + this.description;

        if (items.size() > 0) {
            message += "\nYou see:";
            for (Item item : items) {
                message += "\n- " + item.getName();
            }
        }

        message += "\nExits:";
        for (String direction : exits.keySet()) {
            message += " " + direction;
        }

        return message;
    }
}