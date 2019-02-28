package application;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.stage.Stage;

public class ConnectionsFX extends Application
{
	private static int nodeStatus[];
	private static int matrix [][];
	private static TextArea taOutput = new TextArea();
	
	public static void main(String[] args) 
	{
        launch(args);
    }//main
	
	public void start(Stage primaryStage)
	{
		 nodeStatus = resetNodeStatus();
		 
		 //The coordinates for the rectangles nodes/"houses"
				  // A    B     C   D    E    F    G    H
		 int x[] = { 50, 210,   20, 170, 320, 140, 370, 20};
		 int y[] = { 20,   20, 170, 170, 170, 320, 320, 320};
		 String houseLetter[] = {"A","B","C","D","E","F","G","H"};

		 //Coordinates for the lines between the houses
		 int line[][] = {{	x[0]+20,	//(A-C)x0	0
							x[2]+20,	//(A-C)x1
							x[0]+40,	//(A-B)x0	1
							x[1],		//(A-B)x1
							x[0]+20,	//(A-D)x0	2
							x[3]+20,	//(A-D)x1
							x[1]+20,	//(B-D)x0	3
							x[3]+20,	//(B-D)x1
							x[2]+40,	//(C-D)x0	4
							x[3],		//(C-D)x1
							x[3]+40,	//(D-E)x0	5
							x[4],		//(D-E)x1
							x[2]+20,	//(C-F)x0	6
							x[5]+20,	//(C-F)x1
							x[5]+20,	//(F-E)x0	7
							x[4]+20,	//(F-E)x1
							x[3]+20,	//(D-G)x0	8
							x[6],		//(D-G)x1
							x[4]+20,	//(E-G)x0	9
							x[6]+20,	//(E-G)x1
							x[5]+40,	//(F-G)x0	10
							x[6] ,		//(F-G)x1
							x[5],	//(F-H)x0	11
							x[7]+40},	//(F-H)x1
							
						{	y[0]+40,	//(A-C)y0
							y[2],		//(A-C)y1
							y[0]+20,	//(A-B)y0
							y[1]+20,	//(A-B)y1
							y[0]+40,	//(A-D)y0
							y[3],		//(A-D)y1
							y[1]+40,	//(B-D)y0
							y[3],		//(B-D)y1
							y[2]+20,	//(C-D)y0
							y[3]+20,	//(C-D)y1
							y[3]+20,	//(D-E)y0
							y[4]+20,	//(D-E)y1
							y[2]+40,	//(C-F)y0
							y[5],		//(C-F)y1
							y[5],		//(F-E)y0
							y[4]+40, 	//(F-E)y1
							y[3]+40, 	//(D-G)y0
							y[6]+20,	//(D-G)y1
							y[4]+40,	//(E-G)y0
							y[6],		//(E-G)y1
							y[5]+20,	//(F-G)y0
							y[6]+20, 	//(F-G)y1
							y[5]+20,	//(F-H)y0
							y[7]+20 }};	//(F-H)y1
	
		 //Numbers to indicate the cost of a line
		 String connectionCost[] = {"8","10","13","11","13","12","12","5","11","9","7","30"};
	
		//Coordinates for the numbers (Cost) between the houses
		 int position[][] ={{	(line[0][2]+line[0][3])/2-5,	//x8
								(line[0][0]+line[0][1])/2-32,	//x10
								(line[0][4]+line[0][5])/2,		//x13
								(line[0][6]+line[0][7])/2+5,	//x11
								(line[0][8]+line[0][9])/2-10,	//x13
								(line[0][10]+line[0][11])/2-10,	//x12
								(line[0][12]+line[0][13])/2-38,	//x12
								(line[0][14]+line[0][15])/2-50,	//x5
								(line[0][16]+line[0][17])/2+15,	//x11
								(line[0][18]+line[0][19])/2+10,	//x9
								(line[0][20]+line[0][21])/2-5,	//x7
								(line[0][22]+line[0][23])/2-15},//x30
								
							{	(line[1][2]+line[1][3])/2-3,	//y8
								(line[1][0]+line[1][1])/2+10,	//y10
								(line[1][4]+line[1][5])/2,		//y13
								(line[1][6]+line[1][7])/2+10,	//y11
								(line[1][8]+line[1][9])/2-3,	//y13
								(line[1][10]+line[1][11])/2-3,	//y12
								(line[1][12]+line[1][13])/2+10,	//y12
								(line[1][14]+line[1][15])/2+20,	//y5
								(line[1][16]+line[1][17])/2+10,	//y11
								(line[1][18]+line[1][19])/2+10,	//y9
								(line[1][20]+line[1][21])/2-3,	//y7
								(line[1][22]+line[1][23])/2-3}};//y30
		 
		 Label lblStart = new Label("Click on the first house to be connected to a telephone cable. "
					+ "The cheapest way to connect the rest of the houses will be calculated and displayed on the right side.");
			lblStart.setWrapText(true);
		 
		 Rectangle rectangle[] = new Rectangle[x.length];//Rectangles for houses
		 Text txtHouseLetter[] = new Text[houseLetter.length];//HouseLetters
		 Line connectionLine[] = new Line[connectionCost.length];//Line between houses
		 Text lineCost[] = new Text[connectionCost.length];//Line Cost
         
         Rectangle rectMark = new Rectangle();//Rectangle to mark selected house
         rectMark.setFill(Color.RED);
         rectMark.setWidth(40);
         rectMark.setHeight(40);
         rectMark.setVisible(false);
         
			
	 	Stage stgStage = new Stage();
	 	BorderPane bpPane = new BorderPane();
	 	Scene scnScene = new Scene(bpPane ,430, 400);
	 	stgStage.setScene(scnScene);
		stgStage.setAlwaysOnTop(true);
		stgStage.setResizable(false);
		stgStage.setTitle("Connections");//add title
		
		
		Group root = new Group();
		final Canvas canvas = new Canvas();
        Pane pane = new Pane();
        
        pane.getChildren().add(canvas);
        root.getChildren().add(rectMark);
		root.getChildren().add(pane);
		bpPane.setCenter(root);
		
		taOutput.setEditable(false);
		taOutput.setMaxSize(175, 162);
		taOutput.setTranslateX(260);
		root.getChildren().add(taOutput);
		bpPane.setTop(lblStart);
		
		//creates the lines between the houses together with the right cost of the line
		int b = 0;
		for (int a = 0; a <= connectionCost.length*2-2; a = a+2)
		{
			connectionLine[b] = new Line(line[0][a], line[1][a], line[0][a+1], line[1][a+1]);
			lineCost[b] = new Text(connectionCost[b]);
			lineCost[b].setFont(Font.font("Serif", FontWeight.BOLD, 30));
			lineCost[b].setX(position[0][b]);
			lineCost[b].setY(position[1][b]);
			root.getChildren().add(lineCost[b]);
			root.getChildren().add(connectionLine[b]);
			b++;
		}
		
		//creates a new rectangle for each house and adds a letter inside
		for(int teller = 0; teller < x.length; teller++)
		 {
			rectangle[teller] = new Rectangle();
			rectangle[teller].setFill(Color.TRANSPARENT);
			rectangle[teller].setStroke(Color.BLACK);
			rectangle[teller].setX(x[teller]);
			rectangle[teller].setY(y[teller]);
			rectangle[teller].setWidth(40);
			rectangle[teller].setHeight(40);
			rectangle[teller].toFront();
			root.getChildren().add(rectangle[teller]);
			txtHouseLetter[teller] = new Text();
			txtHouseLetter[teller].setText(houseLetter[teller]);
			txtHouseLetter[teller].setFont(Font.font("Serif", FontWeight.BOLD, 30));
			txtHouseLetter[teller].setX(x[teller]+10);
			txtHouseLetter[teller].setY(y[teller]+30);
			txtHouseLetter[teller].setFill(Color.BLACK);
			root.getChildren().add(txtHouseLetter[teller]);
		 }//for
		stgStage.show();
		
		//Listener for mouseclick
		root.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

	        public void handle(MouseEvent me) 
	        {
	        	// If A is clicked
			     if (me.getX() >= x[0] && me.getX() <= x[0]+40 && 
			    	me.getY() >= y[0] && me.getY() <= y[0]+40)
			     {
			    	 rectMark.setX(x[0]);
			    	 rectMark.setY(y[0]);
			    	 rectMark.setVisible(true);
			    	 nodeStatus[0] = 1;
			    	 CalculateConnections(connectionLine);
			     }
			     //if B is clicked
			     else if (me.getX() >= x[1] && me.getX() <= x[1]+40 && 
			    	me.getY() >= y[1] && me.getY() <= y[1]+40)
			     {
			    	 rectMark.setX(x[1]);
			    	 rectMark.setY(y[1]);
			    	 rectMark.setVisible(true);
			    	 nodeStatus[1] = 1;
			    	 CalculateConnections(connectionLine);
			     }
			     //if C is clicked
			     else if (me.getX() >= x[2] && me.getX() <= x[2]+40 &&
			    	me.getY() >= y[2] && me.getY() <= y[2]+40)
			     {
			    	 rectMark.setX(x[2]);
			    	 rectMark.setY(y[2]);
			    	 rectMark.setVisible(true);
			    	 nodeStatus[2] = 1;
			    	 CalculateConnections(connectionLine);
			     }
			     //if D is clicked
			     else if (me.getX() >= x[3] && me.getX() <= x[3]+40 && 
			    	me.getY() >= y[3] && me.getY() <= y[3]+40)
			     {
			    	 rectMark.setX(x[3]);
			    	 rectMark.setY(y[3]);
			    	 rectMark.setVisible(true);
			    	 nodeStatus[3] = 1;
			    	 CalculateConnections(connectionLine);
			     }
			     //if E is clicked
			     else if (me.getX() >= x[4] && me.getX() <= x[4]+40 && 
			    	me.getY() >= y[4] && me.getY() <= y[4]+40)
			     {
			    	 rectMark.setX(x[4]);
			    	 rectMark.setY(y[4]);
			    	 rectMark.setVisible(true);
			    	 nodeStatus[4] = 1;
			    	 CalculateConnections(connectionLine);
			     }
			     //if F is clicked
			     else if (me.getX() >= x[5] && me.getX() <= x[5]+40 && 
			    	me.getY() >= y[5] && me.getY() <= y[5]+40)
			     {
			    	 rectMark.setX(x[5]);
			    	 rectMark.setY(y[5]);
			    	 rectMark.setVisible(true);
			    	 nodeStatus[5] = 1;
			    	 CalculateConnections(connectionLine);
			     }
			     //if G is clicked
			     else if (me.getX() >= x[6] && me.getX() <= x[6]+40 && 
			    	me.getY() >= y[6] && me.getY() <= y[6]+40)
			     {
			    	 rectMark.setX(x[6]);
			    	 rectMark.setY(y[6]);
			    	 rectMark.setVisible(true);
			    	 nodeStatus[6] = 1;
			    	 CalculateConnections(connectionLine);
			     }
			     //if H is clicked
			     else if (me.getX() >= x[7] && me.getX() <= x[7]+40 && 
					    	me.getY() >= y[7] && me.getY() <= y[7]+40)
				{
					rectMark.setX(x[7]);
					rectMark.setY(y[7]);
					rectMark.setVisible(true);
			    	nodeStatus[7] = 1;
			    	CalculateConnections(connectionLine);
				}
	           
	        }
	    });
			
		}//start
		
		private static int[][] getMatrix()
		{
			
									 //A0  B1 C2  D3   E4  F5  G6  H7
			int matrix [][] = { 	{  0,  8, 10, 13,  0,  0,  0,  0},//A0
									{  8,  0,  0, 11,  0,  0,  0,  0},//B1
									{ 10,  0,  0, 13,  0, 12,  0,  0},//C2
									{ 13, 11, 13,  0, 12,  0, 11,  0},//D3
									{  0,  0,  0, 12,  0,  5,  9,  0},//E4
									{  0,  0, 12,  0,  5,  0,  7, 30},//F5
									{  0,  0,  0, 11,  9,  7,  0,  0},//G6
									{  0,  0,  0,  0,  0, 30,  0,  0} //H7
								};
				return matrix;
		}//setMatrix()
		
		private static int[] resetNodeStatus()
		{
			//NodeStatus
			int NodeStatus[] = {0,0,0,0,0,0,0,0};
			return NodeStatus;
		}//resetNodeStatus()
		
		public static void CalculateConnections(Line connectionLine[])
		{
			matrix = getMatrix();
			int tempX = 0;
			int tempY = 0;
			int lowestValue = 0;
			int totalCost = 0;
			int remainingConnections = matrix.length-1;
			String nodeLetter[] = new String[2];
			String output = "";
			
			while(remainingConnections > 0)
			{
				lowestValue = 0;
				for (int x = 0 ; x <= matrix.length-1 ; x++)
				{
					if (nodeStatus[x] == 1)//Checks if the node is active/connected
					{
						for (int y = 0 ; y <= matrix.length-1 ; y++)
						{
							if (lowestValue == 0 && nodeStatus[y] == 0)	//Set the lowest value to the current value if not set yet
							{						//(lowestValue not set if lowestValue == 0
								lowestValue = matrix[x][y];
								tempX = x;
								tempY = y;
							}//if
							//Check if the lowest value is lower than the value before
							//and if the next node already is active or not
							//then sets a temporary "best choice"
							if (matrix[x][y] < lowestValue && matrix[x][y] != 0 && nodeStatus[y] == 0)
							{
									lowestValue = matrix[x][y];
									tempX = x;
									tempY = y;
							}//if
						}//for
					}//if
				}//for

				//Checks which letters to use in the string "output"
				if (tempX == 0) nodeLetter[0] = "A";	
				else if (tempX == 1) nodeLetter[0] = "B";	
				else if (tempX == 2) nodeLetter[0] = "C";	
				else if (tempX == 3) nodeLetter[0] = "D";	
				else if (tempX == 4) nodeLetter[0] = "E";	
				else if (tempX == 5) nodeLetter[0] = "F";	
				else if (tempX == 6) nodeLetter[0] = "G";	
				else if (tempX == 7) nodeLetter[0] = "H";	
				
				if (tempY == 0) nodeLetter[1] = "A";
				else if (tempY == 1) nodeLetter[1] = "B";
				else if (tempY == 2) nodeLetter[1] = "C";
				else if (tempY == 3) nodeLetter[1] = "D";
				else if (tempY == 4) nodeLetter[1] = "E";
				else if (tempY == 5) nodeLetter[1] = "F";
				else if (tempY == 6) nodeLetter[1] = "G";
				else if (tempY == 7) nodeLetter[1] = "H";
				
				//Checks which nodes are selected and highlights the line between them
				if ((tempX == 0 && tempY == 2) || (tempX == 2 && tempY == 0))connectionLine[0].setStroke(Color.YELLOW);
				else if ((tempX == 0 && tempY == 1) || (tempX == 1 && tempY == 0)) connectionLine[1].setStroke(Color.YELLOW);
				else if ((tempX == 0 && tempY == 3) || (tempX == 3 && tempY == 0)) connectionLine[2].setStroke(Color.YELLOW);
				else if ((tempX == 1 && tempY == 3) || (tempX == 3 && tempY == 1)) connectionLine[3].setStroke(Color.YELLOW);
				else if ((tempX == 2 && tempY == 3) || (tempX == 3 && tempY == 2)) connectionLine[4].setStroke(Color.YELLOW);
				else if ((tempX == 3 && tempY == 4) || (tempX == 4 && tempY == 3)) connectionLine[5].setStroke(Color.YELLOW);
				else if ((tempX == 2 && tempY == 5) || (tempX == 5 && tempY == 2)) connectionLine[6].setStroke(Color.YELLOW);
				else if ((tempX == 5 && tempY == 4) || (tempX == 4 && tempY == 5)) connectionLine[7].setStroke(Color.YELLOW);
				else if ((tempX == 3 && tempY == 6) || (tempX == 6 && tempY == 3)) connectionLine[8].setStroke(Color.YELLOW);
				else if ((tempX == 4 && tempY == 6) || (tempX == 6 && tempY == 4)) connectionLine[9].setStroke(Color.YELLOW);
				else if ((tempX == 5 && tempY == 6) || (tempX == 6 && tempY == 5)) connectionLine[10].setStroke(Color.YELLOW);
				else if ((tempX == 5 && tempY == 7) || (tempX == 7 && tempY == 5)) connectionLine[11].setStroke(Color.YELLOW);
				
				output = (output + "\nFrom " + nodeLetter[0] + " to " + nodeLetter[1] + ".\t Cost: " + matrix[tempX][tempY] + "000 kr");
				totalCost = totalCost + matrix[tempX][tempY];
				matrix[tempX][tempY] = matrix[tempY][tempX] = 0;
				remainingConnections--;
				nodeStatus[tempY] = 1;
			}//while
			
			output = ("Total cost: " + totalCost +"000 kr.\n" + output);
			taOutput.setText(output);
			nodeStatus = resetNodeStatus();
		}//calculate 
	 }//class