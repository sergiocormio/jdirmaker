package resources;

import javax.swing.ImageIcon;

public class ResourcesFactory {
	
		//APP icon
		public static ImageIcon getAppIcon(){
			return new ImageIcon(ResourcesFactory.class.getResource("folders_32.png"));
		}
		
		//APP Image
		public static ImageIcon getAppImage() {
			return new ImageIcon(ResourcesFactory.class.getResource("folders_256.png"));
		}

		//Create icon
		public static ImageIcon getCreateIcon(){
			return new ImageIcon(ResourcesFactory.class.getResource("tick.png"));
		}
		
		//Open Folder Icon
		public static ImageIcon getOpenFolderIcon(){
			return new ImageIcon(ResourcesFactory.class.getResource("folder-horizontal-open.png"));
		}
		
		//Choose Folder Icon
		public static ImageIcon getChooseFolderIcon(){
			return new ImageIcon(ResourcesFactory.class.getResource("folder-search-result.png"));
		}
				
		
}
