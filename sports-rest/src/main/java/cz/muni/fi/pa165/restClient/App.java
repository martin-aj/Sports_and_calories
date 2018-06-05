package cz.muni.fi.pa165.restClient;

import cz.muni.fi.pa165.restClient.view.MainForm;
import javax.swing.SwingUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * Main class for starting REST client app
 *
 * @author mato
 */
public class App {
    
    @Autowired
    private MainForm mainForm;

    public static void main(String[] args) {
        ConfigurableApplicationContext context
                = new ClassPathXmlApplicationContext(new String[]{"applicationContext_rest.xml"});

    }

    public void init() {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                mainForm.setVisible(true);                    
            }
        }); 
    }       
}
