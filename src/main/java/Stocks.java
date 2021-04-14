import java.util.ArrayList;


public class Stocks {
    private String stockName;
    private double stockRate;

    public void setStockRate(double stockRate) {
        this.stockRate = stockRate;
    }

    public void setStockPrice(double stockPrice) {
        this.stockPrice = stockPrice;
    }

    private double stockPrice;
    private double left;
    private double width;
    private double height;


    public Stocks(){
        super();

    }
    public Stocks(String n, double r, double p, double x,double y,double w, double h){
        stockName= n;
        stockRate =r;
        stockPrice =p;
        top = x;
        left = y;
        width =x;
        height =x;

    }



    public double getStockRate() {
        return stockRate;
    }
    public double getStockAbRate() {
        return Math.abs(stockRate);
    }
    public String getStockName() {
        return stockName;
    }
    public double getStockPrice() {
        return stockPrice;
    }
    public double getTop() {
        return top;
    }

    public double getLeft() {
        return left;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    private double top;

    public void setTop(double top) {
        this.top = top;
    }

    public void setLeft(double left) {
        this.left = left;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

}
