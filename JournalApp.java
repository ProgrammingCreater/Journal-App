package CompSciIA;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

// The class extends JFrame b/c of inheritance
// It has all the content area for other components used for Java Swing

/**
 * JournalApp is a Swing-based application for managing journal entries.
 * It provides functionality for saving, viewing, and selecting entries by year.
 * The class extends JFrame b/c of inheritance
 * It has all the content area for other components used for Java Swing
 */
public class JournalApp extends JFrame{
    private int screenwidth, screenheight;
    private JFrame viewEntries = new JFrame();
    private JFrame viewYears = new JFrame("Select Year Of Entries");
    private JTextArea Entry;
    private JButton saveButton;
    private JButton viewButton;
    private JButton homeButton;
    
    /**
     * Constructor for the JournalApp class.
     * Initializes the main screen for the journal app.
     */
    public JournalApp() {
        firstScreen();
    }
    
     /**
     * Sets up the first screen of the journal application.
     * This includes a header, a text area for writing journal entries,
     * and buttons for saving, viewing, and returning home.
     */
    private void firstScreen()
    {
        screenwidth = 500;
        screenheight = (int)(screenwidth * .7);
        
        setSize(screenwidth, screenheight);
        // Centers the screen
        setLocationRelativeTo(null);
        
        // Creates and Centers a Label then additionally styles it
        JLabel header = new JLabel("Journal Entry", SwingConstants.CENTER);
        header.setFont(new Font("Consolas", Font.BOLD, 20));
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(header);
        
        // Create UI Components
        Entry = new JTextArea();
        Entry.setLineWrap(true);
        // Long sentences move to next line
        Entry.setWrapStyleWord(true);
        
        JScrollPane Pane = new JScrollPane(Entry);
        Pane.setPreferredSize(new Dimension(screenwidth-100, screenheight-50));
        
        
        JPanel screen = new JPanel();
        screen.setLayout(new BoxLayout(screen, BoxLayout.Y_AXIS));
        
        // Adds the top panel/header to the overall screen
        screen.add(topPanel, BorderLayout.NORTH);
        
        // Variable for the padding from the top
        int verticalPadding = 25;
        // Moves the input screen down
        screen.add(Box.createVerticalStrut(verticalPadding));
        
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
        
        // Horizontal Space will always be double the vertical space
        // Space from Left
        inputPanel.add(Box.createHorizontalStrut(verticalPadding*2));
        
        inputPanel.add(Pane);
        // Space from Right
        inputPanel.add(Box.createHorizontalStrut(verticalPadding*2));
        
        screen.add(inputPanel, BorderLayout.CENTER);
        
        // Create the Buttons
        JPanel buttonPanel = new JPanel();
        saveButton = new JButton("Save Entry");
        viewButton = new JButton("View Past Entries");
        homeButton = new JButton("Home");
        
        // Adds the previously made buttons to the button panel
        buttonPanel.add(saveButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(homeButton);
        
        // Assigns functionality to the buttons and the "->" is a lambda function to avoid making another function
        saveButton.addActionListener(e -> saveEntry());
        viewButton.addActionListener(e -> selectYear());
        homeButton.addActionListener(e -> goHome());

        // Places buttons are the south/bottom of the panel
        screen.add(buttonPanel, BorderLayout.SOUTH);
        
        // Prevents overall from previous components
        getContentPane().removeAll();
        add(screen);
        revalidate();
        repaint();
    }
    
    /**
     * Opens a new window allowing the user to select a year to view entries from.
     * Displays buttons for each year and allows multiple selections.
     */
    private void selectYear()
    {
        //Uses JFrame for a scroll wheel
        viewYears.setSize(screenwidth-100,screenheight-70);
        viewYears.setLocationRelativeTo(this);
        
        // # of years as options
        String[] options = {"2022", "2023", "2024", "2025", "2026"};
        // Creates an array of toggled buttons nased upon the amount of options
        JToggleButton[] buttons = new JToggleButton[options.length];
        
        JPanel buttonPanel = new JPanel();
        // Sets up a grid with rows x columns then spacing
        // Amount of rows is based upon the amount of available options
        buttonPanel.setLayout(new GridLayout((options.length/2)+1,3,5,5));
        
        for(int i = 0; i < options.length; i++)
        {
            // initializes each button in a array as a toggled button
            buttons[i] = new JToggleButton(options[i]);
            // Adds each new button into the button Panel
            buttonPanel.add(buttons[i]);
        }
        
        // Creates a submit button for all of the years
        JButton submit = new JButton("Submit");
        
        // Created the functionality for the submit button
        submit.addActionListener(e-> {
            // Creates an arrayList of strings
            ArrayList<String> selected = new ArrayList<>();
            // Iterates over each button in the array of buttons
            for(JToggleButton button : buttons)
            {
                // If the user selects the button add the text of the button to the selected ArrayList 
                if(button.isSelected())
                {
                    selected.add(button.getText());
                }
            }
            
            // Reseting all of the toggled buttons to default state
            for(Component button : buttonPanel.getComponents())
            {
                if (button instanceof JToggleButton) {
                    ((JToggleButton)button).setSelected(false);
                }
            }
            // Checks if the selected ArrayList is empty then show an error message 
            if(!selected.isEmpty()) {
                // If there are selected entries then display them using the viewEntries function
                viewEntry(selected);
            } else {
                JOptionPane.showMessageDialog(this,"Please Select at Least One Year", "Warning", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        // Adds the button panel and the submit button to the viewYears frame
        viewYears.add(buttonPanel, BorderLayout.CENTER);
        viewYears.add(submit,BorderLayout.SOUTH);
        // Sets the viewYears frame to visible
        viewYears.setVisible(true);
    }

    /**
     * Displays the journal entries for the selected years.
     * Retrieves the entries from files and displays them in a text area.
     *
     * @param years The list of years for which the entries will be displayed.
     */
    private void viewEntry(ArrayList<String> years)
    {
        // Sets viewEntriess Size as less than the main screen
        viewEntries.setSize(screenwidth-100,screenheight-70);
        
        // Centers it relative to the window relative to main app
        viewEntries.setLocationRelativeTo(this);
        
        // Initializes the text area and disables editing it
        JTextArea display = new JTextArea(10, 40);
        display.setEditable(false);
        display.setLineWrap(true);
        // Long sentences move to next line
        display.setWrapStyleWord(true);
        
        // Creates the scrollPane in case there are too many entries 
        JScrollPane scrollPane = new JScrollPane(display);
        viewEntries.add(scrollPane);
        
        // Created ArrayLists for both the fileDates and their content
        // Variable files Count counts all of the files iternated over for the fileDates
        ArrayList<String> fileDates = new ArrayList<String>();
        ArrayList<String> content = new ArrayList<String>();
        int filesCount = 1;
        
        // For loop iterates according to the size of the parameter ArrayList
        for(int i = 0; i < years.size(); i++)
        {
            // If fileDates and amount of content are out of sync then display error message and stop function
            if(fileDates.size() != content.size()) 
            {
                JOptionPane.showMessageDialog(null, "Reading File Error", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Get the first year in the arrayList
            String year = years.get(i);

            // Checks to see if the year in the arrayList exists
            if(year == null || year.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Invalid Year", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Get the directory with the same name
            File directory = new File(year);
            
            // Checks to see if the directory exists if not then gives error message
            if(!directory.exists() || !directory.isDirectory())
            {
                JOptionPane.showMessageDialog(null, "Year(s) Not Found", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Create a files array of all of the files in the gotten directory
            File[] files = directory.listFiles();
            
            // For-each loop for all of the files in the array
            for(File file : files)
            {
                // if the file is null then skip the iteration
                if(files == null)
                {
                    continue;
                } 
                
                if(file.isFile())
                {
                    try{
                    //Add its number and date while removing the file name identfiers (Ex: .txt or /)
                    fileDates.add((filesCount + ".) " + file.toString().substring(4).replace(".txt", "")).replace("/","") + "\n");
                    // Creates a temporary file with the correct file name to read and add to the content ArrayList
                    FileManager temp = new FileManager((LocalDate.now()).toString());
                    content.add(temp.ReadFile(file) + "\n\n");
                    }
                    catch(IOException x) {
                        JOptionPane.showMessageDialog(this,"Screwed up Reading", "Warning", JOptionPane.INFORMATION_MESSAGE);
                    }
                    // Iterate file count
                    filesCount++;
                }
            }
        }
        // Allows a mutable string where you can append and "build" a string iteravely 
        StringBuilder displayText = new StringBuilder();
        for(int i = 0; i < fileDates.size(); i++)
        {
            displayText.append(fileDates.get(i)).append(content.get(i));
        }
        // Sets the text display on the screen to the previously built String
        display.setText(displayText.toString());
        
        // Resets everything to prepare for next iterations
        viewEntries.revalidate();
        viewEntries.repaint();
        // Makes the frame visible
        viewEntries.setVisible(true);
    }
    
    /**
     * Saves the current journal entry to a file with the current date.
     * If the entry is empty, a warning is displayed.
     */
    private void saveEntry()
    {
        // Gets the text from the initial extry text area and trims out any extra spaces/new lines
        String text = Entry.getText().trim();
        
        if(text.equals(""))
        {
            // Error message if the text area was empty
            JOptionPane.showMessageDialog(this,"Cannot Save an Empty Entry", "Warning", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
       try{
            LocalDate today = LocalDate.now();
           // Creates a new file named todays date
           FileManager f = new FileManager((today.toString() + ".txt"), String.valueOf(today.getYear())); 
           // Writes the entry to the file
           f.WriteToFile(text);
           // Clears the text area
           Entry.setText("");
       
            // Create congratulatory message if entry is saved without any errors
            JOptionPane.showMessageDialog(this,"Entry saved successfully!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
       } // Date exception if the LocalDate.now has any complications
       catch (DateTimeParseException e) {
           // Prints out the error stack in console while sending a visual error for the user 
           e.printStackTrace();
           JOptionPane.showMessageDialog(this,"Error Saving This Entry","Warning", JOptionPane.INFORMATION_MESSAGE);
       }
    }
    
     /**
     * Returns to the main "home" screen, hiding the current frames.
     */
    private void goHome()
    {   
        // Deletes the viewYears frame
        viewYears.dispose();
        // Makes the view Entries page invisible
        viewEntries.setVisible(false);
        // Removes all of the content from the view Entries frame 
        viewEntries.getContentPane().removeAll();
        
        // Resets it to the first "home" screen
        // Thread safety by executing it after all pending UI events have processed
        // () -> ___ lambda 
        SwingUtilities.invokeLater(() -> firstScreen());
    }
    
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new JournalApp().setVisible(true));
    }
}