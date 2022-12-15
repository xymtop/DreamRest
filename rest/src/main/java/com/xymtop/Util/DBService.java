package com.xymtop.Util;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DBService {

    public static Boolean InsertObject(Object object, String form)
            throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException {
        MyDB myDB = new MyDB();
        Connection conn = myDB.Conn();
        PreparedStatement ptm = null;
        Boolean res = false;

        Class c = Class.forName(object.getClass().getCanonicalName());

        Field[] fields = c.getDeclaredFields();

        String sql = "insert into  " + form + " (";
        for (Field field : fields) {
            if(field.getName().equals("classid")){
                sql = sql + "`" + "class" + "`,";
            }else {
                sql = sql + "`" + field.getName() + "`,";
            }

        }
        sql = sql.substring(0, sql.length() - 1);
        sql = sql + ") values (";

        for (Field f : fields) {
            // if(f.getName().equals("id")){
            // sql = sql + "'"+new Date().getTime()+"',";
            // }else{

            sql = sql + "'" + f.get(object) + "',";
            // }

        }
        sql = sql.substring(0, sql.length() - 1);
        sql = sql + ")";

        System.out.println(sql);

        try {
            ptm = (PreparedStatement) conn.prepareStatement(sql);
            int value = ptm.executeUpdate();
            if (value == 1) {
                res = true;
            } else {
                res = false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            myDB.close(conn, ptm);
        }

        return res;
    }

    public static Boolean DelObject(Object object, String form)
            throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException {
        MyDB help = new MyDB();
        Connection conn = help.Conn();
        PreparedStatement ptm = null;
        Boolean res = false;

        Class c = Class.forName("com.xymtop.Vo.StuVo");

        Field[] fields = c.getDeclaredFields();

        String sql = "delete from  " + form + "  where";

        for (Field f : fields) {

            sql = sql + " `" + f.getName() + "` = " + "'" + f.get(object) + "' and ";
        }
        sql = sql.substring(0, sql.length() - 4);
        // sql = sql + ")";

        System.out.println(sql);

        try {
            ptm = (PreparedStatement) conn.prepareStatement(sql);
            int value = ptm.executeUpdate();
            if (value == 1) {
                res = true;
            } else {
                res = false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            help.close(conn, ptm);
        }

        return res;
    }


    public static Boolean DelObjectById(String id, String form)
            throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException {
        MyDB help = new MyDB();
        Connection conn = help.Conn();
        PreparedStatement ptm = null;
        Boolean res = false;




        String sql = "delete from  " + form + " where id = " + id;


        System.out.println(sql);

        try {
            ptm = (PreparedStatement) conn.prepareStatement(sql);
            int value = ptm.executeUpdate();
            if (value == 1) {
                res = true;
            } else {
                res = false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            help.close(conn, ptm);
        }

        return res;
    }

    // 查询操作
    public static List<Object> Get(String sql, String returnType)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        MyDB help = new MyDB();
        Connection conn = help.Conn();
        PreparedStatement pst = null;
        Class c = Class.forName(returnType);
        Field[] fields = c.getDeclaredFields();
        List<Object> list = new ArrayList<>();
        try {
            pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            int Count = 0;
            Object[] objects = new Object[100];
            for (int i = 0; i < objects.length; i++) {
                objects[i] = c.newInstance();
            }
            while (rs.next()) {
                list.add(objects[Count]);
                for (Field f : fields) {

                    if(f.getName().equals("classid")){
                        f.set(list.get(Count), rs.getString("class"));
                    }else {
                        f.set(list.get(Count), rs.getString(f.getName()));
                    }

                }
                Count++;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            help.close(conn, pst);
        }
        return list;
    }

    // 查询操作
    public static List<Object> GetList(Object object, String form)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        MyDB help = new MyDB();
        Connection conn = help.Conn();
        PreparedStatement pst = null;
        Class c = Class.forName(object.getClass().getCanonicalName());

        Field[] fields = c.getDeclaredFields();
        List<Object> list = new ArrayList<Object>();
        String sql = "select * from " + form + " where ";

        for (Field f : fields) {
            if (f.get(object) != null) {
//                System.out.println(f.getName() + f.get(object));
                sql = sql + f.getName().toString() + " = '" + f.get(object).toString() + "' and ";
            }
        }
        sql = sql.substring(0, sql.length() - 4);
        System.out.println(sql);
        try {
            pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if(rs != null) {


                int Count = 0;
                Object[] objects = new Object[100];
                for (int i = 0; i < objects.length; i++) {
                    objects[i] = c.newInstance();
                }
                while (rs.next()) {
                    list.add(objects[Count]);
                    for (Field f : fields) {

                        f.set(list.get(Count), rs.getString(f.getName()));
                    }
                    Count++;

                }
            }else {
                return  null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            help.close(conn, pst);
        }
        return list;
    }

    // 更新操作
    public static Boolean Update(Object object, String form)
            throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        MyDB help = new MyDB();
        Connection conn = help.Conn();
        PreparedStatement ptm = null;
        Boolean res = false;

        Class c = Class.forName(object.getClass().getCanonicalName());

        Field[] fields = c.getDeclaredFields();

        String sql = "update " + form + " set ";

        for (Field f : fields) {
            if (f.get(object) != null) {
                if (!f.getName().equals("id")) {
                    sql = sql + f.getName().toString() + " = '" + f.get(object).toString() + "',";
                }
            }

        }
        sql = sql.substring(0, sql.length() - 1);
        sql = sql + " where id = '" + fields[0].get(object) + "'";
        System.out.println(sql);

        try {
            ptm = (PreparedStatement) conn.prepareStatement(sql);
            int value = ptm.executeUpdate();
            if (value == 1) {
                res = true;
            } else {
                res = false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            help.close(conn, ptm);
        }

        return res;
    }

    public static int execSql(String sql){
        MyDB help = new MyDB();
        Connection conn = help.Conn();
        PreparedStatement ptm = null;
        int value = 0;
        try {
            ptm = (PreparedStatement) conn.prepareStatement(sql);
             value = ptm.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            help.close(conn, ptm);
        }
        return value;
    }


}
