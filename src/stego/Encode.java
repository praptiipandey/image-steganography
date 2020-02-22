
package stego;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.awt.image.*;
import com.sun.image.codec.jpeg.*;
import stego.Stegno;

public class Encode extends JFrame implements ActionListener
{
	private JLabel code_label,secret_label,picture_label;
	private JTextField code_text,secret_text,picture_text;
	private JButton picture_load_button,hide_button,home_button;
	String filepath="",secret_code="",secret_info,user_key="";
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
	static Image newimg;
	int key,k;

	Encode()
	{
		super("Encode");
		con=getContentPane();
		con.setLayout(null);

		code_label=new JLabel("Security Code");
		code_label.setBounds(230,100,150,50);
		code_text=new JTextField(200);
		code_text.setBounds(400,100,250,40);
		secret_label=new JLabel("Secret Information");
		secret_label.setBounds(230,200,150,50);
		secret_text=new JTextField(200);
		secret_text.setBounds(400,200,250,40);

		picture_label=new JLabel("Picture");
		picture_label.setBounds(230,300,250,40);
		picture_text=new JTextField(200);
		picture_text.setBounds(400,300,250,50);
		picture_load_button=new JButton("Load");	
		picture_load_button.setBounds(700,300,150,30);
		picture_load_button.addActionListener(this);
	
		hide_button=new JButton("Encode");
		hide_button.setBounds(400,400,150,30);
		hide_button.addActionListener(this);
		home_button=new JButton("Home");
		home_button.setBounds(700,400,150,30);
		home_button.addActionListener(this);

		jl=new JLabel();
		jl.setBounds(700,500,150,30);

		fd=new FileDialog(new JFrame());

		con.add(code_label);
		con.add(code_text);
		con.add(secret_label);
		con.add(secret_text);
		con.add(picture_label);
		con.add(picture_text);
		con.add(picture_load_button);
		con.add(hide_button);
		con.add(home_button);
		//con.add(jl);
	}

	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==picture_load_button)//picture loading process
		{
			fd.setVisible(true);
			filepath=fd.getDirectory()+fd.getFile();
			picture_text.setText(filepath);
		}else if(ae.getSource()==hide_button)
		{
			int starflag=0;
			secret_code=code_text.getText();
			for(int i=0;i<secret_code.length();i++)
			{
				if(secret_code.charAt(i)=='*')
				{
					starflag=1;
				}
			}
			if(starflag==0)
			{
				secret_info=secret_text.getText();
				user_key=secret_code+"*"+new String(""+secret_info.length());
				System.out.println("user key  :"+user_key);
				String secret_code_info=user_key+"*"+secret_info+"*";
				byte secret_byte_array[]=secret_code_info.getBytes();
				int secret_int_array[]=new int[secret_byte_array.length];
			
				try{
					if(filepath.equals("") && (secret_text.getText()).equals(""))
						JOptionPane.showMessageDialog(null,"image and secret info are empty. enter them");
					else if(secret_info.length()==0 && filepath.length()>0)
						JOptionPane.showMessageDialog(null,"enter secret key");
					else if(filepath.length()==0 && (secret_text.getText()).length()>0)
						JOptionPane.showMessageDialog(null,"load an image");
					else
					{
						ImageIcon ic=new ImageIcon(filepath);
						img=ic.getImage();
						iw=img.getWidth(null);
						ih=img.getHeight(null);
						pix=new int[iw*ih];
						t=new int[iw*ih];
						PixelGrabber pg=new PixelGrabber(img,0,0,iw,ih,pix,0,iw);
						ColorModel cm=pg.getColorModel();
						int ww=pg.getWidth();
						int hh=pg.getHeight();
						pg.grabPixels();

						key=secret_byte_array.length;
						int k=key;
						int j=0;

						for(int i=0;i<pix.length;i++)
						{
							if((i%20)==0 && k>0)
							{
								secret_int_array[j]=(int)secret_byte_array[j];
								System.out.println("user key :"+secret_int_array[j]);
								pix[i]=secret_int_array[j];
								j++;
								k--;
							}
						}
						newimg =con.createImage(new MemoryImageSource(ww,hh,cm,pix, 0, ww));
						jl.setIcon(new ImageIcon(newimg));
						JOptionPane.showMessageDialog(null,"your secret code: "+user_key+"");

						MediaTracker mediaTracker = new MediaTracker(new Container());
						mediaTracker.addImage(newimg, 0);
						mediaTracker.waitForID(0);

						int thumbWidth = 400;//Integer.parseInt(400);
						int thumbHeight = 400;//Integer.parseInt(400);
						double thumbRatio = (double)thumbWidth / (double)thumbHeight;
						int imageWidth = newimg.getWidth(null);
						int imageHeight = newimg.getHeight(null);
						double imageRatio = (double)imageWidth / (double)imageHeight;
		
						if (thumbRatio < imageRatio)
						{
      							thumbHeight = (int)(thumbWidth / imageRatio);
						}
    						else 
						{
      							thumbWidth = (int)(thumbHeight * imageRatio);
	    					}

						// draw original image to thumbnail image object and
						// scale it to the new size on-the-fly
						BufferedImage thumbImage = new BufferedImage(newimg.getWidth(null), newimg.getHeight(null), BufferedImage.TYPE_INT_RGB);
    						Graphics2D graphics2D = thumbImage.createGraphics();
    						graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
      						RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    						graphics2D.drawImage(newimg, 0, 0, newimg.getWidth(null), newimg.getHeight(null), null);
    						// save thumbnail image to OUTFILE
						File f=new File("secpic.jpg");
    						BufferedOutputStream out = new BufferedOutputStream(new
						FileOutputStream(f));
    						JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
    						JPEGEncodeParam param = encoder.
      						getDefaultJPEGEncodeParam(thumbImage);
    						int quality = 80;//Integer.parseInt(args[4]);
    						quality = Math.max(0, Math.min(quality, 100));
    						param.setQuality((float)quality / 100.0f, false);
    						encoder.setJPEGEncodeParam(param);
    						encoder.encode(thumbImage);
    						out.close(); 
    						System.out.println("Done.");	
				
					}
				}catch(Exception e)
				{
					System.out.println(e);			
				}
                                
			}else
				JOptionPane.showMessageDialog(null,"Do not enter '*' in secrect code");	
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
		Encode cp=new Encode();
		cp.setSize(1035,740);
		cp.setVisible(true);
	}
}
