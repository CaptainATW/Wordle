import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Random;
import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;
import java.awt.Font;

public class Wordle {
    static File file = new File("Words.text"); 
    static ImageIcon image = new ImageIcon("Wordle.png");
    static ImageIcon completed = new ImageIcon("FilledInIcon.png");
    static ImageIcon none = new ImageIcon("None.png");
    static ImageIcon some = new ImageIcon("Somewhere.png");
    static ImageIcon here = new ImageIcon("Here.png");
    static ImageIcon icon = new ImageIcon("NotFilledInIcon.png");
    static JLabel actionbar;
    public static int index = 0;
    public static int enter = 0;
    public static boolean done = false;
    public static String word = "";
    public static String answer = getWord();


    public static void main(String[] args) {
        JPanel imagePanel = new JPanel(new GridLayout(6, 5));
        imagePanel.setBackground(Color.decode("#121213"));
        imagePanel.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(70, 20, 50, 20),
                imagePanel.getBorder()
        ));
        
        JPanel textPanel = new JPanel(new GridLayout(6, 5));
        textPanel.setBackground(new Color(255, 255, 255, 100)); // set a semi-transparent background color
        textPanel.setOpaque(false); // set the panel to be transparent
        textPanel.setBorder(BorderFactory.createCompoundBorder(
                new EmptyBorder(70, 15  , 50, 25),
                textPanel.getBorder()
        ));

        actionbar = new JLabel("Wordle");
        actionbar.setFont(new Font("KarnakPro-Bold", Font.BOLD, 50));
        actionbar.setForeground(Color.WHITE);
        actionbar.setHorizontalAlignment(JLabel.CENTER);
        actionbar.setVerticalAlignment(JLabel.CENTER);
        actionbar.setBounds(150, -100, 300, 300);
        JLabel[][] labels = new JLabel[6][5];
        
        JLabel[][] label = new JLabel[6][5];
        
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                labels[i][j] = new JLabel(" "); //sets text to blank text
                labels[i][j].setFont(new Font("Arial", Font.BOLD, 65));
                labels[i][j].setForeground(Color.WHITE);
                labels[i][j].setHorizontalAlignment(JLabel.CENTER);
                labels[i][j].setVerticalAlignment(JLabel.CENTER);
                labels[i][j].setBounds(0, 0, 100, 100);
                label[i][j] = new JLabel();
                label[i][j].setIcon(icon);

                
                imagePanel.add(label[i][j]);
                textPanel.add(labels[i][j]);
            }
        }
        imagePanel.setBounds(0, 0, 600, 800);  
        textPanel.setBounds(0, 0, 600, 800);  

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.add(imagePanel, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(textPanel, JLayeredPane.MODAL_LAYER);
        layeredPane.add(actionbar, JLayeredPane.MODAL_LAYER);

        JFrame frame = new JFrame("Wordle");
        frame.add(layeredPane, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage((image).getImage());

        
        answer = answer.toUpperCase();
        System.out.println(answer);
        frame.setSize(new Dimension(600, 800));
        frame.revalidate();
        frame.setFocusable(true); 
        frame.setVisible(true);
        frame.addKeyListener(new KeyAdapter() {
        
            public void keyPressed(KeyEvent e) {
                
                if (e.getID() == KeyEvent.KEY_PRESSED) {
                    if (e.getKeyCode() == KeyEvent.VK_DELETE || e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                        if(word.length() != 0) {
                            index--;
                            labels[index / 5][index % 5].setText("");
                            label[index / 5][index % 5].setIcon(icon);
                            System.out.println(word.substring(0, word.length() - 1));
                            word = word.substring(0, word.length() - 1);
                            if(index % 5 == 0 || index != 0 && done == false) {
                                done = true;
                            }
                        }
                    }
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        if(index % 5 == 0 || index != 0 && done == false) {
                            if(word.length() == 5) {
                                if(containsWord(word)) {
                                    System.out.println("enter" + index);
                                    done = true;
                                    int matches = 0;
                                    int wordnumber = 0;
                                    for(int z = (index - 5); z<=index - 1; z++ ) {
                                        
                                        System.out.println(z);
                                        System.out.println("if " + word.charAt(wordnumber) + "is equal to" + answer.charAt(wordnumber));
                                        if(word.charAt(wordnumber) == answer.charAt(wordnumber)) {
                                            System.out.println("if " + word.charAt(wordnumber) + "is equal to" + answer.charAt(wordnumber));
                                            label[z / 5][z % 5].setIcon(here);
                                            matches++;
                                        }
                                        else {
                                            if(answer.contains(Character.toString(word.charAt(wordnumber)))) {
                                                label[z / 5][z % 5].setIcon(some);
                                            }
                                            else {
                                                label[z / 5][z % 5].setIcon(none);
                                            }
                                        }
                                        wordnumber++;
                                        
                                        
                                        
                                    }
                                    if(index >= 30) {
                                        display(answer, Color.RED);
                                    }
                                    word = "";
                                    if (matches == 5) {
                                        //System.out.println("You got the word!");
                                        display("CORRECT", Color.GREEN);
                                        
                                    }
                                }
                                else {
                                    display("NOT WORD", Color.RED);
                                }
                                
                            }    
       
                        }

                    }
                    char c = e.getKeyChar();
                    c = Character.toUpperCase(c);
                    if (Character.isLetter(c)) {
                        System.out.println("Ccharacter typer is called" + index + done);
                        if(index % 5 != 0 || index == 0 || done == true) {
                            done = false;
                            labels[index / 5][index % 5].setText(c + "");
                            word = (word + labels[index/5][index%5].getText());
                            System.out.println(word + index);
                            
                            label[index / 5][index % 5].setIcon(completed);
                            index++;
                        }

                        
                    }
                }
                //System.out.println("Key Pressed: " + e.getKeyChar());
                

            }
        });
    }

    public static String getWord() {
        Random newWord = new Random();
        int randomLine = newWord.nextInt(12975);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;
            int currentLine = 1;
            while ((line = reader.readLine()) != null) {
                if (currentLine == randomLine) {
                    reader.close();
                    return(line);
                }
                currentLine++;
            }
            reader.close();
        } catch (IOException e) {
            return "error";
        }
        return "error";
    }

    public static boolean containsWord(String a) {
        Scanner fileScanner = null;
        boolean found = false;

        try {
            fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if (line.equalsIgnoreCase(a)) {
                    found = true;
                    break;
                }
            }

            if (found) {
                return true;
            } else {
                return false;
            }
        } catch (FileNotFoundException e) {
            return false;
        } finally {
            if (fileScanner != null) {
                fileScanner.close();
            }
        }
    }

    public static void display(String s, Color c) {
        actionbar.setText(s);
        actionbar.setForeground(c);
    }

    //how to write function in java that adds 5 to input?  


}