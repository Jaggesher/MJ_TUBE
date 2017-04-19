import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.File;
import java.security.acl.Group;
import java.util.concurrent.CountDownLatch;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import com.sun.xml.internal.ws.client.dispatch.MessageDispatch;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.util.Duration;

public class MainFrame{
	JFrame frame;
	JFXPanel Panel_1;
	JPanel Panel_2,Panel_3,Panel_4;
	private MediaView view;
	private MediaPlayer player;
	private Scene scene;
	final FileChooser fileChooser = new FileChooser();
	final DirectoryChooser directoryChooser=new DirectoryChooser();
	private SplitPane splitPane;
	private File file;
	private int i = 0; //how many times played
	private JLabel BrBTN,SrBTN;
	private JFrame fullscreen;
	private JFXPanel fullpanel;
	private Scene fullscene;
	private String path;
	private int fullWidth;
	private int fullHeight;
	private MediaPlayer fullplayer;
	private int prewidth;
	private int preheight;
	File CommonPath;
	JList<Icon_Data> list_1;
	Playlist ob=new Playlist();
	private boolean Asa;
	DoubleProperty width;
	DoubleProperty height;
	
	public static void main(String arg[]){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame window = new MainFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public MainFrame() {
		initialize();
		
	}
	
	private void initialize() {
		Asa=true;
		frame=new JFrame("MJ-TUBE");
		frame.setBounds(100, 100, 600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(600, 400));
		frame.setLocationRelativeTo(null);
		
		fullscreen = new JFrame();
		fullscreen.setLayout(new GridLayout(1,1));
		fullscreen.setSize(600, 600);
		fullscreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Panel_1=new JFXPanel();
		Panel_1.setLayout(new GridLayout(1, 1));
		frame.add(Panel_1,BorderLayout.CENTER);
		
		fullpanel = new JFXPanel();
		fullpanel.setLayout(new GridLayout(1, 1));
		
		
		Panel_2= new JPanel();
		frame.add(Panel_2,BorderLayout.EAST);
		Panel_2.setLayout(new BorderLayout());
		
		Panel_4=new JPanel();
		Panel_4.setLayout(new GridLayout(1, 2));
		Panel_2.add(Panel_4,BorderLayout.NORTH);
		
		BrBTN=new JLabel("Folder.");
		BrBTN.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		BrBTN.setHorizontalAlignment(SwingConstants.CENTER);
		BrBTN.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, java.awt.Color.GREEN));
		
		SrBTN=new JLabel("File.");
		SrBTN.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		SrBTN.setHorizontalAlignment(SwingConstants.CENTER);
		SrBTN.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, java.awt.Color.GREEN));
		
		Panel_4.add(SrBTN);
		Panel_4.add(BrBTN);
		
		Panel_3=new JPanel();
		Panel_3.setBackground(java.awt.Color.BLUE);
		Panel_2.add(Panel_3,BorderLayout.CENTER);
		Panel_3.setLayout(new GridLayout(1, 1));
		
		list_1=new JList<Icon_Data>(ob.books);
	    list_1.setCellRenderer(new Renderer());
	    list_1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    JScrollPane pane = new JScrollPane(list_1);
		Panel_3.add(pane);
		
		SetSize();
		

		fileChooser.getExtensionFilters().addAll(
				 new FileChooser.ExtensionFilter("Media files (*.mp4)", "*.mp4"),
				 new FileChooser.ExtensionFilter("Media files (*.mp3)", "*.mp3")
	            );
		
		BrBTN.addMouseListener(new MouseAdapter(){
			 public void mouseClicked(MouseEvent e)  {
				 if(Asa)
				 {
					 Asa=false;
					 Show_2();
				 }
			 }
		});
		
		SrBTN.addMouseListener(new MouseAdapter(){
			 public void mouseClicked(MouseEvent e)  {
				 if(Asa)
				 {
					 Asa=false;
					 Show();
				 }
			 }
		});
		
		list_1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent evt) {
				if (evt.getClickCount() == 2 && !evt.isConsumed()) {
                    evt.consume();
                	int x=list_1.getSelectedIndex();
    				Icon_Data element = (Icon_Data) list_1.getModel()
    			            .getElementAt(x);
    				
    				path=CommonPath.getAbsolutePath()+"\\"+element.getTitle();
    				
    				remove_previous_video();
					ini_player();
					add_controls();
					listener();
                 }
			 }
		});
		
		frame.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent arg0) {
				SetSize();
			}
		});
		
		frame.addWindowStateListener(new WindowStateListener() {
			public void windowStateChanged(WindowEvent arg0) {
				SetSize();
			}
		});
		
	
		Panel_1.addMouseListener(new MouseAdapter() { //Panel_1 is for regualr screen so if it is clicked user must want full screen
			public void mousePressed(MouseEvent arg0) {
			    if(arg0.getClickCount() == 2 && i > 0) 
				{
			    	makeFullscreen();
				}
			}
		
		});
		
		fullpanel.addMouseListener(new MouseAdapter() { //full panel is for full screen so if it is clicked user must want regular screen
			public void mousePressed(MouseEvent arg0) {
			    if(arg0.getClickCount() == 2) //player played atleast onece
				{
			    	make_small();
				}
			}
		
		});
		
	}
	
	private void ini_player()
    {
		  splitPane = new SplitPane(); // splitpane used to make two parts of frame
		  splitPane.setOrientation(Orientation.VERTICAL);
		  splitPane.setStyle("-fx-background-color: #2f4f4f");
		  System.out.println(path);
          Media media = new Media(new File(path).toURI().toString());
          player = new MediaPlayer(media);
          view = new MediaView(player);
          player.play();
    }
	
	

	private void add_controls()
	{
     	Slider slider = new Slider(); //video slider
     	slider.setMinHeight(30);
     	Slider volume = new Slider();
     	volume.setMinWidth(30);
     	volume.setValue(player.getVolume()*100);
     	
     	Image img = new Image("/sources/play.png", 20, 20, true,true);
     	Image img1 = new Image("/sources/pause.png", 20, 20, true,true);
     	Image img2 = new Image("/sources/stop.png", 20, 20, true,true);
     	Image img3 = new Image("/sources/volume.png", 20, 20, true,true);
     	Button pause = new Button("pause"); //button for play and pause
     	pause.setGraphic(new ImageView(img1));
     	pause.setPrefSize(10, 10);
    	Button stop = new Button();
     	stop.setGraphic(new ImageView(img2));
     	stop.setPrefSize(10, 10);
     	Label label = new Label();
     	label.setGraphic(new ImageView(img3));
     
     	HBox hbox = new HBox();
     	hbox.getChildren().add(pause);
     	hbox.getChildren().add(stop);
     	hbox.getChildren().add(label);
     	hbox.getChildren().add(volume);
     	
     	VBox vBox = new VBox();
		vBox.setMinHeight(70);
   	 	vBox.setStyle("-fx-background-color: #2f4f4f");
   	 	vBox.getChildren().add(slider);
   	 	vBox.getChildren().add(hbox);
   	 	hbox.setAlignment(Pos.TOP_CENTER);
   	 	
      	VBox vbox = new VBox();
      	vbox.getChildren().add(view);
      	splitPane.getItems().addAll(vbox,vBox);
      	scene = new Scene(splitPane,400,400,Color.BLACK);
        Panel_1.setScene(scene);
        
        //add listeners
        slider.valueProperty().addListener(new InvalidationListener() { //listener for video jump using slider
 			public void invalidated(Observable arg0) {
 				if(slider.isPressed())
 					player.seek(player.getMedia().getDuration().multiply(slider.getValue()/100));
 			}
 		});
        
	        volume.valueProperty().addListener(new InvalidationListener() {
				public void invalidated(Observable arg0) {
					player.setVolume(volume.getValue()/100);
				}
			});
	        
	       player.currentTimeProperty().addListener(new InvalidationListener() { //updating video slider according to time
				public void invalidated(Observable observable) {
					Platform.runLater(new Runnable() {
						public void run() {
							slider.setValue(player.getCurrentTime().toMillis()/player.getTotalDuration().toMillis()*100);
						}
					});
					
				}
			});
	       
	       pause.setOnAction(new EventHandler<ActionEvent>() { //play pause alteration
		   		public void handle(ActionEvent arg0) {
		   			if(pause.getText() == "pause")
		   			{
		   				player.pause();
		   				pause.setText("play");
		   				pause.setGraphic(new ImageView(img));
		   			}
		   			else
		   			{
		   				player.play();
		   				pause.setText("pause");
		   				pause.setGraphic(new ImageView(img1));
		   			}
		   		}
		   	   });
	       
	       stop.setOnAction(new EventHandler<ActionEvent>() {
	      		public void handle(ActionEvent arg0) {
	      			player.stop();
	      		}
	      	});
       
	}
	
	private void listener()
    {
		width = view.fitWidthProperty();
        height = view.fitHeightProperty();
        
        width.bind(Bindings.selectDouble(view.sceneProperty(), "width")); //for stretching purpose so that video stretches with scene or frame
        height.bind(Bindings.selectDouble(view.sceneProperty(), "height"));
        view.setPreserveRatio(false);
    }
	
	private void remove_previous_video() //to stop playing multiple videos togather
	{
		if(i>0)
			if(player.getStatus().PLAYING != null)
				player.stop();
		
		i++;
	}
	
	private void Show(){
		 final CountDownLatch latch = new CountDownLatch(1);
		 Platform.runLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				File file_Tm=fileChooser.showOpenDialog(null);

				latch.countDown();
				try {
		            latch.await();
		        } catch (InterruptedException ex) {
		            throw new RuntimeException(ex);
		        }
				if(file_Tm!=null){
					CommonPath=new File(file_Tm.getParentFile().getAbsolutePath());
					ob.AddSingle(file_Tm);
					list_1.setListData(ob.books);
					file =file_Tm;
					path = file.getAbsolutePath();
					remove_previous_video();
					ini_player();
					add_controls();
					listener();
				}
				Asa=true;
			}

		 });
	}
	
	private void Show_2(){
		 final CountDownLatch latch = new CountDownLatch(1);
		 Platform.runLater(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				File file_Tm=directoryChooser.showDialog(null);

				latch.countDown();
				try {
		            latch.await();
		        } catch (InterruptedException ex) {
		            throw new RuntimeException(ex);
		        }
				if(file_Tm!=null){
					CommonPath=new File(file_Tm.getAbsolutePath());
					ob.MakePlaylist(CommonPath);
					list_1.setListData(ob.books);
				}
				Asa=true;
			}

		 });
	}
	
	private void SetSize(){
		Rectangle r = frame.getBounds();
    	int  h = r.height;
    	int w = r.width;
    	Panel_4.setPreferredSize(new Dimension(w-(w/4),20));
        Panel_2.setPreferredSize(new Dimension((w/4), h-20));
	}
	
	public void make_small()
	{
		player.pause();
		
		//again iniatializing frame because we deleted all the components of frame in makeFullScreen() function
		frame.add(Panel_1,BorderLayout.CENTER);
		frame.add(Panel_2,BorderLayout.EAST);
		Panel_2.add(Panel_4,BorderLayout.NORTH);
    	Panel_2.add(Panel_3,BorderLayout.CENTER);
    	frame.setBounds(100, 100, prewidth, preheight);
		
		view = new MediaView(player); //start the media from last played . player is paused at the last location
		player.play();
		
		//same as first time intialization of frame
		Slider slider = new Slider();
     	slider.setMinHeight(30);
     	Slider volume = new Slider();
     	volume.setMinWidth(30);
     	volume.setValue(player.getVolume()*100);
     	
     	Image img = new Image("/sources/play.png", 20, 20, true,true);
     	Image img1 = new Image("/sources/pause.png", 20, 20, true,true);
     	Image img2 = new Image("/sources/stop.png", 20, 20, true,true);
     	Image img3 = new Image("/sources/volume.png", 20, 20, true,true);
     	Button pause = new Button("pause");
     	pause.setGraphic(new ImageView(img1));
     	pause.setPrefSize(10, 10);
    	Button stop = new Button();
     	stop.setGraphic(new ImageView(img2));
     	stop.setPrefSize(10, 10);
     	Label label = new Label();
     	label.setGraphic(new ImageView(img3));
     
     	HBox hbox = new HBox();
     	hbox.getChildren().add(pause);
     	hbox.getChildren().add(stop);
     	hbox.getChildren().add(label);
     	hbox.getChildren().add(volume);
     	
     	VBox vBox = new VBox();
		vBox.setMinHeight(70);
   	 	vBox.setStyle("-fx-background-color: #2f4f4f");
   	 	vBox.getChildren().add(slider);
   	 	vBox.getChildren().add(hbox);
   	 	hbox.setAlignment(Pos.TOP_CENTER);
   	 	
      	VBox vbox = new VBox();
      	vbox.getChildren().add(view);
      	
      	SplitPane split = new SplitPane();
      	split.setOrientation(Orientation.VERTICAL);
		split.setStyle("-fx-background-color: #2f4f4f");
      	split.getItems().addAll(vbox,vBox);
      	
      	scene = new Scene(split,400,400,Color.BLACK);
        Panel_1.setScene(scene);
        
        DoubleProperty width = view.fitWidthProperty();
        DoubleProperty height = view.fitHeightProperty();
        
        width.bind(Bindings.selectDouble(view.sceneProperty(), "width"));
        height.bind(Bindings.selectDouble(view.sceneProperty(), "height"));
        view.setPreserveRatio(false);
        
        //add listeners
        slider.valueProperty().addListener(new InvalidationListener() {
 			
 			@Override
 			public void invalidated(Observable arg0) {
 				if(slider.isPressed())
 					player.seek(player.getMedia().getDuration().multiply(slider.getValue()/100));
 			}
 		});
        
	        volume.valueProperty().addListener(new InvalidationListener() {
				public void invalidated(Observable arg0) {
					player.setVolume(volume.getValue()/100);
				}
			});
	        
	       player.currentTimeProperty().addListener(new InvalidationListener() {
				public void invalidated(Observable observable) {
					Platform.runLater(new Runnable() {
						public void run() {
							slider.setValue(player.getCurrentTime().toMillis()/player.getTotalDuration().toMillis()*100);
						}
					});
					
				}
			});
        
	       pause.setOnAction(new EventHandler<ActionEvent>() {
		   		public void handle(ActionEvent arg0) {
		   			if(pause.getText() == "pause")
		   			{
		   				player.pause();
		   				pause.setText("play");
		   				pause.setGraphic(new ImageView(img));
		   			}
		   			else
		   			{
		   				player.play();
		   				pause.setText("pause");
		   				pause.setGraphic(new ImageView(img1));
		   			}
		   		}
		   	   });
	       stop.setOnAction(new EventHandler<ActionEvent>() {
	      		public void handle(ActionEvent arg0) {
	      			player.stop();
	      		}
	      	});
	   
	     //remove all the contents from full screen panel and dispose the panel
		fullpanel.removeAll();
		fullpanel.revalidate();
		fullpanel.repaint();
		fullscreen.getContentPane().removeAll();
		fullscreen.getContentPane().invalidate();
		fullscreen.getContentPane().validate();
		frame.setVisible(true);
		fullscreen.dispose();
		
	}
	
	private void makeFullscreen()
	{
		
		player.pause(); //player paused when double clicked
		
		Toolkit tk = Toolkit.getDefaultToolkit();
		fullWidth = (int) tk.getScreenSize().getWidth();
		fullHeight = (int) tk.getScreenSize().getHeight();
		
		prewidth = frame.getWidth(); //this is for making the frame as it is now later on make_small() function
		preheight = frame.getHeight();
		
		fullscreen.setSize(fullWidth, fullHeight);
		
		BorderPane borderPane = new BorderPane();
        view = new MediaView(player);
        player.play();
        borderPane.setCenter(view);
        
        view.setFitHeight(fullHeight-60);
        view.setFitWidth(fullWidth);
        view.setPreserveRatio(true);
        
     	//adding controls to full screen
     	Slider slider = new Slider();
     	slider.setMinHeight(30);
     	Slider volume = new Slider();
     	volume.setMinWidth(30);
     	volume.setValue(player.getVolume()*100);
     	Image img = new Image("/sources/play.png", 20, 20, true,true);
     	Image img1 = new Image("/sources/pause.png", 20, 20, true,true);
     	Image img2 = new Image("/sources/stop.png", 20, 20, true,true);
     	Image img3 = new Image("/sources/volume.png", 20, 20, true,true);
    
     	Button pause = new Button("pause");
     	pause.setGraphic(new ImageView(img1));
     	pause.setPrefSize(10, 10);
    	Button stop = new Button();
     	stop.setGraphic(new ImageView(img2));
     	stop.setPrefSize(10, 10);
     	Label label = new Label();
     	label.setGraphic(new ImageView(img3));
     
     	HBox hbox = new HBox();
     	hbox.getChildren().add(pause);
     	hbox.getChildren().add(stop);
     	hbox.getChildren().add(label);
     	hbox.getChildren().add(volume);
     	hbox.setAlignment(Pos.TOP_CENTER);
    
        VBox vbox = new VBox();
        vbox.getChildren().add(slider);
        vbox.getChildren().add(hbox);
        vbox.setStyle("-fx-background-color: #2f4f4f");
        vbox.setMinHeight(40);
        
        borderPane.setBottom(vbox);
        borderPane.setStyle("-fx-background-color: BLACK");
        
        
        //adding listeners to controls
        slider.valueProperty().addListener(new InvalidationListener() {
 			public void invalidated(Observable arg0) {
 				if(slider.isPressed())
 					player.seek(player.getMedia().getDuration().multiply(slider.getValue()/100));
 			}
 		});
        
	        volume.valueProperty().addListener(new InvalidationListener() {
				public void invalidated(Observable arg0) {
					player.setVolume(volume.getValue()/100);
				}
			});
	        
	       player.currentTimeProperty().addListener(new InvalidationListener() {
				public void invalidated(Observable observable) {
					Platform.runLater(new Runnable() {
						public void run() {
							slider.setValue(player.getCurrentTime().toMillis()/player.getTotalDuration().toMillis()*100);
						}
					});
					
				}
			});
        
	       pause.setOnAction(new EventHandler<ActionEvent>() {
	   		public void handle(ActionEvent arg0) {
	   			if(pause.getText() == "pause")
	   			{
	   				player.pause();
	   				pause.setText("play");
	   				pause.setGraphic(new ImageView(img));
	   			}
	   			else
	   			{
	   				player.play();
	   				pause.setText("pause");
	   				pause.setGraphic(new ImageView(img1));
	   			}
	   		}
	   	   });
	       stop.setOnAction(new EventHandler<ActionEvent>() {
	      		public void handle(ActionEvent arg0) {
	      			player.stop();
	      		}
	      	});
	       
	    fullpanel.setMaximumSize(new Dimension(fullWidth, fullHeight));
        
        fullscene = new Scene(borderPane, 400, 400,Color.BLACK);
        fullpanel.setScene(fullscene);
		fullscreen.add(fullpanel);
		fullscreen.setUndecorated(true);
		fullscreen.setVisible(true);
		
		//remove all the components from frame and related panel and dispose frame
		Panel_1.removeAll();
		Panel_1.revalidate();
		Panel_1.repaint();
		Panel_2.removeAll();
		Panel_2.revalidate();
		Panel_2.repaint();
		
		
		
		frame.getContentPane().removeAll();
		frame.getContentPane().invalidate();
		frame.getContentPane().revalidate();
		
		frame.dispose();
	}
	
}
