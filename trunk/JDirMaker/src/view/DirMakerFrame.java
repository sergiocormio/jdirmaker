package view;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.util.prefs.Preferences;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import model.DirMaker;
import resources.ResourcesFactory;


public class DirMakerFrame extends JFrame{
	private static final String DESTINATION_FOLDER = "DESTINATION_FOLDER";
	/**
	 * 
	 */
	private static final long serialVersionUID = 3866085610288574261L;
	private JTextArea pathsToCreateTextArea;
	private JButton createButton;
	private JRadioButton slashSeparator;
	private JRadioButton dotSeparator;
	private JRadioButton enterPathSeparator;
	private JTextField destinationFolderTextField;
	private JButton chooseDestinationFolderButton;
	private Preferences preferences;
	private JButton openDestinationFolderButton;
	private JFileChooser folderChooser;

	private DirMakerFrame() {
		preferences = Preferences.userRoot().node(this.getClass().getName());
		createForm();
		pack();
		this.setSize(new Dimension(640, 480));
		this.setLocationRelativeTo(null);
	}

	private void createForm() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setTitle("JDirMaker");
		setIconImage(ResourcesFactory.getAppIcon().getImage());
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		createTopPanel(mainPanel);
		createTextAreaPanel(mainPanel);
		createButtonPanel(mainPanel);
		createMenu();
		
		this.add(mainPanel);
	}
	
	private void createMenu() {
		JMenuBar menuBar = new JMenuBar();
		//FILE
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		
		//SEPARATOR
		fileMenu.add(new JSeparator());
		
		//Exit
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}

		});
		fileMenu.add(exitMenuItem);
		
		//HELP
		JMenu helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);
		
		//About
		JMenuItem aboutMenuItem = new JMenuItem("About JDirMaker...");
		aboutMenuItem.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				new AboutDialog(DirMakerFrame.this).setVisible(true);
			}
		});
		helpMenu.add(aboutMenuItem);
		this.setJMenuBar(menuBar);
		
	}

	private void createTopPanel(JPanel mainPanel) {
		JPanel topPanel = new JPanel(new BorderLayout());
		JPanel separatorsPanel = new JPanel(new FlowLayout());
		createDirSeparatorPanel(separatorsPanel);
		createPathSeparatorPanel(separatorsPanel);
		createDestinationFolderPanel(topPanel);
		topPanel.add(separatorsPanel,BorderLayout.CENTER);
		mainPanel.add(topPanel,BorderLayout.NORTH);
	}

	private void createDestinationFolderPanel(JPanel topPanel) {
		JPanel destFolderPanel = new JPanel(new BorderLayout(5,5));
		destFolderPanel.add(new JLabel("Destination Folder:"),BorderLayout.WEST);
		destinationFolderTextField = new JTextField();
		loadDestinationFolder();
		destinationFolderTextField.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
				saveDestinationFolder();
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
			}
		});
		destFolderPanel.add(destinationFolderTextField,BorderLayout.CENTER);
		JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,2,0));
		//CHOOSE FOLDER
		chooseDestinationFolderButton = new JButton("Select",ResourcesFactory.getChooseFolderIcon());
		chooseDestinationFolderButton.setToolTipText("Choose Destination Folder");
		//Folder chooser
		folderChooser = new JFileChooser();
		folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		folderChooser.setFileHidingEnabled(true);
		chooseDestinationFolderButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int retVal = folderChooser.showOpenDialog(DirMakerFrame.this);
				if(retVal == JFileChooser.APPROVE_OPTION){
					File selectedFile = folderChooser.getSelectedFile();
					destinationFolderTextField.setText(selectedFile.getAbsolutePath());
					saveDestinationFolder();
				}
			}
		});
		
		buttonsPanel.add(chooseDestinationFolderButton);
		
		//OPEN FOLDER
		openDestinationFolderButton = new JButton("Open",ResourcesFactory.getOpenFolderIcon());
		openDestinationFolderButton.setToolTipText("Open Destination Folder");
		openDestinationFolderButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				File file = new File (destinationFolderTextField.getText());
				Desktop desktop = Desktop.getDesktop();
				try {
					desktop.open(file);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(DirMakerFrame.this, "Error opening destination folder", "Error", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		buttonsPanel.add(openDestinationFolderButton);
		
		destFolderPanel.add(buttonsPanel,BorderLayout.EAST);
		topPanel.add(destFolderPanel,BorderLayout.NORTH);
	}

	private void saveDestinationFolder() {
		preferences.put(DESTINATION_FOLDER, destinationFolderTextField.getText());
	}
	
	private void loadDestinationFolder() {
		String destinationFolder = preferences.get(DESTINATION_FOLDER, System.getProperty("user.dir"));
		destinationFolderTextField.setText(destinationFolder);
	}

	private void createButtonPanel(JPanel mainPanel) {
		JPanel buttonPanel = new JPanel(new FlowLayout());
		createButton = new JButton("Create!",ResourcesFactory.getCreateIcon());
		createButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				String paths = pathsToCreateTextArea.getText().trim();
				String dirSeparator = getDirectoriesSeparator();
				String pathsSeparator = getPathsSeparator();
				String parentPath = getParentPath();
				boolean result = new DirMaker().createSetOfPaths(paths, dirSeparator, pathsSeparator, parentPath);
				if(result){
					JOptionPane.showMessageDialog(DirMakerFrame.this, "Done!","Creation Success",JOptionPane.INFORMATION_MESSAGE);
				}else{
					JOptionPane.showMessageDialog(DirMakerFrame.this, "There was an error creating the directories.","Error",JOptionPane.ERROR_MESSAGE);
				}
			}

			
		});
		
		buttonPanel.add(createButton);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
	}

	protected String getParentPath() {
		return destinationFolderTextField.getText();
	}

	protected String getDirectoriesSeparator() {
		if(dotSeparator.isSelected()){
			return ".";
		}else{
			return "/";
		}
	}
	
	private String getPathsSeparator() {
		if(enterPathSeparator.isSelected()){
			return "\n";
		}
		return "\n";
	}

	private void createTextAreaPanel(JPanel mainPanel) {
		JPanel textAreaPanel = new JPanel(new BorderLayout());
		JLabel label = new JLabel("Paths to be created:");
		textAreaPanel.add(label,BorderLayout.NORTH);
		pathsToCreateTextArea = new JTextArea("ar/com/tsoluciones/emergencies/server/businesslogic/resources/service/implementation/");
		JScrollPane textScroll = new JScrollPane(pathsToCreateTextArea);
		textAreaPanel.add(textScroll,BorderLayout.CENTER);
		mainPanel.add(textAreaPanel);
	}
	
	private void createPathSeparatorPanel(JPanel topPanel) {
		JPanel pathSeparatorPanel = new JPanel();
		pathSeparatorPanel.setLayout(new BoxLayout(pathSeparatorPanel,BoxLayout.Y_AXIS));
		pathSeparatorPanel.setBorder(new TitledBorder("Path Separator"));
		enterPathSeparator = new JRadioButton("Enter");
		enterPathSeparator.setSelected(true);
		pathSeparatorPanel.add(enterPathSeparator);
		
		ButtonGroup separatorPathGroup = new ButtonGroup();
		separatorPathGroup.add(enterPathSeparator);

		pathSeparatorPanel.setPreferredSize(new Dimension(130, 80));
		topPanel.add(pathSeparatorPanel);
	}
	
	private void createDirSeparatorPanel(JPanel mainPanel) {
		JPanel separatorPanel = new JPanel(new BorderLayout());
		separatorPanel.setBorder(new TitledBorder("Directory Separator"));
		JPanel radioButtonsPanel = new JPanel();
		radioButtonsPanel.setLayout(new BoxLayout(radioButtonsPanel,BoxLayout.Y_AXIS));
		
		slashSeparator = new JRadioButton("/ (slash)");
		slashSeparator.setSelected(true);
		radioButtonsPanel.add(slashSeparator);
		
		//Point separator
		dotSeparator = new JRadioButton(". (dot)");
		radioButtonsPanel.add(dotSeparator);
		separatorPanel.add(radioButtonsPanel, BorderLayout.CENTER);
		
		separatorPanel.setPreferredSize(new Dimension(130, 80));
		mainPanel.add(separatorPanel);
		
		ButtonGroup separatorGroup = new ButtonGroup();
		separatorGroup.add(dotSeparator);
		separatorGroup.add(slashSeparator);
	}

	
	
	public static void main(String[] args) {
		new DirMakerFrame().setVisible(true);
	}
}
