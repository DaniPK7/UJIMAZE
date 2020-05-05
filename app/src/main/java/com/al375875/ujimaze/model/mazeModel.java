package com.al375875.ujimaze.model;

import android.content.Context;
import android.util.Log;

import com.al375875.ujimaze.Controller;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.al375875.ujimaze.model.Direction.RIGHT;

public class mazeModel {

    Controller c;

    private float posX, posY;
    private Position posOrigin;
    private Set<Position> tarjetsOrigin;


    private boolean moving, mazeComplete, onTarget;
    static int SPEED =8;


    String[] maz= new String[]
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
    String[] maz2= new String[]
            {
                    "+-+-+-+-+-+-+-+",
                    "| |     |     |",      //
                    "+ + +-+ +   +-+",
                    "|      X      |",      //
                    "+     +     + +",
                    "|     |O    | |",      //
                    "+ +-+ + + + + +",
                    "|       | |   |",      //
                    "+      -+ +  -+",
                    "|             |",      //
                    "+-+ + + +-+   +",
                    "|   |X|       |",      //
                    "+   +-+ +   + +",
                    "|       |   | |",      //
                    "+-+-+-+-+-+-+-+"


            };



    private Maze maze;
    private List<String[]> mazes= Arrays.asList(maz,maz2);

    public mazeModel(Context context, int width, int height) {

        /*mazes.add(maz);
        mazes.add(maz2);*/

        maze= new Maze(mazes.get(1));
        posOrigin= new Position( maze.getOrigin());

        tarjetsOrigin= new HashSet<>();

        for(Position p  :   maze.getTargets()){
            Log.d("tarjetsO","P: "+ p.toString());
            tarjetsOrigin.add(p);
        }



        Log.d("model", "Filas: "+maze.getNRows());
        Log.d("model", "Columnas: "+maze.getNCols());

        mazeComplete=false;
        onTarget=false;
    }

    public void update(float deltaTime){
        if(!isMoving()){
            return;
        }
        else{
            //setCoords(deltaTime);
        }
    }


    public void setCoords(float t, Direction dir){

        if (dir == Direction.UP){ posY = posY + (t*SPEED) ; }

        else if (dir == Direction.DOWN) { posY = posY - (t*SPEED) ; }

        else if (dir == RIGHT){ posX = posX + (t*SPEED) ; }

        else if (dir == Direction.LEFT){ posX = posX - (t*SPEED) ; }

        //targetReached();

    }



    // Maze
    public void resetMaze(Position playerP, Collection<Position> t){
        Log.d("reset","Or: "+ posOrigin + " Tarjets: "+tarjetsOrigin.size());
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
        if(playerPos.getCol() == tarjetPos.getCol() && playerPos.getRow() == tarjetPos.getRow() ) {onTarget=true;}
        else{onTarget=false;}

        return onTarget;
    }


    public boolean mazeCompleted() {
        return mazeComplete;
    }

    public void movementDone(Direction dir, Position player) {
        //moverlo segun la direccion
        Log.d("swipe", "funco");

        while(!maze.hasWall(player,dir)){
            moving =true;
            player.move(dir);
        }
        //targetReached(player);
        moving =false;


    }
}
