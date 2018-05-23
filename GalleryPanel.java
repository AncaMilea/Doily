import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GalleryPanel extends JPanel {

    private ArrayList<JLabel> galleryImage;

    private JLabel selected;

    public GalleryPanel()
    {
        super();

        selected = null;

        galleryImage = new ArrayList<>();

        //set the gallery to grid layout 12*1 so it shows 12 elements in a column
        setLayout(new GridLayout(12, 1));
        Border big = BorderFactory.createLineBorder(Color.WHITE, 3);
        setBorder(big);
        //add 12 empty JLabels to the gallery
        for(int i =1 ; i<=12;i++)
        {
            //create a temporary JLabel, set a few parameters and add it to the gallery
            JLabel empty = new JLabel();
            empty.addMouseListener(new MouseGallery());
            galleryImage.add(empty);
        }
        //add the JLabels to the Panel
        for(JLabel i : galleryImage)
        {
            add(i);
        }

        }

    //after each removing from the gallery another JLabel is added
    public void addJlabel() {
        JLabel l = new JLabel();
        l.addMouseListener(new MouseGallery());
        galleryImage.add(l);

        add(l);

    }

    public void addImage(BufferedImage toGallery)
    {
        //resize the passed buffered image using one of the JLabels sizes (in this case the first one)
            BufferedImage imageToAdd = resize(toGallery, galleryImage.get(0).getWidth(), galleryImage.get(0).getHeight());
            //iterating through the gallery arraylist and adding the image on the first instance of an empty image icon
            for (JLabel i : this.galleryImage) {

                if (i.getIcon() == null) {
                    i.setIcon(new ImageIcon(imageToAdd));
                    break;
                }
            }
        }

    public static BufferedImage resize(BufferedImage image, double width, double height) {

        //scale the buffered image to the given dimensions
        Image thumb = image.getScaledInstance((int)width,(int) height,Image.SCALE_SMOOTH);


        BufferedImage resizedImage = new BufferedImage((int)width, (int)height, BufferedImage.TYPE_INT_ARGB);

        //get graphics and draw to them the image
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(thumb, 0, 0, null);
        g2d.dispose();

        return resizedImage;
    }
    class MouseGallery extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            //put all the JLabel to be unselected each time another is selected
            Border noBorder = BorderFactory.createLineBorder(new Color(0,0,0), 0);
            for(JLabel i: galleryImage)
            {
                i.setBorder(noBorder);
            }
            //get the selected label
            JLabel clickedJLabel = (JLabel) e.getSource();

            if(clickedJLabel.getIcon()==null)
            {
                return;
            }
            //if the selected is the same as the clicked, then unselect it
            if(selected==clickedJLabel)
            {
                clickedJLabel.setBorder(noBorder);
                selected = null;
            }
            else {
                //else make it's border red and set the selected JLabel to the clicked one
                Border selectedBorder = BorderFactory.createLineBorder(Color.red, 1);
                clickedJLabel.setBorder(selectedBorder);
                selected = clickedJLabel;
            }
        }
    }

    public JLabel getSelected() {
        return selected;
    }
    public void setSelected(JLabel selected)
    {
        this.selected = selected;
    }

}

