package com.al375875.ujimaze;

import android.content.Context;
import android.graphics.Bitmap;

import com.al375875.ujimaze.model.Direction;
import com.al375875.ujimaze.model.Maze;
import com.al375875.ujimaze.model.mazeModel;

import java.util.List;

import es.uji.vj1229.framework.Graphics;
import es.uji.vj1229.framework.IGameController;
import es.uji.vj1229.framework.TouchHandler;

import static com.al375875.ujimaze.model.Direction.DOWN;
import static com.al375875.ujimaze.model.Direction.LEFT;
import static com.al375875.ujimaze.model.Direction.RIGHT;
import static com.al375875.ujimaze.model.Direction.UP;


public class Controller implements IGameController {

    private static final int LINE_COLOR = 2;
    private static final int CIRCLE_COLOR = 20;
    final float porc=0.9f;

    float lineWidth=1f;
    float radio=10f;


    Context ctx;
    int widthPixels, heightPixels;
    mazeModel model;
    Direction dir;
    Graphics graphics;
    GestureDetector gestureDetector;

    private int cellSide, xOffset, yOffset;

    public Controller(Context ctx, int widthPixels, int heightPixels) {
        this.ctx = ctx;
        this.widthPixels = widthPixels;
        this.heightPixels = heightPixels;

        graphics = new Graphics(widthPixels,heightPixels);
        gestureDetector= new GestureDetector(widthPixels);






        model= new mazeModel(ctx, widthPixels, heightPixels);
    }

    @Override
    public void onUpdate(float deltaTime, List<TouchHandler.TouchEvent> touchEvents) {
        //CODIFICAR VUESTRO JUEGO
        //LLAMANDO A METODOS DEL MODELO

        for(TouchHandler.TouchEvent event: touchEvents){
            if(event.type == TouchHandler.TouchType.TOUCH_DOWN){
                gestureDetector.onTouchDown(event.x, event.y);
            }
            else if(event.type == TouchHandler.TouchType.TOUCH_UP){
                if(gestureDetector.onTouchUp(event.x, event.y) == GestureDetector.Gesture.SWIPE){
                    model.movementDone(gestureDetector.getDir());
                }
                else if(gestureDetector.onTouchUp(event.x, event.y)== GestureDetector.Gesture.CLICK){
                    //informar al modelo de clicks en algun boton
                    //decir donde se ha hecho el click y gestionarlo en el modelo
                }
            }
        }

        /*Gesture g= gestureDetector.lastGesture();
        dir= gestureDetector.lastDirection();*/
        model.update(deltaTime);

        if(model.mazeCompleted()){
            //Pasar al siguiente
        }

    }

    @Override
    public Bitmap onDrawingRequested() {
        //LLAMANDO A LAS FUNCIONES DE DIBUJO

        //Color fondo
        graphics.clear(0xFFFFFFFF);

        Maze maze= model.getCurrentMaze();

        graphics.drawCircle(maze.getOrigin().getRow(), maze.getOrigin().getCol(),radio,CIRCLE_COLOR);

        cellSide=(int) Math.min((widthPixels* porc)/maze.getNCols(), (heightPixels* porc)/maze.getNRows());
        xOffset =    widthPixels-cellSide*maze.getNCols();
        yOffset =    heightPixels-cellSide*maze.getNCols();;

        for (int i =0; i < maze.getNRows();i++){
            float y1= yOffset*i*cellSide;
            float y2= y1+cellSide;
            for (int j =0; i < maze.getNCols(); j++){
                float x1 = xOffset * j * cellSide;
                float x2 = x1 + cellSide;

                //Dibujar lo que toque
                if(maze.hasWall(/*posiciones*/,UP)){
                    //dibujas linea
                    graphics.drawLine(x1, y1, x2, y1, lineWidth, LINE_COLOR);
                }
                if(maze.hasWall(/*posiciones*/,DOWN)){
                    //dibujas linea
                    graphics.drawLine();

                }
                if(maze.hasWall(/*posiciones*/,LEFT)){
                    //dibujas linea
                    graphics.drawLine();
                }
                if(maze.hasWall(/*posiciones*/,RIGHT)){
                    //dibujas linea
                    graphics.drawLine();

                }


            }
        }

        /*

        for (int row = 0 ; row < m.length ; row++) {
            String a= m[row];

            for (int col = 0 ; col < a.length() ; col++) {
                char  b=  a.charAt(col);

                if( b != ' ' ){
                    if(b=='O'){
                        graphics.drawCircle(row, col, 1, 25 );
                    }
                    if(b=='X'){
                        graphics.drawRect(row,col,1,1,35);
                    }
                    if(b == '|' || b=='+'){
                        graphics.drawLine( (float)row,(float) col,(float) row+1, (float)col, 1, 35);
                    }
                    if(b=='-'){
                        graphics.drawLine( (float)row,(float) col,(float) row, (float)col+1, 1, 35);
                    }

                }
            }
        }*/

        return graphics.getFrameBuffer();
    }
}
