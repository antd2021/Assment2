public class Product {
    private String id;
    private String productName;
    private String productDesc;
    private double productCost;

    //Constructures


    public Product(String id, String productName, String productDesc, double productCost) {
        this.id = id;
        this.productName = productName;
        this.productDesc = productDesc;
        this.productCost = productCost;
    }

    //Getters/Setters


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public double getProductCost() {
        return productCost;
    }

    public void setProductCost(double productCost) {
        this.productCost = productCost;
    }


    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", productName='" + productName + '\'' +
                ", productDesc='" + productDesc + '\'' +
                ", productCost=" + productCost +
                '}';
    }

    public String toCSV(){
        return id + ", " + productName + ", " + productDesc + ", " + productCost;
    }
}
