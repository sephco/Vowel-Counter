import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;

@SuppressWarnings("serial")
public class VowelCounterGUI extends JFrame implements ActionListener {
	private File[] selectedFiles; // Array to hold Files
	
	private JPanel contentPane;
	// North Panel
	private JLabel lblTitle;
	// West Panel
	private DefaultListModel<File> modelWest = new DefaultListModel<>();
	private JList<File> lsGetFiles = new JList<>(modelWest);
	// East Panel
	private DefaultListModel<String> modelEast = new DefaultListModel<>();
	private JList<String> lsResults = new JList<>(modelEast);
	// SouthPanel
	private JButton btnAddFiles = new JButton("Add Files");
	private JButton btnProcess = new JButton("Process");
	private JButton btnClear = new JButton("Clear");
	private JButton btnHelp = new JButton("Help");

	// Constructor with window title as argument
	public VowelCounterGUI(String title) {
		super(title);
		setBounds(300, 300, 607, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setResizable(false);
		
		//********** NORTH PANEL **********
		lblTitle = new JLabel("Count Vowels in Big Files");
		lblTitle.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblTitle, BorderLayout.NORTH);
		
		//********** WEST PANEL **********
		JPanel westPanel = new JPanel();
		add(westPanel, BorderLayout.WEST);
		// Scroll Pane
		JScrollPane scrollPaneWest = new JScrollPane(lsGetFiles);
		scrollPaneWest.setPreferredSize(new Dimension(300, 200));
		westPanel.add(scrollPaneWest);
		
		//********** EAST PANEL **********
		JPanel eastPanel = new JPanel();
		add(eastPanel, BorderLayout.EAST);
		// Scroll Pane
		JScrollPane scrollPaneEast = new JScrollPane(lsResults);
		scrollPaneEast.setPreferredSize(new Dimension(300, 200));
		westPanel.add(scrollPaneEast);

		//********** SOUTH PANEL **********
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new FlowLayout());
		add(southPanel, BorderLayout.SOUTH);
		southPanel.add(btnAddFiles);
		southPanel.add(btnProcess);
		southPanel.add(btnClear);
		southPanel.add(btnHelp);
		// Disable process button
		checkProcessButton();
		
		//********** ADD LISTENERS **********
		btnAddFiles.addActionListener(this);
		btnProcess.addActionListener(this);
		btnClear.addActionListener(this);
		btnHelp.addActionListener(this);
	}

	//********** EVENT HANDLER **********
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		final JFileChooser fc = new JFileChooser();
		// filter files for txt only
		fc.setFileFilter(new FileNameExtensionFilter("TEXT FILES", "txt", "text"));
		fc.setMultiSelectionEnabled(true);
		// If Add Files button is clicked
		if (source == btnAddFiles){
			int returnVal = fc.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				// This is where a real application would open the file.
	            selectedFiles = fc.getSelectedFiles();
	            for (File file : selectedFiles){
	            	modelWest.addElement(file);
	            }
	        } else if (returnVal == JFileChooser.CANCEL_OPTION) {
	        	// Do nothing
	        }
			checkProcessButton();
		}
		// If Process button is clicked 
		else if (source == btnProcess){
			for (File file : selectedFiles){
				VowelCounterProcess task = new VowelCounterProcess(file, modelEast);
				task.execute();
			}
		}
		// If Clear button is clicked
		else if (source == btnClear){
			// clear text from List Models
			modelWest.clear();
			modelEast.clear();
			// nullify array to empty
			selectedFiles = null;
			// check process button
			checkProcessButton();
		}
		// If Help button is clicked
		else if (source == btnHelp){
			JOptionPane.showMessageDialog(this, "Select text files with the \"Add Files\" button and\n" 
											+ "press \"Process\" to get the count of vowels in each one.\n" 
											+ "\"Clear\" will reset the application.", "Help", JOptionPane.PLAIN_MESSAGE);
		}
	}
	
	// Enable/disable Process button as needed
	private void checkProcessButton(){
		if (selectedFiles == null)
			btnProcess.setEnabled(false);
		else
			btnProcess.setEnabled(true);
	}
}
