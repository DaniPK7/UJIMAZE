package com.al375875.ujimaze.model;

import android.content.Context;
import android.util.Log;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class mazeModel {



    private float posX, posY;
    private Position posOrigin;
    private Set<Position> tarjetsOrigin;

    public int currentLvl=0;

    private boolean moving, mazeComplete, onTarget;
    static float SPEED =2;


    static String[] maz= new String[]
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
    static String[] solMaz= new String[]
            {
                    "+-+-+-+-+-+-+-+",
                    "| |     |     |",      //⟵
                    "+ + +-+ +   +-+",
                    "|        O    |",      //⟵
                    "+ + + + + + + +",
                    "|     |     | |",      //⟵
                    "+ +-+ + + + + +",
                    "|       | |   |",      //⟵
                    "+      -+ +  -+",
                    "|             |",      //⟵
                    "+-+ + + +-+   +",
                    "|   |X|       |",      //⟵
                    "+   +-+ +   + +",
                    "|       |   | |",      //⟵
                    "+-+-+-+-+-+-+-+"
                    //        ^ ^ ^ ^ ^ ^ ^
                    //        | | | | | | |

            };
    static String[] maz1= new String[]
            {
                    "+-+-+-+-+-+-+-+",
                    "|             |",      //⟵
                    "+ + + + + + + +",
                    "|             |",      //⟵
                    "+-+ + + + + + +",
                    "|O  |         |",      //⟵
                    "+-+ + +-+ +-+ +",
                    "|     |      X|",      //⟵
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

    static String[] maz2= new String[]
            {
                    "+-+-+-+-+-+-+-+",
                    "| |X|    |    |",      //⟵
                    "+ + +-+ +   +-+",
                    "| |           |",      //⟵
                    "+     +     + +",
                    "|     |O    | |",      //⟵
                    "+ +-+ + + + + +",
                    "|     | | |   |",      //⟵
                    "+      -+ +  -+",
                    "|             |",      //⟵
                    "+-+ + + +-+   +",
                    "|   |X|       |",      //⟵
                    "+   +-+ +   + +",
                    "|       |   | |",      //⟵
                    "+-+-+-+-+-+-+-+"
                    //        ^ ^ ^ ^ ^ ^ ^
                    //        | | | | | | |
            };



    private Maze maze;
    //private String[][] templates =Arrays.asList(maz,maz2,maz3);

    private static final String[][] templates ={solMaz,maz,maz1,maz2} ;

    public static final Maze mazes[];
    static {
        mazes = new Maze[templates.length];
        int i = 0;
        for (String[] template: templates) {
            mazes[i++] = new Maze(template);
        }
    }


    public mazeModel(Context context, int width, int height) {

        /*mazes.add(maz);
        mazes.add(maz2);*/

        //maze= new Maze(templates.get(0));
        maze = mazes[currentLvl];
        posOrigin= new Position( maze.getOrigin());

        tarjetsOrigin= new HashSet<>();

        for(Position p  :   maze.getTargets()){
            //Log.d("tarjetsO","P: "+ p.toString());
            tarjetsOrigin.add(p);
        }



        //Log.d("model", "Filas: "+maze.getNRows());
        //Log.d("model", "Columnas: "+maze.getNCols());

        mazeComplete=false;
        onTarget=false;
    }

    public void update(float deltaTime, Direction dir){


        if(!isMoving()){
            return;
        }
        else{
            //setCoords(deltaTime, dir);
        }
    }


    public void setCoords(float t, Direction dir){


        Log.d("setCoords", "Col: "+ maze.getOrigin().getCol());
        //maze.getOrigin().setCol(maze.getOrigin().getCol()-1);
        Log.d("setCoords", "Col post: "+ maze.getOrigin().getCol());

        posX=maze.getOrigin().getCol();
        posY=maze.getOrigin().getRow();

        while(isMoving()){
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
        //targetReached();



    // Maze
    public void resetMaze(Position playerP, Collection<Position> t){
        //Log.d("reset","Or: "+ posOrigin + " Tarjets: "+tarjetsOrigin.size());
        //Position or= maze.getOrigin();
        playerP.set(posOrigin);

        for(Position p  :  tarjetsOrigin){
            t.add(p);
        }

    }

    public Maze getCurrentMaze(){
            return  maze;
    }

    public void nextMaze(){

        currentLvl+=1;
        maze = mazes[currentLvl];

    }
    public void returnTargetPos(){

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

    public void startMoving(){

        // character is moving if()
        int intX= (int) Math.floor(posX);
        int intY= (int) Math.floor(posY);
        Position position= new Position(intX, intY);

        Direction dir= Direction.DOWN;

        while(!maze.hasWall(position, dir))
        {
            moving =true;
            position.move(dir);
        }
        moving=false;

    }

    public boolean targetReached(Position playerPos, Position tarjetPos){

        /*Collection<Position> tarjetsPos= maze.getTargets();

        for(Position t: tarjetsPos){
            if(player == t){
                maze.
            }

        }*/
//faltanm cosas

        //Log.d("tarRes","PC: "+playerPos.getCol()+" PR: "+ playerPos.getRow()+" JC: "+tarjetPos.getCol()+" JR: "+tarjetPos.getRow());
        if((playerPos.getCol() == tarjetPos.getCol()) && (playerPos.getRow() == tarjetPos.getRow()) ) {onTarget=true;}
        else{onTarget=false;}

        return onTarget;
    }


    public boolean mazeCompleted() {
        return mazeComplete;
    }

    public void movementDone(Direction dir, Position player) {

        //moverlo segun la direccion
        //Log.d("swipe", "funco");

        while(!maze.hasWall(player,dir)){
            moving =true;
            player.move(dir);

        }
        //targetReached(player);
        moving =false;


    }


}
