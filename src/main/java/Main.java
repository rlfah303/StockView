import java.awt.event.*;
import java.util.*;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;

//yahoo stock api https://financequotes-api.com/
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import java.math.BigDecimal;



public class Main extends JFrame implements KeyListener{

    private Vis mainPanel;
    private ArrayList<ArrayList<Stocks>>stocks;
    private  ArrayList<Stocks> first;
    private  ArrayList<Stocks> second;
    private  ArrayList<Stocks> third;
    String stockInput;

    List<Stocks> toRemove = new ArrayList<Stocks>();





    public Main() throws IOException {

        JMenuBar mb = setupMenu();
        setJMenuBar(mb);
        addKeyListener(this);



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
        JMenuItem item3 = new JMenuItem("Remove");




        item1.addActionListener(e -> {
            stockInput = JOptionPane.showInputDialog("Choose a stock");
            try {
                getStockInfo(stockInput);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            mainPanel.setStockArray(stocks);
            repaint();

        });
        item2.addActionListener(e -> {
            resetStock();
            mainPanel.setStockArray(stocks);
            repaint();

        });
        item3.addActionListener(e -> {
            stockInput = JOptionPane.showInputDialog("Which one to remove?");
            try {
                removeStock(stockInput);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            mainPanel.setStockArray(stocks);
            repaint();


        });
        menuBar.add(fileMenu);
        fileMenu.add(item1);
        fileMenu.add(item2);
        fileMenu.add(item3);



        return menuBar;

    }

    /**
     * get stock info from API
     * @param stockName
     * @throws IOException
     */
    public void getStockInfo(String stockName) throws IOException {
        Stock stock = YahooFinance.get(stockName);
        BigDecimal p = stock.getQuote().getPrice();
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

    /**
     * remove one stock
     * @param stockName
     * @throws IOException
     */
    public void removeStock(String stockName) throws IOException {
        for (int i= 0; i<3;i++){
            for (var v: stocks.get(i)){
                if (v.getStockName().equals(stockName)){
                    toRemove.add(v);
                }
            }
        }
        for (int i= 0; i<3;i++){
            stocks.get(i).removeAll(toRemove);
        }
    }

    /**
     * reset stock percent
     * @param arr
     * @throws IOException
     */
    public void resetStockPercent(ArrayList<ArrayList<Stocks>> arr) throws IOException {
        for (int i= 0; i<3;i++){
            for (var v: arr.get(i)){
                Stock stock = YahooFinance.get(v.getStockName());
                BigDecimal c= stock.getQuote().getChangeInPercent();
                double change = Double.parseDouble((c.toString()));
                v.setStockRate(change);

            }
        }


    }

    /**
     * reset stock array
     */
    public void resetStock(){
        stocks.get(0).clear();
        stocks.get(1).clear();
        stocks.get(2).clear();
    }

    /**
     * refreshing page
     */
    public void refreshPage(){
        try {
            resetStockPercent(stocks);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        mainPanel.setStockArray(stocks);
        mainPanel.update(mainPanel.getGraphics());

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


    @Override
    public void keyTyped(KeyEvent e) {
        refreshPage();
        System.out.println("keyPressed");
        mainPanel.setGetOneStock(false);
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

