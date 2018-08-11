package com.airom.jigsaw_puzzle.GameActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.airom.jigsaw_puzzle.BaseActivity;
import com.airom.jigsaw_puzzle.ModeActivity.ModeActivity;


/**
 * Created by zyw on 2017/10/18.
 */
public class MainView extends View {
    private static final String TAG ="MainView" ;
    private Context context;
    private Bitmap img;
    private Paint paint;
    private int tileWidth;
    private int tileHeight;
    private Bitmap[] bitmapTiles;
    private int[][] dataTiles;
    private Board tilesBoard;
    private int COL;
    private int ROW;
    private int[][] dir={
            {-1,0},//左
            {0,-1},//上
            {1,0},//右
            {0,1}//下
    };
    private  boolean isSuccess;

    public MainView(Context context)
    {
        this(context,null);
    }

    public MainView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public MainView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        paint=new Paint();
        paint.setAntiAlias(true);
        init();
        startGame();
        log(GameActivity.getScreenWidth()+","+ GameActivity.getScreenHeight());
    }


    /**
     * 初始化
     */
    private  void init()
    {
//        img=Bitmap.createScaledBitmap(ModeActivity.getImg(),GameActivity.getScreenWidth(),GameActivity.getScreenWidth(),true);
        img = ModeActivity.getImg();
        COL = ModeActivity.getCOL();
        ROW = ModeActivity.getROW();
        tileWidth=img.getWidth()/COL;
        tileHeight=img.getHeight()/ROW;
        bitmapTiles =new Bitmap[COL*ROW];
        int idx=0;
        for(int i=0;i<ROW;i++)
        {
            for(int j=0;j<COL;j++)
            {
                bitmapTiles[idx++]=Bitmap.createBitmap(img,j*tileWidth,i*tileHeight,tileWidth,tileHeight);
            }
        }

    }

    /**
     * 开始游戏
     */
    private void startGame()
    {
        tilesBoard =new Board();
        dataTiles= tilesBoard.createRandomBoard(ROW,COL);
        isSuccess=false;
        invalidate();

    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawColor(Color.GRAY);
        for(int i=0;i<ROW;i++) {
            for (int j = 0; j < COL; j++) {
                int idx=dataTiles[i][j];
                if(idx==ROW*COL-1&&!isSuccess)
                    continue;
                canvas.drawBitmap(bitmapTiles[idx],j*tileWidth,i*tileHeight,paint);
            }
        }
    }

    /**
     * 将屏幕上的点转换成,对应拼图块的索引
     * @param x
     * @param y
     * @return
     */
    private Point xyToIndex(int x,int y)
    {
        int extraX=x%tileWidth>0?1:0;
        int extraY=x%tileWidth>0?1:0;
        int col=x/tileWidth+extraX;
        int row=y/tileHeight+extraY;

        return new Point(col-1,row-1);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN) {

            Point point = xyToIndex((int) event.getX(), (int) event.getY());

            for(int i=0;i<dir.length;i++)
            {
                int newX=point.getX()+dir[i][0];
                int newY=point.getY()+dir[i][1];

                if(newX>=0&&newX<COL&&newY>=0&&newY<ROW){
                    if(dataTiles[newY][newX]==COL*ROW-1) {//如果移动了某个方块
                        GameActivity.countSteps();
                        BaseActivity.playSound();
                        int temp=dataTiles[point.getY()][point.getX()];
                        dataTiles[point.getY()][point.getX()]=dataTiles[newY][newX];
                        dataTiles[newY][newX]=temp;
                        invalidate();
                        if(tilesBoard.isSuccess(dataTiles)){
                            isSuccess=true;
                            invalidate();

                            new AlertDialog.Builder(context)
                                    .setTitle("拼图成功^_^")
                                    .setCancelable(false)
                                    .setMessage("恭喜你拼图成功(〃'▽'〃)"+"\n用时："+GameActivity.getTime()+"毫秒\n步数："+GameActivity.getSteps()+"步")
                                    .setPositiveButton("再来一局", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            startGame();
                                            GameActivity.resetSteps();
                                            GameActivity.resetTime();
                                        }
                                    })
                                    .setNegativeButton("退出游戏", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            System.exit(0);
                                        }
                                    })
                                    .create()
                                    .show();
                        }
                    }
                }
            }
        }
        return true;
    }



    private  void log(String log){
        System.out.println("------------------->MainView:"+log);
    }


    private  void printArray(int[][] arr){
        StringBuilder sb=new StringBuilder();
        for (int i=0;i<arr.length;i++) {
            for (int j=0;j<arr[i].length;j++) {
                sb.append(arr[i][j]+",");
            }
            sb.append("\n");
        }
        log(sb.toString());
    }

}
