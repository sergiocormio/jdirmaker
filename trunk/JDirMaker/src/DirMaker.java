import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.WindowConstants;


public class DirMaker extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3866085610288574261L;
	private JTextField pathToCreateTextField;
	private JButton createButton;
	private JRadioButton slashSeparator;
	private JRadioButton dotSeparator;

	private DirMaker() {
		createForm();
		pack();
		this.setLocationRelativeTo(null);
	}

	private void createForm() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("Dir Maker");
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		
		addSeparatorPanel(panel);
		
		JLabel label = new JLabel("Path a ser creado:");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(label);
		pathToCreateTextField = new JTextField("ar/com/tsoluciones/emergencies/server/businesslogic/resources/service/implementation/");
		pathToCreateTextField.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(pathToCreateTextField);
		
		createButton = new JButton("Crear");
		createButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				String path = pathToCreateTextField.getText().trim();
				if(dotSeparator.isSelected()){
					path = path.replace('.', '/');
				}
				boolean result = createDirs(path);
				if(result){
					JOptionPane.showMessageDialog(DirMaker.this, "Listo!","Creación exitosa",JOptionPane.INFORMATION_MESSAGE);
				}else{
					JOptionPane.showMessageDialog(DirMaker.this, "Ocurrió un error al crear el directorio.","Error",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		createButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel.add(createButton);
		
		this.add(panel);
	}
	
	private void addSeparatorPanel(JPanel panel) {
		JPanel separatorPanel = new JPanel(new FlowLayout());
		JLabel separatorLabel = new JLabel("Separador:");
		separatorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		separatorPanel.add(separatorLabel);
		
		slashSeparator = new JRadioButton("/ (barra)");
		slashSeparator.setSelected(true);
		separatorPanel.add(slashSeparator);
		
		//Point separator
		dotSeparator = new JRadioButton(". (punto)");
		separatorPanel.add(dotSeparator);
		
		panel.add(separatorPanel);
		
		ButtonGroup separatorGroup = new ButtonGroup();
		separatorGroup.add(dotSeparator);
		separatorGroup.add(slashSeparator);
	}

	public boolean createDirs(String path){
		return new File(path).mkdirs();
//		String[] tokens = path.split("/");
//		for (String token : tokens){
//			token = token.trim();
//			if(!token.equals("")){
//			}
//		}
	}
	
	public static void main(String[] args) {
		new DirMaker().setVisible(true);
		
	}
}
