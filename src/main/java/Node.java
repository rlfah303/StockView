import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.nio.*;

public class Node {

    String orientation;
    String name ="";
    String path ="";
    File file;
    Color dated;
    Color typed;
    Color randomed;
    String lastModifiedColor;
    String mode;
    double redRandom;
    double greenRandom;
    double blueRandom;
    int size =0;
    long lastModifiedDate;
    private ArrayList<Node> children;
    int top;
    int left;
    int width;
    int height;
    private  ArrayList<Stocks> stocks;
    Stocks aaaa;
    Stocks bbbb;
    Stocks cccc;
    Stocks dddd;
    Stocks ffff;


    public Node(){
        super();

    }

    public  Node(Stocks s, String lmc){
//        file =f;
//        name= f.getName();
//        path= f.getPath();
        mode=lmc;
        lastModifiedDate=file.lastModified()%255;
        setTypedColor(path);
        setRandomColor();
        dated= new Color((int)lastModifiedDate, (int)lastModifiedDate, (int)lastModifiedDate);
        top =0;
        left =0;
        width =0;
        height =0;

//        children = new ArrayList<>();
//        if (f.isFile()){
//            size =(int)f.length();
//        }else{
//            try{
//                File[] kids = f.listFiles();
//                for(var c: kids){
//                    Node a =new Node(c,lmc);
//                    children.add(a);
//                    size+=a.getSize();
//                }
//            }catch (Exception e){
//
//            }
//
//        }

    }


    /**
     * drawing tables
     * @param g
     * @param x
     * @param y
     * @param w
     * @param h
     * @param o
     */
//    public void draw(Graphics2D g, double x,double y,double w,double h, String o){
//        this.left = (int)x;
//        this.top = (int)y;
//        this.width = (int)w;
//        this.height = (int)h;
//        if(this.file.isFile()){
//            if(mode.equals("last")){
//                g.setColor(this.dated);
//            }else if(mode.equals("type")){
//                g.setColor(this.typed);
//            }else if(mode.equals("random")){
//                g.setColor(this.randomed);
//            }
//            else{
//                g.setColor(Color.WHITE);
//            }
//            g.fillRect((int)x,(int)y,(int)w,(int)h);
//        }
//        else{
//            if(o.equals("HORIZONTAL")){
//                double pixelByte = w/size;
//                for (var child:stocks){
//                    double childWidth = pixelByte*child.stockRate;
//                    child.draw(g,x,y,childWidth,h,"VERTICAL");
//                    x+= childWidth+1;
//                }
//
//            }else{
//                double pixelByte = h/size;
//                for (var child:children){
//                    double childHeight = pixelByte*child.size;
//                    child.draw(g,x,y,w,childHeight,"HORIZONTAL");
//                    y+= childHeight+1;
//                }
//
//            }
//        }
//
//
//    }

    public int getSize(){
        return size;
    }

    /**
     * setting color by type
     * @param p
     */
    public void setTypedColor(String p){
        if (p.endsWith(".doc")){
            typed =Color.RED;
        }else if(p.endsWith(".xlsx")){
            typed =Color.ORANGE;
        }else if(p.endsWith(".pdf")){
            typed =Color.YELLOW;
        }else if(p.endsWith(".pptx")){
            typed =Color.GREEN;
        }else if(p.endsWith(".jpg")){
            typed =Color.BLUE;
        }else if(p.endsWith(".mov")){
            typed =Color.BLACK;
        }else if(p.endsWith(".txt")){
            typed =new Color(128,0,128);
        }else{
            typed =Color.WHITE;
        }
    }

    /**
     * setting random color
     */
    public void setRandomColor(){
        redRandom = (Math.random()*1000)%255;
        greenRandom = (Math.random()*1000)%255;
        blueRandom = (Math.random()*1000)%255;
        randomed =new Color((int)redRandom,(int)greenRandom,(int)blueRandom);


    }

    /**
     * printing result
     */
    public void printFiles(){
        System.out.println(name+": "+size+"Last Modified date: "+lastModifiedDate+" path:"+path);
        if(!children.isEmpty()){
            for(var v:children){
                v.printFiles();
            }
        }

    }

    /**
     * get coordinations for area
     * @param x
     * @param y
     * @return
     */
    public Node getNodeAt(int x,int y){
        Node result= this;
        for (var child: children){
            if (x>child.left && y>child.top && x<child.left+child.width && y<child.top+child.height){
                result = child.getNodeAt(x,y);
            }
        }
        return result;
    }


}
