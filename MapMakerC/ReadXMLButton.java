public class ReadXMLButton extends Button{
    
    public ReadXMLButton(){
        this.setImage("MakeXML.png");
        this.getImage().scale(80, 80);
    }
    
    public void clicked() {
        MyWorld.theWorld.setupScene();
        MyWorld.readXMLMap("XMLTest.xml");
        getWorld().removeObject(MyWorld.theWorld.mapCreationButton);
        getWorld().removeObject(MyWorld.theWorld.readXMLButton);
      
    }
    
}
