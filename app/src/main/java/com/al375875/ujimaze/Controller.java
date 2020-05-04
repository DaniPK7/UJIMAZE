package com.al375875.ujimaze;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.al375875.ujimaze.model.Direction;
import com.al375875.ujimaze.model.Maze;
import com.al375875.ujimaze.model.Position;
import com.al375875.ujimaze.model.mazeModel;

import java.util.List;

import es.uji.vj1229.framework.Graphics;
import es.uji.vj1229.framework.IGameController;
import es.uji.vj1229.framework.TouchHandler;


public class Controller implements IGameController {


    //int color = (1 & 0xff) << 24 | (255 & 0xff) << 16 | (102 & 0xff) << 8 | (153 & 0xff);
    private static final int LINE_COLOR = 0xff000000;// 0xff000000;//0xff6666
    private static final int CIRCLE_COLOR =0xff000000;



    final float porc=0.9f;




    Context ctx;
    int widthPixels, heightPixels;
    mazeModel model;
    Direction dir;
    Graphics graphics;
    GestureDetector gestureDetector;

    float lineWidth;//= widthPixels * 0.2f;
    float radio;

    //private int cellSide, xOffset, yOffset;
    private float cellSide, xOffset, yOffset;

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

        Log.d("porfi", "AIUDAME");

        for(TouchHandler.TouchEvent event: touchEvents){
            if(event.type == TouchHandler.TouchType.TOUCH_DOWN){
                gestureDetector.onTouchDown(event.x, event.y);
            }
            else if(event.type == TouchHandler.TouchType.TOUCH_UP){
                if(gestureDetector.onTouchUp(event.x, event.y) == GestureDetector.Gesture.SWIPE){
                    //model.movementDone(gestureDetector.getDir());
                }
                else if(gestureDetector.onTouchUp(event.x, event.y)== GestureDetector.Gesture.CLICK){
                    //informar al modelo de clicks en algun boton
                    //decir donde se ha hecho el click y gestionarlo en el modelo
                }
            }
        }

        model.update(deltaTime);

        if(model.mazeCompleted()){
            //Pasar al siguiente
        }

    }

    @Override
    public Bitmap onDrawingRequested() {
        //LLAMANDO A LAS FUNCIONES DE DIBUJO
        Log.d("porfi", "Pre- pintaó");

        //Color fondo
        //int[] r={ 0x00ffbf, 0xff00ff, 0x80ff00};
        int clearColor=0xFFFFFFFF;

        graphics.clear(clearColor);//0xff6699

        //graphics.drawLine(10, 5, 10, 5, lineWidth, LINE_COLOR);


        Maze maze= model.getCurrentMaze();

        radio=lineWidth*2f;
        Log.d("porfi","caca: "+maze.getOrigin().getRow());

        graphics.drawCircle(maze.getOrigin().getRow(), maze.getOrigin().getCol(), radio, CIRCLE_COLOR);

        cellSide= Math.min((widthPixels* porc)/maze.getNCols(), (heightPixels* porc)/maze.getNRows());
        //cellSide = widthPixels*0.95f/maze.getNCols();


        xOffset =   (widthPixels-(cellSide*maze.getNCols()))/2; //widthPixels    -   cellSide    *   maze.getNCols();
        yOffset =   (heightPixels-(cellSide*maze.getNRows()))/2;//heightPixels   -   cellSide    *   maze.getNRows();

        lineWidth= cellSide * 0.1f;


       /* Log.d("porfi", "Casi casi pinto");
        Log.d("porfi", "Filas: "+maze.getNRows());
        Log.d("porfi", "Columnas: "+maze.getNCols());*/



        for (int i =0; i < maze.getNRows(); i++){
            //Log.d("porfi", "Recorriendo filas");

            float y1    =   yOffset   +   i   *   cellSide;
            float y2    =   y1  +   cellSide;

            for (int j =0; j < maze.getNCols(); j++){

               // Log.d("porfi", "Recorriendo columnas");

                float x1    =   xOffset +   j   *   cellSide;
                float x2    =   x1  +   cellSide;

                Position pos= new Position(j,i);
                /*if(i==5 && j==2){
                    Log.d("debug", "Fila: "+i+" Columna: "+ j + " UP "+ "Result:"+ maze.hasWall(pos,UP));
                    Log.d("debug", "Fila: "+i+" Columna: "+ j + " DOWN "+ "Result:"+ maze.hasWall(pos,DOWN));
                    Log.d("debug", "Fila: "+i+" Columna: "+ j + " LEFT "+ "Result:"+ maze.hasWall(pos,LEFT));
                    Log.d("debug", "Fila: "+i+" Columna: "+ j + " RIGHT "+ "Result:"+ maze.hasWall(pos,RIGHT));
                }*/


                //Dibujar lo que toque
                /*if(maze.hasWall(pos,UP)){    //Tienes muro encima (horizontal)
                    //dibujas linea

                    graphics.drawLine(x1, y1, x2, y1, lineWidth, LINE_COLOR);
                }
                else if(maze.hasWall(pos,DOWN)){  //Tienes muro debajo (horizontal)
                    //dibujas linea
                    graphics.drawLine(x1, y2, x2, y2, lineWidth, LINE_COLOR);

                }
                else if(maze.hasWall(pos,LEFT)){  //Tienes muro a la izq (vertical)
                    //dibujas linea
                    graphics.drawLine(x1, y1, x1, y2, lineWidth, LINE_COLOR);
                }
                else if(maze.hasWall(pos,RIGHT)){ //Tienes muro a la derecha (vertical)
                    //dibujas linea
                    graphics.drawLine(x2, y1, x2, y2, lineWidth, LINE_COLOR);

                }*/

                if (maze.hasWall(i, j, Direction.UP))
                    graphics.drawLine(x1,y1,x2,y1,lineWidth,LINE_COLOR);
                if (maze.hasWall(i,j, Direction.DOWN))
                    graphics.drawLine(x1,y2,x2,y2,lineWidth,LINE_COLOR);
                if (maze.hasWall(i,j,Direction.LEFT))
                    graphics.drawLine(x1,y1,x1,y2,lineWidth,LINE_COLOR);
                if (maze.hasWall(i,j,Direction.RIGHT))
                    graphics.drawLine(x2,y1,x2,y2,lineWidth,LINE_COLOR);
            }
        }
        //Log.d("porfi", "Pintaó");

        return graphics.getFrameBuffer();
    }
}
