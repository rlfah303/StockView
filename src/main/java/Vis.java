import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;


public class Vis extends JPanel implements MouseListener,MouseMotionListener {

    private ArrayList<ArrayList<Stocks>> stocks;
    Stocks dummy;
    Color newRGB;
    Font onenameFont;
    boolean getOneStock;
    double top;
    double left;
    double width;
    double height;
    double size=0;
    double firstSize=0;
    double secondSize=0;
    double thirdSize=0;
    double colorRatio;

    public Vis() {
        super();
        getOneStock =false;
        dummy = new Stocks("dd",0,0,0,0,0,0);
        onenameFont = new Font ("Courier New", Font.BOLD, 50);
        addMouseMotionListener(this);
        addMouseListener(this);

    }



    @Override
    public void paintComponent(Graphics g1) {
        Graphics2D g = (Graphics2D)g1;


        //draw blank background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        //render visualization
        int w =getWidth();
        int h =getHeight();
        g.setColor(Color.BLACK);


        // getting one stockInfo
        if (getOneStock){
            double d = dummy.getStockRate();
            double dd = dummy.getStockPrice();
            String rr = Double.toString(d);
            String pp = Double.toString(dd);
            setFont(onenameFont);
            colorRatio =getPriceRange(dummy.getStockPrice());
            setBoxColor(dummy,colorRatio);
            g.setColor(newRGB);
            g.fillRect(0,0,w,h);
            g.setColor(Color.WHITE);
            g.drawString(dummy.getStockName(),(int)(w/2.3),(int)(h/2.3));
            g.drawString("Rate: "+rr+"%",(int)(w/2.7),(int)(h/2.3)+70);
            g.drawString("Price: "+ pp,(int)(w/2.7),(int)(h/2.3)+150);

        }else {
            if (!isStockEmpty()){
                setStockSize(stocks);
                setPosition(stocks);
                for(int i =0; i<3;i++){
                    if (stocks.get(i).size()>0){
                        double sx=stocks.get(i).get(0).getLeft();
                        double sy=stocks.get(i).get(0).getTop();
                        for (var v: stocks.get(i)) {

                            // stock rate size
                            double d = v.getStockRate();
                            String srate = Double.toString(d);

                            //price color
                            colorRatio =getPriceRange(v.getStockPrice());

                            //font
                            double fontSize = 10+(v.getWidth()*v.getHeight())/5000;
                            Font nameFont = new Font ("Courier New", Font.BOLD, (int)fontSize);
                            Font rateFont = new Font ("Courier New", Font.BOLD, (int)(fontSize*0.8));

                            setBoxColor(v,colorRatio);
                            //box
                            g.setColor(newRGB);
                            g.fillRect((int)v.getLeft(),(int)v.getTop(),(int)v.getWidth(),(int)v.getHeight());

                            //borders
                            g.setColor(Color.BLACK);
                            g.drawRect((int)v.getLeft(),(int)v.getTop(),(int)v.getWidth(),(int)v.getHeight());

                            //strings
                            g.setColor(Color.WHITE);
                            g.setFont (nameFont);
                            g.drawString(v.getStockName(),(int)(v.getLeft()+v.getWidth()+sx)/2-w/90-(int)fontSize,(int)(v.getHeight()+v.getTop()+sy)/2+h/200);
                            g.setFont (rateFont);
                            if (v.getStockRate()>0){
                                g.drawString(srate+"%",(int)(v.getLeft()+v.getWidth()+sx)/2-w/90-(int)fontSize,(int)(v.getHeight()+v.getTop()+sy)/2+(int)(fontSize*0.85));
                            }else{
                                g.drawString(srate+"%",(int)(v.getLeft()+v.getWidth()+sx)/2-w/90-(int)(fontSize*1.3),(int)(v.getHeight()+v.getTop()+sy)/2+(int)(fontSize*0.85));
                            }
                            sy+=v.getHeight();

                        }
                    }

                }

                setSizetoZero();
            }

        }
        System.out.println("-------------------------reapint-------------------------");
    }

    /**
     * set stock arr
     * @param arr
     */
    public void setStockArray(ArrayList<ArrayList<Stocks>> arr){
        stocks= arr;
    }

    /**
     * setting stock size
     * @param stock
     */
    public void setStockSize(ArrayList<ArrayList<Stocks>> stock){
        for(int i =0; i<3;i++){
            for (var v: stock.get(i)){
                System.out.println(v.getStockName()+": "+v.getStockRate()+",    "+v.getStockPrice());
                if (i==0){
                    firstSize+=Math.abs(v.getStockAbRate());
                }else if (i==1){
                    secondSize+=Math.abs(v.getStockAbRate());
                }else{
                    thirdSize+=Math.abs(v.getStockAbRate());
                }
                size +=Math.abs(v.getStockAbRate());
            }
        }
    }

    /**
     * setting box position
     * @param ss
     */
    public void setPosition(ArrayList<ArrayList<Stocks>> ss){
        top =0;
        left =0;
        int w =getWidth();
        int h =getHeight();
        for(int i =0; i<3;i++){
            if(i==0){
                width = firstSize/size*w;
                height =0;
                for (var v: ss.get(i)) {
                    height=v.getStockAbRate()/firstSize*h;
                    v.setLeft(left);
                    v.setTop(top);
                    v.setWidth(width);
                    v.setHeight(height);
                    System.out.println(v.getStockName()+": "+left+" - "+top+" - "+width+" - "+height);
                    top+= height;

                }
            }else if(i==1){
                top =0;
                left = firstSize/size*w;
                width = secondSize/size*w;
                height =0;
                for (var v: ss.get(i)) {
                    height=v.getStockAbRate()/secondSize*h;
                    v.setLeft(left);
                    v.setTop(top);
                    v.setWidth(width);
                    v.setHeight(height);
                    System.out.println(v.getStockName()+": "+left+" - "+top+" - "+width+" - "+height);
                    top+= height;

                }
            }else {
                top =0;
                left = (secondSize+firstSize)/size*w;
                width = thirdSize/size*w;
                height =0;
                for (var v: ss.get(i)) {
                    height=v.getStockAbRate()/thirdSize*h;
                    v.setLeft(left);
                    v.setTop(top);
                    v.setWidth(width);
                    v.setHeight(height);
                    System.out.println(v.getStockName()+": "+left+" - "+top+" - "+width+" - "+height);
                    top+= height;

                }
            }

        }
    }

    /**
     * setting stock size to zero
     */
    public void setSizetoZero(){
        firstSize =0;
        secondSize =0;
        thirdSize =0;
        size =0;
    }

    /**
     * geting price range
     * @param pr
     * @return
     */
    public double getPriceRange(double pr){
        if(pr<10){
            return pr*5;
        }else if(pr<100){
            return 50+(pr/2);
        }else if (pr<500){
            return 100+(pr/10);
        }else if (pr<1000){
            return 150+(pr/20);
        }else{
            return 200+(pr/100);
        }

    }

    /**
     * getting positon for a box
     * @param x
     * @param y
     */
    public void clickBox(int x, int y){
        for (int i= 0; i<3;i++){
            for (var v: stocks.get(i)) {
                if (x >v.getLeft() && x<(v.getWidth()+v.getLeft()) && y> v.getTop() && y< (v.getHeight()+v.getTop())){
                    System.out.println("this is:"+v.getStockName());
                    dummy =v;
                }

            }
        }
        getOneStock =true;

    }

    /**
     * one stock setter
     * @param getOneStock
     */
    public void setGetOneStock(boolean getOneStock) {
        this.getOneStock = getOneStock;
    }

    /**
     * setting box color
     * @param v
     * @param cr
     */
    public void setBoxColor(Stocks v,double cr){
        if(v.getStockRate()>0){
            newRGB = new Color(30,250-(int)(cr*0.8), 30);
        }else if(v.getStockRate()<0){
            newRGB = new Color(250-(int)(cr*0.8),30, 30);
        }else{
            newRGB = new Color(0,0,120);
        }
    }

    /**
     * checking if stock is empty
     * @return
     */
    public boolean isStockEmpty(){
        int stockTotalSize=0;
        if (stocks==null){
            return true;
        }else{
            for(int i =0; i<3;i++){
                stockTotalSize+=stocks.get(i).size();
            }
            if (stockTotalSize==0){
                return true;
            }else{
                return false;
            }
        }


    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }


    // EBS CCIV AAPL CPNG DISCB KC KVIS
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("Mouse Pressed");
        int x = e.getX();
        int y = e.getY();
        if (stocks!=null){
            clickBox(x,y);
        }
        repaint();

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
