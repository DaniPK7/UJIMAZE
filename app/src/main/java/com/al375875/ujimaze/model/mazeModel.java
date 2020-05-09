package com.al375875.ujimaze.model;

import android.content.Context;
import android.util.Log;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class mazeModel {


    private final int pixelWidth , heightPixels;
    private float posX, posY;
    private Position posOrigin;
    private Position postMovePosition;
    private Set<Position> tarjetsOrigin;

    public int currentLvl=0;
    public boolean Hint;

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

    private static final String[][] templates ={maz,maz1,maz2} ;

    public static final Maze mazes[];
    static {
        mazes = new Maze[templates.length];
        int i = 0;
        for (String[] template: templates) {
            mazes[i++] = new Maze(template);
        }
    }


    public mazeModel(Context context, int width, int height) {
        this.pixelWidth=width;
        this.heightPixels=height;

        /*mazes.add(maz);
        mazes.add(maz2);*/

        //maze= new Maze(templates.get(0));
        maze = mazes[currentLvl];
        posOrigin= new Position( maze.getOrigin());
        posX= posOrigin.getRow();
        posY= posOrigin.getCol();

        tarjetsOrigin= new HashSet<>();

        for(Position p  :   maze.getTargets()){
            //Log.d("tarjetsO","P: "+ p.toString());
            tarjetsOrigin.add(p);
        }



        //Log.d("model", "Filas: "+maze.getNRows());
        //Log.d("model", "Columnas: "+maze.getNCols());

        mazeComplete=false;
        onTarget=false;
        Hint=false;
    }

    public void update(float deltaTime, Direction dir){


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

    public Position calculateFinalPos(Position pos, Direction dir)
    {
        Position temp= pos;
        Position finalPos;

        while(!maze.hasWall(temp,dir)){
            moving =true;
            temp.move(dir);

        }
        finalPos= temp;
        return finalPos;
    }


    public void setCoords(float t, Direction dir){

        if      (dir == Direction.UP)   { posY = posY - (t * SPEED); }
        else if (dir == Direction.DOWN) { posY = posY + (t * SPEED); }
        else if (dir == Direction.RIGHT){ posX = posX + (t * SPEED); }
        else if (dir == Direction.LEFT) { posX = posX - (t * SPEED); }

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
        }*/


    }
        //targetReached();



    // Maze
    public void resetMaze(Position playerP, Collection<Position> t, Position origin, Collection<Position> resetTarjets){
        //Log.d("reset","Or: "+ posOrigin + " Tarjets: "+tarjetsOrigin.size());
        //Position or= maze.getOrigin();
        //posOrigin= new Position( maze.getOrigin());
        playerP.set(origin);
        //t=resetTarjets;



        for(Position p  :  resetTarjets){
            t.add(p);
        }

    }

    public Maze getCurrentMaze(){
            return  maze;
    }
    public String[] getCurrentSolution()
    {
        return solMaz;
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


    public void ClickManagement(int x, int y, float yOffset, float DRAWABLE_SCALE, Position player, Collection<Position> tarjets)
    {
        Maze tempMaze= new Maze(templates[currentLvl]);

        Position o= new Position(tempMaze.getOrigin());

        Set<Position>resetTarjets= new HashSet<>();

        for(Position p  :   tempMaze.getTargets()){
            //Log.d("tarjetsO","P: "+ p.toString());
            resetTarjets.add(p);
        }


        Log.d("click", "Click: "+ x +", "+y);
        //Reset
        float rxMin= pixelWidth/2-DRAWABLE_SCALE/1.65f;
        float rxMax= pixelWidth/2+DRAWABLE_SCALE/1.65f;
        //Hint
        float hxMin= 3*pixelWidth/4-DRAWABLE_SCALE/1.65f;
        float hxMax= 3*pixelWidth/4+DRAWABLE_SCALE/1.65f;

        float yMax=  yOffset/2 + DRAWABLE_SCALE/1.55f;
        float yMin=  yOffset/2 - DRAWABLE_SCALE/1.55f;




        float h= yOffset;
        Log.d("click","hXMin "+ hxMin);

        if(x>rxMin && x<rxMax && y> yMin && y < yMax){        //Reset

            resetMaze(player, tarjets, o, resetTarjets);
        }
        if(x>hxMin && x<hxMax && y > yMin && y < yMax){         //Hint

            showPath();
        }
    }

    private void showPath()
    {
        boolean r= Hint? false : true;
        Hint=r;

        Log.d("click", "Pista");
    }
}
