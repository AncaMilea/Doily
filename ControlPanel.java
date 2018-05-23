import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.*;
public class ControlPanel extends JPanel {
    public static int nrStroke;
    private Color color;
    private boolean reflection;
    private int size;
    private ArrayList<Line> stroke;
    private ArrayList<Line> undoS;
    private boolean vis;
    private boolean eraseState=false;
    private int nrOfSectors=12;
    Line currStroke;

    public ControlPanel()
    {
        super();
        color = Color.red;
        nrStroke = 12;
        reflection = false;
        size =10;
        stroke = new ArrayList<Line>();
        undoS = new ArrayList<Line>();
        vis = true;
        this.setBackground(Color.BLACK);
        createPen();
    }
    public void createPen() {
        //starts to set the color, size and whether to reflect or not the line we draw
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Line point = new Line(color, size, reflection);
                point.add(e.getPoint());
                stroke.add(point);
            }
        });
        //continues to add to the ArrayList the points while painting them
        this.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                stroke.get(stroke.size() - 1).add(e.getPoint());
                repaint();
            }
        });
    }


        //clear the entire draw area
        public void clear(){
        stroke = new ArrayList<Line>();
        revalidate();
        repaint();
    }

    //transforms the panel in a image and returns it
    public BufferedImage copyDrawingArea() {
        BufferedImage copy = new BufferedImage((int)getSize().getWidth(), (int)getSize().getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        paint(copy.getGraphics());
        return copy;
    }

    public Color getColorPen() {
        return color;
    }

    public void setColorPen(Color color) {
        this.color= color;
        if(getErase()==true)
        {
        currStroke=new Line(color);
        repaint();}
    }
        //undo the last line painted
        public void undo() {
            if (stroke.isEmpty() == false) {
                {
                    undoS.add(stroke.get(stroke.size() - 1));
                    stroke.remove(stroke.size() - 1);
                }
                revalidate();
                repaint();
            }
        }
        //redo the last action
        public void redo() {
            if (undoS.isEmpty() == false) {
                {
                    stroke.add(undoS.get(undoS.size() - 1));
                    undoS.remove(undoS.size() - 1);
                }
                revalidate();
                repaint();

            }
        }
            //split the panel in the wished number for sectors
            public void splitPanel(){
                this.nrStroke = getNrSectors();
                repaint();
            }
            //set the size of the pen
            public void setPenSize(int n){
                this.size=n;
                repaint();
            }
            public int getPenSize(){
                return size;

            }

            //If the sectors are visible or not
            public void setVis(boolean vis){
                this.vis =vis;
                repaint();
            }
            //If it is reflecting the lines or not
            public void setReflection(boolean ref){
                this.reflection = ref;
                repaint();
            }
            //get the number of sectors for the doily
            public int getNrSectors() {
            return nrOfSectors;
            }

            public void setErase(boolean state)
            {
                this.eraseState=state;
            }
            public boolean getErase()
            {
                return this.eraseState;
            }
            public void setNrSectors(int numberOfSectors) {
            this.nrOfSectors = numberOfSectors;
            }

            private void drawSectorLines(Graphics2D g2d)
            {
                for (int i = 0; i <=getNrSectors(); i++) {
                g2d.setStroke(new BasicStroke(1));
                g2d.setColor(Color.WHITE);
                g2d.drawLine(0, 0, 0, -500);
                g2d.rotate(Math.toRadians(360/(double)getNrSectors()));
            }

            }
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHints(hints);
                g2.translate(this.getWidth() / 2, this.getHeight() / 2); //ensure to be in the center
                for (int i = 0; i < getNrSectors(); i++) {
                    for (int j = 0; j < stroke.size(); j++) {
                        //adding a new line to the array
                        Line currStroke = stroke.get(j);
                        boolean reflect = currStroke.getReflect();
                        ArrayList<Point> lines = currStroke.getStroke();
                        g2.setColor(currStroke.returnColor());
                        g2.setStroke(new BasicStroke(currStroke.getSize()));

                        for (int k = 0; k < lines.size() - 1; k++) {
                            Point p = lines.get(k);
                            Point q = lines.get(k + 1);
                            //draw the line using the points
                            g2.drawLine(p.x - this.getWidth() / 2, p.y - this.getHeight() / 2, q.x - this.getWidth() / 2, q.y - this.getHeight() / 2);
                            //reflect the line
                            if (reflect == true) {
                                g2.drawLine(-p.x + this.getWidth() / 2, p.y - this.getHeight() / 2, -q.x + this.getWidth() / 2, q.y - this.getHeight() / 2);
                            }
                        }

                    }
                    g2.rotate(Math.toRadians(360 / (double) getNrSectors()));
                    }
                    if (vis == true) {
                        drawSectorLines(g2);
                    }
                }

            }





