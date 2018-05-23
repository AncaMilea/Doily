import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
public class Frame extends JFrame{
    private ControlPanel controlP;
    private GalleryPanel gallery;

    public Frame(){
        setTitle("Digital Doily");
        setSize(1000,1000);

        setVisible(true);
        start();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JOptionPane.showMessageDialog(this, "Welcome to Digital Doily! This is a magic paint where you can create you own Doily.");
        JOptionPane.showMessageDialog(this, "Mind the fact that the lines option are in the upside bar of the menu.");
        setResizable(false);
    }
    public void start()
    {
        //create the base of a Menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu LineMenu = new JMenu("Line Options");
        gallery = new GalleryPanel();
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        controlP = new ControlPanel();

        //creates the clear button
        JMenuItem clear = new JMenuItem("Clear");
        clear.addActionListener(new ActionListener(){


            @Override
            public void actionPerformed(ActionEvent e) {
                controlP.clear();
            }
        });

        //creates the undo button
        JMenuItem undo = new JMenuItem("Undo");
        undo.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                controlP.undo();

            }
        });
        //creates the redo button
        JMenuItem redo = new JMenuItem("Redo");
        redo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controlP.redo();
            }
        });

        //creates the erase button
        JCheckBox erase = new JCheckBox("Eraser");
        erase.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED)
                {
                    controlP.setErase(true);
                    controlP.setColorPen(Color.BLACK);
                    repaint();
                }
                else
                {
                    controlP.setErase(false);
                    controlP.setColorPen(Color.RED);
                    repaint();
                }
            }
        });
        //creates the size of the pen button
        JLabel statusPen = new JLabel("Pen Size",JLabel.CENTER);
        statusPen.setSize(350,100);
        SpinnerModel penSize = new SpinnerNumberModel(controlP.getPenSize(), 2, 50, 1); //sets the limits
        JSpinner spinnerPen = new JSpinner(penSize);
        spinnerPen.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int nr = (int)((JSpinner)e.getSource()).getValue();
                controlP.setPenSize(nr);
            }
        });

        //creates the set number of sector button
        JLabel statusSectors = new JLabel("Sector Number",JLabel.CENTER);
        statusSectors.setSize(350,100);
        SpinnerModel SectorSize = new SpinnerNumberModel(controlP.getNrSectors(), 3, 50, 1); //sets the limits
        JSpinner spinnerSector = new JSpinner(SectorSize);
        spinnerSector.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                int nr = (int)((JSpinner)e.getSource()).getValue();
                controlP.setNrSectors(nr); //sets the number
                controlP.splitPanel(); //and splits the panel
                controlP.repaint();
            }
        });

        //creates the choose color button
        JMenuItem colorPen = new JMenuItem("Choose Color");
        colorPen.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                Color firstC = controlP.getColorPen();
                Color color = JColorChooser.showDialog(colorPen, "Colors", firstC);//changes the last color with another
                controlP.setColorPen(color);

            }
        });

        //creates the choose button to whether to see or not the sectors
        JToggleButton SectorsStatus = new JToggleButton("Sectors ON/OFF");
        SectorsStatus.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                // TODO Auto-generated method stub
                if(SectorsStatus.isSelected()){
                    controlP.setVis(false);
                } else {
                    controlP.setVis(true);
                }

            }
        });

        //creates the choose button to whether to reflect or not the lines
        JToggleButton reflectionStatus = new JToggleButton("Reflection ON/OFF");
        reflectionStatus.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                // TODO Auto-generated method stub
                if(reflectionStatus.isSelected()){
                    controlP.setReflection(true);
                } else {
                    controlP.setReflection(false);
                }

            }
        });

        //creates the save button for doily
        JButton save = new JButton("Save to gallery");
        save.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                BufferedImage temp = controlP.copyDrawingArea(); //creates a temporary image for the  draw panel
                gallery.addImage(temp); //adds image to the gallery
                gallery.repaint();
                gallery.revalidate();

            }
        });

        //creates the remove button from the gallery
        JButton removeFromGallery = new JButton("Remove Image");
        removeFromGallery.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(gallery.getSelected()==null)
                {
                    return;
                }
                else
                {
                    JLabel selected = gallery.getSelected(); //gets the selected image
                    gallery.remove(selected); //removes it
                    gallery.setSelected(null); //sets the selected to null
                    gallery.addJlabel(); //puts another empty JLabel
                    gallery.revalidate();
                    gallery.repaint();
                }}
        });

        //creates the panels and puts them all together
        JPanel spinnerB = new JPanel();
        spinnerB.add(statusSectors);
        spinnerB.add(spinnerSector);
        spinnerB.add(statusPen);
        spinnerB.add(spinnerPen);

        LineMenu.add(undo);
        LineMenu.add(clear);
        LineMenu.add(redo);
        LineMenu.add(colorPen);
        menuBar.add(LineMenu);
        setJMenuBar(menuBar);


        JPanel toggleB= new JPanel();
        toggleB.add(reflectionStatus);
        toggleB.add(SectorsStatus);

        JPanel menu= new JPanel();
        menu.add(spinnerB);
        menu.add(toggleB);
        menu.add(erase);

        JPanel galleryP = new JPanel();
        galleryP.setLayout(new BorderLayout());

        galleryP.add(save, BorderLayout.NORTH);
        galleryP.add(removeFromGallery, BorderLayout.SOUTH);
        galleryP.add(gallery, BorderLayout.CENTER);

        mainPanel.add(menu, BorderLayout.NORTH);
        mainPanel.add(galleryP, BorderLayout.EAST);
        mainPanel.add(controlP, BorderLayout.CENTER);

        this.add(mainPanel);


    }
    public static void main(String[] args){
        Frame f = new Frame();

    }
}
