import java.io.File;

public class Menu {
    
    public Menu() {
        File file = new File(".");
        String filePath = file.getAbsolutePath();
        File[] fileList = file.listFiles();
        System.out.println(filePath);
        for(File f : fileList) {
            System.out.println(f.getName());
        }
    
    }
    
    
}
