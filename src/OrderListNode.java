public class OrderListNode {

    private Order data;
    private OrderListNode next;
    private OrderListNode prev;

    /**
     * OrderListNode method
     * <p>
     * Creates a new OrderListNode with the order data provided
     * and sets the head and tail of this node to null
     *
     * @param initData -> Order data
     */
    public OrderListNode(Order initData) {
        if (initData == null)
            throw new IllegalArgumentException();

        data = initData;
        next = null;
        prev = null;
    }

    /**
     * Get Data: Getter
     *
     * @return -> Data String
     */
    public Order getData() {
        return data;
    }

    /**
     * Set Data: Setter
     */
    public void setData(Order data) {
        this.data = data;
    }

    /**
     * Get Next: Getter
     *
     * @return -> next OrderListNode
     */
    public OrderListNode getNext() {
        return next;
    }

    /**
     * Set Next: Setter
     */
    public void setNext(OrderListNode next) {
        this.next = next;
    }

    /**
     * Get Previous: Getter
     *
     * @return -> previous OrderListNode
     */
    public OrderListNode getPrev() {
        return prev;
    }

    /**
     * Set Previous: Setter
     */
    public void setPrev(OrderListNode prev) {
        this.prev = prev;
    }

}
