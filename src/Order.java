public class Order {

    private String order;
    private String specialInstruction;
    private double price;

    /**
     * Order Method:
     * <p>
     * Creates a new order with the specified parameters of name,
     * specialInstructions, and price.
     *
     * @param name               -> Name
     * @param specialInstruction -> Special Instructions
     * @param price              -> Price
     */
    public Order(String name, String specialInstruction, double price) {
        order = name;
        this.specialInstruction = specialInstruction;
        this.price = price;
    }

    /**
     * Equals Method:
     * <p>
     * Check if an obejct is equal to another object
     * by comparing it's type and then it's attributes
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof Order))
            return false;
        Order tempOrder = (Order) obj;

        return tempOrder.order == this.order &&
                tempOrder.price == this.price;
    }

    /**
     * Get Order: Getter
     *
     * @return -> Order String
     */
    public String getOrder() {
        return order;
    }

    /**
     * Get Special Instructions: Getter
     *
     * @return -> Special Instructions String
     */
    public String getSpecialInstruction() {
        return specialInstruction;
    }

    /**
     * Get Price: Getter
     *
     * @return -> Price Double
     */
    public double getPrice() {
        return price;
    }

}
