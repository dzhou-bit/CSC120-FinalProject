import java.util.ArrayList;

public class Player {

    private Room currentRoom;
    private ArrayList<Item> inventory;

    public Player(Room startingRoom) {
        this.currentRoom = startingRoom;
        this.inventory = new ArrayList<Item>();
    }

    public Room getCurrentRoom() {
        return this.currentRoom;
    }

    public void setCurrentRoom(Room newRoom) {
        this.currentRoom = newRoom;
    }

    public void addItem(Item item) {
        this.inventory.add(item);
    }

    public boolean hasItem(String itemName) {
        return this.getItem(itemName) != null;
    }

    public Item getItem(String itemName) {
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    public void showInventory() {
        if (inventory.size() == 0) {
            System.out.println("Your inventory is empty.");
            return;
        }

        System.out.println("You are carrying:");
        for (Item item : inventory) {
            System.out.println("- " + item.getName());
        }
    }
}