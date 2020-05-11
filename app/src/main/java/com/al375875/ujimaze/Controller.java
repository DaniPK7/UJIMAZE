package com.al375875.ujimaze;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

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



    private static final int LINE_COLOR = 0xff000000;
    private static final int CIRCLE_COLOR =0xffff6666;
    private static final int RECTANGLE_COLOR =0xffffcc00;

    final float porc=0.9f;

    Context ctx;
    int widthPixels, heightPixels;
    mazeModel model;
    Graphics graphics;
    GestureDetector gestureDetector;
    Maze maze;
    String[] mazeSolution;
    public Position player;
    Collection<Position> tarjets;



    float LINE_WIDTH,CIRCLE_RADIUS, RECTANGLE_SIDE, DRAWABLE_SCALE  ;


    float nX,nY;



    private float cellSide, xOffset, yOffset;

    public Controller(Context ctx, int widthPixels, int heightPixels) {


        this.ctx = ctx;
        this.widthPixels = widthPixels;
        this.heightPixels = heightPixels;

        graphics = new Graphics(widthPixels,heightPixels);
        gestureDetector= new GestureDetector(widthPixels);

        model= new mazeModel(ctx, widthPixels, heightPixels);

        mazeSolution= model.getCurrentSolution();

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

                    model.movementDone(gestureDetector.getDir(), player);

                }
                else if(gestureDetector.onTouchUp(event.x, event.y)== GestureDetector.Gesture.CLICK){

                    model.ClickManagement(event.x, event.y, yOffset, DRAWABLE_SCALE, player, tarjets, gestureDetector.getDir());

                }
            }
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

        cellSide= Math.min((widthPixels* porc)/maze.getNCols(), (heightPixels* porc)/maze.getNRows());

        DRAWABLE_SCALE =widthPixels/8;



        //Offsets
        xOffset =   (widthPixels-(cellSide*maze.getNCols()))/2;
        yOffset =   (heightPixels-(cellSide*maze.getNRows()))/2;

        LINE_WIDTH= cellSide * 0.1f;
        Drawable frame = ctx.getDrawable(R.drawable.frame_and_bg);


        if(!model.gameCompelte)
        {



            graphics.drawDrawable(frame , 0,0 ,widthPixels, /*yOffset-LINE_WIDTH/2*/ heightPixels);

            //Maze white BG
            graphics.drawRect(xOffset, yOffset, widthPixels-(xOffset*2),heightPixels-(yOffset*2) , 0xfffffbe6);//e4f1f7);//f0ffe0


            //boton reset
            Drawable resetButton = ctx.getDrawable(R.drawable.reset);
            graphics.drawDrawable(resetButton , widthPixels/2-DRAWABLE_SCALE/2,yOffset/2 - DRAWABLE_SCALE/2,DRAWABLE_SCALE,DRAWABLE_SCALE);

            //boton help
            Drawable helpButton = ctx.getDrawable(R.drawable.help);
            graphics.drawDrawable(helpButton , (3*widthPixels/4)-DRAWABLE_SCALE/2,yOffset/2 - DRAWABLE_SCALE/2,DRAWABLE_SCALE,DRAWABLE_SCALE);

            //boton undo
            Drawable undoButton = ctx.getDrawable(R.drawable.undo);
            graphics.drawDrawable(undoButton , (widthPixels/4)-DRAWABLE_SCALE/2,yOffset/2 - DRAWABLE_SCALE/2,DRAWABLE_SCALE,DRAWABLE_SCALE);

            if(model.Hint){drawSolution();}

            //Player
            CIRCLE_RADIUS =cellSide/3.5f;
            float playerSCALEX= cellSide/1.6f;
            float playerSCALEY= cellSide/1.5f;
            float xOrigin   =   xOffset + cellSide/2;
            float yOrigin   =   yOffset + cellSide/2;

            nX= player.getCol();      //Valor de nX para Columna de  la X origen jugador     (0 a nCol)
            nY= player.getRow();      //Valor de nY para Fila de la Y origen jugador         (0 a nFilas)*/

            float pCol= nX* cellSide;
            float pRow= nY* cellSide;

            Drawable playerPNG = ctx.getDrawable(R.drawable.player);
            graphics.drawDrawable(playerPNG , (xOrigin + pCol)-playerSCALEX/2,(yOrigin + pRow)-playerSCALEY/2, playerSCALEX, playerSCALEY);

            //graphics.drawCircle(xOrigin + pCol , yOrigin + pRow, CIRCLE_RADIUS, CIRCLE_COLOR);

            //Tarjet
            RECTANGLE_SIDE=cellSide/1.5f;

            float xTarjet   =   xOffset +  RECTANGLE_SIDE/4;
            float yTarjet   =   yOffset +  RECTANGLE_SIDE/4;


            for (Position t : tarjets) {

                float tX = t.getCol();
                float tY = t.getRow();

                float tCol = tX * cellSide;
                float tRow = tY * cellSide;

                if (model.targetReached(player, t)) {
                    tarjets.remove(t);

                }
                if(tarjets.size()==0){
                    model.nextMaze();
                }

                else{

                    Drawable tarjetPNG = ctx.getDrawable(R.drawable.tarjet);
                    graphics.drawDrawable(tarjetPNG ,(xTarjet + tCol), (yTarjet + tRow), RECTANGLE_SIDE, RECTANGLE_SIDE);
                    //graphics.drawRect(xTarjet + tCol, yTarjet + tRow, RECTANGLE_SIDE, RECTANGLE_SIDE, RECTANGLE_COLOR);
                }
            }
            //Muros
            for (int i =0; i < maze.getNRows(); i++){


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


        }
        else{       //Todos los laberintos completados
            Drawable victory = ctx.getDrawable(R.drawable.victory);
            graphics.drawDrawable(frame , 0,0 ,widthPixels, /*yOffset-LINE_WIDTH/2*/ heightPixels);

            graphics.drawDrawable(victory , widthPixels/2-DRAWABLE_SCALE*2.25f,heightPixels/2-DRAWABLE_SCALE*2 ,DRAWABLE_SCALE*4.5f,DRAWABLE_SCALE*2);
        }






        return graphics.getFrameBuffer();
    }

    void drawSolution(){
        mazeSolution= model.getCurrentSolution();
        int COLOR_HELP=0xffcc4637;


        for (int i =0; i < mazeSolution.length; i++){
            float y1    =   yOffset +   i   *   cellSide;

            for (int j =0; j < mazeSolution[i].length(); j++){

                int fila= j;
                float x1    =   xOffset +     fila *  (cellSide/2);

                if(mazeSolution[i].charAt(j)=='r')
                {
                    graphics.drawLine(x1,y1-cellSide/2,x1+cellSide,y1-cellSide/2,LINE_WIDTH,COLOR_HELP);
                }

                if(mazeSolution[i].charAt(j)=='l')
                {
                    graphics.drawLine(x1,y1-cellSide/2,x1-cellSide,y1-cellSide/2,LINE_WIDTH,COLOR_HELP);
                }

                if(mazeSolution[i].charAt(j)=='d')
                {
                    graphics.drawLine(x1,y1-cellSide/2, x1, y1+cellSide/2, LINE_WIDTH,COLOR_HELP);
                }

                if(mazeSolution[i].charAt(j)=='u')
                {
                    graphics.drawLine(x1,y1-cellSide/2, x1, y1-cellSide*1.5f, LINE_WIDTH,COLOR_HELP);
                }
                if(mazeSolution[i].charAt(j)=='c')
                {
                    graphics.drawLine(x1,y1-cellSide/2,x1+cellSide,y1-cellSide/2,LINE_WIDTH,COLOR_HELP);
                    graphics.drawLine(x1,y1-cellSide/2,x1-cellSide,y1-cellSide/2,LINE_WIDTH,COLOR_HELP);
                    graphics.drawLine(x1,y1-cellSide/2, x1, y1+cellSide/2, LINE_WIDTH,COLOR_HELP);
                    graphics.drawLine(x1,y1-cellSide/2, x1, y1-cellSide*1.5f, LINE_WIDTH,COLOR_HELP);

                }
                if(mazeSolution[i].charAt(j)=='t')
                {
                    graphics.drawLine(x1,y1-cellSide/2,x1+cellSide,y1-cellSide/2,LINE_WIDTH,COLOR_HELP);
                    graphics.drawLine(x1,y1-cellSide/2,x1-cellSide,y1-cellSide/2,LINE_WIDTH,COLOR_HELP);
                    graphics.drawLine(x1,y1-cellSide/2, x1, y1-cellSide*1.5f, LINE_WIDTH,COLOR_HELP);

                }

            }
        }
    }
}
