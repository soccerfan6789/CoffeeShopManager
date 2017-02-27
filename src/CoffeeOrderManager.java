import java.util.Scanner;

public class CoffeeOrderManager {

    private static OrderList barista1 = new OrderList();
    private static OrderList barista2 = new OrderList();

    private static Order cut;
    private static int alternate = 0;
    private static Scanner input;

    public static void main(String[] args) {

        System.out.println("Welcome to my Coffee Shop!\n");

        System.out.print("Menu:\n");
        menu();

        //Choice Menu
        char choice;
        while (true) {
            System.out.print("\nPlease select an option: ");
            choice = validateChar();

            switch (choice) {
                case 'o':
                    main_order();
                    break;
                case 'p':
                    main_printOrderLists();
                    break;
                case 'e':
                    main_extraCredit();
                    break;
                case 'c':
                    main_cursorOptions();
                    break;
                case 'q':
                    System.out.print("\nThank you for visiting!");
                    System.exit(0);
                default:
                    System.out.print("\nInvalid choice. Here's the menu:\n");
                    menu();
            }
        }
    }

    /**
     * Order Function:
     * <p>
     * Requests new order information, creates a node of that information,
     * and appends the node to the list depending on where the user would
     * like to place it
     */
    private static void main_order() {
        String name, specialInstruction, option;
        double price;
        int barista;

        System.out.print("Please enter drink name: ");
        name = validate(0);

        System.out.print("Enter any special requests: ");
        specialInstruction = input.nextLine();

        System.out.print("Enter your total price: ");
        price = Double.parseDouble(validate(2));

        System.out.print("Select your Barista (1 or 2): ");
        barista = Integer.parseInt(validate(3));

        System.out.print(
                "\nWhere should the order be added? \n" +
                        "\tF) Front of List\n" +
                        "\tB) Back of List\n" +
                        "\tA) After Cursor\n" +
                        "\tS) After Similar Order (default: end of list)\n" +
                        "\nSelect an option: ");
        option = validate(4);

        switch (option) {
            case "f":
                getCurrentBarista(barista).appendToHead(
                        new Order(name, specialInstruction, price));
                break;
            case "b":
                getCurrentBarista(barista).appendToTail(
                        new Order(name, specialInstruction, price));
                break;
            case "a":
                getCurrentBarista(barista).insertAfterCursor(
                        new Order(name, specialInstruction, price));
                break;
            case "s":
                getCurrentBarista(barista).insertAfterSimilarOrder(
                        new Order(name, specialInstruction, price));
                break;
        }
    }

    /**
     * Helper method to print out both baristas orders
     */
    private static void main_printOrderLists() {
        print(barista1);
        System.out.println();
        print(barista2);
    }

    /**
     * Print Method:
     * <p>
     * Gets the necessary information of the list and traverses
     * through the list and prints out each nodes order information.
     *
     * @param list -> Barista
     */
    private static void print(OrderList list) {
        OrderListNode head = list.getHead();
        OrderListNode cursor = head;
        OrderListNode position = list.getCursor();

        System.out.println("\n\tBarista " + getBaristaNumber(list) + ":");
        System.out.printf("\t%-15s %-35s %-20s",
                "Order Name", "Special Instructions", "Price");
        System.out.println("\n\t" +
                "----------------------------------------------------------");

        if (cursor == null) {
            System.out.println("\t[empty]\n");
        } else {
            for (cursor = head; cursor != null; cursor = cursor.getNext()) {

                if (position == cursor)
                    System.out.print("->");

                System.out.printf("\t%-15s %-35s %-20.2f\n",
                        cursor.getData().getOrder(),
                        cursor.getData().getSpecialInstruction(),
                        cursor.getData().getPrice());
            }
        }
    }

    /**
     * Extra Credit Functions:
     * <p>
     * Allows the user to reverse the list or merge both
     * lists together and store the orders in a list
     */
    private static void main_extraCredit() {
        String option;
        int barista;

        System.out.print("Select your Barista (1 or 2): ");
        barista = Integer.parseInt(validate(3));

        System.out.print(
                "\nExtra Credit Options: \n" +
                        "\tR) Reverse\n" +
                        "\tM) Merge\n" +
                        "\nSelect an option: ");

        option = validate(6);

        switch (option) {
            case "r":
                try {
                    reverse(getCurrentBarista(barista));
                } catch (IllegalArgumentException ex) {
                    System.out.println("Cannot reverse on empty list");
                }
                break;
            case "m":
                try {
                    merge(barista1, barista2, barista);
                } catch (IllegalArgumentException ex) {
                    System.out.println("Cannot merge two empty lists");
                }
                break;

        }
    }

    /**
     * Merge Function:
     * <p>
     * First creates cursors and a new barista where the all the orders
     * will be placed into. Get's the heads of each list and sets them to
     * the appropriate cursor. Goes into the while loop to check if the
     * current cursor is null and appends the nodes in a alternate fashion
     * to barista 3.
     *
     * @param list1
     * @param list2
     * @param barista
     * @throws IllegalArgumentException -> Both lists are empty
     */
    public static void merge(OrderList list1, OrderList list2, int barista) {
        OrderListNode cursor1 = list1.getHead();
        OrderListNode cursor2 = list2.getHead();
        OrderList barista3 = new OrderList();

        alternate = 0;

        if (cursor1 == null && cursor2 == null) {
            throw new IllegalArgumentException();
        }

        while (cursor1 != null || cursor2 != null) {
            try {
                if (cursor1 == null) {
                    barista3.insertAfterCursor(cursor2.getData());
                    cursor2 = cursor2.getNext();
                }
                if (cursor2 == null) {
                    barista3.insertAfterCursor(cursor1.getData());
                    cursor1 = cursor1.getNext();
                }
                if (alternate % 2 == 0) {
                    barista3.insertAfterCursor(cursor1.getData());
                    cursor1 = cursor1.getNext();
                } else if (alternate % 2 != 0) {
                    barista3.insertAfterCursor(cursor2.getData());
                    cursor2 = cursor2.getNext();
                }
                alternate++;
            } catch (NullPointerException ex) {
            }
        }

        getCurrentBarista(barista).setHead(barista3.getHead());
        if (barista == 1)
            list2.setHead(null);
        else if (barista == 2)
            list1.setHead(null);
    }

    /**
     * Reverse Function:
     * <p>
     * Creates a node called head and temp. Until head
     * is not equal to null, aka the list is at the end, continue.
     * Sets the temp node to the next head node. Head's next node is
     * its previous. Set the previous head to temp. Check if head's previous
     * is null and id so set it as the head of the list.
     *
     * @param list
     */
    public static void reverse(OrderList list) {
        OrderListNode head = list.getHead();
        OrderListNode temp = null;

        if (head == null)
            throw new IllegalArgumentException();

        list.resetCursorToHead();

        while (head != null) {
            temp = head.getNext();
            head.setNext(head.getPrev());
            head.setPrev(temp);

            if (head.getPrev() == null)
                list.setHead(head);

            head = head.getPrev();
        }
    }

    /**
     * Cursor Options:
     * <p>
     * Allows the user to modify the current cursors
     * position as well as cut, paste, and remove nodes.
     */
    private static void main_cursorOptions() {
        String option;
        int barista;

        System.out.print("Select your Barista (1 or 2): ");
        barista = Integer.parseInt(validate(3));

        System.out.print(
                "\nCursor Options: \n" +
                        "\tF) Forward\n" +
                        "\tB) Backward\n" +
                        "\tH) To Head\n" +
                        "\tT) To Tail\n" +
                        "\tR) Remove\n" +
                        "\tC) Cut\n" +
                        "\tP) Paste\n" +
                        "\nSelect an option: ");

        option = validate(5);

        switch (option) {
            case "f":
                getCurrentBarista(barista).cursorForward();
                break;
            case "b":
                getCurrentBarista(barista).cursorBackward();
                break;
            case "h":
                getCurrentBarista(barista).resetCursorToHead();
                break;
            case "t":
                getCurrentBarista(barista).resetCursorToTail();
                break;
            case "r":
                try {
                    String order = getCurrentBarista(barista).getCursorOrder().getOrder();
                    getCurrentBarista(barista).removeCursor();
                    System.out.println(order + " has been removed");
                } catch (NullPointerException ex) {
                    System.out.println("There is no order to remove");
                }
                break;
            case "c":
                try {
                    if (cut != null)
                        throw new IllegalArgumentException();
                    cut = getCurrentBarista(barista).getCursor().getData();
                    getCurrentBarista(barista).removeCursor();
                    System.out.println(cut.getOrder() + " is in clipboard");
                } catch (NullPointerException ex) {
                    System.out.println("Can't cut on empty list");
                } catch (IllegalArgumentException ex) {
                    System.out.println(cut.getOrder() + " is currently in the clipboard");
                }
                break;
            case "p":
                if (cut == null)
                    System.out.println("Nothing to paste");
                else {
                    getCurrentBarista(barista).insertAfterCursor(cut);
                    System.out.println(cut.getOrder() + " was pasted " +
                            " after list " + barista + " cursor");
                    cut = null;
                }
                break;
        }
    }

    /**
     * Menu Options
     */
    private static void menu() {
        System.out.println(
                "O) Order\n" +
                        "P) Print Order Lists\n" +
                        "E) Extra Credit Functions\n" +
                        "C) Cursor Options\n" +
                        "Q) Quit");
    }

    /**
     * Validats any of the above inputs depending
     * on the situation at hand
     *
     * @param type
     * @return
     */
    private static String validate(int type) {
        input = new Scanner(System.in);
        String testCase;
        int testInteger;
        double testDouble;

        if (type == 0) { //Validate string
            testCase = input.nextLine();
            if (testCase.matches("^[a-zA-Z\\s]+$"))
                return testCase;
            else {
                System.out.print("Try again: ");
                return validate(0);
            }
        } else if (type == 1) { //Validate integer
            try {
                testCase = input.nextLine();
                testInteger = Integer.parseInt(testCase);
                return testCase;
            } catch (NumberFormatException ex) {
                System.out.print("Try again: ");
                return validate(1);
            }
        } else if (type == 2) { //Validate price
            try {
                testCase = input.nextLine();
                testDouble = Double.parseDouble(testCase);
                return testCase;
            } catch (NumberFormatException ex) {
                System.out.print("Try again: ");
                return validate(2);
            }
        } else if (type == 3) { //Validate barista
            try {
                testCase = input.nextLine();
                if (!testCase.matches("([1-2])"))
                    throw new NumberFormatException();
                testInteger = Integer.parseInt(testCase);
                return testCase;
            } catch (NumberFormatException ex) {
                System.out.print("Try again: ");
                return validate(3);
            }
        } else if (type == 4) { //Validate character for options
            try {
                testCase = input.nextLine();
                if (testCase.length() > 1)
                    throw new IllegalArgumentException();
                if (!testCase.toLowerCase().matches("([fabs])"))
                    throw new IllegalArgumentException();
                return testCase.toLowerCase();
            } catch (NumberFormatException ex) {
                System.out.print("Try again: ");
                return validate(4);
            } catch (IllegalArgumentException ex) {
                System.out.print("Try again: ");
                return validate(4);
            }
        } else if (type == 5) { //Validate character for cursor
            try {
                testCase = input.nextLine();
                if (testCase.length() > 1)
                    throw new IllegalArgumentException();
                if (!testCase.toLowerCase().matches("([fbhtrcp])"))
                    throw new IllegalArgumentException();
                return testCase.toLowerCase();
            } catch (NumberFormatException ex) {
                System.out.print("Try again: ");
                return validate(5);
            } catch (IllegalArgumentException ex) {
                System.out.print("Try again: ");
                return validate(5);
            }
        } else if (type == 6) { //Validate character for cursor
            try {
                testCase = input.nextLine();
                if (testCase.length() > 1)
                    throw new IllegalArgumentException();
                if (!testCase.toLowerCase().matches("([mr])"))
                    throw new IllegalArgumentException();
                return testCase.toLowerCase();
            } catch (NumberFormatException ex) {
                System.out.print("Try again: ");
                return validate(6);
            } catch (IllegalArgumentException ex) {
                System.out.print("Try again: ");
                return validate(6);
            }
        }

        return "";
    }

    /**
     * Get Current Barista
     * <p>
     * Helper method which returns the current barista
     * depending on the integer provided as the argument
     *
     * @param currentBarista integer
     * @return OrderList
     */
    private static OrderList getCurrentBarista(int currentBarista) {
        if (currentBarista == 1)
            return barista1;
        else
            return barista2;
    }

    /**
     * Get Barista Number
     * <p>
     * Helper method which returns an integer depending
     * on the OrderList which was provided as an argument
     *
     * @param list
     * @return integer
     */
    private static int getBaristaNumber(OrderList list) {
        if (list == barista1)
            return 1;
        else
            return 2;
    }

    /**
     * Helper method to determine if the character provided
     * is valid
     *
     * @return character
     */
    private static char validateChar() {
        input = new Scanner(System.in);
        return input.next().toLowerCase().charAt(0);
    }
}
