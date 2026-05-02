import java.util.Scanner;

public class Game {

    private Player player;
    private boolean stillPlaying;
    private boolean gateUnlocked;

    private Room library;
    private Room dorm;
    private Room basement;
    private Room gate;

    public Game() {
        this.stillPlaying = true;
        this.gateUnlocked = false;
        this.setupWorld();
    }

    private void setupWorld() {
        // Create rooms
        library = new Room(
            "Neilson Library",
            "You are in Neilson Library. The lights are dim and the building is quiet."
        );

        dorm = new Room(
            "Dorm Hallway",
            "You are in a dorm hallway. There are posters on the wall and a desk by the door."
        );

        basement = new Room(
            "Basement",
            "You are in a dark basement. It smells dusty. There might be something useful here."
        );
        basement.setDark(true);

        gate = new Room(
            "Campus Gate",
            "You are standing in front of the locked campus gate. Freedom is just beyond it."
        );
        gate.setLocked(true);

        // Connect rooms
        library.setExit("east", dorm);
        dorm.setExit("west", library);

        library.setExit("south", basement);
        basement.setExit("north", library);

        library.setExit("north", gate);
        gate.setExit("south", library);

        // Create items
        Item flashlight = new Item(
            "flashlight",
            "A small flashlight. It should help you see in dark places."
        );

        Item note = new Item(
            "note",
            "The note says: \"The key to the gate is hidden below.\""
        );

        Item keycard = new Item(
            "keycard",
            "A campus keycard. It might unlock something important."
        );

        // Place items
        dorm.addItem(flashlight);
        library.addItem(note);
        basement.addItem(keycard);

        // Create player
        player = new Player(library);
    }

    public void play() {
        Scanner input = new Scanner(System.in);

        printWelcome();
        System.out.println(player.getCurrentRoom().getLongDescription());

        while (stillPlaying) {
            System.out.print("\n> ");
            String command = input.nextLine().trim().toLowerCase();
            processCommand(command);
        }

        input.close();
    }

    private void printWelcome() {
        System.out.println("************************************");
        System.out.println("ESCAPE FROM CAMPUS");
        System.out.println("************************************");
        System.out.println("You fell asleep while studying and woke up alone on campus at night.");
        System.out.println("Find a way to unlock the gate and escape.");
        System.out.println("Type HELP for a list of commands.");
    }

    private void processCommand(String command) {
        if (command.equals("help")) {
            printHelp();
        } else if (command.equals("look")) {
            System.out.println(player.getCurrentRoom().getLongDescription());
        } else if (command.startsWith("go ")) {
            String direction = command.substring(3).trim();
            goRoom(direction);
        } else if (command.startsWith("take ")) {
            String itemName = command.substring(5).trim();
            takeItem(itemName);
        } else if (command.equals("inventory")) {
            player.showInventory();
        } else if (command.startsWith("read ")) {
            String itemName = command.substring(5).trim();
            readItem(itemName);
        } else if (command.startsWith("use ")) {
            String itemName = command.substring(4).trim();
            useItem(itemName);
        } else if (command.equals("quit")) {
            System.out.println("You give up and sit back down in the library.");
            stillPlaying = false;
        } else {
            System.out.println("I don't understand that command.");
        }
    }

    private void printHelp() {
        System.out.println("Available commands:");
        System.out.println("  look");
        System.out.println("  go north / go south / go east / go west");
        System.out.println("  take <item>");
        System.out.println("  read <item>");
        System.out.println("  use <item>");
        System.out.println("  inventory");
        System.out.println("  quit");
    }

    private void goRoom(String direction) {
        Room currentRoom = player.getCurrentRoom();
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("You can't go that way.");
            return;
        }

        if (nextRoom.isDark() && !player.hasItem("flashlight")) {
            System.out.println("It's too dark to go in there safely. You need a flashlight.");
            return;
        }

        if (nextRoom == gate && gate.isLocked()) {
            System.out.println("The campus gate is locked. Maybe something can unlock it.");
            return;
        }

        player.setCurrentRoom(nextRoom);
        System.out.println(player.getCurrentRoom().getLongDescription());

        if (player.getCurrentRoom() == gate && gateUnlocked) {
            System.out.println("You swipe the keycard, step through the gate, and escape campus.");
            System.out.println("You win!");
            stillPlaying = false;
        }
    }

    private void takeItem(String itemName) {
        Room currentRoom = player.getCurrentRoom();
        Item item = currentRoom.removeItem(itemName);

        if (item == null) {
            System.out.println("That item is not here.");
        } else {
            player.addItem(item);
            System.out.println("Taken: " + item.getName());
        }
    }

    private void readItem(String itemName) {
        Item item = player.getItem(itemName);

        if (item == null) {
            item = player.getCurrentRoom().getItem(itemName);
        }

        if (item == null) {
            System.out.println("You don't see that here.");
        } else {
            System.out.println(item.getDescription());
        }
    }

    private void useItem(String itemName) {
        if (!player.hasItem(itemName)) {
            System.out.println("You don't have that item.");
            return;
        }

        Room currentRoom = player.getCurrentRoom();

        if (itemName.equals("flashlight")) {
            if (currentRoom == basement) {
                System.out.println("You shine the flashlight around the basement and can see clearly.");
            } else {
                System.out.println("You turn on the flashlight.");
            }
        } else if (itemName.equals("keycard")) {
            if (currentRoom == library) {
                gate.setLocked(false);
                gateUnlocked = true;
                System.out.println("You hear a click from the campus gate to the north. It is now unlocked.");
            } else {
                System.out.println("Nothing happens here.");
            }
        } else {
            System.out.println("You can't use that right now.");
        }
    }
}