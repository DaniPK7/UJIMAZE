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



public class Controller implements IGameController {
    Context ctx;
    int widthPixels, heightPixels;
    mazeModel model;
    Direction dir;

    public Controller(Context ctx, int widthPixels, int heightPixels) {
        this.ctx = ctx;
        this.widthPixels = widthPixels;
        this.heightPixels = heightPixels;

        model= new mazeModel(ctx, widthPixels, heightPixels);
        onDrawingRequested();
    }

    @Override
    public void onUpdate(float deltaTime, List<TouchHandler.TouchEvent> touchEvents) {
        //CODIFICAR VUESTRO JUEGO
        //LLAMANDO A METODOS DEL MODELO
        dir=Direction.DOWN;
        model.update(deltaTime, dir);

    }

    @Override
    public Bitmap onDrawingRequested() {
        //LLAMANDO A LAS FUNCIONES DE DIBUJO
        Graphics graphics = new Graphics(widthPixels,heightPixels);

        String[] m =model.getCurrentMaze();
        Maze m2= new Maze(m);

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
        }

        return null;
    }
}
