import java.awt.event.*;
import java.util.*;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

//yahoo stock api https://financequotes-api.com/
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import java.math.BigDecimal;



public class Main extends JFrame{

    private Vis mainPanel;
    String stockInput;
    private ArrayList<ArrayList<Stocks>>stocks;
    private  ArrayList<Stocks> first;
    private  ArrayList<Stocks> second;
    private  ArrayList<Stocks> third;
    private boolean timer;



    public Main() throws IOException {

        JMenuBar mb = setupMenu();
        setJMenuBar(mb);

        mainPanel = new Vis();
        setContentPane(mainPanel);
        stocks =new ArrayList<>();
        first = new ArrayList<>();
        second = new ArrayList<>();
        third = new ArrayList<>();

        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Put the title of your program here");
        setVisible(true);


        //ex
//        aaaa = new Stocks("AAAA", 10.68, 23.23,0,0,0,0);
//        bbbb = new Stocks("BBBB", 1.33, 400.53,0,0,0,0);
//        cccc = new Stocks("CCCC", -70.23, 2.48,0,0,0,0);
//        dddd = new Stocks("DDDD", -5.95, 1200.12,0,0,0,0);
//        ffff = new Stocks("FFFF", -30.20, 650.99,0,0,0,0);
//        gggg = new Stocks("GGGG", 90.23, 41.39,0,0,0,0);
//        hhhh = new Stocks("HHHH", 40.66, 2068.63,0,0,0,0);
//        jjjj = new Stocks("JJJJ", -27.88, 1.66,0,0,0,0);
//        kkkk = new Stocks("KKKK", 35.88, 5.26,0,0,0,0);

        //add arraylist
        stocks.add(first);
        stocks.add(second);
        stocks.add(third);



    }


    private JMenuBar setupMenu () {
        //instantiate menubar, menus, and menu options
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Menu");

        JMenuItem item1 = new JMenuItem("Choose Stock");
        JMenuItem item2 = new JMenuItem("Reset");
        JMenuItem item3 = new JMenuItem("F5");




        item1.addActionListener(e -> {
            stockInput = JOptionPane.showInputDialog("Choose a stock");
            try {
                getStockInfo(stockInput);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            mainPanel.setStockArray(stocks);
            repaint();
//            final JFileChooser fc = new JFileChooser(FileSystemView.getFileSystemView());
//            fc.setDialogTitle("Choose a folder");
//            fc.setCurrentDirectory(new File(System.getProperty("user.home")));
//            fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
//            int fcValue = fc.showOpenDialog(getParent());
//            if(fcValue == JFileChooser.APPROVE_OPTION){
//                filepath = fc.getSelectedFile().getPath();
//                mainPanel.setPath(filepath);
//                ff = new File(filepath);
//                nodePanel= new Node(ff,"none");
//                mainPanel.setRoot(nodePanel);
//            }

        });
        item2.addActionListener(e -> {
            resetStock();
            mainPanel.setStockArray(stocks);
            repaint();

        });
        item3.addActionListener(e -> {
            try {
                resetStockPercent(stocks);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            mainPanel.setStockArray(stocks);
            System.out.println("got new array");
            mainPanel.update(mainPanel.getGraphics());



        });
        menuBar.add(fileMenu);
        fileMenu.add(item1);
        fileMenu.add(item2);
        fileMenu.add(item3);



        return menuBar;

    }

    public void getStockInfo(String stockName) throws IOException {
        Stock stock = YahooFinance.get(stockName);
        BigDecimal p = stock.getQuote().getPrice();
        System.out.println("p asdasdasdasdas"+p);
        BigDecimal c= stock.getQuote().getChangeInPercent();
        double price = Double.parseDouble((p.toString()));
        double change = Double.parseDouble((c.toString()));
        System.out.println(price);
        System.out.println(change);
        Stocks s1 = new Stocks(stockName,change,price,0,0,0,0);
        if (Math.abs(change)<10){
            stocks.get(0).add(s1);
        }else if (Math.abs(change)<15){
            stocks.get(1).add(s1);
        }else{
            stocks.get(2).add(s1);
        }

    }
    public void resetStockPercent(ArrayList<ArrayList<Stocks>> arr) throws IOException {
        System.out.println("getting reset");
        for (int i= 0; i<3;i++){
            for (var v: arr.get(i)){
                Stock stock = YahooFinance.get(v.getStockName());
                BigDecimal c= stock.getQuote().getChangeInPercent();
                double change = Double.parseDouble((c.toString()));
                v.setStockRate(change);


            }
        }


    }
    public void resetStock(){
        stocks.get(0).clear();
        stocks.get(1).clear();
        stocks.get(2).clear();
    }
    public void setTimer(int t){
        try {
            Thread.sleep(t);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    public void setLive(){
        timer =false;
        System.out.println("setlive******************************");

    }

    public static void main (String[]args){

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Main();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

