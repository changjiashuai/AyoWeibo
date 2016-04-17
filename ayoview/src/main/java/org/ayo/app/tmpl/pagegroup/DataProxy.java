package org.ayo.app.tmpl.pagegroup;

/**
 * Created by Administrator on 2016/4/6.
 */
/**
 * ���ݼ��ػ���Ҳ�����߼��ģ�
 * ��ʱ���أ��������Ƿ���ҳ���Ƿ��һ�ν�������setUserVisibleHint
 * ��ô���أ��Ƿ���Ҫ���ػ���
 * ���ع�����ô��ʾ�������ڵ�ǰ�Ƿ�������
 *
 * T��ISubPage
 *
 * �����ľ������ڣ�һ����ҳ������ݽӿ���κܶ࣬���Ҽ���ʱ��������ͬ�������ﴦ������
 *
 *
 *
 */
@Deprecated
public abstract class DataProxy<T extends ISubPage>{

    /**
     * �Ƿ��һ�ν����ҳ��
     */
    public boolean isFirstCome = true;

    /**
     * ��ҳ���Ƿ���ҳ�����Ƿ���Ҫֱ�ӳ��ָ��û�
     */
    public boolean isTheFirstPage = false;


    /**
     * �����ISubPageʵ��Ӧ���ṩһ��refresh�ӿڣ����������Լ�����Ȥ��entiry���ͣ������û���Ҫ�Լ�֪�����Ķ�����
     * ��Ӧ���ṩ����Ϊ�գ��������ʱ�Ľӿ�
     *
     * ���Ӧ�ò��Ǳ������Ҫ���ǵ������ˣ���Ҫ��λ�����Լ���UIģ���Presenter
     *
     * ����������Ե��˴�ס
     *
     */
    public abstract void loadData(T page);
}
