package aplicacionesmoviles.avanzado.todosalau.misalud.Producto;

import java.util.HashMap;
import java.util.Map;

public class Producto {
    private String id;
    private String name;
    private  double price;
    private  boolean deleted;

    public Producto(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;

    }
      public Producto(){

      }

    public Producto(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return (int) price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    public Map<String,Object> toMap(){
        Map<String , Object> resultado = new HashMap<>();
        resultado.put("id" , id);
        resultado.put("name" , name);
        resultado.put("price", price);
        return resultado;


    }
}
