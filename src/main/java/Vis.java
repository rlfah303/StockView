import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.swing.JPanel;


public class Vis extends JPanel implements MouseListener,MouseMotionListener {

    String orientation;
    Node root;
    double top;
    double left;
    double width;
    double height;
    private Ellipse2D.Double seth;
    private ArrayList<ArrayList<Stocks>> stocks;
    double size=0;
    double firstSize=0;
    double secondSize=0;
    double thirdSize=0;
    Color newRGB;

    public Vis() {
        super();
        seth = new Ellipse2D.Double(50, 100, 40, 40);

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
        orientation = "HORIZONTAL";
        g.setColor(Color.BLACK);
        double rate=0;
        double boxWidth;
        double boxHeight;
        // x,y,w,h 잡
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
                        double cr =getPriceRange(v.getStockPrice());

                        //font
                        double fontSize = 10+(v.getWidth()*v.getHeight())/5000;
                        Font nameFont = new Font ("Courier New", Font.BOLD, (int)fontSize);
                        Font rateFont = new Font ("Courier New", Font.BOLD, (int)(fontSize*0.8));


                        if(v.getStockRate()>0){
                            newRGB = new Color(30,250-(int)(cr*0.8), 30);
                        }else if(v.getStockRate()<0){
                            newRGB = new Color(250-(int)(cr*0.8),30, 30);
                        }else{
                            newRGB = new Color(0,0,120);
                        }
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
        System.out.println("-------------------------reapint-------------------------");
    }

    public void setStockArray(ArrayList<ArrayList<Stocks>> arr){
        stocks= arr;
        System.out.println("Setting to new array");
    }

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
        System.out.println("Reset stockSise");
//        System.out.println("firstSize: "+firstSize);
//        System.out.println("secondSize: "+secondSize);
//        System.out.println("thirdSize: "+thirdSize);
//        System.out.println("totalSize: "+size);
    }
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
        System.out.println("Reset stockposition");
    }
    public void setSizetoZero(){
        firstSize =0;
        secondSize =0;
        thirdSize =0;
        size =0;
    }

    // 0 10: 50
    // 10 100: 50 100
    // 100 500 100 150
    //500 1000 150 200
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
    public void setLive(){
        System.out.println("setlive******************************");

    }





    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (root!=null){
            String result = root.getNodeAt(x,y).path;
            setToolTipText(result);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        if (x>0){
            setLive();

        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

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
