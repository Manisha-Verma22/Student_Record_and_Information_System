import java.awt.*;

import javax.swing.*;
/**
 *@author  Jeshurun Flores; derived from <tt>EasyGridLayout</tt> of Sheila Nisperos
 *@version 3
 *@see     EasyGridLayout
 *@since   JDK 1.4
 */
public class EasierGridLayout extends GridBagLayout {
	/**
	 *Overides <tt>setConstraints</tt> method from GridBagLayout and defines
	 *predefined settings for accurate accesssibility of programmer. Also defines
	 *default alignments and insets for each Component.
	 *@param	c       Component .
	 *@param	row     Component x-position placement.
	 *@param	col     Component y-position placement.
	 *@param	width   Component row span.
	 *@param	height  Component column span.
	 */
  public void setConstraints(Component c, int row, int col, int width, int height) {
    if(c instanceof JLabel)
      finishSet(c,row,col, width, height,
                0,0,
                GridBagConstraints.NONE,
                GridBagConstraints.WEST);
    else if(c instanceof JButton)
      finishSet(c,row,col, width, height,
                0,0,
                GridBagConstraints.NONE,
                GridBagConstraints.CENTER);
    else if(c instanceof JTextField)
      finishSet(c,row,col, width, height,
                50,0,
                GridBagConstraints.NONE,
                GridBagConstraints.NORTHWEST);
    else if(c instanceof JPasswordField)
      finishSet(c,row,col, width, height,
                100,0,
                GridBagConstraints.HORIZONTAL,
                GridBagConstraints.NORTHWEST);
    else if(c instanceof JScrollPane)
      finishSet(c,row,col, width, height,
                0,0,
                GridBagConstraints.BOTH,
                GridBagConstraints.NORTHWEST);
    else if(c instanceof JTextArea)
      finishSet(c,row,col, width, height,
                100,100,
                GridBagConstraints.NONE,
                GridBagConstraints.WEST);
    else if(c instanceof JScrollPane)
      finishSet(c,row,col, width, height,
                100,100,
                GridBagConstraints.BOTH,
                GridBagConstraints.WEST);
    else if(c instanceof JList)
      finishSet(c,row,col, width, height,
                100,100,
                GridBagConstraints.NONE,
                GridBagConstraints.NORTHWEST);
    else if(c instanceof JCheckBox)
      finishSet(c,row,col, width, height,
                0,0,
                GridBagConstraints.HORIZONTAL,
                GridBagConstraints.WEST);
    else if(c instanceof JRadioButton)
      finishSet(c,row,col, width, height,
                0,0,
                GridBagConstraints.HORIZONTAL,
                GridBagConstraints.NORTHWEST);
    else if(c instanceof JPanel)
      finishSet(c,row,col, width, height,
                100,100,
                GridBagConstraints.BOTH,
                GridBagConstraints.NORTHWEST);
    else if(c instanceof JDesktopPane)
      finishSet(c,row,col, width, height,
                100,100,
                GridBagConstraints.BOTH,
                GridBagConstraints.CENTER);
    else if(c instanceof JTabbedPane)
      finishSet(c,row,col, width, height,
                100,100,
                GridBagConstraints.BOTH,
                GridBagConstraints.CENTER);
    else if(c instanceof JSpinner)
      finishSet(c,row,col, width, height,
                0,0,
                GridBagConstraints.HORIZONTAL,
                GridBagConstraints.CENTER);
    else if(c instanceof JComboBox)
      finishSet(c,row,col, width, height,
                0,0,
                GridBagConstraints.HORIZONTAL,
                GridBagConstraints.CENTER);
  }
  
  /**
   *@param	c        Component of any instance.
   *@param	y        Component y-position placement.
   *@param	x        Component x-position placement.
   *@param	w        Component row span.
   *@param	h        Component column span.
   *@param	weightx  Component x-weight with default value according
   *                 to the Component instance.
   *@param	weighty  Component y-weight with default value according
   *                 to the Component instance.
   *@param	fill     Component stretch fill.
   *@param	anchor   Component alignment.
   */
  //default constraint settings
  private void finishSet(Component c, int y, int x, int w, int h, 
                  int weightx, int weighty, int fill, int anchor) {
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets.bottom = 3;
    gbc.insets.left = 3;
    gbc.insets.right = 3;
    gbc.insets.top = 3;
    
    gbc.weightx = weightx;
    gbc.weighty = weighty;
    
    gbc.fill = fill;
    gbc.anchor = anchor;
    
    gbc.gridx = x-1;
    gbc.gridy = y-1;

    gbc.gridwidth = w;
    gbc.gridheight = h;
    
    setConstraints(c,gbc);
  }
  /**
   *Can be used for defining own component layout setting, placements, and
   *adjustments.
   *
   *@param	c              Component of any instance.
   *@param	y              Component y-position placement.
   *@param	x              Component x-position placement.
   *@param	w              Component row span.
   *@param	h              Component column span.
   *@param	weightx        Component x-weight with default value according
   *                       to the Component instance.
   *@param	weighty        Component y-weight with default value according
   *                       to the Component instance.
   *@param	fill           Component stretch fill.
   *@param	anchor         Component alignment.
   *@param	bottomInsets   Component bottom inset setting.
   *@param	leftInsets     Component left inset setting.
   *@param	rightInsets    Component right inset setting.
   *@param	topInsets      Component top inset setting.
   */
  //user defined constraint setting
  
  public void defineOwnConstraints(Component c, int y, int x, int w, int h,
              int weightx, int weighty, int fill, int anchor,int bottomInsets,
              int leftInsets, int rightInsets, int topInsets){
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets.bottom = bottomInsets;
    gbc.insets.left = leftInsets;
    gbc.insets.right = rightInsets;
    gbc.insets.top = topInsets;
    
    gbc.weightx = weightx;
    gbc.weighty = weighty;
    
    gbc.fill = fill;
    gbc.anchor = anchor;
    
    gbc.gridx = x-1;
    gbc.gridy = y-1;

    gbc.gridwidth = w;
    gbc.gridheight = h;
    
    setConstraints(c,gbc);
  }
  
  
  public void Center(Component c, int y, int x, int w, int h){
  	defineOwnConstraints(c,y,x,w,h,1,1,GridBagConstraints.NONE,
  											GridBagConstraints.CENTER,1,1,1,1);
  }
  public void West(Component c,int y, int x, int w, int h){
  	defineOwnConstraints(c,y,x,w,h,1,1,GridBagConstraints.NONE,
  											GridBagConstraints.WEST,1,1,1,1);
  }
  public void WestHorizontal(Component c,int y, int x, int w, int h){
  	defineOwnConstraints(c,y,x,w,h,1,1,GridBagConstraints.HORIZONTAL,
  											GridBagConstraints.WEST,1,1,1,1);
  }
  public void East(Component c,int y, int x, int w, int h){
  	defineOwnConstraints(c,y,x,w,h,1,1,GridBagConstraints.NONE,
  											GridBagConstraints.EAST,1,1,1,1);
  }
  public void CenterBoth(Component c,int y, int x, int w, int h){
  	defineOwnConstraints(c,y,x,w,h,1,1,GridBagConstraints.BOTH,
  											GridBagConstraints.CENTER,1,1,1,1);
  }
  public void CenterVertical(Component c,int y, int x, int w, int h){
  	defineOwnConstraints(c,y,x,w,h,1,1,GridBagConstraints.VERTICAL,
  											GridBagConstraints.CENTER,1,1,1,1);
  }
  public void CenterHorizontal(Component c,int y, int x, int w, int h){
  	defineOwnConstraints(c,y,x,w,h,1,1,GridBagConstraints.HORIZONTAL,
  											GridBagConstraints.CENTER,1,1,1,1);
  }

  
}
