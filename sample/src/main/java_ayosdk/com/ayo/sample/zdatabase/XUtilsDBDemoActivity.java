package com.ayo.sample.zdatabase;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.ayoview.sample.ztmpl_listview.TmplAdapter;
import com.ayoview.sample.ztmpl_listview.TmplBean;
import com.cowthan.sample.BaseActivity;
import com.cowthan.sample.R;

import org.ayo.Ayo;
import org.ayo.app.OnViewClickListener;
import org.ayo.jlog.JLog;
import org.ayo.lang.Lang;
import org.ayo.notify.Toaster;
import org.xutils.common.util.DbException;
import org.xutils.db.DbManager;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.db.table.TableEntity;
import org.xutils.db.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/26.
 */
public class XUtilsDBDemoActivity extends BaseActivity implements OnViewClickListener<TmplBean> {

    TextView tv_receiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_db_xutils);

        Button btn_post = findViewById(R.id.btn_post);
        tv_receiver = findViewById(R.id.tv_reveiver);

        testDB_query();

        findViewById(R.id.btn_reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    getDB().dropTable(Emp.class);
                } catch (DbException e) {
                    e.printStackTrace();
                }

                testDB_query();
            }
        });

        findViewById(R.id.btn_insert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testDB_add();
            }
        });

        findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testDB_update();
            }
        });

        findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testDB_delete();
            }
        });



    }

    private DbManager getDB(){
        DbManager.DaoConfig dbConfig = new DbManager.DaoConfig();
        dbConfig.setAllowTransaction(true);
        dbConfig.setDbDir(new File(Ayo.ROOT));
        dbConfig.setDbName("a2016.db");
        dbConfig.setDbVersion(1);
        dbConfig.setTableCreateListener(new DbManager.TableCreateListener() {
            @Override
            public void onTableCreated(DbManager db, TableEntity<?> table) {
                JLog.i("数据库创建完事了");
            }
        });
        dbConfig.setDbUpgradeListener(new DbManager.DbUpgradeListener() {
            @Override
            public void onUpgrade(DbManager db, int oldVersion, int newVersion) {

            }
        });

        DbManager db = x.getDb(dbConfig);
        return db;
    }

    private void testDB_add(){
        DbManager db = getDB();
        Emp emp = new Emp();
        try {
            db.save(emp);
            JLog.i("插入数据完事了");
        } catch (DbException e) {
            e.printStackTrace();
        }

        testDB_query();
    }

    private void testDB_update(){
        DbManager db = getDB();
        Emp emp; // = new Emp();
        try {
            emp = db.selector(Emp.class).findFirst();
            emp.name = "name new";
            db.saveOrUpdate(emp);
            JLog.i("更新数据完事了");

        } catch (DbException e) {
            e.printStackTrace();
        }

        testDB_query();
    }

    private void testDB_delete(){
        DbManager db = getDB();
        Emp emp; // = new Emp();
        try {
            emp = db.selector(Emp.class).findFirst();
            db.delete(Emp.class, WhereBuilder.b("id", "=", emp.id));
        } catch (DbException e) {
            e.printStackTrace();
        }

        testDB_query();
    }

    private void testDB_query(){
        DbManager db = getDB();
        try {
            List<Emp> list = db.selector(Emp.class).findAll();
            if(Lang.isEmpty(list)){
                Toaster.toastShort("没数据");
                TmplAdapter adapter = new TmplAdapter(getActivity(), null, this);
                ListView lv_list = findViewById(R.id.lv_list);
                lv_list.setAdapter(adapter);

            }else{
                List<TmplBean> ts = new ArrayList<TmplBean>();
                for(Emp e: list){
                    TmplBean t = new TmplBean();
                    t.title = e.name;
                    ts.add(t);
                }
                TmplAdapter adapter = new TmplAdapter(getActivity(), ts, this);
                ListView lv_list = findViewById(R.id.lv_list);
                lv_list.setAdapter(adapter);
            }


        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onViewClicked(int position, TmplBean tmplBean, View targetView) {

    }
}
