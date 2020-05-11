package com.al375875.ujimaze.model;

import android.content.Context;
import android.util.Log;

import com.al375875.ujimaze.MazeActivity;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

import static com.al375875.ujimaze.model.Direction.DOWN;
import static com.al375875.ujimaze.model.Direction.LEFT;
import static com.al375875.ujimaze.model.Direction.RIGHT;
import static com.al375875.ujimaze.model.Direction.UP;

public class mazeModel {


    private final int pixelWidth, heightPixels;
    private final MazeActivity mazeActivity;
    Context context;

    private float posX, posY;
    private Position posOrigin;
    private Position postMovePosition;
    private Set<Position> tarjetsOrigin;

    public int currentLvl = 0;
    public boolean Hint;
    private Deque<Direction> lastDirections;
    private Deque<Position> lastPositions;
    public boolean gameCompelte;

    private boolean moving, mazeComplete, onTarget;
    static float SPEED = 2;


    static String[] maz = new String[]
            {
                    "+-+-+-+-+-+-+-+",
                    "| |     |     |",
                    "+ + +-+ +   +-+",
                    "|        O    |",
                    "+     +     + +",
                    "|     |     | |",
                    "+ +-+ + + + + +",
                    "|       | |   |",
                    "+      -+ +  -+",
                    "|             |",
                    "+-+ + + +-+   +",
                    "|   |X|       |",
                    "+   +-+ +   + +",
                    "|       |   | |",
                    "+-+-+-+-+-+-+-+"

            };
    static String[] solMaz = new String[]
            {
                    "+-+-+-+-+-+-+-+",
                    "| |r r d|     |",      //⟵
                    "+ +u+-+d+r+r+d+",
                    "|  u l d O   d|",      //⟵
                    "+ +d+c+ +-+d+l+",
                    "|  d d|    d| |",      //⟵
                    "+d+l+ +r+d+d+ +",
                    "|r r r u| |l  |",      //⟵
                    "+       + +  -+",
                    "|             |",      //⟵
                    "+-+ + + +-+   +",
                    "|   |X|       |",      //⟵
                    "+   +-+ +   + +",
                    "|       |   | |",      //⟵
                    "+-+-+-+-+-+-+-+"
                    //        ^ ^ ^ ^ ^ ^ ^
                    //        | | | | | | |

            };


    static String[] maz1 = new String[]
            {
                    "+-+-+-+-+-+-+-+",
                    "|     |O      |",      //⟵   |
                    "+ + + + +-+-+-+",
                    "| | |   |     |",      //⟵
                    "+ + +-+-+ +-+ +",
                    "| | |X      | |",      //⟵
                    "+ + + + +-+ + +",
                    "| | |   |   | |",      //⟵
                    "+ + +-+ + +-+ +",
                    "| | | | |   | |",      //⟵
                    "+ + + +-+-+-+ +",
                    "| |   |     | |",      //⟵
                    "+ +-+-+ + + + +",
                    "|       |     |",      //⟵
                    "+-+-+-+-+-+-+-+"
                    //        ^ ^ ^ ^ ^ ^ ^
                    //        | | | | | | |
            };
    static String[] solMaz1 = new String[]
            {
                    "+-+-+-+-+-+-+-+",
                    "|d|l l d|     |",      //⟵
                    "+d+ +u+l+d+l+l+",
                    "|d     l l   u|",      //⟵
                    "+d+ + + +-+ +u+",
                    "|d    |     |u|",      //⟵
                    "+d+ + +r+r+d+u+",
                    "|r r r u| |r u|",      //⟵
                    "+       + +  -+",
                    "|             |",      //⟵
                    "+-+ + + +-+   +",
                    "|   |X|       |",      //⟵
                    "+   +-+ +   + +",
                    "|       |   | |",      //⟵
                    "+-+-+-+-+-+-+-+"
                    //        ^ ^ ^ ^ ^ ^ ^
                    //        | | | | | | |
            };
    static String[] maz2 = new String[]
            {
                    "+-+-+-+-+-+-+-+",
                    "|         |   |",      //⟵
                    "+ + + +-+ + +-+",
                    "| |           |",      //⟵
                    "+-+ + + + + + +",
                    "|O  |         |",      //⟵
                    "+-+-+ +-+-+ +-+",
                    "|     |      X|",      //⟵
                    "+ + +-+ + + + +",
                    "| |           |",      //⟵
                    "+ + + + +-+ +-+",
                    "|     |       |",      //⟵
                    "+ +-+-+ + + + +",
                    "|X            |",      //⟵
                    "+-+-+-+-+-+-+-+"
                    //        ^ ^ ^ ^ ^ ^ ^
                    //        | | | | | | |
            };
    static String[] solMaz2 = new String[]
            {
                    "+-+-+-+-+-+-+-+",
                    "|  r r r d    |",      //⟵
                    "+ +u+ + +d+ + +",
                    "|r u d l l    |",      //⟵
                    "+d+l+l+r+r+r+ +",
                    "|d  |  u      |",      //⟵
                    "+d+ + +u+l+l+l+",
                    "|r r r|r r r u|",      //⟵
                    "+ + + + + + +-+",
                    "|             |",      //⟵
                    "+ + + + + + +-+",
                    "|     |       |",      //⟵
                    "+ +-+ + + + + +",
                    "|X|           |",      //⟵
                    "+-+-+-+-+-+-+-+"
                    //        ^ ^ ^ ^ ^ ^ ^
                    //        | | | | | | |
            };

    static String[] maz3 = new String[]
            {
                    "+-+-+-+-+-+-+-+",
                    "|X|    O|   | |",      //⟵
                    "+ + +-+-+ + + +",
                    "| | |     |   |",      //⟵
                    "+ + + +-+-+-+ +",
                    "| |   |       |",      //⟵
                    "+ +-+ + +-+-+-+",
                    "| |   | |     |",      //⟵
                    "+ +-+-+ + +-+ +",
                    "| |       |   |",      //⟵
                    "+ + +-+-+-+ +-+",
                    "| |X|     |   |",      //⟵
                    "+ +-+ +-+ + + +",
                    "|       |     |",      //⟵
                    "+-+-+-+-+-+-+-+"
                    //        ^ ^ ^ ^ ^ ^ ^
                    //        | | | | | | |
            };
    static String[] solMaz3 = new String[]
            {
                    "+-+-+-+-+-+-+-+",
                    "|  d l l r d  |",      //⟵
                    "+u+d+r+r+u+r+d+",
                    "|u r u d l l l|",      //⟵
                    "+u+ + +d+r+r+d+",
                    "|u d l t u d l|",      //⟵
                    "+u+ +d+l+l+d+ +",
                    "|u l l   u l  |",      //⟵
                    "+ + + + + + + +",
                    "|             |",      //⟵
                    "+ + + + + + + +",
                    "|             |",      //⟵
                    "+ + + + + + + +",
                    "|             |",      //⟵
                    "+-+-+-+-+-+-+-+"
                    //        ^ ^ ^ ^ ^ ^ ^
                    //        | | | | | | |
            };

    static String[] maz4 = new String[]
            {
                    "+-+-+-+-+-+-+-+",
                    "|     |  O|   |",      //⟵
                    "+ + + + +-+ + +",
                    "| |     |     |",      //⟵
                    "+ +-+-+-+ + + +",
                    "|    X| |   | |",      //⟵
                    "+ + + + + +-+ +",
                    "| |       |   |",      //⟵
                    "+ +-+-+ +-+ + +",
                    "| |   |     | |",      //⟵
                    "+ + + +-+ + +-+",
                    "|   |     |   |",      //⟵
                    "+-+-+-+ + + + +",
                    "|X            |",      //⟵
                    "+-+-+-+-+-+-+-+"
                    //        ^ ^ ^ ^ ^ ^ ^
                    //        | | | | | | |
            };

    static String[] solMaz4 = new String[]
            {
                    "+-+-+-+-+-+-+-+",
                    "|d l   d l d l|",      //⟵
                    "+d+u+l+l+ +d+u+",
                    "|d r     d l u|",      //⟵
                    "+d+u+l+l+l+r+u+",
                    "|d r d     u  |",      //⟵
                    "+r+u+r+ + +u+l+",
                    "|  l l t r r u|",      //⟵
                    "+ + + + + + + +",
                    "|             |",      //⟵
                    "+ + + + + + + +",
                    "|             |",      //⟵
                    "+ + + + + + + +",
                    "|             |",      //⟵
                    "+-+-+-+-+-+-+-+"
                    //        ^ ^ ^ ^ ^ ^ ^
                    //        | | | | | | |
            };


    private Maze maze;

    private static final String[][] templateSolution = {solMaz,solMaz1,solMaz2,solMaz3,solMaz4};

    private static final String[][] templates = {maz,maz1,maz2,maz3,maz4};

    public static final Maze mazes[];

    static {
        mazes = new Maze[templates.length];
        int i = 0;
        for (String[] template : templates) {
            mazes[i++] = new Maze(template);
        }
    }

    private Position preMovePos;
    private Direction lastDir;


    public mazeModel(Context context, int width, int height) {
        this.pixelWidth = width;
        this.heightPixels = height;
        this.context=context;
        gameCompelte=false;

        mazeActivity= new MazeActivity();

        lastDirections= new ArrayDeque<Direction>();
        lastPositions= new ArrayDeque<Position>();


        maze = mazes[currentLvl];
        posOrigin = new Position(maze.getOrigin());
        posX = posOrigin.getRow();
        posY = posOrigin.getCol();

        tarjetsOrigin = new HashSet<>();

        for (Position p : maze.getTargets()) {
            tarjetsOrigin.add(p);
        }

        mazeComplete = false;
        onTarget = false;
        Hint = false;
    }

    public void update(Direction dir) {
        /*Log.d("stack","Stack: "+ Arrays.toString(lastDirections.toArray())+"\nLastDir: "+lastDir);

        Log.d("update", "Last pos: "+preMovePos+ "CurrentPos"+ maze.getOrigin());*/



        //Calcular pos final
       /* Position currentPlayerPos= new Position(maze.getOrigin().getRow(),maze.getOrigin().getCol());   //la posicion del jugador en la matriz

        postMovePosition= calculateFinalPos(posOrigin,dir);
        Log.d("updateModel", "PlayerPos "+ maze.getOrigin().toString()+"postMovePos: "+ posX);

        while((int)posX != postMovePosition.getRow() || (int)posY != postMovePosition.getCol() )
        {
            Log.d("updateModel", "PlayerPos "+ posX);
            moving=true;
            setCoords(deltaTime, dir);
        }


       /*
        if(!isMoving()){
           return;
        }
        else{
            //setCoords(deltaTime, dir);
        }*/
    }

    public Position calculateFinalPos(Position pos, Direction dir) {
        Position temp = pos;
        Position finalPos;

        while (!maze.hasWall(temp, dir)) {
            moving = true;
            temp.move(dir);

        }
        finalPos = temp;
        return finalPos;
    }


  /*  public void setCoords(float t, Direction dir) {

        if (dir == UP) {
            posY = posY - (t * SPEED);
        } else if (dir == DOWN) {
            posY = posY + (t * SPEED);
        } else if (dir == RIGHT) {
            posX = posX + (t * SPEED);
        } else if (dir == Direction.LEFT) {
            posX = posX - (t * SPEED);
        }

        /*maze.getOrigin().move(dir);
        maze.getOrigin().move(dir);*/

/*
        while(moving){
            if      (dir == Direction.UP)   { posY = posY - (t * SPEED); }
            else if (dir == Direction.DOWN) { posY = posY + (t * SPEED); }
            else if (dir == Direction.RIGHT){ posX = posX + (t * SPEED); }
            else if (dir == Direction.LEFT) { posX = posX - (t * SPEED); }

        maze.getOrigin().setCol((int)posX);
        maze.getOrigin().setRow((int)posY);

        Position p=new Position((int)posX,(int)posY);
        if(maze.hasWall(p,dir)){moving=false;}
        }


    }
    //targetReached();*/


    // Maze
    public void resetMaze(Position playerP, Collection<Position> t, Position origin, Collection<Position> resetTarjets) {

        lastDirections= new ArrayDeque<Direction>();
        lastPositions= new ArrayDeque<Position>();
        playerP.set(origin);

        for (Position p : resetTarjets) {
            t.add(p);
        }

    }

    public Maze getCurrentMaze() {
        maze = mazes[currentLvl];
        return maze;
    }

    public String[] getCurrentSolution() {
        return templateSolution[currentLvl];
    }

    public void nextMaze() {

        Log.d("next", "current: "+currentLvl+" Lenght: "+mazes.length);
        //int a=
        if(currentLvl+1 < mazes.length)
        {
            lastDirections= new ArrayDeque<Direction>();
            lastPositions= new ArrayDeque<Position>();
            Hint = false;
            currentLvl += 1;
            Log.d("next", "newcurrent: "+currentLvl+" Lenght: "+mazes.length);

            maze = mazes[currentLvl];
        }
        else {

            gameCompelte=true;
            //mazeActivity.endGame();
        }
    }



    //Moving around
    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    public boolean isMoving() {
        return moving;
    }

    public void startMoving() {

        // character is moving if()
        int intX = (int) Math.floor(posX);
        int intY = (int) Math.floor(posY);
        Position position = new Position(intX, intY);

        Direction dir = DOWN;

        while (!maze.hasWall(position, dir)) {
            moving = true;
            position.move(dir);
        }
        moving = false;

    }

    public boolean targetReached(Position playerPos, Position tarjetPos) {

        if ((playerPos.getCol() == tarjetPos.getCol()) && (playerPos.getRow() == tarjetPos.getRow())) {
            onTarget = true;
        } else {
            onTarget = false;
        }

        return onTarget;
    }


    public boolean mazeCompleted() {
        return mazeComplete;
    }

    public void movementDone(Direction dir, Position player) {      //Swipes

        preMovePos= new Position(player);
        lastPositions.push(preMovePos);
        //moverlo segun la direccion

        while (!maze.hasWall(player, dir)) {
            moving = true;
            player.move(dir);

        }

        moving = false;

        lastDirections.push(dir);
        lastDir=lastDirections.peek();


    }


    public void ClickManagement(int x, int y, float yOffset, float DRAWABLE_SCALE, Position player, Collection<Position> tarjets, Direction dir) {



        Maze tempMaze = new Maze(templates[currentLvl]);

        Position o = new Position(tempMaze.getOrigin());

        Set<Position> resetTarjets = new HashSet<>();

        for (Position p : tempMaze.getTargets()) {
            resetTarjets.add(p);
        }


        //Reset
        float rxMin = pixelWidth / 2 - DRAWABLE_SCALE / 1.65f;
        float rxMax = pixelWidth / 2 + DRAWABLE_SCALE / 1.65f;
        //Hint
        float hxMin = 3 * pixelWidth / 4 - DRAWABLE_SCALE / 1.65f;
        float hxMax = 3 * pixelWidth / 4 + DRAWABLE_SCALE / 1.65f;

        //undo
        float uxMin = pixelWidth    /  4 - DRAWABLE_SCALE / 1.65f;
        float uxMax = pixelWidth    /  4 + DRAWABLE_SCALE / 1.65f;

        //Undo
        float yMax = yOffset / 2 + DRAWABLE_SCALE / 1.55f;
        float yMin = yOffset / 2 - DRAWABLE_SCALE / 1.55f;


        float h = yOffset;


        if (x > rxMin && x < rxMax && y > yMin && y < yMax) {        //Reset

            resetMaze(player, tarjets, o, resetTarjets);
        }
        if (x > hxMin && x < hxMax && y > yMin && y < yMax) {         //Hint

            showPath();
        }
        if (x > uxMin && x < uxMax && y > yMin && y < yMax) {         //undo


            lastDir=lastDirections.peek();

            if(!lastDirections.isEmpty())
            {


                if(lastDir==UP){
                    while(maze.getOrigin().getRow()!= lastPositions.peek().getRow()){player.move(DOWN);}
                }

                if(lastDir==DOWN){
                    while(maze.getOrigin().getRow()!= lastPositions.peek().getRow()){player.move(UP);}
                }

                if(lastDir==RIGHT){
                    while(maze.getOrigin().getCol()!= lastPositions.peek().getCol()){player.move(LEFT);}
                }

                if(lastDir==LEFT){
                    while(maze.getOrigin().getCol()!= lastPositions.peek().getCol()){player.move(RIGHT);}
                }
                preMovePos= new Position(player);
                lastPositions.pop();

                lastDir=lastDirections.pop();
                lastDir=lastDirections.peek();
            }
        }
    }

    private void showPath() {
        boolean r = Hint ? false : true;
        Hint = r;
    }

    public String[] getMazeDiagram() {
        return templates[currentLvl];
    }

}
