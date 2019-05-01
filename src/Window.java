import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

    /**
    * @author 	Renan Nakazawa
    * @nUsp	10723836
    * @description Classe que contém a interface gráfica do EP
    * @references https://www.guru99.com/java-swing-gui.html    
    */
public class Window {
    
    public static void main(String[] args) {
        new Window();
    }

    public Window() {
        JFrame guiFrame = new JFrame();
        //make sure the program exits when the frame closes
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setTitle("Example GUI");
        guiFrame.setSize(640,480);
        guiFrame.setLocationRelativeTo(null);


        final JPanel panelButtons = new JPanel();
        panelButtons.setVisible(true);
        JButton btnSimples = new JButton("Simples");
        btnSimples.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                //When the fruit of veg button is pressed
                //the setVisible value of the listPanel and
                //comboPanel is switched from true to
                //value or vice versa.
            }
        });

        JButton btnFlutuante = new JButton("Ponto flutuante");
        btnFlutuante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                //When the fruit of veg button is pressed
                //the setVisible value of the listPanel and
                //comboPanel is switched from true to
                //value or vice versa.
            }
        });
        panelButtons.add(btnSimples);
        panelButtons.add(btnFlutuante);

        guiFrame.add(panelButtons, BorderLayout.NORTH);
        //make sure the JFrame is visible
        guiFrame.setVisible(true);
    }
}