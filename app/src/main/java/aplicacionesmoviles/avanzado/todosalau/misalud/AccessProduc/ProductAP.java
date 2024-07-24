package aplicacionesmoviles.avanzado.todosalau.misalud.AccessProduc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import aplicacionesmoviles.avanzado.todosalau.misalud.DataBase.DataBaseHelper;
import aplicacionesmoviles.avanzado.todosalau.misalud.Producto.Producto;

public class ProductAP {
    private SQLiteDatabase db;
    private DataBaseHelper dbHelper;

    public ProductAP(Context context){dbHelper = new DataBaseHelper(context);}
    public void cerrar (){dbHelper.close();}
    public void abrir (){db= dbHelper.getWritableDatabase();}
    public Long insertarProduct(String nombre , Float price){
        ContentValues Values = new ContentValues();
        Values.put("nombre", nombre);
        Values.put("precio" ,price);
        return db.insert("productos",null,Values);
    }
    public List<Producto> obtnTodProductos(){
        List<Producto>productos=new ArrayList<>();
        Cursor cursor= db.rawQuery("SELECT* FROM productos",null);
        if(cursor.moveToFirst()){
            do{
                Producto producto = new Producto();
                producto.setId(String.valueOf(cursor.getInt(0)));
                producto.setName(cursor.getString(1));
                producto.setPrice(String.valueOf(cursor.getFloat(2)));
                productos.add(producto);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return productos;
    }
    public void  actualizarProducto(int id ,String name , String price){
        ContentValues values = new ContentValues();
        values.put("nombre", name);
        values.put("price", price);
        db.update("productos",values,"id=?",new String[]{String.valueOf(id)});

    }
    public void eliminarProducto(int id){
        db.delete("productos", "id=?",new String[]{String.valueOf(id)});
    }
}