package com.al375875.ujimaze.model;

import android.content.Context;

import java.util.Collection;

public class mazeModel {

    private float posX, posY;
    private boolean moving, mazeComplete;
    static int SPEED =8;
    String[] maz= new String[]{
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
            "+-+-+-+-+-+-+-+"};

    private Maze maze;

    public mazeModel(Context context, int width, int height) {

        maze= new Maze(maz);

        mazeComplete=false;
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

        else if (dir == Direction.RIGHT){ posX = posX + (t*SPEED) ; }

        else if (dir == Direction.LEFT){ posX = posX - (t*SPEED) ; }

        targetReached();

    }



    // Maze
    public void resetMaze(){

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

    public boolean targetReached(){

        Collection<Position> a= maze.getTargets();
//faltanm cosas
        return true;
    }


    public boolean mazeCompleted() {
        return mazeComplete;
    }

    public void movementDone(Direction dir) {
    }
}
