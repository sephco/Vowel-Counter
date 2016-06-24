import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;
import javax.swing.DefaultListModel;
import javax.swing.SwingWorker;

public class VowelCounterProcess extends SwingWorker<String, Void> {
	private File file;
	DefaultListModel<String> modelEast;
	
	// Constuctor that takes a Fle and a ListModel<String> 
	public VowelCounterProcess(File file, DefaultListModel<String> modelEast) {
		this.file = file;
		this.modelEast = modelEast;
	}

	@Override
	protected String doInBackground() throws Exception { // New worker not on EDT
		int vowelCount = 0; // Counter
		// Throw error
		try (FileInputStream fis = new FileInputStream(file); InputStreamReader reader = new InputStreamReader(fis)) {
			int c;
			//read each character, unless it's -1 cast to char and check if vowel
			while ((c = reader.read()) != -1) {
				char ch = (char) c;
				ch = Character.toLowerCase(ch);
				if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
					vowelCount++;
				}
			}
			fis.close(); // close input stream
			reader.close(); // close reader.
		}
		return "Vowel count for " + "\"" + file.getName() + "\"" + ": " + vowelCount;
	}

	@Override
	protected void done() { // Return on EDT
		try {
			String result = get(); // Get return value from doInBackground()
			modelEast.addElement(result); // Update modelEast in GUI
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}

}
