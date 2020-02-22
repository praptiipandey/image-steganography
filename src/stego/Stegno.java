
package stego;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.Icon;
import java.lang.*;
import java.util.*;
import java.text.*;
import javax.swing.Icon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.Serializable;

public class Stegno extends JFrame implements ActionListener,Serializable{
    JLabel label1;
    ButtonGroup cbg;
    JRadioButton radio1;
    JRadioButton radio2;
    JButton button1;
    JTextField timeField;
    JTextField dateLongField;
    JMenuBar menuBar=new JMenuBar();
	JMenuItem TopicsItem=new JMenuItem("Topics");
	JRadioButton radio3;


    public Stegno(){
        StegnoLayout customLayout = new StegnoLayout();

        getContentPane().setFont(new Font("Helvetica", Font.PLAIN, 12));
        getContentPane().setLayout(customLayout);
        //getContentPane().setBackground(Color.LIGHT_GRAY);

       label1 = new JLabel("                   IMAGE STEGANOGRAPHY");
        getContentPane().add(label1);
        label1.setFont(new Font("RockWell", Font.BOLD, 27));
        

        cbg = new ButtonGroup();
        radio1 = new JRadioButton("ENCODE", false);
        cbg.add(radio1);
        getContentPane().add(radio1);
        radio1.setFocusable(true);
        radio1.setRolloverEnabled(true);
        radio1.setVerifyInputWhenFocusTarget(true);
        radio1.addActionListener(this);
        radio1.setFont(new Font("Engravers MT", Font.BOLD, 20));


        radio2 = new JRadioButton("DECODE", false);
        cbg.add(radio2);
        getContentPane().add(radio2);
        radio2.addActionListener(this);  
        radio2.setFocusable(true);
        radio2.setRolloverEnabled(true);
        radio2.setVerifyInputWhenFocusTarget(true);
        radio2.setFont(new Font("Engravers MT", Font.BOLD, 20));


        button1 = new JButton("EXIT");
        getContentPane().add(button1);
        button1.addActionListener(this);
        button1.setFocusable(true);
        button1.setRolloverEnabled(true);
        button1.setVerifyInputWhenFocusTarget(true);
        //button1.setSelected(true);
        button1.setFont(new Font("Engravers MT", Font.BOLD, 18));
        
        timeField = new JTextField(5);
		showSysTime(timeField);
		timeField.setFont(new Font("TimesRoman",Font.BOLD,12));
	//	timeField.setForeground(Color.GRAY);
		timeField.setEditable(false);
		getContentPane().add(timeField);
		
		dateLongField = new JTextField(18);
		dateLongField.setText(showTodayDate());
		getContentPane().add(dateLongField);
		dateLongField.setEditable(false);
		//dateLongField.setForeground(Color.GRAY);
		dateLongField.setFont(new Font("TimesRoman",Font.BOLD,12));
		
        JMenu helpMenu=new JMenu("Help");
	    helpMenu.setMnemonic('h');
        TopicsItem.addActionListener(this);
	    
	    helpMenu.add(TopicsItem);
	    
	    menuBar.add(helpMenu);
	    setJMenuBar(menuBar);
	    
	    radio3 = new JRadioButton("SEND IMAGE", false);
        cbg.add(radio3);
        getContentPane().add(radio3);
        radio3.setFocusable(true);
        radio3.setRolloverEnabled(true);
        radio3.setVerifyInputWhenFocusTarget(true);
        radio3.addActionListener(this);
        radio3.setFont(new Font("Engravers MT", Font.BOLD, 20));


        setSize(getPreferredSize());

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
    	public void showSysTime(final JTextField tf)
	{
	 	final SimpleDateFormat timef = new SimpleDateFormat("HH:mm:ss");
		javax.swing.Timer timer = new javax.swing.Timer(1000, new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
			String s = timef.format(new Date(System.currentTimeMillis()));
			tf.setText(s);
		  }
		});
		timer.start();
	}
	public String showTodayDate()
	{
		Date dt = new Date();
		System.out.println(dt.toString());
		SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy");
		return sdf.format(dt).toString();
	}

    
    public void actionPerformed(ActionEvent e)
    {
    		 String cmd;
	         cmd = e.getActionCommand();
	       
    	if(e.getSource() == radio1)
         {
         	this.dispose();
			Encode cp=new Encode();
			cp.setSize(1000,690);
			cp.setVisible(true);;
          }  
          
           if(e.getSource() == radio2)
         {
         	this.dispose();
			Decode bp=new Decode();
			bp.setSize(1000,690);
			bp.setVisible(true);
          }  
          
         if(e.getSource()==TopicsItem)
          {
          	try
				{
					Runtime run= Runtime.getRuntime();
					run.exec("notepad help.txt");
				}
				catch(Exception ee)
				{
					System.out.println("Error: "+ ee);
				}
				} 
          
      if(cmd.equals("EXIT"))
  
          	{
  	 int res;
      res = JOptionPane.showConfirmDialog(this, "SURE WANT TO EXIT");
      switch(res) 
      {
        case JOptionPane.OK_OPTION:
        System.exit(0);
        break;
        case JOptionPane.NO_OPTION:
        break;
			
		}
      }      
      
}
      public static void main(String args[]){
        Stegno s = new Stegno();

        s.setTitle("Main Menu");
        s.pack();
        s.show();
        
        JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		
		try
		{
			   	UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
	           
		}
		catch (Exception ex)
		{
			System.out.println("Failed loading L&F: ");
			System.out.println(ex);
		}
       
    }
}

class StegnoLayout implements LayoutManager {

    public StegnoLayout() {
    }

    public void addLayoutComponent(String name, Component comp) {
    }

    public void removeLayoutComponent(Component comp) {
    }

    public Dimension preferredLayoutSize(Container parent) {
        Dimension dim = new Dimension(0, 0);

        Insets insets = parent.getInsets();
        dim.width = 700 + insets.left + insets.right;
        dim.height = 400 + insets.top + insets.bottom;

        return dim;
    }

    public Dimension minimumLayoutSize(Container parent) {
        Dimension dim = new Dimension(0, 0);
        return dim;
    }

    public void layoutContainer(Container parent) {
        Insets insets = parent.getInsets();

        Component c;
        c = parent.getComponent(0);
        if (c.isVisible()) {c.setBounds(insets.left+45,insets.top+30,648,42);}
        c = parent.getComponent(1);
        if (c.isVisible()) {c.setBounds(insets.left+285,insets.top+108,240,48);}
        c = parent.getComponent(2);
        if (c.isVisible()) {c.setBounds(insets.left+285,insets.top+190,240,48);}
        c = parent.getComponent(3);
        if (c.isVisible()) {c.setBounds(insets.left+260,insets.top+310,184,48);}
        c = parent.getComponent(4);
        if (c.isVisible()) {c.setBounds(insets.left+536,insets.top+378,58,22);}
        c = parent.getComponent(5);
        if (c.isVisible()) {c.setBounds(insets.left+593,insets.top+378,105,22);}
                
    }
}

