package com.al375875.ujimaze;

import com.al375875.ujimaze.model.Direction;

enum Gesture{
    CLICK,
    NONE,
    SWIPE;
}

public class GestureDetector {

    int touchX0, touchY0;
    int touchX1, touchY1;

    //final int CLICK_THRESHOLD = 10;
    final int SWIPE_THRESHOLD=100;
    final int SWIPE_MARGIN=80;
    final int CLICK_MARGIN=10;


    String dir="";


    public void onTouchDown(int x, int y){
        touchX0 = x;
        touchY0 = y;
    }

    public void onTouchUp(int x, int y){
        touchX1 = x;
        touchY1 = y;
    }

    public  Gesture lastGesture(){

        int difX=touchX0-touchX1;
        int difY=touchY0-touchY1;

        int difTotal= Math.abs(difX)-Math.abs(difY);

        if(difTotal > 0){                   //Si es positivo, nos movemos en horizontal

            if(Math.abs(difX) > SWIPE_THRESHOLD   && Math.abs(difY) < SWIPE_MARGIN){      //Swipe horizontal
                if (difX<0){                       //Swipe hacia derecha
                    //Direction.RIGHT;
                    dir="right";
                }
                else if (difX>0){                  //Swipe hacia izq
                    //Direction.LEFT;
                    dir="left";
                }
                return Gesture.SWIPE;

            }

            else if(Math.abs(difY) < CLICK_MARGIN   && Math.abs(difX) < CLICK_MARGIN){  //Click
                return Gesture.CLICK;
            }

            else{
                return  Gesture.NONE;
            }


        }
        else {                              //Si es negativo, nos movemos en vertical

            if(Math.abs(difY) > SWIPE_THRESHOLD   && Math.abs(difX) < SWIPE_MARGIN){      //Swipe vertical

                if (difY>0){                       //Swipe hacia abajo
                    //Direction.DOWN;
                    dir="down";
                }
                else if (difY>0){                  //Swipe hacia arriba
                    //Direction.UP;
                    dir="up";
                }
                return Gesture.SWIPE;
            }
            else if(Math.abs(difY) < CLICK_MARGIN   && Math.abs(difX) < CLICK_MARGIN){  //Click
                return Gesture.CLICK;
            }

            else{
                return  Gesture.NONE;
            }
        }
    }

    public Direction lastDirection(){
       /* switch (dir){
            case "up":
                return Direction.UP;
                break;
            case "down":
                return Direction.DOWN;
                break;
            case "left":
                return Direction.LEFT;
                break;
            case "right":
                return Direction.RIGHT;
                break;
            default:
                return  null;
        }*/
       if(dir=="up"){   return Direction.UP;    }
       if(dir=="down"){   return Direction.DOWN;    }
       if(dir=="left"){   return Direction.LEFT;    }
       else{   return Direction.RIGHT;    }

    }
}
