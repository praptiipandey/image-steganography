
package stego;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.awt.image.*;

public class Decode extends JFrame implements ActionListener
{
	private JLabel code_label,picture_label;
	private JTextField code_text,picture_text;
	private JButton picture_load_button,break_button,home_button;
	String filepath="",secret_code="",secret_info="";
	Container con=null;
	JLabel jl;
	byte img_byte[]=new byte[6000];
	FileDialog fd;
	

	//////// Variables for creating an image from an integer array  ///////////////////////////

	Image img;
	Dimension d;
	int iw,ih;
	int w=10,h=10;
	int pix[];
	int hist[]=new int[256];
	int t[];
	int max_hist=0;
	boolean ok;
	Image newimg;
	int key,k;
	String user_key="";

	Decode()
	{
		super("DECODE");
		con=getContentPane();
		con.setLayout(null);

		code_label=new JLabel("Security Code");
		code_label.setBounds(230,200,150,50);
		code_text=new JTextField(200);
		code_text.setBounds(400,200,250,40);
		
		picture_label=new JLabel("Picture");
		picture_label.setBounds(230,300,250,40);
		picture_text=new JTextField(200);
		picture_text.setBounds(400,300,250,50);
		picture_load_button=new JButton("Load");	
		picture_load_button.setBounds(700,300,150,30);
		picture_load_button.addActionListener(this);
	
		break_button=new JButton("Decode");
		break_button.setBounds(400,400,150,30);
		break_button.addActionListener(this);
		home_button=new JButton("Home");
		home_button.setBounds(700,400,150,30);
		home_button.addActionListener(this);
		
		jl=new JLabel();
		jl.setBounds(700,500,150,30);

		fd=new FileDialog(new JFrame());

		con.add(code_label);
		con.add(code_text);
		con.add(picture_label);
		con.add(picture_text);
		con.add(picture_load_button);
		con.add(break_button);
		con.add(home_button);
		con.add(jl);
	}

	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==picture_load_button)
		{
			fd.setVisible(true);
			filepath=fd.getDirectory()+fd.getFile();
			picture_text.setText(filepath);
		}else if(ae.getSource()==break_button)
		{
			String sc=code_text.getText();
			int star_flag=0;
			String star_value="";
			for(int i=0;i<sc.length();i++)
			{
				if(sc.charAt(i)=='*')
					star_flag=1;
				if(star_flag==1&& star_flag!=2)
				{
					i=++i;
					star_value=sc.substring(i);
					star_flag=2;					
				}
			}
			System.out.println("star value er:"+Integer.parseInt(star_value));
			k=sc.length()+1+Integer.parseInt(star_value);
			try{
			img=Encode.newimg;
			key=k;
			System.out.println("key ckeck in temp:"+key);
			user_key=sc;

			Container con=getContentPane();

			iw=img.getWidth(null);
			ih=img.getHeight(null);
			pix=new int[iw*ih];
			t=new int[iw*ih];

			PixelGrabber pg=new PixelGrabber(img,0,0,iw,ih,pix,0,iw);
			ColorModel cm=pg.getColorModel();
			int ww=pg.getWidth();
			int hh=pg.getHeight();
			pg.grabPixels();

			int secret_check[]=new int[sc.length()];
			byte sc_byte[]=sc.getBytes();
		
			for(int i=0;i<sc.length();i++)
				secret_check[i]=sc_byte[i];

			int secret_info[]=new int[key];		
			byte b[]=new byte[key];
			int j=0,loop=0,flag=0,star2_flag=0;

			System.out.println("hi welcome");

			for(int i=0;i<pix.length;i++)
			{
				if((i%20)==0 && k>0 && flag==0) 
				{
					
					System.out.println("one");

					if(loop<user_key.length() && secret_check[loop]==pix[i] && star2_flag<2)
					{
						System.out.println("two");
						if((char)secret_check[loop]=='*')						
						{
							star2_flag++;
						}

						k--;
						loop++;
					}else if(star2_flag>=1)
					{
						System.out.println("else if");
						secret_info[j]=pix[i];
						b[j]=(byte)pix[i];
						System.out.println("secrect pix :"+new String(""+(char)b[j])+"");
						j++;
						k--;	
					}
					else 
					{
						System.out.println("star flag  :"+star2_flag);
						System.out.println("else");
						flag=1;
					}
				}
			}
			if(flag==0)	
			{
				String s=new String(b);
				s=new String(s.substring(1));
				
				System.out.println("secret information :"+s);
				System.out.println("key  :"+key);			
				JOptionPane.showMessageDialog(null,"Secret Information is : "+s);
			}
			else
				JOptionPane.showMessageDialog(null,"code you entered is not valid");		
			newimg =con.createImage(new MemoryImageSource(ww,hh,cm,pix, 0, ww));
		}catch(Exception e)
		{
			System.out.println(e);
		}
		}else 
		{
			this.dispose();
			Stegno h=new Stegno();
			h.setSize(750,500);
			h.setVisible(true);
                }
	}
	public static void main(String args[])
	{
		Decode bp=new Decode();
		bp.setSize(1035,740);
		bp.setVisible(true);
	}
}
