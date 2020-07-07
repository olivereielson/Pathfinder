import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Vector;


public class Main implements MouseListener {


    public static int boardlength = 10;
    public static int boardwidth = 10;
    static int[][] board = new int[boardwidth][boardlength];
    static ArrayList<Integer> usedpaths = new ArrayList<> ();
    public static TreeMap<Integer, String> vlaue = new TreeMap<> ();
    public static JFrame frame;
    public static Canvas canvas;
    public static JPanel panel;
    public static BufferStrategy bufferStrategy;
    public static int WIDTH = 890;
    public static int HEIGHT = 1000;
    public static String finalpath2 = "nul";
    public static int startx, starty, endx, endy;
    public static MouseListener mouseListener;
    public Boolean start = true;


    public static void main(String[] args) throws InterruptedException {

        Main ex = new Main ();

        ex.setUpGraphics ();

        for (int x = 0; x < boardwidth; x++) {

            for (int y = 0; y < boardlength; y++) {


                board[x][y] = (int) (255 * Math.random ());

            }

        }

        render ();


    }

    public static void prune(int x, int y, String path, int value) throws InterruptedException {

        if (x == endx && y == endy) {

            drawpath (path);

            System.out.println (value);


        } else {

            move (x, y, value, path, 0);


            try {


                TreeMap<Integer, String> treetop = (TreeMap<Integer, String>) vlaue.clone ();

                //System.out.println (treetop);

                vlaue.clear ();

                int top = 1;

                for (int h = 0; h < top; h++) {


                    String test = new Vector (treetop.values ()).get (h).toString ();


                    int valuenew = (int) new Vector (treetop.keySet ()).get (h) - ((int) Math.hypot (Math.abs (x - endx), Math.abs (y - endy)) * 100);

                    //int valuenew = (int) new Vector (treetop.keySet ()).get (h);
                    String[] test1 = test.split (" ");


                    if (!usedpaths.contains (Integer.parseInt (test1[0]) * 10 + Integer.parseInt (test1[1]))) {


                        usedpaths.add (Integer.parseInt (test1[0]) * 10 + Integer.parseInt (test1[1]));


                        prune (Integer.parseInt (test1[0]), Integer.parseInt (test1[1]), test1[2], valuenew);


                    } else {

                        top++;
                    }


                }


            } catch (Exception e) {


            }


        }


    }

    public static void move(int x, int y, int value, String path, int counter) {


        counter++;


        if (counter > 4 || (x == endx && y == endy)) {

            value = value + ((int) Math.hypot (Math.abs (x - endx), Math.abs (y - endy)) * 100);


            vlaue.put (value, (x + " " + y + " " + path));

            finalpath2 = path;

        } else {


            value = value + board[x][y];


            if (x > 0) {


                move (x - 1, y, value, path + "1", counter);
            }

            if (y > 0 && x > 0) {

                move (x - 1, y - 1, value, path + "0", counter);

            }

            if (y > 0) {

                move (x, y - 1, value, path + "2", counter);

            }

            if (x < boardwidth - 1) {

                move (x + 1, y, value, path + "3", counter);
            }

            if (y < boardlength - 1 && x < boardwidth - 1) {

                move (x + 1, y + 1, value, path + "4", counter);
            }

            if (y < boardlength - 1) {

                move (x, y + 1, value, path + "5", counter);

            }

            if (x > 0 && y < boardlength - 1) {


                move (x - 1, y + 1, value, path + "6", counter);

            }


            if (y > 0 && x < boardwidth - 1) {

                move (x + 1, y - 1, value, path + "7", counter);

            }

        }


    }

    public void setUpGraphics() {


        frame = new JFrame ("Application Template");   //Create the program window or frame.  Names it.

        panel = (JPanel) frame.getContentPane ();  //sets up a JPanel which is what goes in the frame
        panel.setPreferredSize (new Dimension (WIDTH, HEIGHT));  //sizes the JPanel
        panel.setLayout (null);   //set the layout


        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events (Mouse and Keyboard events)
        canvas = new Canvas ();
        canvas.setBounds (0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint (true);

        panel.add (canvas);  // adds the canvas to the panel.

        // frame operations
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
        frame.pack ();  //adjusts the frame and its contents so the sizes are at their default or larger
        frame.setResizable (true);   //makes it so the frame cannot be resized
        frame.setVisible (true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!
        canvas.addMouseListener (this);

        // sets up things so the screen displays images nicely.
        canvas.createBufferStrategy (2);
        bufferStrategy = canvas.getBufferStrategy ();
        canvas.requestFocus ();


        System.out.println ("DONE graphic setup");


    }

    public static void render() {


        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics ();


        for (int x = 0; x < boardwidth; x++) {

            for (int y = 0; y < boardwidth; y++) {


                g.setColor (new Color (0, 0, 255 - board[x][y]));

                g.fillRect ((x) * 10 + 40, y * 10 + 20, 10, 10);

            }

        }


       // System.out.println ("board done");

        g.dispose ();
        bufferStrategy.show ();

    }

    public static void drawpath(String path) throws InterruptedException {

        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics ();


        int d = startx + 1, f = starty + 1;

        int truevalue = 0;

        for (int x = 0; x < path.length (); x++) {

            //render ();


            String draw = (path.substring (x, x + 1));


            if (draw.equals ("1")) {

                d--;

            }

            if (draw.equals ("0")) {

                d--;
                f--;

            }
            if (draw.equals ("2")) {

                f--;

            }
            if (draw.equals ("3")) {

                d++;

            }
            if (draw.equals ("4")) {

                d++;
                f++;

            }
            if (draw.equals ("5")) {

                f++;

            }

            if (draw.equals ("6")) {

                f++;
                d--;

            }
            if (draw.equals ("7")) {

                f--;
                d++;

            }

            truevalue = truevalue + board[d][f];


                g.setColor (Color.white);
                g.fillRect (40 + d * 10, 20 + f * 10, 10, 10);


            bufferStrategy.show ();

            Thread.sleep (20);


        }


        System.out.println ("the value is= " + truevalue + " the path is= " + path);

        g.dispose ();
        bufferStrategy.show ();


    }

    public static void drawsquare() throws InterruptedException {

        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics ();


        g.setColor (Color.white);
        g.fillRect (40 + startx * 10, 20 + starty * 10, 10, 10);




        g.dispose ();
        bufferStrategy.show ();


    }


    @Override
    public void mouseClicked(MouseEvent e) {

        //System.out.println ();
        //System.out.println ((e.getY ()-40)/10);


        if (start) {


            startx = (e.getX () - 40) / 10;
            starty = (e.getY () - 20) / 10;

            try {
                render ();
                drawsquare ();
            } catch (InterruptedException ex) {
                ex.printStackTrace ();
            }

            start = false;

        } else {

            endx = (e.getX () - 40) / 10;
            endy = (e.getY () - 20) / 10;

            try {
                prune (startx, starty, "0", 0);
            } catch (InterruptedException ex) {
                ex.printStackTrace ();
            }

            start = true;

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


test(0)

public int test (int test1){


    if(test1==10){

        return test1

    } else{



        test( test1++)


    }






}