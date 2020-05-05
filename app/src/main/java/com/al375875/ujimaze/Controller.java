package com.al375875.ujimaze;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.al375875.ujimaze.model.Direction;
import com.al375875.ujimaze.model.Maze;
import com.al375875.ujimaze.model.Position;
import com.al375875.ujimaze.model.mazeModel;

import java.util.Collection;
import java.util.List;

import es.uji.vj1229.framework.Graphics;
import es.uji.vj1229.framework.IGameController;
import es.uji.vj1229.framework.TouchHandler;

import static com.al375875.ujimaze.model.Direction.DOWN;
import static com.al375875.ujimaze.model.Direction.LEFT;
import static com.al375875.ujimaze.model.Direction.RIGHT;
import static com.al375875.ujimaze.model.Direction.UP;


public class Controller implements IGameController {


    //int color = (1 & 0xff) << 24 | (255 & 0xff) << 16 | (102 & 0xff) << 8 | (153 & 0xff);
    private static final int LINE_COLOR = 0xff000000;// 0xff000000;//0xff6666
    private static final int CIRCLE_COLOR =0xffff6666;
    private static final int RECTANGLE_COLOR =0xffffcc00;



    final float porc=0.9f;




    Context ctx;
    int widthPixels, heightPixels;
    mazeModel model;
    Direction dir;
    Graphics graphics;
    GestureDetector gestureDetector;
    Maze maze, mazeSolution;
    public Position player;
    Collection<Position> tarjets;

    float LINE_WIDTH,CIRCLE_RADIUS, RECTANGLE_SIDE ;//= widthPixels * 0.2f;
    float nX,nY;


    //private int cellSide, xOffset, yOffset;
    private float cellSide, xOffset, yOffset;

    public Controller(Context ctx, int widthPixels, int heightPixels) {
        this.ctx = ctx;
        this.widthPixels = widthPixels;
        this.heightPixels = heightPixels;

        graphics = new Graphics(widthPixels,heightPixels);
        gestureDetector= new GestureDetector(widthPixels);

        model= new mazeModel(ctx, widthPixels, heightPixels);



        /*nX=player.getCol();      //Valor de nX para Columna de  la X origen jugador     (0 a nCol)
        nY=player.getRow();      //Valor de nY para Fila de la Y origen jugador         (0 a nFilas)*/

    }

    @Override
    public void onUpdate(float deltaTime, List<TouchHandler.TouchEvent> touchEvents) {
        //CODIFICAR VUESTRO JUEGO
        //LLAMANDO A METODOS DEL MODELO

        //Log.d("porfi", "AIUDAME");

        for(TouchHandler.TouchEvent event: touchEvents){
            if(event.type == TouchHandler.TouchType.TOUCH_DOWN){
                gestureDetector.onTouchDown(event.x, event.y);
            }
            else if(event.type == TouchHandler.TouchType.TOUCH_UP){
                if(gestureDetector.onTouchUp(event.x, event.y) == GestureDetector.Gesture.SWIPE){
                    //model.movementDone(gestureDetector.getDir(), player);
                    //model.setCoords(deltaTime, gestureDetector.getDir());

                    model.movementDone(gestureDetector.getDir(), player);
                    //model.update(deltaTime, gestureDetector.getDir());
                }
                else if(gestureDetector.onTouchUp(event.x, event.y)== GestureDetector.Gesture.CLICK){

                    Log.d("click", "Click: "+ event.x +", "+ event.y);
                    if(event.x>420 && event.x<620 && event.y<407){
                        Log.d("click", "Click: "+ event.x +", "+ event.y);
                        model.resetMaze(player, tarjets);
                    }
                    //nX+=1;
                    //informar al modelo de clicks en algun boton
                    //decir donde se ha hecho el click y gestionarlo en el modelo
                }
            }
        }

        //model.update(deltaTime);

        if(model.mazeCompleted()){
            //Pasar al siguiente
        }

    }

    @Override
    public Bitmap onDrawingRequested() {
        //LLAMANDO A LAS FUNCIONES DE DIBUJO

        maze= model.getCurrentMaze();

        player= maze.getOrigin();
        tarjets= maze.getTargets();
        //Color fondo

        int clearColor=0xFFFFFFFF;

        graphics.clear(clearColor);



        //Maze maze= model.getCurrentMaze();
        //tarjets= maze.getTargets();


        cellSide= Math.min((widthPixels* porc)/maze.getNCols(), (heightPixels* porc)/maze.getNRows());

        //boton reset
        graphics.drawRect(420 , 205, cellSide*1.5f, cellSide*1.5f, 0xff4499b8);


        //cellSide = widthPixels*0.95f/maze.getNCols();


        xOffset =   (widthPixels-(cellSide*maze.getNCols()))/2; //widthPixels    -   cellSide    *   maze.getNCols();
        yOffset =   (heightPixels-(cellSide*maze.getNRows()))/2;//heightPixels   -   cellSide    *   maze.getNRows();

        LINE_WIDTH= cellSide * 0.1f;

        //Player
        CIRCLE_RADIUS =cellSide/3.5f;
        float xOrigin   =   xOffset + CIRCLE_RADIUS *   2;//(widthPixels/maze.getNCols())-xOffset/2;
        float yOrigin   =   yOffset + CIRCLE_RADIUS *   2;//(heightPixels/maze.getNRows()+yOffset/1.65f);

        nX=player.getCol();      //Valor de nX para Columna de  la X origen jugador     (0 a nCol)
        nY=player.getRow();      //Valor de nY para Fila de la Y origen jugador         (0 a nFilas)*/

        float pCol= nX* cellSide;
        float pRow= nY* cellSide;

        graphics.drawCircle(xOrigin + pCol , yOrigin + pRow, CIRCLE_RADIUS, CIRCLE_COLOR);

        //Tarjet
        RECTANGLE_SIDE=cellSide/1.5f;

        float xTarjet   =   xOffset +  RECTANGLE_SIDE/4;// + RECTANGLE_SIDE *  2;
        float yTarjet   =   yOffset +  RECTANGLE_SIDE/4;


        for (Position t : tarjets) {

            float tX = t.getCol();
            float tY = t.getRow();

            float tCol = tX * cellSide;
            float tRow = tY * cellSide;


           // Log.d("tarjeta", "Pre criba: "+tarjets.size());
            if (model.targetReached(player, t)) {
                    tarjets.remove(t);
                //Log.d("tarjeta", "post criba: "+tarjets.size());
            }
            if(tarjets.size()==0){
                model.nextMaze();
            }


            else{graphics.drawRect(xTarjet + tCol, yTarjet + tRow, RECTANGLE_SIDE, RECTANGLE_SIDE, RECTANGLE_COLOR);}
        }


        //Muros
        for (int i =0; i < maze.getNRows(); i++){
            //Log.d("porfi", "Recorriendo filas");

            float y1    =   yOffset   +   i   *   cellSide;
            float y2    =   y1  +   cellSide;

            for (int j =0; j < maze.getNCols(); j++){

                float x1    =   xOffset +   j   *   cellSide;
                float x2    =   x1  +   cellSide;

                Position pos= new Position(i,j);


                //Dibujar lo que toque

                if (maze.hasWall(pos, UP)){                                 //Tienes muro encima (horizontal)
                    graphics.drawLine(x1,y1,x2,y1,LINE_WIDTH,LINE_COLOR);
                }

                if (maze.hasWall(pos, DOWN)){                               //Tienes muro debajo (horizontal)
                    graphics.drawLine(x1,y2,x2,y2,LINE_WIDTH,LINE_COLOR);
                }

                if (maze.hasWall(pos,LEFT)){                                //Tienes muro a la izq (vertical)
                    graphics.drawLine(x1,y1,x1,y2,LINE_WIDTH,LINE_COLOR);
                }

                if (maze.hasWall(pos,RIGHT)){                               //Tienes muro a la derecha (vertical)
                    graphics.drawLine(x2,y1,x2,y2,LINE_WIDTH,LINE_COLOR);
                }
            }
        }
        drawSolution();

        return graphics.getFrameBuffer();
    }

    void drawSolution(){
        int n=5;    //fila  realdeseada
        int fila= (n*2)+1;
        float x1    =   xOffset +   4   *   cellSide;
        float y1    =   yOffset +     fila *  (cellSide/2);

        graphics.drawLine(x1-cellSide/2,y1,x1+cellSide/2,y1,LINE_WIDTH,0xffe3d084);


        /*for (int i =0; i < mazeSolution.getNRows(); i++){            //Log.d("porfi", "Recorriendo filas");

            //float y1    =   yOffset   +   i   *   cellSide;

            for (int j =0; j < mazeSolution.getNCols(); j++){

                //float x1    =   xOffset +   j   *   cellSide;
                Position pos= new Position(i,j);
                mazeSolution

            }*/


    }


}
