package tarekmahedy.app.smallaccountant;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

//that the class of the slider pages slider
public class realswitcher extends ViewGroup {

	// TODO: This class does the basic stuff right now, but it would be cool to have certain things implemented,
	// e.g. using an adapter for getting views instead of setting them directly, memory management and the
	// possibility of scrolling vertically instead of horizontally. If you have ideas or patches, please visit

	public static interface OnScreenSwitchListener {

		void onScreenSwitched(int screenx,int screeny);
		
	}

	int childnumber=3;
	int movingpointer=0;
	int pointerdown=0;
	public int switchertypeleft=1;
	public int switchertypetop=1;
	public int blockrightside=0;
	private OnScreenSwitchListener mOnScreenSwitchListener=null;

	public static final int pointercount=2;
	private static final int SNAP_VELOCITY = 1500;
	private static final int SNAP_VELOCITYn = 1000;
	private static final int INVALID_SCREEN = -1;
	private Scroller mScroller;
	private VelocityTracker mVelocityTracker;
	public final static int TOUCH_STATE_REST = 0;
	public final static int TOUCH_STATE_SCROLLING = 1;

	public int mTouchState = TOUCH_STATE_REST;
	private float mLastMotionX;
	private float mLastMotionY;
	private int mTouchSlop;
	private int mMaximumVelocity;
	private int mCurrentScreen=0;
	private int mCurrentScreeny=0;
	private int pagehight;
	public int pagewidth;
	private int mNextScreen = INVALID_SCREEN;
	private boolean mFirstLayout = true;
	public int switchervalue=1;
	//private int blockrl=0;
	private int blocktb=0;
	public int blockright=0;
	private Boolean blockall=false;
	int touchdown=0;
	int movedstart=0;
	int down=0;
	int enterd=0;
	int slidepart=50;
	
	Boolean mainswitcher=false;
    int lang=1;
	//int langdir=Integer.valueOf(getContext().getString(R.string.langdir));
	int langdir=2;
	public realswitcher(Context context) {
		super(context);
		setBackgroundColor(Color.rgb(245, 240, 235));
		init();
	}

	public static int blockmovment=0;

	public realswitcher(Context context, AttributeSet attrs,int _lang) {
		super(context, attrs);
		int langd=Integer.valueOf(getContext().getString(R.string.langdir));
        if(langd==3)slidepart=slidepart*-1;
      	init();
	}



	private void init() {

		mScroller = new Scroller(getContext());
		final ViewConfiguration configuration = ViewConfiguration.get(getContext());
		mTouchSlop = configuration.getScaledTouchSlop();
		mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		lang=Integer.valueOf(getContext().getString(R.string.langdir));
		  
		final int width = MeasureSpec.getSize(widthMeasureSpec);

		final int height = MeasureSpec.getSize(heightMeasureSpec);

		pagehight=height;
		pagewidth=width;
		//slidepart= pagewidth/5;
		final int widthMode = MeasureSpec.getMode(widthMeasureSpec);

		if (widthMode != MeasureSpec.EXACTLY) {
			throw new IllegalStateException("ViewSwitcher can only be used in EXACTLY mode.");
		}

		final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		if (heightMode != MeasureSpec.EXACTLY) {
			throw new IllegalStateException("ViewSwitcher can only be used in EXACTLY mode.");
		}

		// The children are given the same width and height as the workspace
		final int count = getChildCount();

		for (int i = 0; i < count; i++) {
          
          getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);

		}

		//if(lang==2)
		//	mScroller.setFinalX(pagewidth-slidepart);
     

	}



	// 
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		lang=Integer.valueOf(getContext().getString(R.string.langdir));
	       
		
		final int count = getChildCount();
		ViewGroup gchild=this;
          
		if(count==childnumber){
			blockall=true;
			
		        int a=0;
				while(a<count){
					gchild=(ViewGroup)getChildAt(a);
					int xv=pagewidth*a;
					gchild.layout(xv,0,xv+pagewidth,pagehight);
					a++;
				}
						
			
			 
		}
		
	}




	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {

		if(blockmovment==1)
			return super.dispatchTouchEvent(ev);

		if(switchervalue==0)return super.dispatchTouchEvent(ev);
		if(ev.getPointerCount()==pointercount) onTouchEvent(ev);
		else {
			if(ev.getAction()!=MotionEvent.ACTION_MOVE &&switchervalue!=0)onTouchEvent(ev);
			return  super.dispatchTouchEvent(ev);

		}
		return super.dispatchTouchEvent(ev);
	}


	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		
		if(blockmovment==1)return super.onTouchEvent(ev);
		if(blockall)return blockall;

		if (mVelocityTracker == null) {
			mVelocityTracker = VelocityTracker.obtain();
		}

		mVelocityTracker.addMovement(ev);

		final int action = ev.getAction();
		final float x =ev.getX(0);
		final float y=ev.getY(0);


		switch (action) {
		case MotionEvent.ACTION_DOWN:

			touchdown(ev,x,y);

			break;

		case MotionEvent.ACTION_MOVE:
			if((ev.getPointerCount()==pointercount)){

				touchmove(x,y);
			}
			return false;
			//break;

		case MotionEvent.ACTION_UP:

			touchup();

			break;

		case MotionEvent.ACTION_CANCEL:
			movingpointer=0;
			mTouchState = TOUCH_STATE_REST;
		}

		return true;

	}

	public void touchdown(MotionEvent ev,float x,float y){

		down=0;
		enterd=0;
		pointerdown+=ev.getPointerCount();
		Log.v("msg","msgdown: "+movedstart);

		if (!mScroller.isFinished()){  
			mScroller.abortAnimation();
		}

		// Remember where the motion event started

		mLastMotionX = x;
		mLastMotionY=y;
		mTouchState = mScroller.isFinished() ? TOUCH_STATE_REST : TOUCH_STATE_SCROLLING;

	}


	/////////////////////////////////////////////////////////

	public int getindex(int _indexrea){

		int realindex=_indexrea;


		return realindex;
	}

	public int getrightsidechildern(){

		if(blockrightside==1){
			int chilrighttop  = ((ViewGroup)getChildAt(1)).getChildCount();

			//		int childrightbott = ((ViewGroup)getChildAt((getChildCount()-1))).getChildCount();
			//
			if(mCurrentScreeny==0&&chilrighttop==0) return 1;
			//		else  if(mCurrentScreeny==1&&childrightbott==0)return 1;

		}return 0;

	}

	public void touchmove(float x,float y){

		movingpointer=2;
		pointerdown=2;
		movedstart=1;

		final int xDiff = (int) Math.abs(x - mLastMotionX);
		final int yDiff = (int) Math.abs(y - mLastMotionY);

		boolean xMoved = xDiff > mTouchSlop;
		boolean yMoved = yDiff > mTouchSlop;

		if (xMoved||yMoved) {
			// Scroll if the user moved far enough along the X axis
			mTouchState = TOUCH_STATE_SCROLLING;
		}

		if (mTouchState == TOUCH_STATE_SCROLLING) {

			if((xDiff>yDiff && down==0)||(enterd==1&&down==0)){

				enterd=1;
				down=0;
				if(getrightsidechildern()!=0)return;

				final int deltaX = (int) (mLastMotionX - x);
				mLastMotionX = x;

				final int scrollX = getScrollX();

				if (deltaX < 0) {
					if (scrollX > 0) {
						scrollBy(Math.max(-scrollX, deltaX),0);
					}
				} else if (deltaX > 0) {
					final int availableToScroll = getChildAt((getChildCount()-1)).getRight() - scrollX - getWidth();
					if (availableToScroll > 0) {
						scrollBy(Math.min(availableToScroll, deltaX), 0);

					}
				}

			}
			else if((down==1&&enterd==1)||enterd==0){

				enterd=1;
				down=1;

				if(blocktb!=0)return;

				final int deltaY = (int) (mLastMotionY - y);
				mLastMotionY = y;
				final int scrolly = getScrollY();
				if (deltaY < 0) {
					if (scrolly > 0) {
						scrollBy(0,Math.max(-scrolly, deltaY));
					}
				} else if (deltaY > 0) {
					final int availableToScroll = getChildAt((getChildCount()-1)).getBottom() - scrolly - getHeight();
					if (availableToScroll > 0) {
						scrollBy(0, Math.min(availableToScroll, deltaY));
					}
				}


			}



		}	
	}

	public void touchup(){


		if (mTouchState == TOUCH_STATE_SCROLLING) {

			final VelocityTracker velocityTracker = mVelocityTracker;

			velocityTracker.computeCurrentVelocity(500, mMaximumVelocity);
			int velocityX = (int) velocityTracker.getXVelocity();
			int velocityY = (int) velocityTracker.getYVelocity();

			if(down==0){
				if(getrightsidechildern()!=0)return;
				if (velocityY > SNAP_VELOCITY &&getrightsidechildern()==0) {
					// Fling hard enough to move left
					snapToScreen(mCurrentScreen - 1);
				} else if (velocityY < -SNAP_VELOCITYn &&getrightsidechildern()==0) {
					// Fling hard enough to move right
					snapToScreen(mCurrentScreen + 1);
				}
				else snapToDestination();


			}else {
				if(blocktb!=0)return;
				if (velocityX > SNAP_VELOCITY && mCurrentScreeny > 0&&blocktb==0) {
					// Fling hard enough to move left
					snapToScreeny(mCurrentScreeny + 1);
				} else if (velocityX < -SNAP_VELOCITY && mCurrentScreeny < 1&&blocktb==0) {
					// Fling hard enough to move right
					snapToScreeny(mCurrentScreeny - 1);
				}
				else  snapToDestinationy();

			}

			if (mVelocityTracker != null) {

				mVelocityTracker.recycle();
				mVelocityTracker = null;
			}

			down=0;
			enterd=0;
		}


		snapToDestination();
		snapToDestinationy();
		mTouchState = TOUCH_STATE_REST;
		movedstart=0;

	}

	private void snapToDestination() {

		final int screenWidth = getWidth();

		final int whichScreen = (getScrollX() + (screenWidth / 2 )) / screenWidth;
		snapToScreen(whichScreen);

	}


	private void snapToDestinationy() {

		final int screenhieght = getHeight();

		final int whichScreen = (getScrollY() + (screenhieght / 2)) / screenhieght;
		snapToScreeny(whichScreen);

	}


	public void snapToScreen(int whichScreen) {

		if (!mScroller.isFinished())
			return;

		whichScreen = Math.max(0, Math.min(whichScreen, (getChildCount()-1)));
		
		mCurrentScreen=whichScreen;
		int newX = whichScreen * getWidth();

		int oldx=getScrollX();
		final int newy = mCurrentScreeny * getHeight();
		 int delta = newX - getScrollX();

		if(switchervalue==0){
			oldx=oldx-(whichScreen*slidepart);
			if(oldx<0){delta+=oldx; oldx=0;}
		}
        int durat= (int)(Math.abs(delta) * 2);
		mScroller.startScroll(oldx, newy, delta,0,durat);
		
		invalidate();
		if(mOnScreenSwitchListener!=null)
			mOnScreenSwitchListener.onScreenSwitched(mCurrentScreen,mCurrentScreeny);


	}

	public void snapToScreeny(int whichScreen) {

		if (!mScroller.isFinished())
			return;


		whichScreen = Math.max(0, Math.min(whichScreen,1));
		mCurrentScreeny=whichScreen;

		final int newX = getWidth()-slidepart;
		final int deltay = getScrollY();

		mScroller.startScroll(newX,getScrollY(),0,deltay, Math.abs(deltay) * 1);

		invalidate();
		if(mOnScreenSwitchListener!=null)
			mOnScreenSwitchListener.onScreenSwitched(mCurrentScreen,mCurrentScreeny);

	}


	public void setcurrenlayout(int current,int sttop,int i){

		ViewGroup gchild=(ViewGroup) getChildAt(i);
		final int gchildheight = getHeight();
		final int gchildWidth = getWidth();
		int stleft=current*gchildWidth;
		gchild.layout(stleft,sttop,stleft+gchildWidth,sttop+gchildheight);

	}


	public void setcurrenlayouty(int currenty,int stleft,int i){

		ViewGroup gchild;
		gchild=(ViewGroup) getChildAt(i);
		final int gchildheight = getHeight();
		final int gchildWidth = getWidth();

		int sttop=currenty*gchildheight;

		gchild.layout(stleft,sttop,stleft+gchildWidth,sttop+gchildheight);


	}

	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			postInvalidate();
		} else if (mNextScreen != INVALID_SCREEN) {
			//			mCurrentScreen = Math.max(0, Math.min(mNextScreen, 2));
			//
			//			// notify observer about screen change
			//			if (mOnScreenSwitchListener != null)
			//				mOnScreenSwitchListener.onScreenSwitched(mCurrentScreen);
			//
			//			mNextScreen = INVALID_SCREEN;
		}
	}

	/**
	 * Returns the index of the currently displayed screen.
	 * 
	 * @return The index of the currently displayed screen.
	 */

	public int getCurrentScreen() {
		return mCurrentScreen;
	}
	public int getCurrentScreeny() {
		return mCurrentScreeny;
	}


	/**
	 * Sets the current screen.
	 * 
	 * @param currentScreen The new screen.
	 */


	public void setCurrentScreen(int currentScreen) {

		mCurrentScreen = Math.max(0, Math.min(currentScreen,1));
		int oldx=mCurrentScreen * getWidth();

		scrollTo(oldx, mCurrentScreeny*getHeight());

		invalidate();
	}



	public void setCurrentScreeny(int currentScreen) {

		mCurrentScreeny = Math.max(0, Math.min(currentScreen, (getChildCount()-1)));
		int oldx=mCurrentScreen * getWidth();

		scrollTo(oldx, mCurrentScreeny*getHeight());

		invalidate();
	}



	public void setOnScreenSwitchListener(OnScreenSwitchListener onScreenSwitchListener) {
		mOnScreenSwitchListener = onScreenSwitchListener;
	}


}
