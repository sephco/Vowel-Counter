import javax.swing.JFrame;


public class VowelCounterApp {
	public static void main(String[] args){
		VowelCounterGUI frame = new VowelCounterGUI("Count Vowels");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
}
