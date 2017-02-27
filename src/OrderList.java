public class OrderList {

    private OrderListNode head;
    private OrderListNode tail;
    private OrderListNode cursor;
    private int orderCounter;

    /**
     * OrderList Method:
     * Creates a new OrderList with specified attributes
     */
    public OrderList() {
        this.head = null;
        this.tail = null;
        this.cursor = null;
    }

    /**
     * Get Head: Getter
     *
     * @return -> Head OrderListNode
     */
    public OrderListNode getHead() {
        return head;
    }

    /**
     * Set Head: Setter
     */
    public void setHead(OrderListNode head) {
        this.head = head;
    }


    /**
     * Get Tail: Getter
     *
     * @return -> Tail OrderListNode
     */
    public OrderListNode getTail() {
        return tail;
    }

    /**
     * Get Cursor: Getter
     *
     * @return -> Cursor OrderListNode
     */
    public OrderListNode getCursor() {
        return cursor;
    }

    /**
     * Number of Orders Method:
     * O(1)
     *
     * @return the number of orders in the linked list
     */
    public int numOrders() {
        return orderCounter;
    }

    /**
     * Get Cursor Order Method
     *
     * @return the "data" of the current cursor
     */
    public Order getCursorOrder() {
        if (cursor == null)
            return null;

        return cursor.getData();
    }

    /**
     * Reset Cursor to Head:
     * <p>
     * Resets cursor to the head of the list.
     */
    public void resetCursorToHead() {
        if (head != null) {
            cursor = head;
            System.out.println("Cursor has been moved to the head");
        } else if (head == null) {
            cursor = null;
            System.out.println("There is currently no head");
        }
    }

    /**
     * Reset Cursor to Tail:
     * <p>
     * Resets cursor to the tail of the list.
     */
    public void resetCursorToTail() {
        if (tail != null) {
            cursor = tail;
            System.out.println("Cursor has been moved to the tail");
        } else if (tail == null) {
            cursor = null;
            System.out.println("There is currently no tail");
        }
    }

    /**
     * Cursor Forward:
     * <p>
     * Moves the cursor forward one node if allowed
     *
     * @throws EndOfListException if the cursor is trying to move forward but
     *                            is already at the end of the list
     */
    public void cursorForward() {
        try {
            if ((cursor == tail) || (cursor == null && tail == null))
                throw new EndOfListException();
            else {
                cursor = cursor.getNext();
                System.out.println("Cursor has been moved forward");
            }

        } catch (EndOfListException ex) {
            System.out.println("Cursor is already at the tail of the list");
        }
    }

    /**
     * Cursor Backward:
     * <p>
     * Moves the cursor backwards one node if allowed
     *
     * @throws EndOfListException if the cursor is trying to move backwards but
     *                            is already at the beginning of the list
     */
    public void cursorBackward() {
        try {
            if ((cursor == head) || (cursor == null && head == null))
                throw new EndOfListException();
            else {
                cursor = cursor.getPrev();
                System.out.println("Cursor has been moved backward");
            }

        } catch (EndOfListException ex) {
            System.out.println("Cursor is already at the head of the list");
        }
    }

    /**
     * Insert after Cursor:
     * <p>
     * Creates a new node which the newOrder parameters and
     * appends the new node after the current cursor position
     *
     * @param newOrder -> new order
     * @throws IllegalArgumentException if the new order is null
     */
    public void insertAfterCursor(Order newOrder) {
        if (newOrder == null)
            throw new IllegalArgumentException();

        OrderListNode tempNode = new OrderListNode(newOrder);

        if (cursor != null) {
            if (cursor == tail) {
                cursor.setNext(tempNode);
                tempNode.setPrev(cursor);
                tail = tempNode;
            } else {
                tempNode.setNext(cursor.getNext());
                cursor.getNext().setPrev(tempNode);
                cursor.setNext(tempNode);
                tempNode.setPrev(cursor);
            }
        } else {
            head = tempNode;
            tail = tempNode;
        }
        orderCounter++;
        cursor = tempNode;
    }

    /**
     * Insert After Similar Node:
     * <p>
     * Traverses through the linked list in search for
     * a node which is similar in terms of Order name and if one is
     * found, appends it after the current cursor position. If it isn't
     * found, appends it at the end of the list
     *
     * @param newOrder -> new order
     */
    public void insertAfterSimilarOrder(Order newOrder) {
        if (newOrder == null)
            throw new IllegalArgumentException();

        if (cursor == null) {
            insertAfterCursor(newOrder);
            return;
        }

        //Iterate through list to find similar order
        for (cursor = head; cursor != null; cursor = cursor.getNext()) {

            //Found similar order
            if (cursor.getData().getOrder().equalsIgnoreCase(newOrder.getOrder())) {
                insertAfterCursor(newOrder);
                break;
            }

            //Reached end of list and not found
            else if (cursor.getNext() == null) {
                insertAfterCursor(newOrder);
                break;
            }
        }
    }

    /**
     * Append to head:
     * <p>
     * Creates a temporary node which has the information of
     * a new order and sets the new node to the head of the list
     *
     * @param newOrder -> new order
     * @throws IllegalArgumentException when the new order is null
     */
    public void appendToHead(Order newOrder) {
        if (newOrder == null)
            throw new IllegalArgumentException();

        OrderListNode tempNode = new OrderListNode(newOrder);

        if (head != null) {
            head.setPrev(tempNode);
            tempNode.setNext(head);
            head = tempNode;
        } else if (head == null) {
            head = tempNode;
            tail = tempNode;
            cursor = tempNode;
        }
        orderCounter++;
    }

    /**
     * Append to tail:
     * <p>
     * Creates a temporary node which has the information of
     * a new order and sets the new node to the tail of the list
     *
     * @param newOrder -> new order
     * @throws IllegalArgumentException when the new order is null
     */
    public void appendToTail(Order newOrder) {
        if (newOrder == null)
            throw new IllegalArgumentException();

        OrderListNode tempNode = new OrderListNode(newOrder);

        if (tail != null) {
            tail.setNext(tempNode);
            tempNode.setPrev(tail);
            tail = tempNode;
        } else if (tail == null) {
            head = tempNode;
            tail = tempNode;
            cursor = tempNode;
        }
        orderCounter++;
    }

    /**
     * Remove Cursor:
     * <p>
     * Removes the current node which is pointed by the cursor
     *
     * @return the Order which was removed
     * @throws EndOfListException when the cursor is null
     */
    public Order removeCursor() {
        try {
            if (cursor == null)
                throw new EndOfListException();

            OrderListNode tempNode = cursor;

            if (cursor == tail && cursor == head) {
                head = null;
                tail = null;
                cursor = null;
                orderCounter--;
                return tempNode.getData();
            } else if (cursor == tail) {
                tail = cursor.getPrev();
                cursor = tail;
                cursor.setNext(null);
                orderCounter--;
                return tempNode.getData();
            } else if (cursor == head) {
                head = cursor.getNext();
                cursor = head;
                cursor.setPrev(null);
                orderCounter--;
                return tempNode.getData();
            } else {
                cursor.getPrev().setNext(cursor.getNext());
                cursor.getNext().setPrev(cursor.getPrev());
                cursor = cursor.getPrev();
                orderCounter--;
                return tempNode.getData();
            }

        } catch (EndOfListException ex) {
            System.out.println("Cursor is null");
        } catch (NullPointerException ex) {
        }

        return cursor.getData();
    }


}
