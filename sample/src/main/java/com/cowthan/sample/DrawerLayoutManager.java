package com.cowthan.sample;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

/**
 * 还没加入ActionBar相关的功能
 * ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
 //                R.drawable.ic_drawer, R.string.drawer_open,
 //                R.string.drawer_close)
 //        {
 //
 //            public void onDrawerClosed(View view)
 //            {
 //
 //                //invalidateOptionsMenu(); // creates call to
 //                // onPrepareOptionsMenu()
 //            }
 //
 //            public void onDrawerOpened(View drawerView)
 //            {
 //
 //                //invalidateOptionsMenu(); // creates call to
 //                // onPrepareOptionsMenu()
 //            }
 //        };

 //getActionBar().setDisplayHomeAsUpEnabled(true);
 // getActionBar().setHomeButtonEnabled(true);
 */
public class DrawerLayoutManager{

    private DrawerLayout drawerLayout;
    private int gravity;
    private DrawerLayout.DrawerListener drawerListener;

    private DrawerLayoutManager(){

    }

    /**
     * 初始化一个DrawerLayoutManager，并指定滑动方向
     * @param drawerLayout
     * @param gravity  GravityCompat.START, END
     */
    private DrawerLayoutManager(DrawerLayout drawerLayout, int gravity, DrawerLayout.DrawerListener drawerListener){
        this.drawerLayout = drawerLayout;
        this.gravity = gravity;
        this.drawerListener = drawerListener;
        this.drawerLayout.setDrawerListener(drawerListener);

        //要设置DrawerLayout的方向，要给DrawerLayout的第二个view设置一个android_gravity
        int childCount = drawerLayout.getChildCount();
        if(childCount != 2){
            throw new RuntimeException("DrawerLayout必须被设置了一个MarginLayoutParams");
        }

        DrawerLayout.LayoutParams lp = (DrawerLayout.LayoutParams) drawerLayout.getChildAt(1).getLayoutParams();
        lp.gravity = gravity;
        drawerLayout.getChildAt(1).setLayoutParams(lp);
    }

    public static DrawerLayoutManager attachLeft(DrawerLayout drawerLayout, DrawerLayout.DrawerListener drawerListener){
        DrawerLayoutManager dm = new DrawerLayoutManager(drawerLayout, GravityCompat.START, drawerListener);
        return dm;
    }

    public static DrawerLayoutManager attachRight(DrawerLayout drawerLayout, DrawerLayout.DrawerListener drawerListener){
        DrawerLayoutManager dm = new DrawerLayoutManager(drawerLayout, GravityCompat.END, drawerListener);
        return dm;
    }

    public DrawerLayout getDrawerLayout(){
        return drawerLayout;
    }

    public boolean isOpen(){
        return drawerLayout.isDrawerOpen(gravity);
    }

//        public boolean isOpeningLeft(){
//            return isOpen(GravityCompat.START);
//        }
//
//        public boolean isOpeninRight(){
//            return isOpen(GravityCompat.END);
//        }

    public void toggle(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

//        public void toggleLeft(){
//            toggle(GravityCompat.START);
//        }
//
//        public void toggleRight(){
//            toggle(GravityCompat.END);
//        }

    /**
     * 把drawerLayout锁定在当前状态，无法再通过手指动作来控制开关，只能通过代码控制，toggle()
     */
    public void lockInCurrentMode(){
        if(drawerLayout.isDrawerOpen(gravity)){
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
        }else{
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }

    /**
     * 将DrawerLayout锁定在开启状态，如果当前未开启，会开启，无法再通过手指动作来控制开关，只能通过代码控制，toggle()
     */
    public void lockOpen(){
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
    }

    /**
     * 将DrawerLayout锁定在关闭状态，如果当前未关闭，会关闭，无法再通过手指动作来控制开关，只能通过代码控制，toggle()
     */
    public void lockClose(){
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    /**
     * 解除锁定，但不会改变当前开关状态
     */
    public void unlock(){
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

}
